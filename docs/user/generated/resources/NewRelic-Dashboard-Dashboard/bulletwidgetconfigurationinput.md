# NewRelic::Dashboard::Dashboard BulletWidgetConfigurationInput

Configuration object for the widget Line (Metric).

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#nrqlqueries" title="NrqlQueries">NrqlQueries</a>" : <i>[ <a href="nrqlqueryinput.md">NrqlQueryInput</a>, ... ]</i>,
    "<a href="#limit" title="Limit">Limit</a>" : <i>Double</i>
}
</pre>

### YAML

<pre>
<a href="#nrqlqueries" title="NrqlQueries">NrqlQueries</a>: <i>
      - <a href="nrqlqueryinput.md">NrqlQueryInput</a></i>
<a href="#limit" title="Limit">Limit</a>: <i>Double</i>
</pre>

## Properties

#### NrqlQueries

_Required_: No

_Type_: List of <a href="nrqlqueryinput.md">NrqlQueryInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Limit

Goal against which all query results are compared to.

_Required_: No

_Type_: Double

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

