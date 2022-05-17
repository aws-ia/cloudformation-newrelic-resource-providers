# Cloudsoft::NewRelic::Dashboard DashboardCreateResult

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#entityresult" title="entityResult">entityResult</a>" : <i><a href="dashboardentityresult.md">DashboardEntityResult</a></i>,
    "<a href="#errors" title="errors">errors</a>" : <i>[ <a href="dashboardcreateerror.md">DashboardCreateError</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#entityresult" title="entityResult">entityResult</a>: <i><a href="dashboardentityresult.md">DashboardEntityResult</a></i>
<a href="#errors" title="errors">errors</a>: <i>
      - <a href="dashboardcreateerror.md">DashboardCreateError</a></i>
</pre>

## Properties

#### entityResult

Dashboard creation result

_Required_: No

_Type_: <a href="dashboardentityresult.md">DashboardEntityResult</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### errors

Expected errors while processing request

_Required_: No

_Type_: List of <a href="dashboardcreateerror.md">DashboardCreateError</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

