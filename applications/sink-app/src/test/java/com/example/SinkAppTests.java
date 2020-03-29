package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class SinkAppTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Sink channels;

    @Test
    public void testSink() {
        AbstractMessageChannel input = (AbstractMessageChannel) channels.input();

        final AtomicReference<Message<?>> messageAtomicReference =
                new AtomicReference<>();

        ChannelInterceptor assertionInterceptor = new ChannelInterceptorAdapter() {

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel,
                                            boolean sent, Exception ex) {
                messageAtomicReference.set(message);
                super.afterSendCompletion(message, channel, sent, ex);
            }
        };

        input.addInterceptor(assertionInterceptor);
        input.send(new GenericMessage<>("Sample message, contents does not matter"));

        Message<?> message1 = messageAtomicReference.get();
        assertThat(message1).isNotNull();
        assertThat(message1).hasFieldOrPropertyWithValue("payload", "Sample message, contents does not matter");
        Integer count = restTemplate.getForObject(String.format("http://localhost:%d/counter", port), Integer.class);
        assertThat(count).isEqualTo(1);
    }
}
