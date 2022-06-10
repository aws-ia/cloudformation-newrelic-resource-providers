package com.newrelic.aws.cfn.resources.alert.alertspolicy;

import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema.AlertsPolicyResult;
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
public class AlertsPolicyCrudLiveTest extends AbstractResourceCrudLiveTest<AlertsPolicyResourceHandler, AlertsPolicyResult, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {

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

        return ResourceModel.builder()
                .accountId(accountId)
                .alertsPolicy(AlertsPolicyInput.builder()
                        .name("My Alert")
                        .incidentPreference("PER_CONDITION")
                        .build())
                .build();
    }

    @Override
    protected ResourceModel newModelForUpdate() throws Exception {
        return ResourceModel.builder()
                .accountId(this.model.getAccountId())
                .alertsPolicyId(this.model.getAlertsPolicyId())
                .alertsPolicy(AlertsPolicyInput.builder()
                        .name("My Alert (updated)")
                        .incidentPreference("PER_CONDITION_AND_TARGET")
                        .build())
                .build();
    }

    @Override
    protected HandlerWrapper newHandlerWrapper() {
        return new HandlerWrapper();
    }
}
