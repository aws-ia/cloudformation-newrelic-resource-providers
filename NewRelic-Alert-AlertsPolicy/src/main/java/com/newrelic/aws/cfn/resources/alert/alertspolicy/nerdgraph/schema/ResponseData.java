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
    private AlertsPolicyResult alertCreateResult;

    @JsonProperty("alertsPolicyUpdate")
    private AlertsPolicyResult alertUpdateResult;

    @JsonProperty("alertsPolicyDelete")
    private AlertsPolicyResult alertsPolicyDeleteResult;

    @JsonProperty("actor")
    private Actor<T> actor;
}
