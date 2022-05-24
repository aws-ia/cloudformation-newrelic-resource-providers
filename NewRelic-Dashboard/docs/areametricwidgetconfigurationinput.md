# Cloudsoft::NewRelic::Dashboard AreaMetricWidgetConfigurationInput

Configuration object for the widget Area (Metric)

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#duration" title="Duration">Duration</a>" : <i>Double</i>,
    "<a href="#endtime" title="EndTime">EndTime</a>" : <i>Double</i>,
    "<a href="#entityids" title="EntityIds">EntityIds</a>" : <i>[ Integer, ... ]</i>,
    "<a href="#metrics" title="Metrics">Metrics</a>" : <i>[ Map, ... ]</i>,
    "<a href="#type" title="Type">Type</a>" : <i>String</i>
}
</pre>

### YAML

<pre>
<a href="#duration" title="Duration">Duration</a>: <i>Double</i>
<a href="#endtime" title="EndTime">EndTime</a>: <i>Double</i>
<a href="#entityids" title="EntityIds">EntityIds</a>: <i>
      - Integer</i>
<a href="#metrics" title="Metrics">Metrics</a>: <i>
      - Map</i>
<a href="#type" title="Type">Type</a>: <i>String</i>
</pre>

## Properties

#### Duration

Duration of the requested time window, in milliseconds. When provided with endTime, the time window is set to the last x milliseconds ending at the specified time. If endTime is null, the time window is set to the last x milliseconds ending now.

_Required_: Yes

_Type_: Double

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### EndTime

Optional. End of the time window, in milliseconds.

_Required_: No

_Type_: Double

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### EntityIds

Array of source agent Ids to fetch data from.

_Required_: Yes

_Type_: List of Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Metrics

For type SCOPE_BREAKDOWN only. List of metrics to be fetched.

_Required_: No

_Type_: List of Map

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Type

Type of the predefined chart.

_Required_: Yes

_Type_: String

_Allowed Values_: <code>APPLICATION_BREAKDOWN</code> | <code>BACKGROUND_BREAKDOWN</code> | <code>BROWSER_BREAKDOWN</code> | <code>GC_RUNS_BREAKDOWN</code> | <code>SCOPE_BREAKDOWN</code> | <code>SOLR_BREAKDOWN</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

