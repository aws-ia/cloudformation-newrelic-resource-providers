package com.newrelic.aws.cfn.resources.alert.notificationchannel;

import aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.NerdGraphClient;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.ChannelError;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.ErrorCode;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.NotificationChannel;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.NotificationChannelResult;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.ResponseData;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.exceptions.CfnServiceInternalErrorException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class NotificationChannelResourceHandler extends AbstractCombinedResourceHandler<NotificationChannelResourceHandler, NotificationChannel, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(NotificationChannelResourceHandler.class);
    private static final String CHANNEL_TYPE_EMAIL = "EMAIL";

    public static class BaseHandlerAdapter extends BaseHandler<CallbackContext, TypeConfigurationModel> implements BaseHandlerAdapterDefault<NotificationChannelResourceHandler, ResourceModel, CallbackContext, TypeConfigurationModel> {
        @Override
        public ProgressEvent<ResourceModel, CallbackContext> handleRequest(AmazonWebServicesClientProxy proxy, ResourceHandlerRequest<ResourceModel> request, CallbackContext callbackContext, Logger logger, TypeConfigurationModel typeConfiguration) {
            return AbstractCombinedResourceHandler.BaseHandlerAdapterDefault.super.handleRequest(proxy, request, callbackContext, logger, typeConfiguration);
        }

        @Override
        public NotificationChannelResourceHandler newCombinedHandler() {
            return new NotificationChannelResourceHandler();
        }
    }

    @Override
    public NotificationChannelHelper newHelper() {
        return new NotificationChannelHelper();
    }

    class NotificationChannelHelper extends Helper {

        private final NerdGraphClient nerdGraphClient;

        public NotificationChannelHelper() {
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
            return model.getAccountId() == null || model.getChannelId() == null
                    ? null
                    : Pair.of(model.getAccountId(), model.getChannelId());
        }

        @Override
        protected Optional<NotificationChannel> findExistingItemWithNonNullId(Pair<Integer, Integer> id) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("notificationChannelSearch.query.template");
            String query = String.format(template, id.getLeft(), id.getRight());
            ResponseData<NotificationChannelResult> responseData = nerdGraphClient.doRequest(NotificationChannelResult.class, typeConfiguration.getNewRelicAccess().getEndpoint(), "", typeConfiguration.getNewRelicAccess().getApiKey(), query, ImmutableList.of(ErrorCode.BAD_USER_INPUT.name()));
            return Optional.ofNullable(responseData.getActor().getAccount().getAlerts().getNotificationChannel());
        }

        @Override
        public List<NotificationChannel> readExistingItems() throws Exception {
            List<NotificationChannel> results = Lists.newArrayList();
            String template = nerdGraphClient.getGraphQLTemplate("notificationChannelRead.query.template");
            String cursorTemplate = nerdGraphClient.getGraphQLTemplate("notificationChannelReadCursor.query.template");
            String query = String.format(template, model.getAccountId());

            ResponseData<NotificationChannelResult> responseData = nerdGraphClient.doRequest(NotificationChannelResult.class, typeConfiguration.getNewRelicAccess().getEndpoint(), "", typeConfiguration.getNewRelicAccess().getApiKey(), query);
            results.addAll(responseData.getActor().getAccount().getAlerts().getNotificationChannels().getChannels());
            String nextCursor = responseData.getActor().getAccount().getAlerts().getNotificationChannels().getNextCursor();
            while (nextCursor != null) {
                String cursorQuery = String.format(cursorTemplate, model.getAccountId(), nextCursor);
                responseData = nerdGraphClient.doRequest(NotificationChannelResult.class, typeConfiguration.getNewRelicAccess().getEndpoint(), "", typeConfiguration.getNewRelicAccess().getApiKey(), cursorQuery);
                nextCursor = responseData.getActor().getAccount().getAlerts().getNotificationChannels().getNextCursor();
                results.addAll(responseData.getActor().getAccount().getAlerts().getNotificationChannels().getChannels());
            }
            return results;
        }

        @Override
        public ResourceModel modelFromItem(NotificationChannel channelResult) {
            ResourceModel.ResourceModelBuilder builder = ResourceModel.builder();
            builder.accountId(model.getAccountId());
            builder.channelId(model.getChannelId());
            if (channelResult != null) {
                builder.channelId(channelResult.getChannelId());
                ChannelInput.ChannelInputBuilder channelBuilder = ChannelInput.builder();
                if (CHANNEL_TYPE_EMAIL.equals(channelResult.getType())) {
                    channelBuilder.email(Email.builder()
                                    .includeJson(channelResult.getConfig().getIncludeJson())
                                    .name(channelResult.getName())
                                    .emails(channelResult.getConfig().getEmails())
                            .build());
                }
                builder.channel(channelBuilder.build());
            }

            return builder.build();
        }

        @Override
        public NotificationChannel createItem() throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("notificationChannelCreate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), nerdGraphClient.genGraphQLArg(model.getChannel(), ImmutableList.of(ChannelInput.class.getPackage().getName())));
            ResponseData<NotificationChannelResult> responseData = nerdGraphClient.doRequest(NotificationChannelResult.class, typeConfiguration.getNewRelicAccess().getEndpoint(), "", typeConfiguration.getNewRelicAccess().getApiKey(), mutation);

            checkForChannelErrors(responseData.getAlertCreateResult().getError(), "NerdGraph alertsNotificationChannelCreate mutation");

            return responseData.getAlertCreateResult().getNotificationChannel();
        }

        @Override
        public void updateItem(NotificationChannel alertEntityResult, List<String> updates) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("notificationChannelUpdate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getChannelId(), nerdGraphClient.genGraphQLArg(model.getChannel(), ImmutableList.of(ChannelInput.class.getPackage().getName())));
            ResponseData<NotificationChannelResult> responseData = nerdGraphClient.doRequest(NotificationChannelResult.class, typeConfiguration.getNewRelicAccess().getEndpoint(), "", typeConfiguration.getNewRelicAccess().getApiKey(), mutation);
            if (responseData.getAlertUpdateResult() != null) {
                updates.add("Updated");
            }

            checkForChannelErrors(responseData.getAlertUpdateResult().getError(), "NerdGraph alertsNotificationChannelUpdate mutation");
        }

        @Override
        public void deleteItem(NotificationChannel alertEntityResult) throws Exception {
            String template = nerdGraphClient.getGraphQLTemplate("notificationChannelDelete.mutation.template");
            String mutation = String.format(template, model.getAccountId(), model.getChannelId());
            ResponseData<NotificationChannelResult> responseData = nerdGraphClient.doRequest(NotificationChannelResult.class, typeConfiguration.getNewRelicAccess().getEndpoint(), "", typeConfiguration.getNewRelicAccess().getApiKey(), mutation);

            checkForChannelErrors(responseData.getAlertDeleteResult().getError(), "NerdGraph alertsNotificationChannelDelete mutation");

        }

        private void checkForChannelErrors(ChannelError channelError, String operation) throws CfnServiceInternalErrorException {
            if (channelError != null) {
                throw new CfnServiceInternalErrorException(
                        operation,
                        new Exception(String.format(
                                "The following error(s) occurred interacting with the NerdGraph API:\n==> [%s] %s",
                                channelError.getErrorType(),
                                channelError.getDescription())));
            }
        }
    }
}