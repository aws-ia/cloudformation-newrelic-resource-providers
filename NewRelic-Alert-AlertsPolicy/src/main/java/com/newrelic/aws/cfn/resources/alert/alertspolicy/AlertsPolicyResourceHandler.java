package com.newrelic.aws.cfn.resources.alert.alertspolicy;

import aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema.AlertsPolicyResult;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema.ErrorCode;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema.ResponseData;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class AlertsPolicyResourceHandler extends AbstractCombinedResourceHandler<AlertsPolicyResourceHandler, AlertsPolicyResult, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AlertsPolicyResourceHandler.class);

    public static class BaseHandlerAdapter extends BaseHandler<CallbackContext, TypeConfigurationModel> implements BaseHandlerAdapterDefault<AlertsPolicyResourceHandler, ResourceModel, CallbackContext, TypeConfigurationModel> {
        @Override
        public ProgressEvent<ResourceModel, CallbackContext> handleRequest(AmazonWebServicesClientProxy proxy, ResourceHandlerRequest<ResourceModel> request, CallbackContext callbackContext, Logger logger, TypeConfigurationModel typeConfiguration) {
            return BaseHandlerAdapterDefault.super.handleRequest(proxy, request, callbackContext, logger, typeConfiguration);
        }

        @Override
        public AlertsPolicyResourceHandler newCombinedHandler() {
            return new AlertsPolicyResourceHandler();
        }
    }

    @Override
    public AlertsPolicyHelper newHelper() {
        return new AlertsPolicyHelper();
    }

    class AlertsPolicyHelper extends Helper {

        private final NerdGraphClient nerdGraphClient;

        public AlertsPolicyHelper() {
            String userAgent = String.format(
                    "AWS CloudFormation (+https://aws.amazon.com/cloudformation/) CloudFormation resource %s/%s",
                    ResourceModel.TYPE_NAME,
                    getVersion());
            nerdGraphClient = new NerdGraphClient(userAgent);
        }

        private String getVersion() {
            Properties properties = new Properties();
            Optional.ofNullable(getClass().getClassLoader().getResourceAsStream("resource.properties")).ifPresent(inputStream -> {
                try {
                    properties.load(inputStream);
                } catch (IOException e) {
                    logger.log("Warning: failed to load resource version.");
                }
            });
            return properties.containsKey("version")
                    ? properties.getProperty("version")
                    : "Unknown";
        }

        @Override
        public Pair<Integer, Integer> getId(ResourceModel model) {
            return model.getAccountId() == null || model.getAlertsPolicyId() == null
                    ? null
                    : Pair.of(model.getAccountId(), model.getAlertsPolicyId());
        }

        @Override
        protected Optional<AlertsPolicyResult> findExistingItemWithNonNullId(Pair<Integer, Integer> id) throws Exception {
            Preconditions.checkNotNull(typeConfiguration, "Type Configuration (Endpoint & API Key) must be set");
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyRead.query.template");
            String query = String.format(template, id.getLeft(), id.getRight());
            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query, ImmutableList.of(ErrorCode.BAD_USER_INPUT.name()));
            return Optional.ofNullable(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResult());
        }

        @Override
        public List<AlertsPolicyResult> readExistingItems() throws Exception {
            Preconditions.checkNotNull(typeConfiguration, "Type Configuration (Endpoint & API Key) must be set");
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicySearch.query.template");
            String query = String.format(template, model.getAccountId());
            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);

            return ImmutableList.<AlertsPolicyResult>builder()
                    .addAll(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResults().getPolicies()).build();
        }

        @Override
        public ResourceModel modelFromItem(AlertsPolicyResult alertEntityResult) {
            Preconditions.checkNotNull(typeConfiguration, "Type Configuration (Endpoint & API Key) must be set");
            ResourceModel.ResourceModelBuilder builder = ResourceModel.builder();
            builder.accountId(alertEntityResult != null ? alertEntityResult.getAccountId() : model.getAccountId());
            builder.alertsPolicyId(alertEntityResult != null ? alertEntityResult.getAlertsPolicyId() : model.getAlertsPolicyId());
            if (alertEntityResult != null && alertEntityResult.getName() != null) {
                builder.alertsPolicy(AlertsPolicyInput.builder()
                                .incidentPreference(alertEntityResult.getIncidentPreference().name())
                                .name(alertEntityResult.getName())
                        .build());
            }
            return builder.build();
        }

        @Override
        public AlertsPolicyResult createItem() throws Exception {
            Preconditions.checkNotNull(typeConfiguration, "Type Configuration (Endpoint & API Key) must be set");
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyCreate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
            return responseData.getAlertCreateResult();
        }

        @Override
        public void updateItem(AlertsPolicyResult alertEntityResult, List<String> updates) throws Exception {
            Preconditions.checkNotNull(typeConfiguration, "Type Configuration (Endpoint & API Key) must be set");
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyUpdate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
            if (responseData.getAlertUpdateResult() != null) {
                updates.add("Updated");
            }
        }

        @Override
        public void deleteItem(AlertsPolicyResult alertEntityResult) throws Exception {
            Preconditions.checkNotNull(typeConfiguration, "Type Configuration (Endpoint & API Key) must be set");
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyDelete.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId());
            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
        }
    }
}