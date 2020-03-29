package com.example.source;

import com.example.models.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@EnableBinding(Source.class)
public class PublisherSource {

    private Source source;

    @Autowired
    public PublisherSource(Source source) {
        this.source = source;
    }

    @Component
    @ConditionalOnProperty(name = "com.example.source.schedulerEnabled", havingValue = "true", matchIfMissing = true)
    static class ScheduledPublisherSource {
        @InboundChannelAdapter(value = Source.OUTPUT)
        public Greeting source() {
            return new Greeting("Hello from Spring Cloud Stream", LocalDateTime.now());
        }
    }

    public void source(String message) {
        source.output().send(
                MessageBuilder
                        .withPayload(new Greeting(message, LocalDateTime.now()))
                        .build());
    }
}

