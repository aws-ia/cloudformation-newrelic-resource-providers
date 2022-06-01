package com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic;

import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema.NrqlConditionStaticResult;
import org.apache.commons.lang3.tuple.Pair;
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
public class NrqlConditionStaticCrudLiveTest extends AbstractResourceCrudLiveTest<NrqlConditionStaticHandler, NrqlConditionStaticResult, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {

    @Override
    protected TypeConfigurationModel newTypeConfiguration() throws Exception {
        return TypeConfigurationModel.builder()
                .endpoint(System.getenv("NR_ENDPOINT"))
                .apiKey(System.getenv("NR_API_KEY"))
                .build();
    }

    @Override
    protected ResourceModel newModelForCreate() throws Exception {
        int accountId = Integer.parseInt(System.getenv("NR_ACCOUNT_ID"));
        int policyId = Integer.parseInt(System.getenv("NR_POLICY_ID"));
        return ResourceModel.builder()
                .policyId(policyId)
                .accountId(accountId)
                .condition(ConditionInput.builder()
                        .name("My condition name")
                        .description("Description of my new condition")
                        .enabled(true)
                        .expiration(Expiration.builder()
                                .closeViolationsOnExpiration(false)
                                .openViolationOnExpiration(false)
                                .expirationDuration(12345)
                                .build()
                            )
                        .signal(Signal.builder()
                                .aggregationDelay(140)
                                .aggregationMethod("EVENT_FLOW")
                                .aggregationWindow(30)
                                .fillOption("NONE")
                                .build()
                            )
                        .nrql(Nrql.builder()
                                .query("SELECT COUNT(*) FROM Metric")
                                .build()
                            )
                        .terms(Terms.builder()
                                .operator("ABOVE")
                                .priority("CRITICAL")
                                .threshold(1)
                                .thresholdDuration(60)
                                .thresholdOccurrences("ALL")
                                .build()
                        )
                        .violationTimeLimitSeconds(250000)
                        .build()
                )
                .build();
    }

    @Override
    protected ResourceModel newModelForUpdate() throws Exception {
        int accountId = Integer.parseInt(System.getenv("NR_ACCOUNT_ID"));
        int policyId = Integer.parseInt(System.getenv("NR_POLICY_ID"));
        return ResourceModel.builder()
                .policyId(policyId)
                .accountId(accountId)
                .conditionId(this.model.getConditionId())
                .condition(ConditionInput.builder()
                        .name("Updated condition name")
                        .description("New description of my new condition")
                        .enabled(true)
                        .expiration(Expiration.builder()
                                .closeViolationsOnExpiration(true)
                                .openViolationOnExpiration(true)
                                .expirationDuration(54321)
                                .build()
                        )
                        .signal(Signal.builder()
                                .aggregationDelay(160)
                                .aggregationMethod("EVENT_FLOW")
                                .aggregationWindow(30)
                                .fillOption("NONE")
                                .build()
                        )
                        .nrql(Nrql.builder()
                                .query("SELECT COUNT(*) FROM Metric")
                                .build()
                        )
                        .terms(Terms.builder()
                                .operator("BELOW")
                                .priority("CRITICAL")
                                .threshold(1000)
                                .thresholdDuration(120)
                                .thresholdOccurrences("AT_LEAST_ONCE")
                                .build()
                        )
                        .violationTimeLimitSeconds(200000)
                        .build()
                )
                .build();
    }

    @Override
    protected HandlerWrapper newHandlerWrapper() {
        return new HandlerWrapper();
    }
}
