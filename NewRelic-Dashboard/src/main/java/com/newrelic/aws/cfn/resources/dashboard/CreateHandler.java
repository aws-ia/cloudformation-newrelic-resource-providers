package com.newrelic.aws.cfn.resources.dashboard;


import com.newrelic.aws.cfn.resources.dashboard.graphql.GraphQLResponseData;
import com.newrelic.aws.cfn.resources.dashboard.graphql.Util;
import software.amazon.cloudformation.exceptions.BaseHandlerException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.HandlerErrorCode;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.stream.Collectors;

public class CreateHandler extends BaseHandler<CallbackContext, TypeConfigurationModel> {

    private Util util;

    public CreateHandler() {
        this.util = new Util();
    }

    public void setUtil(Util util) {
        this.util = util;
    }

    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(
            final AmazonWebServicesClientProxy proxy,
            final ResourceHandlerRequest<ResourceModel> request,
            final CallbackContext callbackContext,
            final Logger logger,
            final TypeConfigurationModel typeConfiguration) {

        final ResourceModel model = request.getDesiredResourceState();

        try {
            String template = util.getGraphQLTemplate("dashboardCreate.mutation.template");
            String mutation = String.format(template, model.getAccountId(), util.genGraphQLArg(model.getDashboard()));
            GraphQLResponseData graphQLResponse = util.doRequest(typeConfiguration.getEndpoint(), "", typeConfiguration.getApiKey(), mutation);

            if (graphQLResponse.getDashboardCreateResult() != null && graphQLResponse.getDashboardCreateResult().getErrors() != null) {
                String errorMessage = graphQLResponse.getDashboardCreateResult().getErrors()
                        .stream()
                        .map(dashboardCreateError -> String.format("==> %s", dashboardCreateError.getDescription()))
                        .collect(Collectors.joining("\n", "The following error occurred while trying to create the New Relic dashboard:\n", ""));
                logger.log(errorMessage);
                return ProgressEvent.<ResourceModel, CallbackContext>builder()
                        .resourceModel(model)
                        .message(errorMessage)
                        .errorCode(HandlerErrorCode.ServiceInternalError)
                        .status(OperationStatus.FAILED)
                        .build();
            }

            model.setDashboardId(graphQLResponse.getDashboardCreateResult().getEntityResult().getGuid());
        } catch (BaseHandlerException ex) {
            logger.log(ex.getMessage());
            return ProgressEvent.<ResourceModel, CallbackContext>builder()
                    .resourceModel(model)
                    .message(ex.getMessage())
                    .errorCode(ex.getErrorCode())
                    .status(OperationStatus.FAILED)
                    .build();
        } catch (RuntimeException ex) {
            logger.log(ex.getMessage());
            return ProgressEvent.<ResourceModel, CallbackContext>builder()
                    .resourceModel(model)
                    .message(ex.getMessage())
                    .errorCode(HandlerErrorCode.InternalFailure)
                    .status(OperationStatus.FAILED)
                    .build();
        }

        return ProgressEvent.<ResourceModel, CallbackContext>builder()
                .resourceModel(model)
                .status(OperationStatus.SUCCESS)
                .build();
    }
}
