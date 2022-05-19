package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import software.amazon.cloudformation.exceptions.CfnHandlerInternalFailureException;
import software.amazon.cloudformation.exceptions.CfnNetworkFailureException;
import software.amazon.cloudformation.exceptions.CfnServiceInternalErrorException;

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
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Util {

    public String getGraphQLTemplate(String name) {
        try {
            URL resource = Resources.getResource("graphql/" + name);
            return Resources.toString(resource, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CfnHandlerInternalFailureException(new Exception(String.format("Fail to retrieve the GraphQL template \"%s\"", name), e));
        }
    }

    public String genGraphQLArg(Object instance) {
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
                            schema.add(String.format("%1$s: %2$s", field.getName(), list.stream().map(this::genGraphQLArg).collect(Collectors.joining(", ", "[", "]"))));
                        } else if (Objects.equals(field.getType().getPackage().getName(), "com.newrelic.aws.cfn.resources.dashboard")) {
                            schema.add(String.format("%1$s: %2$s", field.getName(), genGraphQLArg(value)));
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

    public <T extends EntityResult> GraphQLResponseData<T> doRequest(Class<T> clazz, String endpoint, String variables, String apiKey, String query) {
        GraphQLResponse<T> result;

        try {
            ObjectMapper mapper = new ObjectMapper();

            ImmutableMap<String, String> of = ImmutableMap.of(
                    "query", query,
                    "variable",  variables);
            byte[] dataBytes = mapper.writeValueAsString(of).getBytes(StandardCharsets.UTF_8);

            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("API-Key", apiKey);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.getOutputStream().write(dataBytes);

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            result = mapper.readValue(response, mapper
                    .getTypeFactory()
                    .constructParametricType(GraphQLResponse.class, clazz));
        } catch (Exception ex) {
            throw new CfnNetworkFailureException("Fail to execute the request to NerdGraph API", ex);
        }

        if (result.getGraphQLResponseError() != null) {
            throw new CfnServiceInternalErrorException(result.getGraphQLResponseError()
                    .stream()
                    .map(graphQLResponseError -> String.format("==> For path \"%s\": %s", graphQLResponseError.getPath(), graphQLResponseError.getMessage()))
                    .collect(Collectors.joining("\n", "The following error occurred while talking to New Relic NerdGraph API:\n", "")));
        }

        return result.getGraphQLResponseData();
    }
}
