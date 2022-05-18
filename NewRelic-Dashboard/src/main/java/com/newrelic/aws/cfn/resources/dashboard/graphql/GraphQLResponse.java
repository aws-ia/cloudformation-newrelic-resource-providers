package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GraphQLResponse {

    @JsonProperty("data")
    private GraphQLResponseData graphQLResponseData;

    @JsonProperty("errors")
    private List<GraphQLResponseError> graphQLResponseError;

    public GraphQLResponseData getGraphQLResponseData() {
        return graphQLResponseData;
    }

    public List<GraphQLResponseError> getGraphQLResponseError() {
        return graphQLResponseError;
    }

}
