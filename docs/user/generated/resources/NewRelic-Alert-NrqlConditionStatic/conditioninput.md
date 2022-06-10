# NewRelic::Alert::NrqlConditionStatic ConditionInput

Input settings for the static NRQL condition.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#description" title="Description">Description</a>" : <i>String</i>,
    "<a href="#enabled" title="Enabled">Enabled</a>" : <i>Boolean</i>,
    "<a href="#expiration" title="Expiration">Expiration</a>" : <i><a href="conditioninput.md">ConditionInput</a></i>,
    "<a href="#name" title="Name">Name</a>" : <i>String</i>,
    "<a href="#nrql" title="Nrql">Nrql</a>" : <i><a href="conditioninput.md">ConditionInput</a></i>,
    "<a href="#runbookurl" title="RunbookUrl">RunbookUrl</a>" : <i>String</i>,
    "<a href="#signal" title="Signal">Signal</a>" : <i><a href="conditioninput.md">ConditionInput</a></i>,
    "<a href="#terms" title="Terms">Terms</a>" : <i><a href="conditioninput.md">ConditionInput</a></i>,
    "<a href="#violationtimelimitseconds" title="ViolationTimeLimitSeconds">ViolationTimeLimitSeconds</a>" : <i>Integer</i>
}
</pre>

### YAML

<pre>
<a href="#description" title="Description">Description</a>: <i>String</i>
<a href="#enabled" title="Enabled">Enabled</a>: <i>Boolean</i>
<a href="#expiration" title="Expiration">Expiration</a>: <i><a href="conditioninput.md">ConditionInput</a></i>
<a href="#name" title="Name">Name</a>: <i>String</i>
<a href="#nrql" title="Nrql">Nrql</a>: <i><a href="conditioninput.md">ConditionInput</a></i>
<a href="#runbookurl" title="RunbookUrl">RunbookUrl</a>: <i>String</i>
<a href="#signal" title="Signal">Signal</a>: <i><a href="conditioninput.md">ConditionInput</a></i>
<a href="#terms" title="Terms">Terms</a>: <i><a href="conditioninput.md">ConditionInput</a></i>
<a href="#violationtimelimitseconds" title="ViolationTimeLimitSeconds">ViolationTimeLimitSeconds</a>: <i>Integer</i>
</pre>

## Properties

#### Description

The custom violation description.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Enabled

Whether the NRQL condition is enabled.

_Required_: Yes

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Expiration

_Required_: No

_Type_: <a href="conditioninput.md">ConditionInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Name

Name of the NRQL condition.

_Required_: Yes

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Nrql

_Required_: Yes

_Type_: <a href="conditioninput.md">ConditionInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### RunbookUrl

Runbook URL.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Signal

_Required_: No

_Type_: <a href="conditioninput.md">ConditionInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Terms

_Required_: Yes

_Type_: <a href="conditioninput.md">ConditionInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### ViolationTimeLimitSeconds

Duration after which a violation automatically closes in seconds

_Required_: No

_Type_: Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

