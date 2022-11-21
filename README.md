# New Relic CloudFormation Resource Types

This collection of [AWS CloudFormation resource types][1] allow NewRelic to be controlled using [AWS CloudFormation][2].

| Resource | Description | Documentation |
| --- | --- | --- |
| NewRelic::Alert::AlertsPolicy | This resource type manages a [New Relic AlertsPolicy][3] | [/NewRelic-Alert-AlertsPolicy][4] |
| NewRelic::Agent::Configuration | This resource type manages a [New Relic Configuration][5] | [/NewRelic-Agent-Configuration][6] |
| NewRelic::Alert::NrqlConditionStatic | This resource type manages a [static NRQL Alert Condition in NewRelic ][7] | [/NewRelic-Alert-NrqlConditionStatic][8] |
| NewRelic::Dashboard::Dashboard | This resource type manages a [New Relic dashboard ][9] | [/NewRelic-Dashboard-Dashboar][10] |

## Prerequisites
* [AWS Account][14]
* [AWS CLI][15]
* [New Relic account][16] and [API key][17]
## AWS Management Console

To get started:

1. Sign in to the [AWS Management Console][11] with your account and navigate to CloudFormation.

2. Select "Public extensions" from the left hand pane and filter Publisher by "Third Party".

3. Use the search bar to filter by the "NewRelic" prefix.

  Note: All official  New Relic resources begin with `NewRelic::` and specify that they are `Published by New Relic`.

4. Select the desired resource name to view more information about its schema, and click **Activate**.

5. On the **Extension details** page, specify:
  - Extension name
  - Execution role ARN
  - Automatic updates for minor version releases
  - Configuration

6. In your terminal, specify the configuration data for the registered New Relic CloudFormation resource type, in the given account and region by using the **SetTypeConfiguration** operation:


  For example:

  ```Bash
  $ aws cloudformation set-type-configuration     --region us-west-2     --type RESOURCE     --type-name NewRelic::Alert::AlertsPolicy     --configuration-alias default     --configuration "{ \"NewRelicAccess\": {   \"Endpoint\": \"https://api.newrelic.com/graphql\",   \"ApiKey\": \"YOURAPIKEY\" } }"
  ```

7. After you have your resource configured, [create your AWS stack][12] that includes any of the activated NewRelic resources.

For more information about available commands and workflows, see the official [AWS documentation][13].

## Supported regions

The Datadog-Amazon CloudFormation resources are available on the CloudFormation Public Registry in the following regions:

| Code            | Name                      |
|-----------------|---------------------------|
| us-east-1       | US East (N. Virginia)     |
| us-east-2       | US East (Ohio)            |
| us-west-1       | US West (N. California)   |
| us-west-2       | US West (Oregon)          |
| ap-south-1      | Asia Pacific (Mumbai)     |
| ap-northeast-1  | Asia Pacific (Tokyo)      |
| ap-northeast-2  | Asia Pacific (Seoul)      |
| ap-southeast-1  | Asia Pacific (Singapore)  |
| ap-southeast-2  | Asia Pacific (Sydney)     |
| ca-central-1    | Canada (Central)          |
| eu-central-1    | Europe (Frankfurt)        |
| eu-west-1       | Europe (Ireland)          |
| eu-west-2       | Europe (London)           |
| eu-west-3       | Europe (Paris)            |
| eu-north-1      | Europe (Stockholm)        |
| sa-east-1       | South America (São Paulo) |

**Note**: To privately register a resource in any other region, use the provided packages.

## Examples

### Dashboard example using the Cloudformation NewRelic resources
```yaml
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

### Shows how to create an Alert Policy in NewRelic
```yaml
---
AWSTemplateFormatVersion: '2010-09-09'
Description: Shows how to create an Alert Policy in NewRelic
Resources:
  MySampleProject:
    Type: NewRelic::Alert::AlertsPolicy
    Properties:
      AccountId: 3504143
      AlertsPolicyId: 123
      AlertsPolicy:
        Name: My CF Created Policy
        IncidentPreference:  PER_POLICY
```

### Shows how to set agent configuration in NewRelic.
```yaml
---
---
AWSTemplateFormatVersion: '2010-09-09'
Description: Shows how to set agent configuration in NewRelic.
Resources:
  AgentConfigurationSample:
    Type: NewRelic::Agent::Configuration
    Properties:
      Guid: MzUwNDE0M3xBUE18QVBQTElDQVRJT058NDE5OTY3OTEw
      AgentConfiguration:
        Settings:
          Alias: "My Test Alias"
          ApmConfig:
            ApdexTarget: 4
            UseServerSideConfig: true
          BrowserConfig:
            ApdexTarget: 6
          SlowSql:
            Enabled: true
```

### Shows how to create a static NRQL Alert Condition in NewRelic
```yaml
---
AWSTemplateFormatVersion: '2010-09-09'
Description: Shows how to create a static NRQL Alert Condition in NewRelic
Resources:
  SampleNrqlCondition:
    Type: NewRelic::Alert::NrqlConditionStatic
    Properties:
      AccountId: 3504143
      PolicyId: 646591
      Condition:
        Name: "Sample Condition"
        Description: "Sample NRQL Condition"
        Enabled: true
        Expiration:
          CloseViolationsOnExpiration: true
          ExpirationDuration: 60
          OpenViolationOnExpiration: true
        Nrql:
          Query: "SELECT count(*) FROM NrAuditEvent"
        Terms:
          Operator: "ABOVE"
          Threshold: 6
          ThresholdDuration: 60
          ThresholdOccurrences: "ALL"
          Priority: "CRITICAL"
```

[1]: https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-types.html
[2]: https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/Welcome.html
[3]: https://docs.newrelic.com/docs/alerts-applied-intelligence/new-relic-alerts/alert-policies/create-edit-or-find-alert-policy/
[4]: ./NewRelic-Alert-AlertsPolicy/docs/README.md
[5]: https://docs.newrelic.com/docs/apm/agents/manage-apm-agents/configuration/view-config-values-your-app/
[6]: ./NewRelic-Agent-Configuration//docs/README.md
[7]: https://docs.newrelic.com/docs/alerts-applied-intelligence/new-relic-alerts/alert-conditions/create-nrql-alert-conditions/
[8]: ./NewRelic-Alert-NrqlConditionStatic/docs/README.md
[9]: https://docs.newrelic.com/docs/query-your-data/explore-query-data/dashboards/introduction-dashboards/
[10]: ./NewRelic-Dashboard-Dashboard/docs/README.md
[11]: https://aws.amazon.com/console/
[12]: https://console.aws.amazon.com/cloudformation/home
[13]: https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/registry.html
[14]: https://aws.amazon.com/account/
[15]: https://aws.amazon.com/cli/
[16]: https://newrelic.com/
[17]: https://docs.newrelic.com/docs/apis/intro-apis/new-relic-api-keys/