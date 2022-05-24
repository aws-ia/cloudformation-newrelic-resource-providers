package com.newrelic.aws.cfn.resources.dashboard;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.newrelic.aws.cfn.resources.dashboard.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.dashboard.nerdgraph.schema.DashboardEntityResult;
import com.newrelic.aws.cfn.resources.dashboard.nerdgraph.schema.DashboardError;
import com.newrelic.aws.cfn.resources.dashboard.nerdgraph.schema.ResponseData;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.exceptions.CfnServiceInternalErrorException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DashboardResourceHandler extends AbstractCombinedResourceHandler<DashboardResourceHandler, DashboardEntityResult, Pair<Integer, String>, ResourceModel, CallbackContext, TypeConfigurationModel> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DashboardResourceHandler.class);

    public static class BaseHandlerAdapter extends BaseHandler<CallbackContext, TypeConfigurationModel> implements BaseHandlerAdapterDefault<DashboardResourceHandler, ResourceModel, CallbackContext, TypeConfigurationModel> {
        @Override
        public ProgressEvent<ResourceModel, CallbackContext> handleRequest(AmazonWebServicesClientProxy proxy, ResourceHandlerRequest<ResourceModel> request, CallbackContext callbackContext, Logger logger, TypeConfigurationModel typeConfiguration) {
            return BaseHandlerAdapterDefault.super.handleRequest(proxy, request, callbackContext, logger, typeConfiguration);
        }

        @Override
        public DashboardResourceHandler newCombinedHandler() {
            return new DashboardResourceHandler();
        }
    }

    @Override
    public DashboardHelper newHelper() {
        return new DashboardHelper();
    }

    class DashboardHelper extends Helper {

        private final NerdGraphClient nerdGraphClient;

        public DashboardHelper() {
            nerdGraphClient = new NerdGraphClient();
        }

        @Override
        public Pair<Integer, String> getId(ResourceModel model) {
            return model.getAccountId() == null || model.getDashboardId() == null
                    ? null
                    : Pair.of(model.getAccountId(), model.getDashboardId());
        }

        @Override
        protected Optional<DashboardEntityResult> findExistingItemWithNonNullId(Pair<Integer, String> id) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("dashboardRead.query.template");
            String query = String.format(template, id.getRight());
            ResponseData<DashboardEntityResult> responseData = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);

            return Optional.ofNullable(responseData.getActor().getEntity());
        }

        @Override
        public List<DashboardEntityResult> readExistingItems() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("dashboardSearch.query.template");
            ResponseData<DashboardEntityResult> responseData = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), template);

            return responseData.getActor().getEntitySearch().getResults().getEntities();
        }

        @Override
        public ResourceModel modelFromItem(DashboardEntityResult dashboardEntityResult) {
            return ResourceModel.builder()
                    .accountId(dashboardEntityResult != null ? dashboardEntityResult.getAccountId() : model.getAccountId())
                    .dashboardId(dashboardEntityResult != null ? dashboardEntityResult.getGuid() : model.getDashboardId())
                    .dashboard(model != null ? model.getDashboard() : null)
                    .build();
        }

        @Override
        public DashboardEntityResult createItem() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("dashboardCreate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), nerdGraphClient.genGraphQLArg(model.getDashboard()));
            ResponseData<DashboardEntityResult> responseData = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            checkForDashboardErrors(responseData.getDashboardCreateResult().getErrors(), "NerdGraph dashboardCreate mutation");

            return responseData.getDashboardCreateResult().getEntityResult();
        }

        @Override
        public void updateItem(DashboardEntityResult dashboardEntityResult, List<String> list) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("dashboardUpdate.mutation.template");
            String mutation = String.format(template, model.getDashboardId(), nerdGraphClient.genGraphQLArg(model.getDashboard()));
            ResponseData<DashboardEntityResult> responseData = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            checkForDashboardErrors(responseData.getDashboardUpdateResult().getErrors(), "NerdGraph dashboardUpdate mutation");
        }

        @Override
        public void deleteItem(DashboardEntityResult dashboardEntityResult) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("dashboardDelete.mutation.template");
            String mutation = String.format(template, model.getDashboardId());
            ResponseData<DashboardEntityResult> responseData = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            checkForDashboardErrors(responseData.getDashboardDeleteResult().getErrors(), "NerdGraph dashboardDelete mutation");
        }

        private <ErrorTypeT> void checkForDashboardErrors(List<DashboardError<ErrorTypeT>> dashboardErrors, String operation) throws CfnServiceInternalErrorException {
            if (dashboardErrors != null && !dashboardErrors.isEmpty()) {
                throw new CfnServiceInternalErrorException(operation, new Exception(dashboardErrors
                        .stream()
                        .map(dashboardError -> String.format("==> [%s] %s", dashboardError.getType(), dashboardError.getDescription()))
                        .collect(Collectors.joining("\n", "The following error(s) occurred interacting with the NerdGraph API:\n", ""))));
            }
        }
    }
}
