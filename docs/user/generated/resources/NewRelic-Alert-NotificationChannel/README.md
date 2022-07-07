# NewRelic::Alert::NotificationChannel

Manage New Relic Notification Channel

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "NewRelic::Alert::NotificationChannel",
    "Properties" : {
        "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
        "<a href="#channel" title="Channel">Channel</a>" : <i><a href="channelinput.md">ChannelInput</a></i>
    }
}
</pre>

### YAML

<pre>
Type: NewRelic::Alert::NotificationChannel
Properties:
    <a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
    <a href="#channel" title="Channel">Channel</a>: <i><a href="channelinput.md">ChannelInput</a></i>
</pre>

## Properties

#### AccountId

Account ID the alerts condition should belong to.

_Required_: Yes

_Type_: Integer

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### Channel

_Required_: Yes

_Type_: <a href="channelinput.md">ChannelInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

## Return Values

### Fn::GetAtt

The `Fn::GetAtt` intrinsic function returns a value for a specified attribute of this type. The following are the available attributes and sample return values.

For more information about using the `Fn::GetAtt` intrinsic function, see [Fn::GetAtt](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-getatt.html).

#### ChannelId

Returns the <code>ChannelId</code> value.

