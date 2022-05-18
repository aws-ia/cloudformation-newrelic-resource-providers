package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GraphQLResponseData {

    @JsonProperty("dashboardCreate")
    private DashboardCreateResult dashboardCreateResult;

    @JsonProperty("dashboardDelete")
    private DashboardDeleteResult dashboardDeleteResult;

    public DashboardCreateResult getDashboardCreateResult() {
        return dashboardCreateResult;
    }

    public DashboardDeleteResult getDashboardDeleteResult() {
        return dashboardDeleteResult;
    }

}
