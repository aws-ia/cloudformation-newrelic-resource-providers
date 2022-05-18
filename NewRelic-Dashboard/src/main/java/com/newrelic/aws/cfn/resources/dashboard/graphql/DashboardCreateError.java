package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardCreateError {
    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    private DashboardCreateErrorType type;

    public String getDescription() {
        return description;
    }

    public DashboardCreateErrorType getType() {
        return type;
    }
}
