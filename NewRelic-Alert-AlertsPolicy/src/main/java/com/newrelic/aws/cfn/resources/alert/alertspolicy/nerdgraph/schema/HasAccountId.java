package com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface HasAccountId {
    @JsonProperty("accountId")
    public Integer getAccountId();
}
