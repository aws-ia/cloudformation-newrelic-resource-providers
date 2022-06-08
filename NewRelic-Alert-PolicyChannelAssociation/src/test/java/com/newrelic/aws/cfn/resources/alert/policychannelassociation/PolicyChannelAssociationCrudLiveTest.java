package com.newrelic.aws.cfn.resources.alert.policychannelassociation;

import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.alert.policychannelassociation.nerdgraph.schema.PolicyChannelAssociationResult;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.cloudformation.Action;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("Live")
public class PolicyChannelAssociationCrudLiveTest extends AbstractResourceCrudLiveTest<PolicyChannelAssociationResourceHandler, PolicyChannelAssociationResult, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {

    // NOTE: These tests assume the following:
    // A Policy exists
    // 3 Channels exist
    // No policy-channel association exists for the above
    // To run these tests, you will need to provide certain values as environment variables, e.g.:
    // NR_API_KEY=XXXXXXXXXXX;NR_ACCOUNT_ID=1234567;NR_ENDPOINT=https://api.eu.newrelic.com/graphql;NR_POLICY_ID=123456;NR_CHANNEL1_ID=234567;NR_CHANNEL2_ID=234568;NR_CHANNEL3_ID=234569

    @Test
    @Order(1)
    @SuppressWarnings("unchecked")
    public void testDeleteOrphanedTestItemIfExists() throws Exception {
        // The association created by the test suite is automatically deleted on the completion of the test
        // suite. However, if the test suite is interrupted, the orphaned association can prevent the tests from
        // passing (as it already exists). This test deletes that association, allowing the test suite to be run again
        this.model = newModelForCreate();
        this.model.setChannelIds(ImmutableList.<Integer>builder()
                .addAll(model.getChannelIds())
                .addAll(newModelForUpdate().getChannelIds())
                .build());
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
        int channel1Id = Integer.parseInt(System.getenv("NR_CHANNEL1_ID"));
        int channel2Id = Integer.parseInt(System.getenv("NR_CHANNEL2_ID"));
        return ResourceModel.builder()
                .policyId(policyId)
                .accountId(accountId)
                .channelIds(ImmutableList.of(channel1Id, channel2Id))
                .build();
    }

    @Override
    protected ResourceModel newModelForUpdate() throws Exception {
        int accountId = Integer.parseInt(System.getenv("NR_ACCOUNT_ID"));
        int policyId = Integer.parseInt(System.getenv("NR_POLICY_ID"));
        int channel3Id = Integer.parseInt(System.getenv("NR_CHANNEL3_ID"));
        return ResourceModel.builder()
                .policyId(policyId)
                .accountId(accountId)
                .channelIds(ImmutableList.of(channel3Id))
                .build();
    }

    @Override
    protected HandlerWrapper newHandlerWrapper() {
        return new HandlerWrapper();
    }
}
