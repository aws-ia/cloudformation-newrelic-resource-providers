package com.newrelic.aws.cfn.resources.agent.configuration;

import aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.Actor;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.ApmConfig;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.ApmSettings;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.ApmSettingsResponse;
import com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph.schema.ResponseData;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.List;
import java.util.Optional;

public class AgentConfigurationResourceHandler extends AbstractCombinedResourceHandler<AgentConfigurationResourceHandler, ApmSettingsResponse, String, ResourceModel, CallbackContext, TypeConfigurationModel> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AgentConfigurationResourceHandler.class);

    public static class BaseHandlerAdapter extends BaseHandler<CallbackContext, TypeConfigurationModel> implements BaseHandlerAdapterDefault<AgentConfigurationResourceHandler, ResourceModel, CallbackContext, TypeConfigurationModel> {
        @Override
        public ProgressEvent<ResourceModel, CallbackContext> handleRequest(AmazonWebServicesClientProxy proxy, ResourceHandlerRequest<ResourceModel> request, CallbackContext callbackContext, Logger logger, TypeConfigurationModel typeConfiguration) {
            return BaseHandlerAdapterDefault.super.handleRequest(proxy, request, callbackContext, logger, typeConfiguration);
        }

        @Override
        public AgentConfigurationResourceHandler newCombinedHandler() {
            return new AgentConfigurationResourceHandler();
        }
    }

    @Override
    public AgentConfigurationHelper newHelper() {
        return new AgentConfigurationHelper();
    }

    class AgentConfigurationHelper extends Helper {

        private final NerdGraphClient nerdGraphClient;

        public AgentConfigurationHelper() {
            nerdGraphClient = new NerdGraphClient();
        }

        @Override
        public String getId(ResourceModel model) {
            return model.getGuid() == null
                    ? null
                    : model.getGuid();
        }

        @Override
        protected Optional<ApmSettingsResponse> findExistingItemWithNonNullId(String id) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("agentConfigurationSearch.query.template");
            String query = String.format(template, id);
            ResponseData<ApmSettings> responseData = nerdGraphClient.doRequest(ApmSettings.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), query);

            boolean useServerSideConfig = Optional.ofNullable(responseData)
                    .map(ResponseData::getActor)
                    .map(Actor::getEntity)
                    .map(ApmSettingsResponse::getApmSettings)
                    .map(ApmSettings::getApmConfig)
                    .map(ApmConfig::getUseServerSideConfig)
                    .orElse(false);

            if (useServerSideConfig) {
                return Optional.of(responseData.getActor().getEntity());
            } else {
                return Optional.empty();
            }

        }

        @Override
        public List<ApmSettingsResponse> readExistingItems() throws Exception {
            // The API does not support listing of all items, so we return the item for the model (if exists)
            Optional<ApmSettingsResponse> found = findExistingItemWithId(model != null ? model.getGuid() : null);
            if (found.isPresent()) {
                return ImmutableList.of(found.get());
            } else {
                return ImmutableList.of();
            }
        }

        @Override
        public ResourceModel modelFromItem(ApmSettingsResponse apmSettings) {
            ResourceModel.ResourceModelBuilder builder = ResourceModel.builder();
            if (model != null) {
                model.getAgentConfiguration().getSettings().getApmConfig().setUseServerSideConfig(apmSettings.getApmSettings().getApmConfig().getUseServerSideConfig());
            }
            builder.guid(apmSettings.getGuid())
                    .agentConfiguration(model != null ? model.getAgentConfiguration() : null);

            return builder.build();
        }

        @Override
        public ApmSettingsResponse createItem() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("agentConfigurationCreate.mutation.template");
            model.getAgentConfiguration().getSettings().getApmConfig().setUseServerSideConfig(true);
            String mutation = String.format(template, model.getGuid(), nerdGraphClient.genGraphQLArg(model.getAgentConfiguration().getSettings(), ImmutableList.of(SlowSql.class.getPackage().getName())));
            ResponseData<ApmSettings> responseData = nerdGraphClient.doRequest(ApmSettings.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
            return responseData.getAgentApplicationSettingsUpdate();
        }

        @Override
        public void updateItem(ApmSettingsResponse agentEntityResult, List<String> updates) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("agentConfigurationUpdate.mutation.template");
            String mutation = String.format(template, model.getGuid(), nerdGraphClient.genGraphQLArg(model.getAgentConfiguration().getSettings(), ImmutableList.of(SlowSql.class.getPackage().getName())));
            nerdGraphClient.doRequest(ApmSettings.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
            updates.add("Updated");
        }

        @Override
        public void deleteItem(ApmSettingsResponse alertEntityResult) throws Exception {
            model.getAgentConfiguration().getSettings().getApmConfig().setUseServerSideConfig(false);
            String template = nerdGraphClient.getGraphQLTemplate("agentConfigurationDelete.mutation.template");
            String mutation = String.format(template, model.getGuid(), nerdGraphClient.genGraphQLArg(model.getAgentConfiguration().getSettings(), ImmutableList.of(SlowSql.class.getPackage().getName())));
            nerdGraphClient.doRequest(ApmSettings.class, typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);
        }
    }
}