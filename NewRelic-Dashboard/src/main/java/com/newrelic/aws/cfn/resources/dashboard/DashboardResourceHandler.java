package com.newrelic.aws.cfn.resources.dashboard;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.newrelic.aws.cfn.resources.dashboard.nerdgraph.schema.DashboardEntityResult;
import com.newrelic.aws.cfn.resources.dashboard.nerdgraph.schema.ResponseData;
import com.newrelic.aws.cfn.resources.dashboard.nerdgraph.NerdGraphClient;
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

    public static class BaseHandlerAdapter extends BaseHandler<CallbackContext,TypeConfigurationModel> implements BaseHandlerAdapterDefault<DashboardResourceHandler, ResourceModel,CallbackContext,TypeConfigurationModel> {
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
            ResponseData<DashboardEntityResult> graphQLResponse = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);

            return Optional.of(graphQLResponse.getActor().getEntity());
        }

        @Override
        public List<DashboardEntityResult> readExistingItems() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("dashboardSearch.query.template");
            ResponseData<DashboardEntityResult> graphQLResponse = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), template);

            return graphQLResponse.getActor().getEntitySearch().getResults().getEntities();
        }

        @Override
        public ResourceModel modelFromItem(DashboardEntityResult dashboardEntityResult) {
            return ResourceModel.builder()
                    .accountId(dashboardEntityResult.getAccountId())
                    .dashboardId(dashboardEntityResult.getGuid())
                    .dashboard(model.getDashboard())
                    .build();
        }

        @Override
        public DashboardEntityResult createItem() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("dashboardCreate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), nerdGraphClient.genGraphQLArg(model.getDashboard()));
            ResponseData<DashboardEntityResult> graphQLResponse = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            if (graphQLResponse.getDashboardCreateResult() != null && graphQLResponse.getDashboardCreateResult().getErrors() != null) {
                throw new CfnServiceInternalErrorException("NerdGraph dashboardCreate mutation", new Exception(graphQLResponse.getDashboardCreateResult().getErrors()
                        .stream()
                        .map(dashboardCreateError -> String.format("==> %s", dashboardCreateError.getDescription()))
                        .collect(Collectors.joining("\n", "The following error occurred while trying to create the New Relic dashboard:\n", ""))));
            }

            return graphQLResponse.getDashboardCreateResult().getEntityResult();
        }

        @Override
        public void updateItem(DashboardEntityResult dashboardEntityResult, List<String> list) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("dashboardUpdate.mutation.template");
            String mutation = String.format(template, model.getDashboardId(), nerdGraphClient.genGraphQLArg(model.getDashboard()));
            ResponseData<DashboardEntityResult> graphQLResponse = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            if (graphQLResponse.getDashboardDeleteResult() != null && graphQLResponse.getDashboardDeleteResult().getErrors() != null) {
                throw new CfnServiceInternalErrorException("NerdGraph dashboardUpdate mutation", new Exception(graphQLResponse.getDashboardDeleteResult().getErrors()
                        .stream()
                        .map(dashboardDeleteError -> String.format("==> %s", dashboardDeleteError.getDescription()))
                        .collect(Collectors.joining("\n", "The following error occurred while trying to delete the New Relic dashboard:\n", ""))));
            }
        }

        @Override
        public void deleteItem(DashboardEntityResult dashboardEntityResult) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("dashboardDelete.mutation.template");
            String mutation = String.format(template, model.getDashboardId());
            ResponseData<DashboardEntityResult> graphQLResponse = nerdGraphClient.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            if (graphQLResponse.getDashboardDeleteResult() != null && graphQLResponse.getDashboardDeleteResult().getErrors() != null) {
                throw new CfnServiceInternalErrorException("NerdGraph dashboardDelete mutation", new Exception(graphQLResponse.getDashboardDeleteResult().getErrors()
                        .stream()
                        .map(dashboardDeleteError -> String.format("==> %s", dashboardDeleteError.getDescription()))
                        .collect(Collectors.joining("\n", "The following error occurred while trying to delete the New Relic dashboard:\n", ""))));
            }
        }
    }
}
