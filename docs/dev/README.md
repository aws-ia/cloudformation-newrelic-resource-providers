# New Relic CloudFormation Resources - Developer Documentation

## Building and Pre-Requisites

This project uses the Resource Provider Development Kit to create customer resource types for AWS CloudFormation.

You should first set up the `aws` and `cfn` command-line tools following the instructions
[here](https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-walkthrough.html).

You should then be able to build the project without tests with: `mvn clean install -DskipTests`

## Tests

### Unit Tests

The project contains unit tests, mainly for the abstractions that have been created. However, most of the tests are
live ones, see section below. To run only the unit tests, use:

```bash
mvn clean verify -Dgroups="\!Live"
```

### Live Java Tests

The live tests require few environment variables to be setup:


| Envvar        | Description                                                                                  |
|---------------|----------------------------------------------------------------------------------------------|
| NR_ENDPOINT   | The Endpoint to target. E.g https://api.eu.newrelic.com/graphql if your account is in Europe |
| NR_API_KEY    | The API Key for your account                                                                 |
| NR_ACCOUNT_ID | Your account ID (this is a number)                                                           |

With the above set, `mvn clean install` or `mvn clean verify -Dgroups="Live"` should work.

### Serverless and Contract Tests

This project can also use [CloudFormation Serverless and Contract Tests](https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-test.html),
using [AWS Serverless Application Model (SAM)](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html).
These allow:

* Local testing of resource code as lambdas, to ensure payload encapsulation and results for individual requests
* CloudFormation contract tests, to ensure compliance with CloudFormation expectations (some of which are a little surprising)
  and to test sequences of requests possibly including callbacks (although New Relic resources are quick enough they do not use callbacks)

This project does not perform this testing automatically, nor does it maintain test artifacts in all cases,
due to the time to run and overhead of creating dependencies. The Java Live tests give good coverage,
and the resources are tested as installed to CloudFormation.
However, from time to time it may be useful to run serverless tests, to confirm obscure contract compliance
and to test serverless locally.

To run serverless tests, first ensure `sam` is set up and available (per the links above).

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
      # Set your actual New Relic endpoint here
      "Endpoint": "https://api.eu.newrelic.com/graphql",
      # Set your actual Api Key here
      "ApiKey": "NRAK-XXXXXXXXXXXXXXX"
    }
}
```

(Remove the lines which start with `#`.)

The test can then be invoked and the output inspected using the CLI:

```
sam local invoke TestEntrypoint --event sam-tests/create-1.json
```


#### CloudFormation Contract Testing

To use the standard `cfn test` contract testing automation, which runs a series of commands,
including callbacks, and some create-update-delete cycles, you must first set the type configuration
to use. This is done in `~/.cfn-cli/typeConfiguration.json`:
```
{
  "Endpoint": "https://api.eu.newrelic.com/graphql",
  "ApiKey": "NRAK-XXXXXXXXXXXXXXX"
}
```

For most resources it is also necessary to set specific parameters to pass for CREATE and UPDATE,
e.g. in `NewRelic-Dashboard`, you can see an example with the [overrides.json](../../NewRelic-Dashboard/overrides.json)

If there are resources in New Relic which must exist prior to execution, add them manually
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
In particular, the `read` and `list` tests can take a bit more time than the default timeout. If this happens, specifying
a higher timeout like this should work: `cfn test --enforce-timeout 60`

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
to set the type configuration for each resource):

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
each of these types, containing the endpoint and the API KEY for connecting to New Relic.
We recommend entering the api key into Systems Manager, say under path `/cfn/newrelic/api-key`,
and then referring to it, e.g. as `{{resolve:ssm-secure:/cfn/newrelic/api-key}}`.

Once stored in SSM, it can be set for each resource type as follows:

```
TYPE=NewRelic::${XXX}::${YYY}
SSM_PATH_TO_API_KEY=/cfn/newrelic/api-key
aws --output yaml --no-cli-pager cloudformation set-type-configuration \
  --type RESOURCE --type-name $TYPE \
  --configuration-alias default \
  --configuration '{"Endpoint": "https://.........", "ApiKey": "{{resolve:ssm-secure:'${SSM_PATH_TO_API_KEY}'}}"}'
```

Or looping through all of them:

```
SSM_PATH_TO_API_KEY=/cfn/newrelic/api-key
for x in NewRelic-* ; do
  TYPE=$(echo $x | sed s/-/::/g)
  echo Setting type configuration for $TYPE...
  aws --output yaml --no-cli-pager cloudformation set-type-configuration \
    --type RESOURCE --type-name $TYPE \
    --configuration-alias default \
    --configuration '{"Endpoint": "https://.........", "ApiKey": "{{resolve:ssm-secure:'${SSM_PATH_TO_API_KEY}'}}"}'
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

You should see the stack update in the CloudFormation console, and see the result in New Relic.

Thereafter, you can `update-stack` and `delete-stack` in the usual way, combining these resources with others,
passing references, as per normal CloudFormation..


## Defining New Resources

### Design Notes

Due to the highly regular nature of the New Relic API, much of the work is done by abstract superclasses (if using NerdGraph)
However, in order to be able to leverage `cfn generate` to autogenerate model classes on schema changes,
there is a bit of a subtle pattern to route from the generated `HandlerWrapper` to the generated-once-and-required
`CreateHandler` (and others) then back to a single concrete combined handler extending the abstract superclass.

The scaffolding is thus a bit clumsy, but it means the actual code needed to be written and maintained for
any resource is quite small, and the majority of the CloudFormation contract behaviour including error checks and
required responses is handled automatically, but the input should be maintained manually.

There is also scaffolding for tests allowing a default pattern of CRUD to be easily and automatically written,
extended with other tests where desired.


### How to Add Code for a new "New Relic" Resource

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

