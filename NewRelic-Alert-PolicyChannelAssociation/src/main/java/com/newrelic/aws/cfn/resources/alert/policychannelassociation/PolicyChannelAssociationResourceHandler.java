package com.newrelic.aws.cfn.resources.alert.policychannelassociation;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.alert.policychannelassociation.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.alert.policychannelassociation.nerdgraph.schema.*;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public PolicyChannelAssociationHelper newHelper() {
        return new PolicyChannelAssociationHelper();
    }

    class PolicyChannelAssociationHelper extends Helper {

        private final NerdGraphClient nerdGraphClient;

        public PolicyChannelAssociationHelper() {
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
            return readExistingItems().stream()
                    .filter(result -> id.getRight().equals(result.getPolicyId())).findFirst();
        }

        @Override
        public List<PolicyChannelAssociationResult> readExistingItems() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("policyChannelAssociationRead.query.template");
            String cursorTemplate = nerdGraphClient.getGraphQLTemplate("policyChannelAssociationReadCursor.query.template");
            String query = String.format(template, model.getAccountId());
            ImmutableList.Builder<PolicyChannelAssociationResult> resultsBuilder = ImmutableList.builder();
            ResponseData<PolicyChannelAssociationResult> responseData = nerdGraphClient.doRequest(PolicyChannelAssociationResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);
            String nextCursor = responseData.getActor().getAccount().getAlerts().getNotificationChannelCursor().getNextCursor();

            resultsBuilder.addAll(readResponseDataToResults(responseData, model.getAccountId()));

            while (nextCursor != null) {
                String cursorQuery = String.format(cursorTemplate, model.getAccountId(), nextCursor);
                responseData = nerdGraphClient.doRequest(PolicyChannelAssociationResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), cursorQuery);
                nextCursor = responseData.getActor().getAccount().getAlerts().getNotificationChannelCursor().getNextCursor();
                resultsBuilder.addAll(readResponseDataToResults(responseData, model.getAccountId()));
            }

            return resultsBuilder.build();
        }

        private List<PolicyChannelAssociationResult> readResponseDataToResults(ResponseData<PolicyChannelAssociationResult> responseData, Integer accountId) {
            // When creating an association the API requires a policyId and a list of channelIds, but when reading existing associations
            // the api returns a list of channels, each with an associated list of policies, so it is necessary to reformat the data
            // accordingly
            return responseData.getActor().getAccount().getAlerts().getNotificationChannelCursor().getNotificationChannels().stream()
                    .flatMap(nc -> nc.getAssociatedPolicies().getPolicies().stream().map(p -> p.getId()))
                    .collect(Collectors.toSet()).stream()
                        .map(pid -> PolicyChannelAssociationResult.builder()
                            .accountId(accountId)
                            .policyId(pid)
                            .channelIds(
                                    ImmutableList.<Integer>builder()
                                            .addAll(responseData.getActor().getAccount().getAlerts().getNotificationChannelCursor().getNotificationChannels().stream()
                                                    .filter(nc -> nc.getAssociatedPolicies().getPolicies().stream()
                                                            .map(p -> p.getId())
                                                            .collect(Collectors.toSet())
                                                            .contains(pid))
                                                    .map(nc -> nc.getNotificationChannelId()).collect(Collectors.toSet()))
                                            .build()
                            )
                            .build())
                        .collect(Collectors.toList());
        }

        @Override
        public ResourceModel modelFromItem(PolicyChannelAssociationResult associationResult) {
            return ResourceModel.builder()
                    .accountId(associationResult.getAccountId())
                    .policyId(associationResult.getPolicyId())
                    .channelIds(associationResult.getChannelIds())
                    .build();
        }

        @Override
        public PolicyChannelAssociationResult createItem() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("policyChannelAssociationCreate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getPolicyId(), nerdGraphClient.genGraphQLArg(model.getChannelIds()));
            ResponseData<PolicyChannelAssociationResult> responseData = nerdGraphClient.doRequest(PolicyChannelAssociationResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            return PolicyChannelAssociationResult.builder()
                    .policyId(responseData.getAlertsNotificationChannelsAddToPolicy().getPolicyId())
                    .accountId(model.getAccountId())
                    .channelIds(responseData.getAlertsNotificationChannelsAddToPolicy().getNotificationChannelIds().stream()
                            .map(nc -> nc.getNotificationChannelId()).collect(Collectors.toList()))
                    .build();
        }

        @Override
        public void updateItem(PolicyChannelAssociationResult alertEntityResult, List<String> updates) throws Exception {
            // The API does not provide the ability to update associations, so we delete the channel associations for the given policy
            // and recreate them
            Optional<PolicyChannelAssociationResult> existingResult = findExistingItemWithNonNullId(Pair.of(model.getAccountId(), model.getPolicyId()));
            List<Integer> oldChannelIds = existingResult.isPresent() ? existingResult.get().getChannelIds() : ImmutableList.of();
            if (oldChannelIds.size() > 0) {
                deleteItem(PolicyChannelAssociationResult.builder()
                        .accountId(model.getAccountId())
                        .policyId(model.getPolicyId())
                        .channelIds(oldChannelIds)
                        .build());
            }
            model.setChannelIds(alertEntityResult.getChannelIds());
            createItem();
            updates.add("Updated");
        }

        @Override
        public void deleteItem(PolicyChannelAssociationResult alertEntityResult) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("policyChannelAssociationDelete.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getPolicyId(), nerdGraphClient.genGraphQLArg(model.getChannelIds()));
            ResponseData<PolicyChannelAssociationResult> responseData = nerdGraphClient.doRequest(PolicyChannelAssociationResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
        }
    }
}