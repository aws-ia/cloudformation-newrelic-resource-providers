package com.newrelic.aws.cfn.resources.alert.alertspolicy;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema.AlertsPolicyResult;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema.AlertsPolicySearchResult;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema.HasAccountId;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema.ResponseData;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.Opt;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.exceptions.CfnServiceInternalErrorException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            nerdGraphClient = new NerdGraphClient();
        }

        @Override
        public Pair<Integer, Integer> getId(ResourceModel model) {
            return model.getAccountId() == null || model.getAlertsPolicyId() == null
                    ? null
                    : Pair.of(model.getAccountId(), model.getAlertsPolicyId());
        }

        @Override
        protected Optional<AlertsPolicyResult> findExistingItemWithNonNullId(Pair<Integer, Integer> id) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyRead.query.template");
            String query = String.format(template, id.getLeft(), id.getRight());
            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);
            return Optional.ofNullable(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResult());
        }

        @Override
        public List<AlertsPolicyResult> readExistingItems() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicySearch.query.template");
            String query = String.format(template, model.getAccountId());
            ResponseData<AlertsPolicySearchResult> responseData = nerdGraphClient.doRequest(AlertsPolicySearchResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);

            return ImmutableList.<AlertsPolicyResult>builder()
                    .addAll(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResults().getPolicies()).build();
        }

        @Override
        public ResourceModel modelFromItem(AlertsPolicyResult alertEntityResult) {
            return ResourceModel.builder()
                    .accountId(alertEntityResult != null && alertEntityResult instanceof HasAccountId ? ((HasAccountId)alertEntityResult).getAccountId() : model.getAccountId())
                    .alertsPolicyId(alertEntityResult != null ? alertEntityResult.getAlertsPolicyId() : model.getAlertsPolicyId())
                    .build();
        }

        @Override
        public AlertsPolicyResult createItem() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyCreate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

//            checkForAlertErrors(responseData.getAlertCreateResult().getErrors(), "NerdGraph alertsPolicyCreate mutation");

            return responseData.getAlertCreateResult();
        }

        @Override
        public void updateItem(AlertsPolicyResult alertEntityResult, List<String> list) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyUpdate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

//            checkForAlertErrors(responseData.getAlertUpdateResult().getErrors(), "NerdGraph alertsPolicyUpdate mutation");
        }

        @Override
        public void deleteItem(AlertsPolicyResult alertEntityResult) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("alertsPolicyDelete.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId());
            ResponseData<AlertsPolicyResult> responseData = nerdGraphClient.doRequest(AlertsPolicyResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

//            checkForAlertErrors(responseData.getAlertsPolicyDeleteResult().getErrors(), "NerdGraph alertsPolicyDelete mutation");
        }

//        private <ErrorTypeT> void checkForAlertErrors(List<AlertsPolicyError<ErrorTypeT>> alertErrors, String operation) throws CfnServiceInternalErrorException {
//            if (alertErrors != null && !alertErrors.isEmpty()) {
//                throw new CfnServiceInternalErrorException(operation, new Exception(alertErrors
//                        .stream()
//                        .map(alertError -> String.format("==> [%s] %s", alertError.getType(), alertError.getDescription()))
//                        .collect(Collectors.joining("\n", "The following error(s) occurred interacting with the NerdGraph API:\n", ""))));
//            }
//        }
    }
}