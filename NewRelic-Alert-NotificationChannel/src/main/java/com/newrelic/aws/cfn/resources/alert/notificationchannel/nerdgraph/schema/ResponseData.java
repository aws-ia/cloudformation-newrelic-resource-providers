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
    @JsonProperty("alertsNotificationChannelCreate")
    private NotificationChannelResult alertCreateResult;

    @JsonProperty("alertsNotificationChannelUpdate")
    private NotificationChannelResult alertUpdateResult;

    @JsonProperty("alertsNotificationChannelDelete")
    private NotificationDeleteResult alertDeleteResult;

    @JsonProperty("actor")
    private Actor<T> actor;
}
