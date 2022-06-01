package com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema.NrqlConditionStaticResult;
import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema.ErrorCode;
import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema.ResponseData;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.List;
import java.util.Optional;

public class NrqlConditionStaticHandler extends AbstractCombinedResourceHandler<NrqlConditionStaticHandler, NrqlConditionStaticResult, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(NrqlConditionStaticHandler.class);

    public static class BaseHandlerAdapter extends BaseHandler<CallbackContext, TypeConfigurationModel> implements BaseHandlerAdapterDefault<NrqlConditionStaticHandler, ResourceModel, CallbackContext, TypeConfigurationModel> {
        @Override
        public ProgressEvent<ResourceModel, CallbackContext> handleRequest(AmazonWebServicesClientProxy proxy, ResourceHandlerRequest<ResourceModel> request, CallbackContext callbackContext, Logger logger, TypeConfigurationModel typeConfiguration) {
            return AbstractCombinedResourceHandler.BaseHandlerAdapterDefault.super.handleRequest(proxy, request, callbackContext, logger, typeConfiguration);
        }

        @Override
        public NrqlConditionStaticHandler newCombinedHandler() {
            return new NrqlConditionStaticHandler();
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
            return model.getAccountId() == null || model.getConditionId() == null
                    ? null
                    : Pair.of(model.getAccountId(), model.getConditionId());
        }

        @Override
        protected Optional<NrqlConditionStaticResult> findExistingItemWithNonNullId(Pair<Integer, Integer> id) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("nrqlConditionStaticRead.query.template");
            String query = String.format(template, id.getLeft(), id.getRight());
            ResponseData<NrqlConditionStaticResult> responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query, ImmutableList.of(ErrorCode.BAD_USER_INPUT.name()));
            throw new NotImplementedException();
//            return Optional.ofNullable(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResult());
        }

        @Override
        public List<NrqlConditionStaticResult> readExistingItems() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("nrqlConditionStaticSearch.query.template");
            String query = String.format(template, model.getAccountId());
            ResponseData<NrqlConditionStaticResult> responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);

            throw new NotImplementedException();
//            return ImmutableList.<AlertsNrqlConditionStaticResult>builder()
//                    .addAll(responseData.getActor().getAccount().getAlerts().getAlertsPolicyResults().getPolicies()).build();
        }

        @Override
        public ResourceModel modelFromItem(NrqlConditionStaticResult alertEntityResult) {
            throw new NotImplementedException();
//            ResourceModel.ResourceModelBuilder builder = ResourceModel.builder();
//            builder.accountId(alertEntityResult != null ? alertEntityResult.getAccountId() : model.getAccountId());
//            builder.alertsPolicyId(alertEntityResult != null ? alertEntityResult.getAlertsPolicyId() : model.getAlertsPolicyId());
//            if (alertEntityResult != null && alertEntityResult.getName() != null) {
//                builder.alertsPolicy(AlertsConditionInput.builder()
//                        .incidentPreference(alertEntityResult.getIncidentPreference().name())
//                        .name(alertEntityResult.getName())
//                        .build());
//            }
//            return builder.build();
        }

        @Override
        public NrqlConditionStaticResult createItem() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("nrqlConditionStaticCreate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getPolicyId(), nerdGraphClient.genGraphQLArg(model.getCondition(), ImmutableList.of(model.getCondition().getClass().getPackage().getName())));
            ResponseData<NrqlConditionStaticResult> responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
            return responseData.getAlertCreateResult();
        }

        @Override
        public void updateItem(NrqlConditionStaticResult alertEntityResult, List<String> updates) throws Exception {
            throw new NotImplementedException();
//            String template = nerdGraphClient.getGraphQLTemplate("nrqlConditionStaticUpdate.mutation.template");
//            String mutation = String.format(template, model.getAccountId(), model.getAlertsPolicyId(), nerdGraphClient.genGraphQLArg(model.getAlertsPolicy()));
//            ResponseData<AlertsNrqlConditionStaticResult> responseData = nerdGraphClient.doRequest(AlertsNrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
//            if (responseData.getAlertUpdateResult() != null) {
//                updates.add("Updated");
//            }
        }

        @Override
        public void deleteItem(NrqlConditionStaticResult alertEntityResult) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("nrqlConditionStaticDelete.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getConditionId());
            ResponseData<NrqlConditionStaticResult> responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
        }
    }
}
