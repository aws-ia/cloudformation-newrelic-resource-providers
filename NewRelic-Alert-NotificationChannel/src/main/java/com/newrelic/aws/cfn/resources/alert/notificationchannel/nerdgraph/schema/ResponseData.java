package com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema;

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
public class ResponseData<T extends NotificationChannelResult> {
    @JsonProperty("alertsNrqlConditionStaticCreate")
    private NotificationChannelResult alertCreateResult;

    @JsonProperty("alertsNrqlConditionStaticUpdate")
    private NotificationChannelResult alertUpdateResult;

    @JsonProperty("alertsConditionDelete")
    private NotificationChannelResult alertDeleteResult;

    @JsonProperty("actor")
    private Actor<T> actor;
}
