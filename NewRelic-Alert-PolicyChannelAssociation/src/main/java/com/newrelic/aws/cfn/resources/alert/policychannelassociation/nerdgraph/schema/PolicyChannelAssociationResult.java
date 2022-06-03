package com.newrelic.aws.cfn.resources.alert.policychannelassociation.nerdgraph.schema;

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
public class PolicyChannelAssociationResult {
    @JsonProperty("accountId")
    private Integer accountId;
    @JsonProperty("policyId")
    private Integer policyId;
    @JsonProperty("channelIds")
    private List<Integer> channelIds;
}
