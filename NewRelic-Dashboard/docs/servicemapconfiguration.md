# Cloudsoft::NewRelic::Dashboard ServiceMapConfiguration

Configuration object for the widget Service Map.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
    "<a href="#additionalentities" title="AdditionalEntities">AdditionalEntities</a>" : <i>[ Map, ... ]</i>,
    "<a href="#deemphasizedconditions" title="DeemphasizedConditions">DeemphasizedConditions</a>" : <i>[ Map, ... ]</i>,
    "<a href="#hiddenentities" title="HiddenEntities">HiddenEntities</a>" : <i>[ Map, ... ]</i>,
    "<a href="#primaryentities" title="PrimaryEntities">PrimaryEntities</a>" : <i>[ Map, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
<a href="#additionalentities" title="AdditionalEntities">AdditionalEntities</a>: <i>
      - Map</i>
<a href="#deemphasizedconditions" title="DeemphasizedConditions">DeemphasizedConditions</a>: <i>
      - Map</i>
<a href="#hiddenentities" title="HiddenEntities">HiddenEntities</a>: <i>
      - Map</i>
<a href="#primaryentities" title="PrimaryEntities">PrimaryEntities</a>: <i>
      - Map</i>
</pre>

## Properties

#### AccountId

Source account to fetch data from.

_Required_: Yes

_Type_: Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### AdditionalEntities

Optional. An array of additional entities to include. The target is the entity downstream, while the source is upstream. Type, vendor, target, and source are optional.

_Required_: No

_Type_: List of Map

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### DeemphasizedConditions

Optional. Entities with these conditions have a faded appearance in the map.

_Required_: No

_Type_: List of Map

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### HiddenEntities

Optional. Entities to be excluded from the map.

_Required_: No

_Type_: List of Map

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### PrimaryEntities

The primary entities which start the map (most upstream).

_Required_: Yes

_Type_: List of Map

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

