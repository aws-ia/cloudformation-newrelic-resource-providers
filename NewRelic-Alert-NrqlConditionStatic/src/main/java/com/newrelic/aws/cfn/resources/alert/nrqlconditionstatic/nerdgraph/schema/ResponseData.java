package com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema;

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
public class ResponseData<T extends NrqlConditionStaticResult> {
    @JsonProperty("alertsNrqlConditionStaticCreate")
    private NrqlConditionStaticResult alertCreateResult;

    @JsonProperty("alertsNrqlConditionStaticUpdate")
    private NrqlConditionStaticResult alertUpdateResult;

    @JsonProperty("alertsConditionDelete")
    private NrqlConditionStaticResult alertDeleteResult;

    @JsonProperty("actor")
    private Actor<T> actor;
}
