package com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic;

import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema.NrqlConditionStaticResult;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.cloudformation.Action;
import software.amazon.cloudformation.proxy.ProgressEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("Live")
public class NrqlConditionStaticCrudLiveTest extends AbstractResourceCrudLiveTest<NrqlConditionStaticHandler, NrqlConditionStaticResult, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {
    @Test
    @Order(1000)
    @Tag("Manual")
    @SuppressWarnings("unchecked")
    public void testListCursor() throws Exception {
        final int createCount = 0;
        this.model = this.newModelForCreate();
        ProgressEvent<ResourceModel, CallbackContext> listResponse = this.invoke(Action.LIST);
        List<ResourceModel> existingModels = listResponse.getResourceModels();
        List<ResourceModel> conditionsCreated = Lists.newArrayList();
        for (int i = 0; i < createCount; i++) {
            ProgressEvent<ResourceModel, CallbackContext> response = this.invoke(Action.CREATE);
            conditionsCreated.add(response.getResourceModel());
        }
        listResponse = this.invoke(Action.LIST);
        List<ResourceModel> newModels = listResponse.getResourceModels();
        Assertions.assertThat(newModels.size()).isEqualTo(existingModels.size() + createCount);
        for (ResourceModel model : conditionsCreated) {
            this.model = model;
            ProgressEvent<ResourceModel, CallbackContext> response = this.invoke(Action.DELETE);
        }

        Awaitility.await().atMost(1, TimeUnit.MINUTES).until(() -> {
            ProgressEvent<ResourceModel, CallbackContext> response = this.invoke(Action.LIST);
            List<ResourceModel> modelsAfterDelete = response.getResourceModels();
            return modelsAfterDelete.size() == existingModels.size();
        });

        ProgressEvent<ResourceModel, CallbackContext> response = this.invoke(Action.LIST);
        List<ResourceModel> modelsAfterDelete = response.getResourceModels();
        Assertions.assertThat(modelsAfterDelete.size()).isEqualTo(existingModels.size());
    }

    @Test
    @Order(1000)
    @Tag("Manual")
    @SuppressWarnings("unchecked")
    public void testDeleteSpuriousItem() throws Exception {
        this.model = newModelForCreate();
        this.model.setConditionId(999999);
        this.invoke(Action.DELETE);
        this.model = null;
    }

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
