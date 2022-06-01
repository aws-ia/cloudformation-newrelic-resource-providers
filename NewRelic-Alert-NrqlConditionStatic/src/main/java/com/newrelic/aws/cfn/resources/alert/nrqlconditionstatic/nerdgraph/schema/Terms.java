package com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema;

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
public class Terms {
    @JsonProperty("operator")
    private String operator;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("threshold")
    private Integer threshold;
    @JsonProperty("thresholdDuration")
    private Integer thresholdDuration;
    @JsonProperty("thresholdOccurrences")
    private String thresholdOccurrences;
}
