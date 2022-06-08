# NewRelic::Alert::NrqlConditionStatic

Manage New Relic NRQL Static Alerts Condition

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "NewRelic::Alert::NrqlConditionStatic",
    "Properties" : {
        "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
        "<a href="#condition" title="Condition">Condition</a>" : <i><a href="conditioninput.md">ConditionInput</a></i>,
        "<a href="#policyid" title="PolicyId">PolicyId</a>" : <i>Integer</i>
    }
}
</pre>

### YAML

<pre>
Type: NewRelic::Alert::NrqlConditionStatic
Properties:
    <a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
    <a href="#condition" title="Condition">Condition</a>: <i><a href="conditioninput.md">ConditionInput</a></i>
    <a href="#policyid" title="PolicyId">PolicyId</a>: <i>Integer</i>
</pre>

## Properties

#### AccountId

Account ID the alerts condition should belong to.

_Required_: Yes

_Type_: Integer

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### Condition

Input settings for the static NRQL condition.

_Required_: Yes

_Type_: <a href="conditioninput.md">ConditionInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### PolicyId

Policy ID for the condition.

_Required_: Yes

_Type_: Integer

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

## Return Values

### Fn::GetAtt

The `Fn::GetAtt` intrinsic function returns a value for a specified attribute of this type. The following are the available attributes and sample return values.

For more information about using the `Fn::GetAtt` intrinsic function, see [Fn::GetAtt](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-getatt.html).

#### ConditionId

Alerts Condition ID.

#### ConditionCreateResult

Returns the <code>ConditionCreateResult</code> value.

