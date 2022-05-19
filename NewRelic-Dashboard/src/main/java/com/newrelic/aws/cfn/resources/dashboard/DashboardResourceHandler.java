package com.newrelic.aws.cfn.resources.dashboard;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.newrelic.aws.cfn.resources.dashboard.graphql.DashboardEntityResult;
import com.newrelic.aws.cfn.resources.dashboard.graphql.GraphQLResponseData;
import com.newrelic.aws.cfn.resources.dashboard.graphql.Util;
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

        private final Util util;

        public DashboardHelper() {
            util = new Util();
        }

        @Override
        public Pair<Integer, String> getId(ResourceModel model) {
            return model.getAccountId() == null || model.getDashboardId() == null
                    ? null
                    : Pair.of(model.getAccountId(), model.getDashboardId());
        }

        @Override
        protected Optional<DashboardEntityResult> findExistingItemWithNonNullId(Pair<Integer, String> id) throws Exception {
            String template = util.getGraphQLTemplate("dashboardRead.query.template");
            String query = String.format(template, id.getRight());
            GraphQLResponseData<DashboardEntityResult> graphQLResponse = util.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);

            return Optional.of(graphQLResponse.getActor().getEntity());
        }

        @Override
        public List<DashboardEntityResult> readExistingItems() throws Exception {
            String template = util.getGraphQLTemplate("dashboardSearch.query.template");
            GraphQLResponseData<DashboardEntityResult> graphQLResponse = util.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), template);

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
            String template = util.getGraphQLTemplate("dashboardCreate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), util.genGraphQLArg(model.getDashboard()));
            GraphQLResponseData<DashboardEntityResult> graphQLResponse = util.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

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
            String template = util.getGraphQLTemplate("dashboardUpdate.mutation.template");
            String mutation = String.format(template, model.getDashboardId(), util.genGraphQLArg(model.getDashboard()));
            GraphQLResponseData<DashboardEntityResult> graphQLResponse = util.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            if (graphQLResponse.getDashboardDeleteResult() != null && graphQLResponse.getDashboardDeleteResult().getErrors() != null) {
                throw new CfnServiceInternalErrorException("NerdGraph dashboardUpdate mutation", new Exception(graphQLResponse.getDashboardDeleteResult().getErrors()
                        .stream()
                        .map(dashboardDeleteError -> String.format("==> %s", dashboardDeleteError.getDescription()))
                        .collect(Collectors.joining("\n", "The following error occurred while trying to delete the New Relic dashboard:\n", ""))));
            }
        }

        @Override
        public void deleteItem(DashboardEntityResult dashboardEntityResult) throws Exception {
            String template = util.getGraphQLTemplate("dashboardDelete.mutation.template");
            String mutation = String.format(template, model.getDashboardId());
            GraphQLResponseData<DashboardEntityResult> graphQLResponse = util.doRequest(DashboardEntityResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            if (graphQLResponse.getDashboardDeleteResult() != null && graphQLResponse.getDashboardDeleteResult().getErrors() != null) {
                throw new CfnServiceInternalErrorException("NerdGraph dashboardDelete mutation", new Exception(graphQLResponse.getDashboardDeleteResult().getErrors()
                        .stream()
                        .map(dashboardDeleteError -> String.format("==> %s", dashboardDeleteError.getDescription()))
                        .collect(Collectors.joining("\n", "The following error occurred while trying to delete the New Relic dashboard:\n", ""))));
            }
        }
    }
}
