package com.newrelic.aws.cfn.resources.dashboard;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import com.newrelic.aws.cfn.resources.dashboard.graphql.GraphQLResponse;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateHandler extends BaseHandler<CallbackContext> {

    private String NR_HOST = "https://api.eu.newrelic.com/graphql";
    private String NR_API_KEY = "NRAK-5SE8PNXSYWYDOGSHDYX5HBWMLH4";

    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(
            final AmazonWebServicesClientProxy proxy,
            final ResourceHandlerRequest<ResourceModel> request,
            final CallbackContext callbackContext,
            final Logger logger) {

        final ResourceModel model = request.getDesiredResourceState();

        String template = "";
        try {
            URL resource = Resources.getResource("graphql/dashboardCreate.mutation.template");
            template = Resources.toString(resource, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mutation = String.format(template, model.getAccountId(), getGraphQLArg(model.getDashboard()));

        try {
            ObjectMapper mapper = new ObjectMapper();

            ImmutableMap<String, String> of = ImmutableMap.of("query", mutation, "variable", "");
            byte[] dataBytes = mapper.writeValueAsString(of).getBytes(StandardCharsets.UTF_8);

            URL url = new URL(NR_HOST);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("API-Key", NR_API_KEY);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.getOutputStream().write(dataBytes);

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            // Manually converting the response body InputStream to APOD using Jackson
            GraphQLResponse result = mapper.readValue(response, GraphQLResponse.class);

            System.out.println(result.getGraphQLResponseData().getDashboardCreateResult().getEntityResult().getGuid());
            System.out.println(result.getGraphQLResponseData().getDashboardCreateResult().getEntityResult().getName());

            model.setDashboardCreateResult(result.getGraphQLResponseData().getDashboardCreateResult());
            model.setDashboardId(result.getGraphQLResponseData().getDashboardCreateResult().getEntityResult().getGuid());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        curl https://api.eu.newrelic.com/graphql \
//        -H 'Content-Type: application/json' \
//        -H 'API-Key: NRAK-5SE8PNXSYWYDOGSHDYX5HBWMLH4' \
//        --data-binary '{"query":"mutation {\n  dashboardCreate(accountId: 3495167, dashboard: {name: \"Economizr Dashboard\", description: \"This is a test dashboard for the CloudFormation integration\", pages: {description: \"Main page\", name: \"Live view\", widgets: {configuration: {line: {nrqlQueries: [{accountId: 3495167, query: \"SELECT count(*) FROM Transaction FACET appName TIMESERIES\"}]}}}}, permissions: PRIVATE}) {\n    entityResult {\n      guid\n      name\n      accountId\n      pages {\n        description\n        guid\n        name\n        widgets {\n          id\n          visualization {\n            id\n          }\n          rawConfiguration\n        }\n      }\n    }\n    errors {\n      description\n      type\n    }\n  }\n}\n", "variables":""}'

        return ProgressEvent.<ResourceModel, CallbackContext>builder()
                .resourceModel(model)
                .status(OperationStatus.SUCCESS)
                .build();
    }

//    @SuppressWarnings("unchecked")
    private String getGraphQLArg(Object instance) {
        // We need to maintain a list of enums fields, because GraphQL SDL expects enum values without quotes!
        List<String> enumFieldNames = ImmutableList.of("permissions", "visualization", "type", "facets", "alertSeverity");
        Field[] allFields = instance.getClass().getDeclaredFields();

        StringJoiner schema = new StringJoiner(", ", "{", "}");
        Arrays.stream(allFields)
                .filter(field -> field.isAnnotationPresent(JsonProperty.class))
                .collect(Collectors.toList())
                .forEach(field -> {
                    try {
                        Object value = new PropertyDescriptor(field.getName(), instance.getClass()).getReadMethod().invoke(instance);
                        if (value == null) {
                            return;
                        }
                        if (value instanceof List) {
                            @SuppressWarnings("unchecked")
                            List<Object> list = (List<Object>) value;
                            schema.add(String.format("%1$s: %2$s", field.getName(), list.stream().map(this::getGraphQLArg).collect(Collectors.joining(", ", "[", "]"))));
                        } else if (Objects.equals(field.getType().getPackage().getName(), "com.newrelic.aws.cfn.resources.dashboard")) {
                            schema.add(String.format("%1$s: %2$s", field.getName(), getGraphQLArg(value)));
                        } else if (value instanceof String && !enumFieldNames.contains(field.getName())) {
                            schema.add(String.format("%1$s: \"%2$s\"", field.getName(), value));
                        } else {
                            schema.add(String.format("%1$s: %2$s", field.getName(), value));
                        }
                    } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        return schema.toString();
    }
}
