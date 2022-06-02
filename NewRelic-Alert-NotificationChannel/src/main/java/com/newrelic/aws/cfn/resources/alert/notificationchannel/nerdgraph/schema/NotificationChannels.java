package com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema;

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
public class NotificationChannels {
    @JsonProperty("channels")
    private List<NotificationChannel> channels;
    @JsonProperty("nextCursor")
    private String nextCursor;
    @JsonProperty("totalCount")
    private Integer totalCount;
}
