package com.example.source;

import com.example.event.GreetingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@EnableBinding(Source.class)
public class PublisherSource {

    private Source source;
    private static long counter = 1;

    @Autowired
    public PublisherSource(Source source) {
        this.source = source;
    }

    @Component
    @ConditionalOnProperty(name = "com.example.source.schedulerEnabled", havingValue = "true", matchIfMissing = true)
    static class ScheduledPublisherSource {
        @InboundChannelAdapter(value = Source.OUTPUT)
        public GreetingEvent source() {
            return new GreetingEvent(counter++, "Hello from Spring Cloud Stream", System.currentTimeMillis());
        }
    }

    public void source(String message) {
        source.output().send(
                MessageBuilder
                        .withPayload(new GreetingEvent(counter++, message, System.currentTimeMillis()))
                        .build());
    }
}

