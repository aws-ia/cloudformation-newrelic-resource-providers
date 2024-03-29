package com.newrelic.aws.cfn.resources.alert.policychannelassociation.nerdgraph.schema;

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
public class ResponseData<T extends PolicyChannelAssociationResult> {
    @JsonProperty("actor")
    private Actor<T> actor;

    @JsonProperty("alertsNotificationChannelsAddToPolicy")
    private AlertsNotificationChannelsAddToPolicy alertsNotificationChannelsAddToPolicy;

    @JsonProperty("alertsNotificationChannelsRemoveFromPolicy")
    private AlertsNotificationChannelsRemoveFromPolicy alertsNotificationChannelsRemoveFromPolicy;
}
