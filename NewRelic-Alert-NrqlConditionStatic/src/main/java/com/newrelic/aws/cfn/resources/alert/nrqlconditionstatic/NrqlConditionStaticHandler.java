package com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
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
    public NrqlConditionStaticHelper newHelper() {
        return new NrqlConditionStaticHelper();
    }

    class NrqlConditionStaticHelper extends Helper {

        private final NerdGraphClient nerdGraphClient;

        public NrqlConditionStaticHelper() {
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
            return Optional.ofNullable(responseData.getActor().getAccount().getAlerts().getNrqlCondition());
        }

        @Override
        public List<NrqlConditionStaticResult> readExistingItems() throws Exception {
            List<NrqlConditionStaticResult> results = Lists.newArrayList();
            String template = nerdGraphClient.getGraphQLTemplate("nrqlConditionStaticSearch.query.template");
            String cursorTemplate = nerdGraphClient.getGraphQLTemplate("nrqlConditionStaticSearchCursor.query.template");
            String query = String.format(template, model.getAccountId());
            ResponseData<NrqlConditionStaticResult> responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);
            results.addAll(responseData.getActor().getAccount().getAlerts().getNrqlConditionsSearch().getNrqlConditions());
            String nextCursor = responseData.getActor().getAccount().getAlerts().getNrqlConditionsSearch().getNextCursor();
            while (nextCursor != null) {
                String cursorQuery = String.format(cursorTemplate, model.getAccountId(), nextCursor);
                responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), cursorQuery);
                nextCursor = responseData.getActor().getAccount().getAlerts().getNrqlConditionsSearch().getNextCursor();
                results.addAll(responseData.getActor().getAccount().getAlerts().getNrqlConditionsSearch().getNrqlConditions());
            }
            return results;
        }

        @Override
        public ResourceModel modelFromItem(NrqlConditionStaticResult alertEntityResult) {
            return ResourceModel.builder()
                    .accountId(alertEntityResult.getEntity().getAccountId())
                    .policyId(alertEntityResult.getPolicyId())
                    .conditionId(alertEntityResult.getNrqlConditionStaticId())
                    .condition(ConditionInput.builder()
                            .name(alertEntityResult.getName())
                            .enabled(alertEntityResult.getEnabled())
                            .description(alertEntityResult.getDescription())
                            .nrql(Nrql.builder()
                                    .query(alertEntityResult.getNrql().getQuery())
                                    .build())
                            .expiration(Expiration.builder()
                                    .expirationDuration(alertEntityResult.getExpiration().getExpirationDuration())
                                    .closeViolationsOnExpiration(alertEntityResult.getExpiration().getCloseViolationsOnExpiration())
                                    .openViolationOnExpiration(alertEntityResult.getExpiration().getOpenViolationOnExpiration())
                                    .build())
                            .runbookUrl(alertEntityResult.getRunbookUrl())
                            .signal(Signal.builder()
                                    .aggregationDelay(alertEntityResult.getSignal().getAggregationDelay())
                                    .aggregationMethod(alertEntityResult.getSignal().getAggregationMethod())
                                    .aggregationTimer(alertEntityResult.getSignal().getAggregationTimer())
                                    .aggregationWindow(alertEntityResult.getSignal().getAggregationWindow())
                                    .fillOption(alertEntityResult.getSignal().getFillOption())
                                    .fillValue(alertEntityResult.getSignal().getFillValue())
                                    .slideBy(alertEntityResult.getSignal().getSlideBy())
                                    .build())
                            .terms(Terms.builder()
                                    .operator(alertEntityResult.getTerms().get(0).getOperator())
                                    .priority(alertEntityResult.getTerms().get(0).getPriority())
                                    .threshold(alertEntityResult.getTerms().get(0).getThreshold())
                                    .thresholdDuration(alertEntityResult.getTerms().get(0).getThresholdDuration())
                                    .thresholdOccurrences(alertEntityResult.getTerms().get(0).getThresholdOccurrences())
                                    .build())
                            .violationTimeLimitSeconds(alertEntityResult.getViolationTimeLimitSeconds())
                            .build())
                    .build();
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
            String template = nerdGraphClient.getGraphQLTemplate("nrqlConditionStaticUpdate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getConditionId(), nerdGraphClient.genGraphQLArg(model.getCondition(), ImmutableList.of(model.getCondition().getClass().getPackage().getName())));
            ResponseData<NrqlConditionStaticResult> responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
            if (responseData.getAlertUpdateResult() != null) {
                updates.add("Updated");
            }
        }

        @Override
        public void deleteItem(NrqlConditionStaticResult alertEntityResult) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("nrqlConditionStaticDelete.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getConditionId());
            ResponseData<NrqlConditionStaticResult> responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
        }
    }
}
