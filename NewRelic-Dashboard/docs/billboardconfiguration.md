# Cloudsoft::NewRelic::Dashboard BillboardConfiguration

Configuration object for the widget Billboard.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
    "<a href="#query" title="Query">Query</a>" : <i>String</i>,
    "<a href="#thresholds" title="Thresholds">Thresholds</a>" : <i>[ <a href="threshold.md">Threshold</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
<a href="#query" title="Query">Query</a>: <i>String</i>
<a href="#thresholds" title="Thresholds">Thresholds</a>: <i>
      - <a href="threshold.md">Threshold</a></i>
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

#### Thresholds

Optional. Array of thresholds to categorize the results of the query in different groups.

_Required_: No

_Type_: List of <a href="threshold.md">Threshold</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

