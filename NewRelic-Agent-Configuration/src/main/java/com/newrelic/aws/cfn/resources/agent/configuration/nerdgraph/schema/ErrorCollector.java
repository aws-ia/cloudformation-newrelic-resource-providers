package com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema;

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
public class ErrorCollector {
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("expectedErrorClasses")
    private List<String> expectedErrorClasses;
    @JsonProperty("expectedErrorCodes")
    private List<String> expectedErrorCodes;
    @JsonProperty("ignoredErrorClasses")
    private List<String> ignoredErrorClasses;
    @JsonProperty("ignoredErrorCodes")
    private List<String> ignoredErrorCodes;

}
