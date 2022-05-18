package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DashboardDeleteResult {

    @JsonProperty("status")
    private DashboardDeleteResultStatus status;

    @JsonProperty("errors")
    private List<DashboardDeleteError> errors;

    public DashboardDeleteResultStatus getStatus() {
        return status;
    }

    public List<DashboardDeleteError> getErrors() {
        return errors;
    }

}
