package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DashboardEntityResult {
    @JsonProperty("accountId")
    private Integer accountId;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("guid")
    private String guid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("updatedAt")
    private Date updatedAt;

    public Integer getAccountId() {
        return accountId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getGuid() {
        return guid;
    }

    public String getName() {
        return name;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
