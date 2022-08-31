# NewRelic CloudFormation Resources

This collection of CloudFormation resource types allow NewRelic to be controlled using AWS CloudFormation.

### Why would I want to do this?

Infrastructure-as-code such as CloudFormation is one of the best ways to create and maintain infrastructure that is reliable and secure. Or a CloudFormation template might just be more convenient for some types of automation.

Here are some sample use cases this supports:

* [Manage server-side Agent Configuration](stories/agent-configuration/)
* [Set up a new Alert Policy with Notification Channels and Alerts](stories/alert/)
* [Create a new Dashboard with custom Pages, Widgets and Layout](stories/dashboard/)

### How do I get started?

In the AWS CloudFormation UI, find the NewRelic types in the third-party registry and activate them.
Alternatively follow the [Developer](../dev) instructions to install them manually.

You will need to set up a [Type Configuration](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/cloudformation/set-type-configuration.html)
for each of the activated types, containing a NewRelic **API Key** and **Endpoint**.
It is recommended to set the API Key into Systems Manager's secure parameter store,
e.g. as `/path/to/newrelic/apiKey`, and then it can be applied to type `${NEWRELIC_TYPE}`,
e.g. `NewRelic::Alert::AlertsPolicy`, using:

```
aws cloudformation set-type-configuration \
  --region eu-north-1 \
  --type RESOURCE \
  --type-name ${NEWRELIC_TYPE} \
  --configuration-alias default \
  --configuration '{"Endpoint": "'$ENDPOINT'", "ApiKey": "{{resolve:ssm-secure:'${SSM_PATH_TO_API_KEY}'}}"}'
```

You should then be able to run the example use cases above or build your own using the full reference below.


### What is supported?

This project does not support all the objects in NewRelic, nor does it support all the properties on the
objects it does support. For many things there just isn't a compelling use case, and of course there are a lot.
We have focussed on those objects and properties which have seemed most useful.
If we missed something, open an issue to let us know, or even better, contribute an extension!

The **Full NewRelic CloudFormation Resources Reference** is available [here](resources/).
