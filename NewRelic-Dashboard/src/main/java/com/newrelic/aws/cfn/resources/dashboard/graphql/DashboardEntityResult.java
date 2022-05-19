package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DashboardEntityResult implements EntityResult {
    @JsonProperty("accountId")
    private Integer accountId;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("guid")
    private String guid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("updatedAt")
    private Date updatedAt;
}
