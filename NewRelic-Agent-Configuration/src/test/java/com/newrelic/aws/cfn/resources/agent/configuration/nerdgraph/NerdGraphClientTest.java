package com.newrelic.aws.cfn.resources.agent.configuration.nerdgraph;

import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.agent.configuration.AgentConfigurationInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.cloudformation.exceptions.CfnHandlerInternalFailureException;

public class NerdGraphClientTest {

    private NerdGraphClient nerdGraphClient;

    @BeforeEach
    void setUp() {
        nerdGraphClient = new NerdGraphClient();
    }

    @Test
    void testGetGraphQLTemplateThrowsIfFileDoesNotExist() {
        Assertions.assertThrows(CfnHandlerInternalFailureException.class, () -> {
            nerdGraphClient.getGraphQLTemplate("does.not.exist.template");
        });
    }

    @Test
    void testGetGraphQLTemplateReturnsTemplateContentIfExists() {
        String template = nerdGraphClient.getGraphQLTemplate("agentConfigurationDelete.mutation.template");

        Assertions.assertEquals("query {\n    foo\n    bar\n}", template);
    }


    @Test
    void testGenGraphQLArgFiltersFieldWithNullValue() {
        String arg = nerdGraphClient.genGraphQLArg(TestArgInstance.builder()
                .foo(null)
                .bar(null)
                .hello(null)
                .world(null)
                .list(null)
                .pair(null)
                .build());

        Assertions.assertFalse(arg.contains("foo"));
        Assertions.assertFalse(arg.contains("bar"));
        Assertions.assertFalse(arg.contains("hello"));
        Assertions.assertFalse(arg.contains("world"));
        Assertions.assertFalse(arg.contains("alertInput"));
        Assertions.assertFalse(arg.contains("pair"));
    }

    @Test
    void testGenGraphQLArgHandleLists() {
        String arg = nerdGraphClient.genGraphQLArg(TestArgInstance.builder()
                .list(ImmutableList.of(AgentConfigurationInput.builder()
                                .build()))
                .build());

        Assertions.assertEquals("{list: [{name: \"My app\", description: \"This is a test app\", permissions: WITHOUT_QUOTE}, {row: 12, column: 1}]}", arg);
    }

    // TODO: Add unit test for "doRequest" method
}
