package com.newrelic.aws.cfn.resources.alert.policychannelassociation.nerdgraph.schema;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class NotificationChannelCursor {
    @JsonProperty("channels")
    private List<NotificationChannel> notificationChannels;
    @JsonProperty("nextCursor")
    private String nextCursor;
    @JsonProperty("totalCount")
    private Integer totalCount;
}
