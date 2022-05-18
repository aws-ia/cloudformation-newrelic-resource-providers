package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardDeleteError {
    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    private DashboardDeleteErrorType type;

    public String getDescription() {
        return description;
    }

    public DashboardDeleteErrorType getType() {
        return type;
    }
}
