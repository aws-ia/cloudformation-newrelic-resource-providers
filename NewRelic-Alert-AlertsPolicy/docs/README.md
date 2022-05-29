# NewRelic::Alert::AlertsPolicy

Manage New Relic AlertsPolicy

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "NewRelic::Alert::AlertsPolicy",
    "Properties" : {
        "<a href="#alertspolicyid" title="AlertsPolicyId">AlertsPolicyId</a>" : <i>Integer</i>,
        "<a href="#alertspolicy" title="AlertsPolicy">AlertsPolicy</a>" : <i><a href="alertspolicyinput.md">AlertsPolicyInput</a></i>
    }
}
</pre>

### YAML

<pre>
Type: NewRelic::Alert::AlertsPolicy
Properties:
    <a href="#alertspolicyid" title="AlertsPolicyId">AlertsPolicyId</a>: <i>Integer</i>
    <a href="#alertspolicy" title="AlertsPolicy">AlertsPolicy</a>: <i><a href="alertspolicyinput.md">AlertsPolicyInput</a></i>
</pre>

## Properties

#### AlertsPolicyId

Alerts Policy ID.

_Required_: Yes

_Type_: Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### AlertsPolicy

_Required_: No

_Type_: <a href="alertspolicyinput.md">AlertsPolicyInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

## Return Values

### Fn::GetAtt

The `Fn::GetAtt` intrinsic function returns a value for a specified attribute of this type. The following are the available attributes and sample return values.

For more information about using the `Fn::GetAtt` intrinsic function, see [Fn::GetAtt](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-getatt.html).

#### AccountId

Account ID the alerts policy should belong to.

#### AlertsPolicyCreateResult

Returns the <code>AlertsPolicyCreateResult</code> value.

