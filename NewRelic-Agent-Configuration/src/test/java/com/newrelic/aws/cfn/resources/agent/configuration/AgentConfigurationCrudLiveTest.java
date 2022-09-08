package com.newrelic.aws.cfn.resources.agent.configuration;

import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.ApmSettingsResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("Live")
public class AgentConfigurationCrudLiveTest extends AbstractResourceCrudLiveTest<AgentConfigurationResourceHandler, ApmSettingsResponse, String, ResourceModel, CallbackContext, TypeConfigurationModel> {

    @Override
    protected TypeConfigurationModel newTypeConfiguration() throws Exception {
        return TypeConfigurationModel.builder()
                .newRelicAccess(NewRelicAccess.builder()
                        .endpoint(System.getenv("NR_ENDPOINT"))
                        .apiKey(System.getenv("NR_API_KEY"))
                        .build())
                .build();
    }

    @Override
    protected ResourceModel newModelForCreate() throws Exception {
        String guid = String.valueOf(System.getenv("NR_AGENT_GUID"));

        return ResourceModel.builder()
                .guid(guid)
                .agentConfiguration(AgentConfigurationInput.builder()
                        .settings(Settings.builder()
                                .alias("My Alias")
                                .apmConfig(ApmConfig.builder()
                                        .apdexTarget(5d)
                                        .build())
                                .browserConfig(BrowserConfig.builder()
                                        .apdexTarget(2)
                                        .build())
                                .errorCollector(ErrorCollector.builder()
                                        .expectedErrorClasses(ImmutableList.of("MyExpectedErrorClass"))
                                        .expectedErrorCodes(ImmutableList.of("404","429"))
                                        .ignoredErrorClasses(ImmutableList.of("MyIgnoredErrorClass", "MyIgnoredErrorClass2"))
                                        .ignoredErrorCodes(ImmutableList.of("418"))
                                        .build())
                                .slowSql(SlowSql.builder()
                                        .enabled(true)
                                        .build())
                                .threadProfiler(ThreadProfiler.builder()
                                        .enabled(true)
                                        .build())
                                .tracerType(TracerType.builder()
                                        .value("CROSS_APPLICATION_TRACER")
                                        .build())
                                .transactionTracer(TransactionTracer.builder()
                                        .captureMemcacheKeys(false)
                                        .enabled(true)
                                        .explainEnabled(true)
                                        .explainThresholdType("VALUE")
                                        .explainThresholdValue(4)
                                        .build())
                                .build())

                        .build())
                .build();
    }

    @Override
    protected ResourceModel newModelForUpdate() throws Exception {
        String guid = String.valueOf(System.getenv("NR_AGENT_GUID"));

        return ResourceModel.builder()
                .guid(guid)
                .agentConfiguration(AgentConfigurationInput.builder()
                        .settings(Settings.builder()
                                .alias("My Updated Alias")
                                .apmConfig(ApmConfig.builder()
                                        .apdexTarget(4d)
                                        .build())
                                .browserConfig(BrowserConfig.builder()
                                        .apdexTarget(1)
                                        .build())
                                .errorCollector(ErrorCollector.builder()
                                        .expectedErrorClasses(ImmutableList.of("MyUpdatedExpectedErrorClass"))
                                        .expectedErrorCodes(ImmutableList.of("504","529"))
                                        .ignoredErrorClasses(ImmutableList.of("MyUpdatedIgnoredErrorClass", "MyUpdatedIgnoredErrorClass2"))
                                        .ignoredErrorCodes(ImmutableList.of("518"))
                                        .build())
                                .slowSql(SlowSql.builder()
                                        .enabled(false)
                                        .build())
                                .threadProfiler(ThreadProfiler.builder()
                                        .enabled(false)
                                        .build())
                                .tracerType(TracerType.builder()
                                        .value("CROSS_APPLICATION_TRACER")
                                        .build())
                                .transactionTracer(TransactionTracer.builder()
                                        .captureMemcacheKeys(true)
                                        .enabled(false)
                                        .explainEnabled(false)
                                        .explainThresholdType("VALUE")
                                        .explainThresholdValue(3)
                                        .build())
                                .build())

                        .build())
                .build();
    }

    @Override
    protected HandlerWrapper newHandlerWrapper() {
        return new HandlerWrapper();
    }
}
