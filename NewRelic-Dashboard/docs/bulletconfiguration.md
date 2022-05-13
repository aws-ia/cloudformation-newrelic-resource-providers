# Cloudsoft::NewRelic::Dashboard BulletConfiguration

Configuration object for the widget Line (Metric).

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
    "<a href="#query" title="Query">Query</a>" : <i>String</i>,
    "<a href="#limit" title="Limit">Limit</a>" : <i>Double</i>
}
</pre>

### YAML

<pre>
<a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
<a href="#query" title="Query">Query</a>: <i>String</i>
<a href="#limit" title="Limit">Limit</a>: <i>Double</i>
</pre>

## Properties

#### AccountId

Source account to fetch data from.

_Required_: Yes

_Type_: Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Query

The NRQL query that provides the data for the widget.

_Required_: Yes

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Limit

Goal against which all query results are compared to.

_Required_: Yes

_Type_: Double

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

