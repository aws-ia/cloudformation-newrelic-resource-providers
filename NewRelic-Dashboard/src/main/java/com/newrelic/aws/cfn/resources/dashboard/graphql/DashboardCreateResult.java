package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DashboardCreateResult {
    @JsonProperty("entityResult")
    private DashboardEntityResult entityResult;

    @JsonProperty("errors")
    private List<DashboardCreateError> errors;

    public DashboardEntityResult getEntityResult() {
        return entityResult;
    }

    public List<DashboardCreateError> getErrors() {
        return errors;
    }
}
