# Cloudsoft::NewRelic::Dashboard WidgetVisualizationInput

Specifies how this widget will be visualized. If null, the WidgetConfigurationInput will be used to determine the visualization.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#id" title="Id">Id</a>" : <i>String</i>
}
</pre>

### YAML

<pre>
<a href="#id" title="Id">Id</a>: <i>String</i>
</pre>

## Properties

#### Id

Nerdpack artifact ID

_Required_: Yes

_Type_: String

_Allowed Values_: <code>viz.area</code> | <code>viz.bar</code> | <code>viz.billboard</code> | <code>viz.line</code> | <code>viz.markdown</code> | <code>viz.pie</code> | <code>viz.table</code> | <code>viz.bullet</code> | <code>viz.eventfeed</code> | <code>viz.funnel</code> | <code>viz.heatmap</code> | <code>viz.histogram</code> | <code>viz.inventory</code> | <code>viz.json</code> | <code>viz.servicemap</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

