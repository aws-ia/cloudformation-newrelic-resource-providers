# Cloudsoft::NewRelic::Dashboard TypeWidgetConfigurationInput

Configuration object for standard typed widget. This includes: Area, Bar, Line, Pie, Table, Event Feed, Funnel, Heatmap, Histogram, JSON

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#nrqlqueries" title="NrqlQueries">NrqlQueries</a>" : <i>[ <a href="nrqlqueryinput.md">NrqlQueryInput</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#nrqlqueries" title="NrqlQueries">NrqlQueries</a>: <i>
      - <a href="nrqlqueryinput.md">NrqlQueryInput</a></i>
</pre>

## Properties

#### NrqlQueries

_Required_: No

_Type_: List of <a href="nrqlqueryinput.md">NrqlQueryInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

