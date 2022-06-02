package com.newrelic.aws.cfn.resources.alert.policychannelassociation;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.newrelic.aws.cfn.resources.alert.policychannelassociation.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.alert.policychannelassociation.nerdgraph.schema.PolicyChannelAssociationResult;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.List;
import java.util.Optional;

public class PolicyChannelAssociationResourceHandler extends AbstractCombinedResourceHandler<PolicyChannelAssociationResourceHandler, PolicyChannelAssociationResult, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PolicyChannelAssociationResourceHandler.class);

    public static class BaseHandlerAdapter extends BaseHandler<CallbackContext, TypeConfigurationModel> implements BaseHandlerAdapterDefault<PolicyChannelAssociationResourceHandler, ResourceModel, CallbackContext, TypeConfigurationModel> {
        @Override
        public ProgressEvent<ResourceModel, CallbackContext> handleRequest(AmazonWebServicesClientProxy proxy, ResourceHandlerRequest<ResourceModel> request, CallbackContext callbackContext, Logger logger, TypeConfigurationModel typeConfiguration) {
            return AbstractCombinedResourceHandler.BaseHandlerAdapterDefault.super.handleRequest(proxy, request, callbackContext, logger, typeConfiguration);
        }

        @Override
        public PolicyChannelAssociationResourceHandler newCombinedHandler() {
            return new PolicyChannelAssociationResourceHandler();
        }
    }

    @Override
    public PolicyChannelAssociation newHelper() {
        return new PolicyChannelAssociation();
    }

    class PolicyChannelAssociation extends Helper {

        private final NerdGraphClient nerdGraphClient;

        public PolicyChannelAssociation() {
            nerdGraphClient = new NerdGraphClient();
        }

        @Override
        public Pair<Integer, Integer> getId(ResourceModel model) {
            return model.getAccountId() == null || model.getPolicyId() == null
                    ? null
                    : Pair.of(model.getAccountId(), model.getPolicyId());
        }

        @Override
        protected Optional<PolicyChannelAssociationResult> findExistingItemWithNonNullId(Pair<Integer, Integer> id) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyRead.query.template");
//            String query = String.format(template, id.getLeft(), id.getRight());
//            ResponseData<PolicyChannelAssociationResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query, ImmutableList.of(ErrorCode.BAD_USER_INPUT.name()));
//            return Optional.ofNullable(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResult());
        }

        @Override
        public List<PolicyChannelAssociationResult> readExistingItems() throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicySearch.query.template");
//            String query = String.format(template, model.getAccountId());
//            ResponseData<PolicyChannelAssociationResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);
//
//            return ImmutableList.<PolicyChannelAssociationResult>builder()
//                    .addAll(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResults().getPolicies()).build();
        }

        @Override
        public ResourceModel modelFromItem(PolicyChannelAssociationResult alertEntityResult) {
            throw new NotImplementedException();
//            ResourceModel.ResourceModelBuilder builder = ResourceModel.builder();
//            builder.accountId(alertEntityResult != null ? alertEntityResult.getAccountId() : model.getAccountId());
//            builder.alertsPolicyId(alertEntityResult != null ? alertEntityResult.getAlertsPolicyId() : model.getAlertsPolicyId());
//            if (alertEntityResult != null && alertEntityResult.getName() != null) {
//                builder.alertsPolicy(PolicyChannelAssociationResult.builder()
//                                .incidentPreference(alertEntityResult.getIncidentPreference().name())
//                                .name(alertEntityResult.getName())
//                        .build());
//            }
//            return builder.build();
        }

        @Override
        public PolicyChannelAssociationResult createItem() throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyCreate.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
//            ResponseData<PolicyChannelAssociationResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
//            return responseData.getAlertCreateResult();
        }

        @Override
        public void updateItem(PolicyChannelAssociationResult alertEntityResult, List<String> updates) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyUpdate.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
//            ResponseData<PolicyChannelAssociationResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
//            if (responseData.getAlertUpdateResult() != null) {
//                updates.add("Updated");
//            }
        }

        @Override
        public void deleteItem(PolicyChannelAssociationResult alertEntityResult) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyDelete.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId());
//            ResponseData<PolicyChannelAssociationResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
        }
    }
}