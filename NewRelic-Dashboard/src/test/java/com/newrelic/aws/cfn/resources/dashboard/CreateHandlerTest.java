package com.newrelic.aws.cfn.resources.dashboard;

import com.google.common.collect.ImmutableList;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CreateHandlerTest {

    @Mock
    private AmazonWebServicesClientProxy proxy;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setup() {
        proxy = mock(AmazonWebServicesClientProxy.class);
        logger = mock(Logger.class);
    }

    @Test
    public void handleRequest_SimpleSuccess() {
        final CreateHandler handler = new CreateHandler();

        final ResourceModel model = ResourceModel.builder()
                .accountId(3495167)
                .dashboard(DashboardInput.builder()
                        .name("My Dashboard")
                        .description("Dashboard for my new app")
                        .pages(ImmutableList.of(PageInput.builder()
                                .name("Page 1")
                                .description("Page 1 of my new dashboard")
                                .widgets(ImmutableList.of(
                                        WidgetInput.builder()
                                                .configuration(
                                                        WidgetInputConfigurationInput.builder()
                                                                .line(TypeWidgetInputConfigurationInputInput.builder()
                                                                        .nrqlQueries(ImmutableList.of(NrqlQueryInput.builder()
                                                                                .accountId(3495167)
                                                                                .query("SELECT count(*) FROM Transaction FACET appName TIMESERIES")
                                                                                .build()))
                                                                        .build())
                                                                .build())
                                                .title("Widget A")
                                                .build()
                                ))
                                .build()))
                        .permissions("PRIVATE")
                        .build())
                .build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        final TypeConfigurationModel typeConfigurationModel = TypeConfigurationModel.builder()
                .endpoint("https://api.eu.newrelic.com/graphql")
                .apiKey("NRAK-5SE8PNXSYWYDOGSHDYX5HBWMLH4")
                .build();

        final ProgressEvent<ResourceModel, CallbackContext> response
            = handler.handleRequest(proxy, request, null, logger, typeConfigurationModel);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isEqualTo(request.getDesiredResourceState());
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }
}
