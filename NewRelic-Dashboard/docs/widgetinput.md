# Cloudsoft::NewRelic::Dashboard WidgetInput

A widget object definition.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#id" title="Id">Id</a>" : <i>String</i>,
    "<a href="#visualization" title="Visualization">Visualization</a>" : <i>String</i>,
    "<a href="#layout" title="Layout">Layout</a>" : <i><a href="layoutinput.md">LayoutInput</a></i>,
    "<a href="#title" title="Title">Title</a>" : <i>String</i>,
    "<a href="#configuration" title="Configuration">Configuration</a>" : <i><a href="widgetinputconfigurationinput.md">WidgetInputConfigurationInput</a></i>,
    "<a href="#rawconfiguration" title="RawConfiguration">RawConfiguration</a>" : <i><a href="typewidgetinputconfigurationinputinput.md">TypeWidgetInputConfigurationInputInput</a>, <a href="areametricinputwidgetinputconfigurationinputinput.md">AreaMetricInputWidgetInputConfigurationInputInput</a>, <a href="bulletwidgetinputconfigurationinputinput.md">BulletWidgetInputConfigurationInputInput</a>, <a href="linemetricinputwidgetinputconfigurationinputinput.md">LineMetricInputWidgetInputConfigurationInputInput</a>, <a href="servicemapwidgetinputconfigurationinputinput.md">ServiceMapWidgetInputConfigurationInputInput</a></i>
}
</pre>

### YAML

<pre>
<a href="#id" title="Id">Id</a>: <i>String</i>
<a href="#visualization" title="Visualization">Visualization</a>: <i>String</i>
<a href="#layout" title="Layout">Layout</a>: <i><a href="layoutinput.md">LayoutInput</a></i>
<a href="#title" title="Title">Title</a>: <i>String</i>
<a href="#configuration" title="Configuration">Configuration</a>: <i><a href="widgetinputconfigurationinput.md">WidgetInputConfigurationInput</a></i>
<a href="#rawconfiguration" title="RawConfiguration">RawConfiguration</a>: <i><a href="typewidgetinputconfigurationinputinput.md">TypeWidgetInputConfigurationInputInput</a>, <a href="areametricinputwidgetinputconfigurationinputinput.md">AreaMetricInputWidgetInputConfigurationInputInput</a>, <a href="bulletwidgetinputconfigurationinputinput.md">BulletWidgetInputConfigurationInputInput</a>, <a href="linemetricinputwidgetinputconfigurationinputinput.md">LineMetricInputWidgetInputConfigurationInputInput</a>, <a href="servicemapwidgetinputconfigurationinputinput.md">ServiceMapWidgetInputConfigurationInputInput</a></i>
</pre>

## Properties

#### Id

Id of the widget. If null, a new widget will be created and added to a dashboard.

_Required_: No

_Type_: String

_Minimum_: <code>1</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Visualization

The widget visualization type, as a string. For example: viz.line, viz.area. See the examples below.

_Required_: No

_Type_: String

_Allowed Values_: <code>viz.area</code> | <code>viz.bar</code> | <code>viz.billboard</code> | <code>viz.line</code> | <code>viz.markdown</code> | <code>viz.pie</code> | <code>viz.table</code> | <code>viz.bullet</code> | <code>viz.eventfeed</code> | <code>viz.funnel</code> | <code>viz.heatmap</code> | <code>viz.histogram</code> | <code>viz.inventory</code> | <code>viz.json</code> | <code>viz.servicemap</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Layout

The widget's position and size in the dashboard. The maximum amount of columns is 12.

_Required_: No

_Type_: <a href="layoutinput.md">LayoutInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Title

The title of the widget.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Configuration

_Required_: No

_Type_: <a href="widgetinputconfigurationinput.md">WidgetInputConfigurationInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### RawConfiguration

_Required_: No

_Type_: <a href="typewidgetinputconfigurationinputinput.md">TypeWidgetInputConfigurationInputInput</a>, <a href="areametricinputwidgetinputconfigurationinputinput.md">AreaMetricInputWidgetInputConfigurationInputInput</a>, <a href="bulletwidgetinputconfigurationinputinput.md">BulletWidgetInputConfigurationInputInput</a>, <a href="linemetricinputwidgetinputconfigurationinputinput.md">LineMetricInputWidgetInputConfigurationInputInput</a>, <a href="servicemapwidgetinputconfigurationinputinput.md">ServiceMapWidgetInputConfigurationInputInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

