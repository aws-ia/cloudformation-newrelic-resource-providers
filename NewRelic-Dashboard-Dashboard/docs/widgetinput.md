# NewRelic::Dashboard::Dashboard WidgetInput

A widget object definition.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#id" title="Id">Id</a>" : <i>String</i>,
    "<a href="#visualization" title="Visualization">Visualization</a>" : <i><a href="widgetvisualizationinput.md">WidgetVisualizationInput</a></i>,
    "<a href="#layout" title="Layout">Layout</a>" : <i><a href="layoutinput.md">LayoutInput</a></i>,
    "<a href="#title" title="Title">Title</a>" : <i>String</i>,
    "<a href="#configuration" title="Configuration">Configuration</a>" : <i><a href="widgetconfigurationinput.md">WidgetConfigurationInput</a></i>,
    "<a href="#rawconfiguration" title="RawConfiguration">RawConfiguration</a>" : <i><a href="typewidgetconfigurationinput.md">TypeWidgetConfigurationInput</a>, <a href="areametricwidgetconfigurationinput.md">AreaMetricWidgetConfigurationInput</a>, <a href="bulletwidgetconfigurationinput.md">BulletWidgetConfigurationInput</a>, <a href="inventorywidgetconfigurationinput.md">InventoryWidgetConfigurationInput</a>, <a href="linemetricwidgetconfigurationinput.md">LineMetricWidgetConfigurationInput</a>, <a href="servicemapwidgetconfigurationinput.md">ServiceMapWidgetConfigurationInput</a></i>
}
</pre>

### YAML

<pre>
<a href="#id" title="Id">Id</a>: <i>String</i>
<a href="#visualization" title="Visualization">Visualization</a>: <i><a href="widgetvisualizationinput.md">WidgetVisualizationInput</a></i>
<a href="#layout" title="Layout">Layout</a>: <i><a href="layoutinput.md">LayoutInput</a></i>
<a href="#title" title="Title">Title</a>: <i>String</i>
<a href="#configuration" title="Configuration">Configuration</a>: <i><a href="widgetconfigurationinput.md">WidgetConfigurationInput</a></i>
<a href="#rawconfiguration" title="RawConfiguration">RawConfiguration</a>: <i><a href="typewidgetconfigurationinput.md">TypeWidgetConfigurationInput</a>, <a href="areametricwidgetconfigurationinput.md">AreaMetricWidgetConfigurationInput</a>, <a href="bulletwidgetconfigurationinput.md">BulletWidgetConfigurationInput</a>, <a href="inventorywidgetconfigurationinput.md">InventoryWidgetConfigurationInput</a>, <a href="linemetricwidgetconfigurationinput.md">LineMetricWidgetConfigurationInput</a>, <a href="servicemapwidgetconfigurationinput.md">ServiceMapWidgetConfigurationInput</a></i>
</pre>

## Properties

#### Id

Id of the widget. If null, a new widget will be created and added to a dashboard.

_Required_: No

_Type_: String

_Minimum_: <code>1</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Visualization

Specifies how this widget will be visualized. If null, the WidgetConfigurationInput will be used to determine the visualization.

_Required_: No

_Type_: <a href="widgetvisualizationinput.md">WidgetVisualizationInput</a>

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

_Type_: <a href="widgetconfigurationinput.md">WidgetConfigurationInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### RawConfiguration

_Required_: No

_Type_: <a href="typewidgetconfigurationinput.md">TypeWidgetConfigurationInput</a>, <a href="areametricwidgetconfigurationinput.md">AreaMetricWidgetConfigurationInput</a>, <a href="bulletwidgetconfigurationinput.md">BulletWidgetConfigurationInput</a>, <a href="inventorywidgetconfigurationinput.md">InventoryWidgetConfigurationInput</a>, <a href="linemetricwidgetconfigurationinput.md">LineMetricWidgetConfigurationInput</a>, <a href="servicemapwidgetconfigurationinput.md">ServiceMapWidgetConfigurationInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

