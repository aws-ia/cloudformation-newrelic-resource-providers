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

public class DeleteHandler extends BaseHandler<CallbackContext> {

    private String NR_HOST = "https://api.eu.newrelic.com/graphql";
    private String NR_API_KEY = "NRAK-5SE8PNXSYWYDOGSHDYX5HBWMLH4";

    private Util util;

    public DeleteHandler() {
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
            final Logger logger) {

        final ResourceModel model = request.getDesiredResourceState();

        try {
            String template = util.getGraphQLTemplate("dashboardDelete.mutation.template");
            String mutation = String.format(template, model.getDashboardId());
            GraphQLResponseData graphQLResponse = util.doRequest(NR_HOST, "", NR_API_KEY, mutation);

            if (graphQLResponse.getDashboardDeleteResult() != null && graphQLResponse.getDashboardDeleteResult().getErrors() != null) {
                return ProgressEvent.<ResourceModel, CallbackContext>builder()
                        .resourceModel(model)
                        .message(graphQLResponse.getDashboardDeleteResult().getErrors()
                                .stream()
                                .map(dashboardDeleteError -> String.format("==> %s", dashboardDeleteError.getDescription()))
                                .collect(Collectors.joining("\n", "The following error occurred while trying to delete the New Relic dashboard:\n", "")))
                        .errorCode(HandlerErrorCode.ServiceInternalError)
                        .status(OperationStatus.FAILED)
                        .build();
            }

            model.setDashboardId(null);
            model.setDashboard(null);
            model.setAccountId(null);
        } catch (BaseHandlerException ex) {
            return ProgressEvent.<ResourceModel, CallbackContext>builder()
                    .resourceModel(model)
                    .message(ex.getMessage())
                    .errorCode(ex.getErrorCode())
                    .status(OperationStatus.FAILED)
                    .build();
        } catch (RuntimeException ex) {
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
