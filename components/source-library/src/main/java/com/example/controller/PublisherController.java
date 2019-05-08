package com.example.controller;

import com.example.model.Event1Example;
import com.example.source.PublisherSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
public class PublisherController {

    private PublisherSource source;
    private BinderAwareChannelResolver resolver;
    private Random random = new Random();

    public PublisherController(PublisherSource source,
                               BinderAwareChannelResolver resolver) {
        this.source = source;
        this.resolver = resolver;
    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.CREATED)
    public void publish(@RequestBody String payload) {
        LoggerFactory.getLogger(PublisherController.class).info("Publishing {}", payload);
        source.source(payload);
    }

    @PostMapping("/publishEvent/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void publishEvent(@PathVariable String name) throws JsonProcessingException {
        LoggerFactory.getLogger(PublisherController.class).info("Publishing Event {}", name);

        Event1Example event = new Event1Example(random.nextLong(), name, "");
        source.source(event);
    }

    @PostMapping("/publish/{topic}")
    @ResponseStatus(HttpStatus.CREATED)
    public void publish(@PathVariable String topic, @RequestBody String payload) {
        LoggerFactory.getLogger(PublisherController.class).info("Publishing to {} {}", topic,
                payload);
        MessageChannel channel = resolver.resolveDestination(topic);
        channel.send(MessageBuilder.withPayload(payload).build());
    }
}
