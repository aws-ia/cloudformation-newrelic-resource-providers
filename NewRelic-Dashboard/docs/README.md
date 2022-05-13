# Cloudsoft::NewRelic::Dashboard

Manage New Relic Dashboard, including widgets

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "Cloudsoft::NewRelic::Dashboard",
    "Properties" : {
        "<a href="#title" title="Title">Title</a>" : <i>String</i>,
        "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
        "<a href="#pages" title="Pages">Pages</a>" : <i>[ <a href="page.md">Page</a>, ... ]</i>
    }
}
</pre>

### YAML

<pre>
Type: Cloudsoft::NewRelic::Dashboard
Properties:
    <a href="#title" title="Title">Title</a>: <i>String</i>
    <a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
    <a href="#pages" title="Pages">Pages</a>: <i>
      - <a href="page.md">Page</a></i>
</pre>

## Properties

#### Title

Title of the dashboard.

_Required_: Yes

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### AccountId

Account ID the dashboard should belong to.

_Required_: Yes

_Type_: Integer

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### Pages

An array of dashboard pages to attach to this resource.

_Required_: Yes

_Type_: List of <a href="page.md">Page</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

## Return Values

### Ref

When you pass the logical ID of this resource to the intrinsic `Ref` function, Ref returns the DashboardId.

### Fn::GetAtt

The `Fn::GetAtt` intrinsic function returns a value for a specified attribute of this type. The following are the available attributes and sample return values.

For more information about using the `Fn::GetAtt` intrinsic function, see [Fn::GetAtt](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-getatt.html).

#### DashboardId

Unique identifier of the current dashboard

