# NewRelic::Agent::Configuration

Manage New Relic Server-Side Agent Configuration

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "NewRelic::Agent::Configuration",
    "Properties" : {
        "<a href="#accountid" title="AccountId">AccountId</a>" : <i>Integer</i>,
        "<a href="#agentconfiguration" title="AgentConfiguration">AgentConfiguration</a>" : <i><a href="agentconfigurationinput.md">AgentConfigurationInput</a></i>
    }
}
</pre>

### YAML

<pre>
Type: NewRelic::Agent::Configuration
Properties:
    <a href="#accountid" title="AccountId">AccountId</a>: <i>Integer</i>
    <a href="#agentconfiguration" title="AgentConfiguration">AgentConfiguration</a>: <i><a href="agentconfigurationinput.md">AgentConfigurationInput</a></i>
</pre>

## Properties

#### AccountId

Account ID the alerts policy should belong to.

_Required_: Yes

_Type_: Integer

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### AgentConfiguration

_Required_: Yes

_Type_: <a href="agentconfigurationinput.md">AgentConfigurationInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

## Return Values

### Fn::GetAtt

The `Fn::GetAtt` intrinsic function returns a value for a specified attribute of this type. The following are the available attributes and sample return values.

For more information about using the `Fn::GetAtt` intrinsic function, see [Fn::GetAtt](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-getatt.html).

#### AgentConfigurationId

Agent Configuration ID.

#### AgentConfigurationResult

Returns the <code>AgentConfigurationResult</code> value.

