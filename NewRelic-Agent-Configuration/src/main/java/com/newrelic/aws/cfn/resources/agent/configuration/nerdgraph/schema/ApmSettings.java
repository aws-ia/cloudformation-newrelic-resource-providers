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
public class ApmSettings {
    @JsonProperty("alias")
    private String alias;
    @JsonProperty("apmConfig")
    private ApmConfig apmConfig;
    @JsonProperty("errorCollector")
    private ErrorCollector errorCollector;
    @JsonProperty("slowSql")
    private SlowSql slowSql;
    @JsonProperty("threadProfiler")
    private ThreadProfiler threadProfiler;
    @JsonProperty("tracerType")
    private String tracerType;
    @JsonProperty("transactionTracer")
    private TransactionTracer transactionTracer;
}
