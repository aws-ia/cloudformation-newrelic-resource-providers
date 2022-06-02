package com.newrelic.aws.cfn.resources.alert.notificationchannel;

import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.NotificationChannel;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.NotificationChannelResult;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("Live")
public class NotificationChannelCrudLiveTest extends AbstractResourceCrudLiveTest<NotificationChannelResourceHandler, NotificationChannel, Pair<Integer, Integer>, ResourceModel, CallbackContext, TypeConfigurationModel> {
//    @Test
//    @Order(1000)
//    @Tag("Manual")
//    @SuppressWarnings("unchecked")
//    public void testListCursor() throws Exception {
//        final int createCount = 150;
//        this.model = this.newModelForCreate();
//        ProgressEvent<ResourceModel, CallbackContext> listResponse = this.invoke(Action.LIST);
//        List<ResourceModel> existingModels = listResponse.getResourceModels();
//        List<ResourceModel> conditionsCreated = Lists.newArrayList();
//        for (int i = 0; i < createCount; i++) {
//            ProgressEvent<ResourceModel, CallbackContext> response = this.invoke(Action.CREATE);
//            conditionsCreated.add(response.getResourceModel());
//        }
//        listResponse = this.invoke(Action.LIST);
//        List<ResourceModel> newModels = listResponse.getResourceModels();
//        Assertions.assertThat(newModels.size()).isEqualTo(existingModels.size() + createCount);
//        for (ResourceModel model : conditionsCreated) {
//            this.model = model;
//            ProgressEvent<ResourceModel, CallbackContext> response = this.invoke(Action.DELETE);
//        }
//
//        Awaitility.await().atMost(1, TimeUnit.MINUTES).until(() -> {
//            ProgressEvent<ResourceModel, CallbackContext> response = this.invoke(Action.LIST);
//            List<ResourceModel> modelsAfterDelete = response.getResourceModels();
//            return modelsAfterDelete.size() == existingModels.size();
//        });
//
//        ProgressEvent<ResourceModel, CallbackContext> response = this.invoke(Action.LIST);
//        List<ResourceModel> modelsAfterDelete = response.getResourceModels();
//        Assertions.assertThat(modelsAfterDelete.size()).isEqualTo(existingModels.size());
//    }

    @Override
    protected TypeConfigurationModel newTypeConfiguration() throws Exception {
        return TypeConfigurationModel.builder()
                .endpoint(System.getenv("NR_ENDPOINT"))
                .apiKey(System.getenv("NR_API_KEY"))
                .build();
    }

    @Override
    protected ResourceModel newModelForCreate() throws Exception {
        int accountId = Integer.parseInt(System.getenv("NR_ACCOUNT_ID"));
        return ResourceModel.builder()
                .accountId(accountId)
                .channel(ChannelInput.builder()
                        .email(Email.builder()
                                .name("My Email Channel")
                                .emails(ImmutableList.of("test1@somedomain.com", "test2@somedomain.com"))
                                .includeJson(true)
                                .build())
                        .build()
                )
                .build();
    }

    @Override
    protected ResourceModel newModelForUpdate() throws Exception {
        int accountId = Integer.parseInt(System.getenv("NR_ACCOUNT_ID"));
        return ResourceModel.builder()
                .accountId(accountId)
                .channelId(model.getChannelId())
                .channel(ChannelInput.builder()
                        .email(Email.builder()
                                .name("My Updated Email Channel")
                                .emails(ImmutableList.of("test3@somedomain.com"))
                                .includeJson(false)
                                .build())
                        .build()
                )
                .build();
    }

    @Override
    protected HandlerWrapper newHandlerWrapper() {
        return new HandlerWrapper();
    }
}
