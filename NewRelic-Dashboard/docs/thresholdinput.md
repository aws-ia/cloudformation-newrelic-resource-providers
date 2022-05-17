# Cloudsoft::NewRelic::Dashboard ThresholdInput

Threshold to categorize the results of the query in different groups.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#alertseverity" title="AlertSeverity">AlertSeverity</a>" : <i>String</i>,
    "<a href="#value" title="Value">Value</a>" : <i>String</i>
}
</pre>

### YAML

<pre>
<a href="#alertseverity" title="AlertSeverity">AlertSeverity</a>: <i>String</i>
<a href="#value" title="Value">Value</a>: <i>String</i>
</pre>

## Properties

#### AlertSeverity

_Required_: Yes

_Type_: String

_Allowed Values_: <code>NOT_ALERTING</code> | <code>WARNING</code> | <code>CRITICAL</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Value

_Required_: Yes

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

