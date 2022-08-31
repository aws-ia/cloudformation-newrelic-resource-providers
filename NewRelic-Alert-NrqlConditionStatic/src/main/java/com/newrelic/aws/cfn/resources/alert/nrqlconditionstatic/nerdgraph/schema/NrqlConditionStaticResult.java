package com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class NrqlConditionStaticResult {
    @JsonProperty("id")
    private Integer nrqlConditionStaticId;
    @JsonProperty("policyId")
    private Integer policyId;
    @JsonProperty("entity")
    private Entity entity;
    @JsonProperty("name")
    private String name;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("description")
    private String description;
    @JsonProperty("nrql")
    private Nrql nrql;
    @JsonProperty("expiration")
    private Expiration expiration;
    @JsonProperty("runbookUrl")
    private String runbookUrl;
    @JsonProperty("signal")
    private Signal signal;
    @JsonProperty("terms")
    private List<Terms> terms;
    @JsonProperty("violationTimeLimitSeconds")
    private Integer violationTimeLimitSeconds;
}
