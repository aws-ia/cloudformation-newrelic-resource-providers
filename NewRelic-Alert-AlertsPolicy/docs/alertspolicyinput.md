# NewRelic::Alert::AlertsPolicy AlertsPolicyInput

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#name" title="Name">Name</a>" : <i>String</i>,
    "<a href="#incidentpreference" title="IncidentPreference">IncidentPreference</a>" : <i>String</i>
}
</pre>

### YAML

<pre>
<a href="#name" title="Name">Name</a>: <i>String</i>
<a href="#incidentpreference" title="IncidentPreference">IncidentPreference</a>: <i>String</i>
</pre>

## Properties

#### Name

Name of the alerts policy.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### IncidentPreference

_Required_: No

_Type_: String

_Allowed Values_: <code>PER_CONDITION</code> | <code>PER_CONDITION_AND_TARGET</code> | <code>PER_POLICY</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

