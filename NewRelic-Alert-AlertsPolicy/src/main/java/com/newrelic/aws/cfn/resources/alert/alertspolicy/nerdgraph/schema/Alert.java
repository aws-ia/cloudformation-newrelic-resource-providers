package com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Alert {
    @JsonProperty("policy")
    private AlertsPolicySearchResult alertsPolicyResult;
    @JsonProperty("policiesSearch")
    private PoliciesSearch alertsPolicyResults;
}
