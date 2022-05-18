package com.newrelic.aws.cfn.resources.dashboard.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * There are more properties for this general GraphQL error, but we don't really care about them for now.
 * For instance, here is an example:
 *     {
 *       "extensions": {
 *         "errorClass": "ACCESS_DENIED"
 *       },
 *       "locations": [
 *         {
 *           "column": 3,
 *           "line": 2
 *         }
 *       ],
 *       "message": "Could not validate account access to the following guids:\n\ngfhfdghf: Invalid entity guid\n",
 *       "path": [
 *         "dashboardDelete"
 *       ]
 *     }
 */
@JsonIgnoreProperties
public class GraphQLResponseError {

    @JsonProperty("message")
    private String message;

    @JsonProperty("path")
    private List<String> path;

    public String getMessage() {
        return message;
    }

    public List<String> getPath() {
        return path;
    }
}
