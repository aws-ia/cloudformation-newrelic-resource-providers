# Cloudsoft::NewRelic::Dashboard Page

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#name" title="Name">Name</a>" : <i>String</i>,
    "<a href="#widgets" title="Widgets">Widgets</a>" : <i>[ <a href="widget.md">Widget</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#name" title="Name">Name</a>: <i>String</i>
<a href="#widgets" title="Widgets">Widgets</a>: <i>
      - <a href="widget.md">Widget</a></i>
</pre>

## Properties

#### Name

Name of the dashboard page

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Widgets

An array of widget objects to attach to this resource.

_Required_: No

_Type_: List of <a href="widget.md">Widget</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

