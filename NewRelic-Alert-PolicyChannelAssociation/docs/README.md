# NewRelic::Alert::PolicyChannelAssociation

Manage New Relic Notification Channel

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "NewRelic::Alert::PolicyChannelAssociation",
    "Properties" : {
        "<a href="#channelids" title="ChannelIds">ChannelIds</a>" : <i>[ Integer, ... ]</i>,
    }
}
</pre>

### YAML

<pre>
Type: NewRelic::Alert::PolicyChannelAssociation
Properties:
    <a href="#channelids" title="ChannelIds">ChannelIds</a>: <i>
      - Integer</i>
</pre>

## Properties

#### ChannelIds

_Required_: Yes

_Type_: List of Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

## Return Values

### Fn::GetAtt

The `Fn::GetAtt` intrinsic function returns a value for a specified attribute of this type. The following are the available attributes and sample return values.

For more information about using the `Fn::GetAtt` intrinsic function, see [Fn::GetAtt](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-getatt.html).

#### AccountId

Account ID the alerts condition should belong to.

#### PolicyId

Returns the <code>PolicyId</code> value.

