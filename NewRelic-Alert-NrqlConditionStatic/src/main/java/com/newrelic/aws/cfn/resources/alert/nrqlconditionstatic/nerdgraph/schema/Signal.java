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
public class Signal {
    @JsonProperty("aggregationDelay")
    private Integer aggregationDelay;
    @JsonProperty("aggregationMethod")
    private String aggregationMethod;
    @JsonProperty("aggregationTimer")
    private Integer aggregationTimer;
    @JsonProperty("aggregationWindow")
    private Integer aggregationWindow;
    @JsonProperty("fillOption")
    private String fillOption;
    @JsonProperty("fillValue")
    private Integer fillValue;
    @JsonProperty("slideBy")
    private Integer slideBy;
}
