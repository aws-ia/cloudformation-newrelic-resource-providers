# New Relic CloudFormation Resources
This collection of CloudFormation resource types allow NewRelic to be controlled using AWS CloudFormation.

There is also [developer documentation](docs/dev) available in you are interested in building or contributing.

## Set up git filter

This project uses a filter set up in the [.gitattributes](.gitattributes) file to replace private values for testing within the different `overides.json` on each resource.

The filter has to be added manually inside the `.git/config` file once the repository has been cloned.

Executing this in the console from the project root will add it. Replace the values inside the __square__ brackets with the actual values for testing

```properties
cat << EOF >> .git/config
[filter "newrelic-data"]
	clean = sed \\
		-e 's:[agentGuid]:<NR_AGENT_GUID>:g' \\
		-e 's:[accountId]:<NR_ACCOUNT_ID>:g' \\
		-e 's:[policyId]:<NR_POLICY_ID>:g' \\
		-e 's:[channel1Id]:<NR_CHANNEL1_ID>:g' \\
		-e 's:[channel2Id]:<NR_CHANNEL2_ID>:g' \\
		-e 's:[channel3Id]:<NR_CHANNEL3_ID>:g' 
	smudge = sed \\
		-e 's:<NR_AGENT_GUID>:[agentGuid]:g' \\
		-e 's:<NR_ACCOUNT_ID>:[accountId]:g' \\
		-e 's:<NR_POLICY_ID>:[policyId]:g' \\
		-e 's:<NR_CHANNEL1_ID>:[channel1Id]:g' \\
		-e 's:<NR_CHANNEL2_ID>:[channel2Id]:g' \\
		-e 's:<NR_CHANNEL3_ID>:[channel3Id]:g'
EOF

git checkout */overrides.json
```
