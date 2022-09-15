package com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.ApmSettings;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.Response;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.ResponseData;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.ResponseError;
import software.amazon.cloudformation.exceptions.CfnHandlerInternalFailureException;
import software.amazon.cloudformation.exceptions.CfnNetworkFailureException;
import software.amazon.cloudformation.exceptions.CfnServiceInternalErrorException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class NerdGraphClient {

    private final String userAgent;

    public NerdGraphClient() {
        this.userAgent = "AWS CloudFormation (+https://aws.amazon.com/cloudformation/) CloudFormation custom resource";
    }

    public NerdGraphClient(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getGraphQLTemplate(String name) throws CfnHandlerInternalFailureException {
        try {
            URL resource = Resources.getResource("graphql/" + name);
            return Resources.toString(resource, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CfnHandlerInternalFailureException(new Exception(String.format("Fail to retrieve the GraphQL template \"%s\"", name), e));
        }
    }

    public String genGraphQLArg(Object instance) {
        return genGraphQLArg(instance, ImmutableList.of());
    }

    public String genGraphQLArg(Object instance, Collection<String> packagePrefixesForRecursion) {
        // We need to maintain a list of enums fields, because GraphQL SDL expects enum values without quotes!
        List<String> enumFieldNames = ImmutableList.of("value", "explainThresholdType");
        Class<?> currentInstanceClass = instance.getClass();
        List<Field> fields = new ArrayList<>(Arrays.asList(currentInstanceClass.getDeclaredFields()));
        while (currentInstanceClass.getSuperclass() != null) {
            fields.addAll(Arrays.asList(currentInstanceClass.getSuperclass().getDeclaredFields()));
            currentInstanceClass = currentInstanceClass.getSuperclass();
        }

        StringJoiner schema = new StringJoiner(", ", "{", "}");
        // Allows for lists of simple types
        if (instance instanceof String) {
            schema.setEmptyValue("\"" + instance + "\"");
        }
        fields.stream()
                .filter(field -> field.isAnnotationPresent(JsonProperty.class))
                .collect(Collectors.toList())
                .forEach(field -> {
                    try {
                        Object value = new PropertyDescriptor(field.getName(), instance.getClass()).getReadMethod().invoke(instance);
                        if (value == null) {
                            return;
                        }
                        if (List.class.isAssignableFrom(value.getClass())) {
                            @SuppressWarnings("unchecked")
                            List<Object> list = (List<Object>) value;
                            schema.add(String.format("%1$s: %2$s", field.getName(), list.stream().map(this::genGraphQLArg).collect(Collectors.joining(", ", "[", "]"))));
                        } else if (Objects.equals(field.getType().getPackage().getName(), "com.newrelic.aws.cfn.resources.alert")) {
                            schema.add(String.format("%1$s: %2$s", field.getName(), genGraphQLArg(value)));
                        } else if (value instanceof String && !enumFieldNames.contains(field.getName())) {
                            schema.add(String.format("%1$s: \"%2$s\"", field.getName(), value));
                        } else if (packagePrefixesForRecursion.stream().anyMatch(s -> value.getClass().getName().startsWith(s))) {
                            schema.add(String.format("%1$s: %2$s", field.getName(), genGraphQLArg(value, packagePrefixesForRecursion)));
                        } else {
                            schema.add(String.format("%1$s: %2$s", field.getName(), value));
                        }
                    } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        return schema.toString();
    }

    public <T extends ApmSettings> ResponseData<T> doRequest(Class<T> resultType,
                                                             String endpoint,
                                                             String variables,
                                                             String apiKey,
                                                             String query)
            throws CfnNetworkFailureException, CfnServiceInternalErrorException {
        return doRequest(resultType, endpoint, variables, apiKey, query, ImmutableList.of());

    }

    public <T extends ApmSettings> ResponseData<T> doRequest(Class<T> resultType,
                                                                    String endpoint,
                                                                    String variables,
                                                                    String apiKey,
                                                                    String query,
                                                                    List<String> ignoreErrorClasses)
            throws CfnNetworkFailureException, CfnServiceInternalErrorException {
        Response<T> result;

        try {
            ObjectMapper mapper = new ObjectMapper();

            ImmutableMap<String, String> of = ImmutableMap.of(
                    "query", query,
                    "variable", variables);
            byte[] dataBytes = mapper.writeValueAsString(of).getBytes(StandardCharsets.UTF_8);

            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", this.userAgent);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("API-Key", apiKey);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.getOutputStream().write(dataBytes);

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; )
                sb.append((char) c);
            String response = sb.toString();

            result = mapper.readValue(response, mapper
                    .getTypeFactory()
                    .constructParametricType(Response.class, resultType));
        } catch (Exception ex) {
            throw new CfnNetworkFailureException("Request to NerdGraph API", ex);
        }

        if (result.getResponseError() != null) {
            List<ResponseError> errors = result.getResponseError().stream().filter(responseError -> {
               return !ignoreErrorClasses.contains(responseError.getExtension() == null ? "" : responseError.getExtension().getErrorClass());
            }).collect(Collectors.toList());
            if (errors.size() > 0) {
                throw new CfnServiceInternalErrorException(errors
                        .stream()
                        .map(responseError -> String.format("==> For path \"%s\": %s", responseError.getPath(), responseError.getMessage()))
                        .collect(Collectors.joining("\n", "The following error occurred while talking to New Relic NerdGraph API:\n", "")));
            }
        }

        return result.getResponseData();
    }
}
