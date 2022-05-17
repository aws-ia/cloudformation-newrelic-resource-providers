package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newrelic.aws.cfn.resources.dashboard.DashboardCreateResult;

public class GraphQLResponseData {

    @JsonProperty("dashboardCreate")
    private DashboardCreateResult dashboardCreateResult;

    public DashboardCreateResult getDashboardCreateResult() {
        return dashboardCreateResult;
    }
}
