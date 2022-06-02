package com.newrelic.aws.cfn.resources.alert.notificationchannel;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.NotificationChannelResult;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.ResponseData;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.List;
import java.util.Optional;

public class NotificationChannelResourceHandler extends AbstractCombinedResourceHandler<NotificationChannelResourceHandler, NotificationChannelResult, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(NotificationChannelResourceHandler.class);

    public static class BaseHandlerAdapter extends BaseHandler<CallbackContext, TypeConfigurationModel> implements BaseHandlerAdapterDefault<NotificationChannelResourceHandler, ResourceModel, CallbackContext, TypeConfigurationModel> {
        @Override
        public ProgressEvent<ResourceModel, CallbackContext> handleRequest(AmazonWebServicesClientProxy proxy, ResourceHandlerRequest<ResourceModel> request, CallbackContext callbackContext, Logger logger, TypeConfigurationModel typeConfiguration) {
            return AbstractCombinedResourceHandler.BaseHandlerAdapterDefault.super.handleRequest(proxy, request, callbackContext, logger, typeConfiguration);
        }

        @Override
        public NotificationChannelResourceHandler newCombinedHandler() {
            return new NotificationChannelResourceHandler();
        }
    }

    @Override
    public AlertsPolicyHelper newHelper() {
        return new AlertsPolicyHelper();
    }

    class AlertsPolicyHelper extends Helper {

        private final NerdGraphClient nerdGraphClient;

        public AlertsPolicyHelper() {
            nerdGraphClient = new NerdGraphClient();
        }

        @Override
        public Pair<Integer, Integer> getId(ResourceModel model) {
            return model.getAccountId() == null || model.getChannelId() == null
                    ? null
                    : Pair.of(model.getAccountId(), model.getChannelId());
        }

        @Override
        protected Optional<NotificationChannelResult> findExistingItemWithNonNullId(Pair<Integer, Integer> id) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyRead.query.template");
//            String query = String.format(template, id.getLeft(), id.getRight());
//            ResponseData<NotificationChannelResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query, ImmutableList.of(ErrorCode.BAD_USER_INPUT.name()));
//            return Optional.ofNullable(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResult());
        }

        @Override
        public List<NotificationChannelResult> readExistingItems() throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicySearch.query.template");
//            String query = String.format(template, model.getAccountId());
//            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);
//
//            return ImmutableList.<AlertsPolicyResult>builder()
//                    .addAll(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResults().getPolicies()).build();
        }

        @Override
        public ResourceModel modelFromItem(NotificationChannelResult channelResult) {
            throw new NotImplementedException();
//            ResourceModel.ResourceModelBuilder builder = ResourceModel.builder();
//            builder.accountId(channelResult != null ? channelResult.getCh() : model.getAccountId());
//            builder.alertsPolicyId(channelResult != null ? channelResult.getAlertsPolicyId() : model.getAlertsPolicyId());
//            if (channelResult != null && channelResult.getName() != null) {
//                builder.alertsPolicy(AlertsPolicyInput.builder()
//                                .incidentPreference(channelResult.getIncidentPreference().name())
//                                .name(channelResult.getName())
//                        .build());
//            }
//            return builder.build();
        }

        @Override
        public NotificationChannelResult createItem() throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyCreate.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
//            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
//            return responseData.getAlertCreateResult();
        }

        @Override
        public void updateItem(NotificationChannelResult alertEntityResult, List<String> updates) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyUpdate.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
//            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
//            if (responseData.getAlertUpdateResult() != null) {
//                updates.add("Updated");
//            }
        }

        @Override
        public void deleteItem(NotificationChannelResult alertEntityResult) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyDelete.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId());
//            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
        }
    }
}