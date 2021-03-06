package com.example.processor;

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
        log.info("Processor received {}", payload);
        return payload + " processed at " + (new Date());
    }
}
