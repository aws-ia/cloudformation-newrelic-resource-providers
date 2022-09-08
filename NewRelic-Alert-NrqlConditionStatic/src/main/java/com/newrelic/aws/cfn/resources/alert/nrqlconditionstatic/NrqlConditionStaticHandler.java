package com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic;

import aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema.ErrorCode;
import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema.NrqlConditionStaticResult;
import com.newrelic.aws.cfn.resources.alert.nrqlconditionstatic.nerdgraph.schema.ResponseData;
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
import java.util.stream.Collectors;

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
            String query = String.format(template, model.getAccountId(), model.getPolicyId());
            ResponseData<NrqlConditionStaticResult> responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);
            results.addAll(responseData.getActor().getAccount().getAlerts().getNrqlConditionsSearch().getNrqlConditions()
                    .stream().filter(nc -> nc.getNrqlConditionStaticId() != null)
                    .collect(Collectors.toList()));
            String nextCursor = responseData.getActor().getAccount().getAlerts().getNrqlConditionsSearch().getNextCursor();
            while (nextCursor != null) {
                String cursorQuery = String.format(cursorTemplate, model.getAccountId(), model.getPolicyId(), nextCursor);
                responseData = nerdGraphClient.doRequest(NrqlConditionStaticResult.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), cursorQuery);
                nextCursor = responseData.getActor().getAccount().getAlerts().getNrqlConditionsSearch().getNextCursor();
                results.addAll(responseData.getActor().getAccount().getAlerts().getNrqlConditionsSearch().getNrqlConditions()
                        .stream().filter(nc -> nc.getNrqlConditionStaticId() != null)
                        .collect(Collectors.toList()));
            }
            return results;
        }

        @Override
        public ResourceModel modelFromItem(NrqlConditionStaticResult alertEntityResult) {
            return ResourceModel.builder()
                    .accountId(model.getAccountId())
                    .policyId(alertEntityResult.getPolicyId())
                    .conditionId(alertEntityResult.getNrqlConditionStaticId())
                    .condition(model != null ? model.getCondition(): null)
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
