package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class GraphQLResponseData<T extends EntityResult> {
    @JsonProperty("dashboardCreate")
    private DashboardCreateResult dashboardCreateResult;

    @JsonProperty("dashboardUpdate")
    private DashboardUpdateResult dashboardUpdateResult;

    @JsonProperty("dashboardDelete")
    private DashboardDeleteResult dashboardDeleteResult;

    @JsonProperty("actor")
    private Actor<T> actor;
}
