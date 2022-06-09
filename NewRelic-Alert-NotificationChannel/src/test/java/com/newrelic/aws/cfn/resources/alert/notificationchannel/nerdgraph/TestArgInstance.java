package com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.NotificationChannelResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class TestArgInstance {
    @JsonProperty("foo_json")
    public String foo;

    protected Integer bar;

    private Float hello;

    @JsonProperty("world_json")
    Double world;

    @JsonProperty("list_json")
    public List<Object> list;

    @JsonProperty("normal_type")
    Pair<String, Integer> pair;

    @JsonProperty("notificationChannelResult")
    NotificationChannelResult notificationChannelResult;
}
