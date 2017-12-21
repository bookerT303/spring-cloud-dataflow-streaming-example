package com.example.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

@EnableBinding(Source.class)
public class PublisherSource {

    private Source source;

    @Autowired
    public PublisherSource(Source source) {
        this.source = source;
    }

    public void source(String message) {
        source.output().send(MessageBuilder.withPayload(message).build());
    }
}