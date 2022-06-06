package com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class TransactionTracer {
    @JsonProperty("captureMemcacheKeys")
    private Boolean captureMemcacheKeys;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("explainEnabled")
    private Boolean explainEnabled;
    @JsonProperty("explainThresholdType")
    private String explainThresholdType;
    @JsonProperty("explainThresholdValue")
    private Integer explainThresholdValue;
    @JsonProperty("logSql")
    private Boolean logSql;
    @JsonProperty("recordSql")
    private String recordSql;
    @JsonProperty("stackTraceThreshold")
    private Integer stackTraceThreshold;
    @JsonProperty("transactionThresholdType")
    private String transactionThresholdType;
    @JsonProperty("transactionThresholdValue")
    private Integer transactionThresholdValue;
}
