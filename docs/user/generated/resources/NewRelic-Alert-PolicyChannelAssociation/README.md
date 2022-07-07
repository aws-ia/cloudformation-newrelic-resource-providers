# NewRelic::Alert::PolicyChannelAssociation

Manage New Relic Notification Channel

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "NewRelic::Alert::PolicyChannelAssociation",
    "Properties" : {
        "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
        "<a href="#channelids" title="ChannelIds">ChannelIds</a>" : <i>[ Integer, ... ]</i>,
        "<a href="#policyid" title="PolicyId">PolicyId</a>" : <i>Integer</i>
    }
}
</pre>

### YAML

<pre>
Type: NewRelic::Alert::PolicyChannelAssociation
Properties:
    <a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
    <a href="#channelids" title="ChannelIds">ChannelIds</a>: <i>
      - Integer</i>
    <a href="#policyid" title="PolicyId">PolicyId</a>: <i>Integer</i>
</pre>

## Properties

#### AccountId

Account ID the alerts condition should belong to.

_Required_: Yes

_Type_: Integer

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### ChannelIds

_Required_: Yes

_Type_: List of Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### PolicyId

_Required_: Yes

_Type_: Integer

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

