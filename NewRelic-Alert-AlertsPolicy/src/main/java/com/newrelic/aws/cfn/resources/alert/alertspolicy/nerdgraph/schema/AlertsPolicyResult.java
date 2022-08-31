package com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class AlertsPolicyResult {
    @JsonProperty("id")
    private Integer alertsPolicyId;
    @JsonProperty("accountId")
    private Integer accountId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("incidentPreference")
    private IncidentPreference incidentPreference;
}
