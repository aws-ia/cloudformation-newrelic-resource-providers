package com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema;

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
public class Response<T extends ApmSettings> {
    @JsonProperty("data")
    private ResponseData<T> responseData;

    @JsonProperty("agentApplicationSettingsUpdate")
    private ApmSettings agentApplicationSettingsUpdate;

    @JsonProperty("errors")
    private List<ResponseError> responseError;
}
