# Creating a Dashboard in NewRelic: Dashboards, Pages, and Widgets with CloudFormation

The following CloudFormation NewRelic resource types can be useful to create a new Dashboard with associated pages and
widgets. This lets infrastructure-as-code, checked in to source control,
be used to create and remove dashboard very simply.

We will use the following types:

* `NewRelic::Dashboard::Dashboard` - to create and configure the dashboard

In this example, we are created a Dashboard with a single page, which contains a single widget. The widget is configured
to display data from the Transaction table over a timeseries

```
---
AWSTemplateFormatVersion: '2010-09-09'
Description: Shows how to create a Dashboard in NewRelic
Resources:
  MySampleProject:
    Type: NewRelic::Dashboard::Dashboard
    Properties:
      AccountId: 3504143
      Dashboard:
        Name: Dashboard Created using NewRelic::Dashboard::Dashboard
        Description: Dashboard example using the Cloudformation NewRelic resources
        Pages:
        - Name: First Page
          Description: First and only page for this dashboard
          Widgets:
          - Title: My first widget
            Configuration:
              Line:
                NrqlQueries:
                - AccountId: 3504143
                  Query: "SELECT count(*) FROM Transaction FACET appName TIMESERIES"
            Visualization:
              Id: viz.line
        Permissions: PRIVATE
```

### Try It

The complete [example code](example.yaml) is included with this documentation.
Edit the default values, then deploy this in the CloudFormation Console or at the command-line with:

```
aws cloudformation create-stack --stack-name newrelic-dashboard --template-body file://example.yaml
```

The following should then be visible in NewRelic:

![NewRelic Console](alert.png)
