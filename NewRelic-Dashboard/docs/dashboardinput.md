# Cloudsoft::NewRelic::Dashboard DashboardInput

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#name" title="Name">Name</a>" : <i>String</i>,
    "<a href="#description" title="Description">Description</a>" : <i>String</i>,
    "<a href="#pages" title="Pages">Pages</a>" : <i>[ <a href="pageinput.md">PageInput</a>, ... ]</i>,
    "<a href="#permissions" title="Permissions">Permissions</a>" : <i>String</i>
}
</pre>

### YAML

<pre>
<a href="#name" title="Name">Name</a>: <i>String</i>
<a href="#description" title="Description">Description</a>: <i>String</i>
<a href="#pages" title="Pages">Pages</a>: <i>
      - <a href="pageinput.md">PageInput</a></i>
<a href="#permissions" title="Permissions">Permissions</a>: <i>String</i>
</pre>

## Properties

#### Name

Name of the dashboard.

_Required_: Yes

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Description

Description of the dashboard.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Pages

An array of dashboard pages to attach to this resource.

_Required_: Yes

_Type_: List of <a href="pageinput.md">PageInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Permissions

_Required_: Yes

_Type_: String

_Allowed Values_: <code>PRIVATE</code> | <code>PUBLIC_READ_ONLY</code> | <code>PUBLIC_READ_WRITE</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

