# NewRelic::Dashboard::Dashboard PageInput

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#name" title="Name">Name</a>" : <i>String</i>,
    "<a href="#description" title="Description">Description</a>" : <i>String</i>,
    "<a href="#pageid" title="PageId">PageId</a>" : <i>String</i>,
    "<a href="#widgets" title="Widgets">Widgets</a>" : <i>[ <a href="widgetinput.md">WidgetInput</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#name" title="Name">Name</a>: <i>String</i>
<a href="#description" title="Description">Description</a>: <i>String</i>
<a href="#pageid" title="PageId">PageId</a>: <i>String</i>
<a href="#widgets" title="Widgets">Widgets</a>: <i>
      - <a href="widgetinput.md">WidgetInput</a></i>
</pre>

## Properties

#### Name

Name of the dashboard page.

_Required_: Yes

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Description

Description of the dashboard page.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### PageId

Unique entity identifier of the page to be updated. When null, it means a new PageInput will be created.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Widgets

An array of widget objects to attach to this resource.

_Required_: Yes

_Type_: List of <a href="widgetinput.md">WidgetInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

