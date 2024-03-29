package com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph;

import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.AlertsPolicyInput;
import com.newrelic.aws.cfn.resources.alert.alertspolicy.nerdgraph.schema.AlertsPolicyResult;
import org.apache.commons.lang3.tuple.Pair;
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
        String template = nerdGraphClient.getGraphQLTemplate("unitTest.query.template");

        Assertions.assertEquals("query {\n    foo\n    bar\n}", template);
    }

    @Test
    void testGenGraphQLArgFiltersFieldWithoutJsonPropertyAnnotation() {
        String arg = nerdGraphClient.genGraphQLArg(TestArgInstance.builder()
                .foo("alert")
                .bar(1)
                .hello(2F)
                .world(3.0)
                .list(ImmutableList.of("a", "b"))
                                .alertsPolicyInput(AlertsPolicyInput.builder()
                                        .name("My Name")
                                        .incidentPreference("PER_POLICY")
                                        .build())
                .pair(Pair.of("left", 1))
                .build());

        Assertions.assertTrue(arg.contains("foo"));
        Assertions.assertFalse(arg.contains("bar"));
        Assertions.assertFalse(arg.contains("hello"));
        Assertions.assertTrue(arg.contains("world"));
        Assertions.assertTrue(arg.contains("alertsPolicyInput"));
        Assertions.assertTrue(arg.contains("pair"));
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
                .list(ImmutableList.of(AlertsPolicyInput.builder()
                                .name("My app")
                                .incidentPreference("PER_POLICY")
                                .build(),
                        AlertsPolicyInput.builder()
                                .name("App2")
                                .incidentPreference("PER_POLICY")
                                .build()
                        ))
                .build());

        Assertions.assertEquals("{list: [{name: \"My app\", incidentPreference: PER_POLICY}, {name: \"App2\", incidentPreference: PER_POLICY}]}", arg);
    }

    @Test
    void testGenGraphQLArgHandleCloudformationModels() {
        String arg = nerdGraphClient.genGraphQLArg(TestArgInstance.builder()
                .alertsPolicyInput(AlertsPolicyInput.builder()
                        .name("Test")
                        .incidentPreference("PER_POLICY")
                        .build()
                ).build());

        Assertions.assertEquals("{alertsPolicyInput: {name: \"Test\", incidentPreference: PER_POLICY}}", arg);
    }

}
