package com.example.sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class LoggerSink {
    final private static Logger log = LoggerFactory.getLogger(LoggerSink.class);
    private Counter counter;

    public LoggerSink(Counter counter) {

        this.counter = counter;
    }

    @StreamListener(Sink.INPUT)
    public void logger(String payload) {
        counter.add(1);
        log.info("received {}", payload);
    }
}
