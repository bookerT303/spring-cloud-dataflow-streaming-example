package com.example.processor;

import com.example.model.Event1Example;
import com.example.model.Event2Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Date;

@EnableBinding(Processor.class)
public class LoggerProcessor {
    final private static Logger log = LoggerFactory.getLogger(LoggerProcessor.class);

    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public String transform(String payload) {
        log.info("Processor received String {}", payload);
        return payload + " processed at " + (new Date());
    }

    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Event1Example transformEvent(Event1Example payload) {
        log.info("Processor received Event {}", payload);
        return new Event1Example(payload.getId(), payload.getName(), " processed Event at " + (new Date()));
    }

    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Event2Example transformEvent(Event2Example payload) {
        log.info("Processor received Event2 {}", payload);
        return new Event2Example(payload.getId(), payload.getName(), " processed Event 2 at " + (new Date()));
    }
}
