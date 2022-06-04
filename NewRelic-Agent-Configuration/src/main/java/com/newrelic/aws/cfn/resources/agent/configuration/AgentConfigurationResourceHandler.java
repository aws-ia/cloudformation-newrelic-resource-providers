package com.newrelic.aws.cfn.resources.agent.configuration;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.AgentConfigurationResult;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.List;
import java.util.Optional;

public class AgentConfigurationResourceHandler extends AbstractCombinedResourceHandler<AgentConfigurationResourceHandler, AgentConfigurationResult, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AgentConfigurationResourceHandler.class);

    public static class BaseHandlerAdapter extends BaseHandler<CallbackContext, TypeConfigurationModel> implements BaseHandlerAdapterDefault<AgentConfigurationResourceHandler, ResourceModel, CallbackContext, TypeConfigurationModel> {
        @Override
        public ProgressEvent<ResourceModel, CallbackContext> handleRequest(AmazonWebServicesClientProxy proxy, ResourceHandlerRequest<ResourceModel> request, CallbackContext callbackContext, Logger logger, TypeConfigurationModel typeConfiguration) {
            return BaseHandlerAdapterDefault.super.handleRequest(proxy, request, callbackContext, logger, typeConfiguration);
        }

        @Override
        public AgentConfigurationResourceHandler newCombinedHandler() {
            return new AgentConfigurationResourceHandler();
        }
    }

    @Override
    public AgentConfigurationHelper newHelper() {
        return new AgentConfigurationHelper();
    }

    class AgentConfigurationHelper extends Helper {

        private final NerdGraphClient nerdGraphClient;

        public AgentConfigurationHelper() {
            nerdGraphClient = new NerdGraphClient();
        }

        @Override
        public Pair<Integer, Integer> getId(ResourceModel model) {
            return model.getAccountId() == null || model.getAgentConfigurationId() == null
                    ? null
                    : Pair.of(model.getAccountId(), model.getAgentConfigurationId());
        }

        @Override
        protected Optional<AgentConfigurationResult> findExistingItemWithNonNullId(Pair<Integer, Integer> id) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyRead.query.template");
//            String query = String.format(template, id.getLeft(), id.getRight());
//            ResponseData<AgentConfigurationResult> responseData = nerdGraphClient.doRequest(AgentConfigurationResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query, ImmutableList.of(ErrorCode.BAD_USER_INPUT.name()));
//            return Optional.ofNullable(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResult());
        }

        @Override
        public List<AgentConfigurationResult> readExistingItems() throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicySearch.query.template");
//            String query = String.format(template, model.getAccountId());
//            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);
//
//            return ImmutableList.<AlertsPolicyResult>builder()
//                    .addAll(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResults().getPolicies()).build();
        }

        @Override
        public ResourceModel modelFromItem(AgentConfigurationResult alertEntityResult) {
            throw new NotImplementedException();
//            ResourceModel.ResourceModelBuilder builder = ResourceModel.builder();
//            builder.accountId(alertEntityResult != null ? alertEntityResult.getAccountId() : model.getAccountId());
//            builder.alertsPolicyId(alertEntityResult != null ? alertEntityResult.getAlertsPolicyId() : model.getAlertsPolicyId());
//            if (alertEntityResult != null && alertEntityResult.getName() != null) {
//                builder.alertsPolicy(AlertsPolicyInput.builder()
//                                .incidentPreference(alertEntityResult.getIncidentPreference().name())
//                                .name(alertEntityResult.getName())
//                        .build());
//            }
//            return builder.build();
        }

        @Override
        public AgentConfigurationResult createItem() throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyCreate.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
//            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
//            return responseData.getAlertCreateResult();
        }

        @Override
        public void updateItem(AgentConfigurationResult agentEntityResult, List<String> updates) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyUpdate.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
//            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
//            if (responseData.getAlertUpdateResult() != null) {
//                updates.add("Updated");
//            }
        }

        @Override
        public void deleteItem(AgentConfigurationResult alertEntityResult) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyDelete.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId());
//            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
        }
    }
}