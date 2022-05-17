# Cloudsoft::NewRelic::Dashboard BillboardWidgetInputConfigurationInputInput

Configuration object for the widget Billboard.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#nrqlqueries" title="NrqlQueries">NrqlQueries</a>" : <i>[ <a href="nrqlqueryinput.md">NrqlQueryInput</a>, ... ]</i>,
    "<a href="#thresholds" title="Thresholds">Thresholds</a>" : <i>[ <a href="thresholdinput.md">ThresholdInput</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#nrqlqueries" title="NrqlQueries">NrqlQueries</a>: <i>
      - <a href="nrqlqueryinput.md">NrqlQueryInput</a></i>
<a href="#thresholds" title="Thresholds">Thresholds</a>: <i>
      - <a href="thresholdinput.md">ThresholdInput</a></i>
</pre>

## Properties

#### NrqlQueries

_Required_: No

_Type_: List of <a href="nrqlqueryinput.md">NrqlQueryInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Thresholds

Optional. Array of thresholds to categorize the results of the query in different groups.

_Required_: No

_Type_: List of <a href="thresholdinput.md">ThresholdInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

