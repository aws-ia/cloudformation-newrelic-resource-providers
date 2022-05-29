package com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema;

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
public class ResponseData<T extends AlertsPolicyResult> {
    @JsonProperty("alertsPolicyCreate")
    private AlertsPolicyCreateResult alertCreateResult;

    @JsonProperty("alertsPolicyUpdate")
    private AlertsPolicyUpdateResult alertUpdateResult;

    @JsonProperty("alertsPolicyDelete")
    private AlertsPolicyDeleteResult alertsPolicyDeleteResult;

    @JsonProperty("actor")
    private Actor<T> actor;
}
