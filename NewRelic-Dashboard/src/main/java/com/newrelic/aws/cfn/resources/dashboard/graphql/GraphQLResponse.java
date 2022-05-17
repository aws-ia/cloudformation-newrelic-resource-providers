package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GraphQLResponse {

    @JsonProperty("data")
    private GraphQLResponseData graphQLResponseData;

    public GraphQLResponseData getGraphQLResponseData() {
        return graphQLResponseData;
    }
}
