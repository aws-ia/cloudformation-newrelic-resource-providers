# NewRelic::Agent::Configuration

Manage New Relic Server-Side Agent Configuration

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "NewRelic::Agent::Configuration",
    "Properties" : {
        "<a href="#guid" title="Guid">Guid</a>" : <i>String</i>,
        "<a href="#agentconfiguration" title="AgentConfiguration">AgentConfiguration</a>" : <i><a href="agentconfigurationinput.md">AgentConfigurationInput</a></i>
    }
}
</pre>

### YAML

<pre>
Type: NewRelic::Agent::Configuration
Properties:
    <a href="#guid" title="Guid">Guid</a>: <i>String</i>
    <a href="#agentconfiguration" title="AgentConfiguration">AgentConfiguration</a>: <i><a href="agentconfigurationinput.md">AgentConfigurationInput</a></i>
</pre>

## Properties

#### Guid

The GUID for the affected Entity.

_Required_: Yes

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### AgentConfiguration

_Required_: Yes

_Type_: <a href="agentconfigurationinput.md">AgentConfigurationInput</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

## Return Values

### Ref

When you pass the logical ID of this resource to the intrinsic `Ref` function, Ref returns the Guid.
