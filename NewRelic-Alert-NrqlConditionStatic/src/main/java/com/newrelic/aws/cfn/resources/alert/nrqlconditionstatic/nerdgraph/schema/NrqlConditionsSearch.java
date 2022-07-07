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
public class NrqlConditionsSearch {
    @JsonProperty("nextCursor")
    private String nextCursor;
    @JsonProperty("totalCount")
    private Integer totalCount;
    @JsonProperty("nrqlConditions")
    private List<NrqlConditionStaticResult> nrqlConditions;
}
