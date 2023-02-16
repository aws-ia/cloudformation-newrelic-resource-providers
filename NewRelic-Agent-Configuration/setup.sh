#!/bin/bash
#
# Set up any prerequisites needed for cfn test
#
mkdir -p inputs
cat example_inputs/inputs_1_create.json | sed "s/NEWRELIC_GUID/${NEWRELIC_GUID}/g" > inputs/inputs_1_create.json
cat example_inputs/inputs_1_update.json  | sed "s/NEWRELIC_GUID/${NEWRELIC_GUID}/g" > inputs/inputs_1_update.json
cat test/integ-template.yml | sed "s/NEWRELIC_GUID/${NEWRELIC_GUID}/g" > test/integ.yml

