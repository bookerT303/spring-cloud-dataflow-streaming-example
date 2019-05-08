package com.example.sink;

import com.example.model.Event1Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class LoggerSink {
    final private static Logger log = LoggerFactory.getLogger(LoggerSink.class);

//    @StreamListener(Sink.INPUT)
//    public void logger(String payload) {
//        log.info("received {}", payload);
//    }
//
    @StreamListener(Sink.INPUT)
    public void logEvent(Event1Example payload) {
        log.info("received Event {}", payload);
    }
}
