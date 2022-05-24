# Cloudsoft::NewRelic::Dashboard LineMetricWidgetConfigurationInput

Configuration object for the widget Area (Metric)

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
    "<a href="#comparewith" title="CompareWith">CompareWith</a>" : <i>Map</i>,
    "<a href="#duration" title="Duration">Duration</a>" : <i>Double</i>,
    "<a href="#endtime" title="EndTime">EndTime</a>" : <i>Double</i>,
    "<a href="#entityids" title="EntityIds">EntityIds</a>" : <i>[ Integer, ... ]</i>,
    "<a href="#facets" title="Facets">Facets</a>" : <i>String</i>,
    "<a href="#limit" title="Limit">Limit</a>" : <i>Integer</i>,
    "<a href="#metrics" title="Metrics">Metrics</a>" : <i>[ Map, ... ]</i>,
    "<a href="#orderby" title="OrderBy">OrderBy</a>" : <i>String</i>
}
</pre>

### YAML

<pre>
<a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
<a href="#comparewith" title="CompareWith">CompareWith</a>: <i>Map</i>
<a href="#duration" title="Duration">Duration</a>: <i>Double</i>
<a href="#endtime" title="EndTime">EndTime</a>: <i>Double</i>
<a href="#entityids" title="EntityIds">EntityIds</a>: <i>
      - Integer</i>
<a href="#facets" title="Facets">Facets</a>: <i>String</i>
<a href="#limit" title="Limit">Limit</a>: <i>Integer</i>
<a href="#metrics" title="Metrics">Metrics</a>: <i>
      - Map</i>
<a href="#orderby" title="OrderBy">OrderBy</a>: <i>String</i>
</pre>

## Properties

#### AccountId

Source account to fetch data from.

_Required_: Yes

_Type_: Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### CompareWith

Optional. Additional time window to query. For example: {
offsetDurationInput: 86400000
presentation: {"#c001", "Yesterday"}
}

_Required_: No

_Type_: Map

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Duration

Duration of the requested time window, in milliseconds. When provided with endTime, the time window is set to the last x milliseconds ending at the specified time. If endTime is null, the time window is set to the last x milliseconds ending now.

_Required_: Yes

_Type_: Double

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### EndTime

Optional. End of the time window, in milliseconds.

_Required_: Yes

_Type_: Double

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### EntityIds

Array of source agent Ids to fetch data from.

_Required_: Yes

_Type_: List of Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Facets

Optional. Facet the data by the given attribute. It can be host, agent, application, or mobile_version.

_Required_: No

_Type_: String

_Allowed Values_: <code>host</code> | <code>agent</code> | <code>application</code> | <code>mobile_version</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Limit

Optional. Maximum amount of series to be returned.

_Required_: No

_Type_: Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Metrics

List of metrics to be fetched.

_Required_: Yes

_Type_: List of Map

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### OrderBy

Optional. Used to sort the results in descending order.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

