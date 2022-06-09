package com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph;

import com.google.common.collect.ImmutableList;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.Config;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.NotificationChannel;
import com.newrelic.aws.cfn.resources.alert.notificationchannel.nerdgraph.schema.NotificationChannelResult;
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
    void testSimpleString() {
        String foo = nerdGraphClient.genGraphQLArg("Hello");
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
                .notificationChannelResult(NotificationChannelResult.builder()
                        .notificationChannel(NotificationChannel.builder()
                                .channelId(1234)
                                .name("My channel")
                                .config(Config.builder()
                                        .includeJson(true)
                                        .emails(ImmutableList.of("foo@somedomain.com"))
                                        .build())
                                .build())
                        .build())
                .pair(Pair.of("left", 1))
                .build());

        Assertions.assertTrue(arg.contains("foo"));
        Assertions.assertFalse(arg.contains("bar"));
        Assertions.assertFalse(arg.contains("hello"));
        Assertions.assertTrue(arg.contains("world"));
        Assertions.assertTrue(arg.contains("notificationChannel"));
        Assertions.assertTrue(arg.contains("includeJson"));
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
                .list(ImmutableList.of(NotificationChannel.builder()
                                .channelId(1234)
                                .name("My channel")
                                .config(Config.builder()
                                        .includeJson(true)
                                        .emails(ImmutableList.of("foo@bar.com"))
                                        .build())
                                .build()))
                .build(), ImmutableList.of(NotificationChannel.class.getPackage().getName(), Config.class.getPackage().getName()));

        Assertions.assertEquals("{list: [{channelId: 1234, name: \"My channel\", config: {includeJson: true, emails: [\"foo@bar.com\"]}}]}", arg);
    }

    @Test
    void testGenGraphQLArgHandleCloudformationModels() {
        String arg = nerdGraphClient.genGraphQLArg(TestArgInstance.builder()
                        .notificationChannelResult(NotificationChannelResult.builder()
                                .notificationChannel(NotificationChannel.builder()
                                        .channelId(1234)
                                        .name("My channel")
                                        .config(Config.builder()
                                                .includeJson(true)
                                                .emails(ImmutableList.of("foo@bar.com"))
                                                .build())
                                        .build())
                                .build())
                .build(), ImmutableList.of(NotificationChannel.class.getPackage().getName()));

        Assertions.assertEquals("{notificationChannelResult: {notificationChannel: {channelId: 1234, name: \"My channel\", config: {includeJson: true, emails: [\"foo@bar.com\"]}}}}", arg);
    }
}
