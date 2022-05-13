# Cloudsoft::NewRelic::Dashboard Widget

A widget object definition.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#id" title="Id">Id</a>" : <i>String</i>,
    "<a href="#visualization" title="Visualization">Visualization</a>" : <i>String</i>,
    "<a href="#layout" title="Layout">Layout</a>" : <i>Integer</i>,
    "<a href="#title" title="Title">Title</a>" : <i>String</i>,
    "<a href="#configuration" title="Configuration">Configuration</a>" : <i><a href="typedconfiguration.md">TypedConfiguration</a>, <a href="billboardconfiguration.md">BillboardConfiguration</a>, <a href="markdownconfiguration.md">MarkdownConfiguration</a></i>,
    "<a href="#rawconfiguration" title="RawConfiguration">RawConfiguration</a>" : <i><a href="typedconfiguration.md">TypedConfiguration</a>, <a href="areametricconfiguration.md">AreaMetricConfiguration</a>, <a href="bulletconfiguration.md">BulletConfiguration</a>, <a href="linemetricconfiguration.md">LineMetricConfiguration</a>, <a href="servicemapconfiguration.md">ServiceMapConfiguration</a></i>
}
</pre>

### YAML

<pre>
<a href="#id" title="Id">Id</a>: <i>String</i>
<a href="#visualization" title="Visualization">Visualization</a>: <i>String</i>
<a href="#layout" title="Layout">Layout</a>: <i>Integer</i>
<a href="#title" title="Title">Title</a>: <i>String</i>
<a href="#configuration" title="Configuration">Configuration</a>: <i><a href="typedconfiguration.md">TypedConfiguration</a>, <a href="billboardconfiguration.md">BillboardConfiguration</a>, <a href="markdownconfiguration.md">MarkdownConfiguration</a></i>
<a href="#rawconfiguration" title="RawConfiguration">RawConfiguration</a>: <i><a href="typedconfiguration.md">TypedConfiguration</a>, <a href="areametricconfiguration.md">AreaMetricConfiguration</a>, <a href="bulletconfiguration.md">BulletConfiguration</a>, <a href="linemetricconfiguration.md">LineMetricConfiguration</a>, <a href="servicemapconfiguration.md">ServiceMapConfiguration</a></i>
</pre>

## Properties

#### Id

The id of the widget.

_Required_: Yes

_Type_: String

_Minimum_: <code>1</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Visualization

The widget visualization type, as a string. For example: viz.line, viz.area. See the examples below.

_Required_: No

_Type_: String

_Allowed Values_: <code>area</code> | <code>area (metric)</code> | <code>bar</code> | <code>billboard</code> | <code>line</code> | <code>line (metric)</code> | <code>markdown</code> | <code>pie</code> | <code>table</code> | <code>bullet</code> | <code>event feed</code> | <code>funnel</code> | <code>heatmap</code> | <code>histogram</code> | <code>inventory</code> | <code>json</code> | <code>service map</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Layout

The widget's position and size in the dashboard. The maximum amount of columns is 12.

_Required_: No

_Type_: Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Title

The title of the widget.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Configuration

_Required_: No

_Type_: <a href="typedconfiguration.md">TypedConfiguration</a>, <a href="billboardconfiguration.md">BillboardConfiguration</a>, <a href="markdownconfiguration.md">MarkdownConfiguration</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### RawConfiguration

_Required_: No

_Type_: <a href="typedconfiguration.md">TypedConfiguration</a>, <a href="areametricconfiguration.md">AreaMetricConfiguration</a>, <a href="bulletconfiguration.md">BulletConfiguration</a>, <a href="linemetricconfiguration.md">LineMetricConfiguration</a>, <a href="servicemapconfiguration.md">ServiceMapConfiguration</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

