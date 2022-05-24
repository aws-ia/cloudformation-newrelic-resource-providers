# Cloudsoft::NewRelic::Dashboard InventoryWidgetConfigurationInput

Configuration object for the widget Inventory.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
    "<a href="#filters" title="Filters">Filters</a>" : <i>Map</i>,
    "<a href="#sources" title="Sources">Sources</a>" : <i>[ String, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
<a href="#filters" title="Filters">Filters</a>: <i>Map</i>
<a href="#sources" title="Sources">Sources</a>: <i>
      - String</i>
</pre>

## Properties

#### AccountId

Source account to fetch data from.

_Required_: No

_Type_: Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Filters

Map of the filters to be applied to the infrastructure sources.

_Required_: No

_Type_: Map

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Sources

List of the infrastructure sources to get data from.

_Required_: No

_Type_: List of String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

