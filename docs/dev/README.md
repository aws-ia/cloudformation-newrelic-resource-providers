# NewRelic CloudFormation Resources - Developer Documentation

## Read Me First: Potential Surprises

* Some surprising folders are blown away during the build.
  * `NewRelic-*/docs/`: so any custom docs should be placed in **docs-extra**
  * `generated/` including **/docs/generated/user/**:
    so any user-facing docs should be placed e.g. in `docs-extra/` so they are copied

* The projects mainly use Live Tests. These require access to NewRelic and parameters as described below.


## Building and Pre-Requisites

This project uses the Resource Provider Development Kit to create customer resource types for AWS CloudFormation.

You should first set up the `aws` and `cfn` command-line tools following the instructions
[here](https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-walkthrough.html).

You should then be able to build the project without tests with: `mvn clean install -DskipTests`


## Tests

### Live Java Tests

This project uses live tests. There isn't much else to test! The live tests require an access token for
connecting to NewRelic and a few other arguments, set in the follow environment variables
or in a properties file which can be sourced prior to running the tests:

```
# A valid API key for newrelic.com
NR_API_KEY=XXXX-YYYYYYYYYYYYYYYYYYYYYYYYYYY

# A valid NewRelic Account ID
NR_ACCOUNT_ID=3504143

# The endpoint used to access the NewRelic API in your region, e.g. the European endpoing 
NR_ENDPOINT=https://api.eu.newrelic.com/graphql

# The GUID of a NewRelic APM agent
NR_AGENT_GUID=MzUwNDE0M3xBUE18QVBQTElDQVRJT058NDE5OTY3OTEw

# The ID of a NewRelic Alert Policy
NR_POLICY_ID=646591

# The IDs of three NewRelic Notification channels (Email, Slack, PagerDuty and WebHook are currently supported)
NR_CHANNEL1_ID=342614
NR_CHANNEL2_ID=342615
NR_CHANNEL3_ID=342616
```

With the above set, `mvn clean install` should work.

### Serverless and Contract Tests

This project can also use [CloudFormation Serverless and Contract Tests](https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-test.html),
using [AWS Serverless Application Model (SAM)](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html).
These allow:

* Local testing of resource code as lambdas, to ensure payload encapsulation and results for individual requests
* CFN contract tests, to ensure compliance with CFN expectations (some of which are a little surprising)
  and to test sequences of requests possibly including callbacks (although NewRelic resources are quick enough they do not use callbacks)

This project does not perform this testing automatically, nor does it maintain test artifacts in all cases,
due to the time to run and overhead of creating dependencies.  The Java Live tests give good coverage,
and the resources are tested as installed to CloudFormation.
However from time to time it may be useful to run serverless tests, to confirm obscure contract compliance
and to test serverless locally.

To run sererless tests, first ensure `sam` is set up and available (per the links above).


#### Local Serverless Single Request Testing

To run a single local serverless test, simply create a file containing the payload, including the type configuration;
e.g. the following `sam-tests/create-1.json`:

```
{
    "credentials": {
        # Real STS credentials need to go here.
        "accessKeyId": "",
        "secretAccessKey": "",
        "sessionToken": ""
    },
    "action": "CREATE",
    "request": {
        # Can be any UUID.
        "clientRequestToken": "4b90a7e4-b790-456b-a937-0cfdfa211dfe", 
        "desiredResourceState": {
            # specify properties required as per resource schema
            "Name": "test" 
        },
        "logicalResourceIdentifier": "TestResource"
    },
    "callbackContext": null,
    "typeConfiguration": {
      "endPoint": "",
      "apiKey": ""
    }
}
```

(Remove the lines which start with `#`.)

The test can then be invoked and the output inspected using the CLI:

```
sam local invoke TestEntrypoint --event sam-tests/create-1.json
```


#### CFN Contract Testing

To use the standard `cfn test` contract testing automation, which runs a series of commands,
including callbacks, and some create-update-delete cycles, you must first set the type configuration
to use. This is done in `~/.cfn-cli/typeConfiguration.json`:
```
{
  "Endpoint": "https://api.eu.newrelic.com/graphql",
  "ApiKey": "XXXX-YYYYYYYYYYYYYYYYYYYYYYYYYYY"
}
```

For most resources it is also necessary to set specific parameters to pass for CREATE and UPDATE,
which can be set in the `overrides.json` file in the project root, e.g. in `NewRelic-Alert-PolicyChannelAssociation`:

```
{
  "CREATE": {
    "/AccountId": 3504143,
    "/ChannelIds": [342613, 342614],
    "/PolicyId": 646591
  },
  "UPDATE": {
    "/AccountId": 3504143,
    "/ChannelIds": [342704],
    "/PolicyId": 646591
  }
}

```

If there are resources in NewRelic which must exist prior to execution, add them manually
(or in `template.yml` and you can then refer to them per the instructions above).

Next start the lambda:

```
sam local start-lambda
```

And in another terminal window run the tests:

```
cfn test
```

If tests do not pass, it can be useful to inspect `rpdk.log` as well as the output in each of the terminal windows,
and the [Python test suite source code](https://github.com/aws-cloudformation/cloudformation-cli/blob/master/src/rpdk/core/contract/suite/).


## Registering Types and Running Examples

First build the resources (optionally `-DskipTests` for speed or if a live test environment is not available):

```
mvn clean install
```

And register them, either individually:

```
cd NewRelic-$XXX-$YYY
cfn submit --set-default
```

Or looping through all of them (optionally include the `set-type-configuration` command from above in the loop,
to set the type configuraition for each resource):

```
for x in NewRelic-* ; do
  cd $x
  TYPE=$(echo $x | sed s/-/::/g)
  echo Registering $TYPE...
  cfn submit --set-default
  cd ..
done
```

### Setting Type Configuration

If this is the first time registering, you will need to set up the type configuration used for
each of these types, containing the api key for connecting to NewRelic.
We recommend entering the access token into Systems Manager, say under path `/cfn/newrelic/apiKey`,
and then referring to it, e.g. as `{{resolve:ssm-secure:/cfn/newrelic/apiKey}}`.

Once stored in SSM, it can be set for each resource type as follows:

```
TYPE=NewRelic::${XXX}::${YYY}
ENDPOINT=https://api.eu.newrelic.com/graphql
SSM_PATH_TO_API_KEY=/cfn/newRelic/apiKey
aws --output yaml --no-cli-pager cloudformation set-type-configuration \            
  --type RESOURCE --type-name NewRelic::Agent::Configuration \                              
  --configuration-alias default \
  --configuration '{"Endpoint": "'$ENDPOINT'", "ApiKey": "{{resolve:ssm-secure:'${SSM_PATH_TO_API_KEY}'}}"}'
```

Or looping through all of them:

```
ENDPOINT=https://api.eu.newrelic.com/graphql
SSM_PATH_TO_API_KEY=/cfn/newRelic/apiKey
for x in NewRelic-* ; do
  TYPE=$(echo $x | sed s/-/::/g)
  echo Setting type configuration for $TYPE...
  aws --output yaml --no-cli-pager cloudformation set-type-configuration \
    --type RESOURCE --type-name $TYPE \
    --configuration-alias default \
    --configuration '{"Endpoint": "'$ENDPOINT'", "ApiKey": "{{resolve:ssm-secure:'${SSM_PATH_TO_API_KEY}'}}"}'
done
```

To inspect the current values:

```
for x in NewRelic-* ; do
  TYPE=$(echo $x | sed s/-/::/g)
  aws --output yaml --no-cli-pager cloudformation batch-describe-type-configurations \
    --type-configuration-identifiers Type=RESOURCE,TypeName=$TYPE

done
```

### Running Examples

Edit the values in the relevant example YAML (e.g. picking a valid parent group ID) and deploy with:

```
aws cloudformation create-stack --stack-name newrelic-test --template-body file://docs/path/to/resource/example.yaml
```

You should see the stack update in the CloudFormation console, and see the result in GitLab.

Thereafter you can `update-stack` and `delete-stack` in the usual way, combining these resources with others,
passing references, as per normal CloudFormation..


## Defining New Resources

### Design Notes

Due to the highly regular nature of the NewRelic API, much of the work is done by abstract superclasses.
However in order to be able to leverage `cfn generate` to autogenerate model classes on schema changes,
there is a bit of a subtle pattern to route from the generated `HandlerWrapper` to the generated-once-and-required
`CreateHandler` (and others) then back to a single concrete combined handler extending the abstract superclass.

The scaffolding is thus a bit clumsy, but it means the actual code needed to be written and maintained for
any resource is quite small, and the majority of the CFN contract behaviour including error checks and
required responses is handled automatically.

There is also scaffolding for tests allowing a default pattern of CRUD to be easily and automatically written,
extended with other tests where desired.


### How to Add Code for a New NewRelic Resource

The recommended way to add a new resource is to use `cfn init` in the directory for the new resource,
e.g. `NewRelic-Xxxx-Yyyyy`,
using a package such as `com.newrelic.aws.cfn.resources.xxxx.yyyy`.

Then to prepare the code, we typically:

* Copy the `XxxxResourceHandler` from one of the other projects, renaming as appropriate.
* Modify the 5 handlers as per other projects (making each `extends XxxxResourceHandler.BaseHandlerAdapter {}`)
* Delete the `example_inputs` folder, `.gitignore` file, and `src/resources` folder
* Delete the generated tests `*.java`
* Copy the `XxxxCrudlLiveTest` from one of the other projects, renaming as appropriate.
* Copy a `docs-extra/example.yaml` from one of the other projects, renaming as appropriate.
* Modify the `pom.xml` to match one of the other projects
* Modify the `template.yml` to match one of the other projects (remove "-handler" and change version to 1.0.0-SNAPSHOT)
* Replace most of the `newrelic-xxxx-yyyy.json` with the entries from another project, renaming as appropriate.
* Add `NewRelic-Xxxx-Yyyy` to the parent `pom.xml`.

Then to develop:

* Edit the properties in `newrelic-xxxx-yyyy.json` as appropriate.
* Run `cfn generate`.
* Edit the `XxxxResourceHandler` and `XxxxCrudlLiveTest` to do the right thing for this resource.
* Copy SAM tests from another project and edit to do the right thing for this resource.
* Create an example in `docs-extra/example.yaml` and test it (as above).

