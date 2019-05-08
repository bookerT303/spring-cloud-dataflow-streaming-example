package com.example.sink;

import com.example.model.Event1Example;
import com.example.model.Event2Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(Sink.class)
public class LoggerSink {
    final private static Logger log = LoggerFactory.getLogger(LoggerSink.class);

//    @StreamListener(Sink.INPUT)
//    public void logger(String payload) {
//        log.info("received {}", payload);
//    }
//
    @StreamListener(value = Sink.INPUT, condition = "headers['contentType'].contains('Event1Example')")
    public void logEvent(@Payload Event1Example payload) {
        log.info("received Event {}", payload);
    }

    @StreamListener(target = Sink.INPUT, condition = "headers['contentType'].contains('Event2Example')")
    public void logEvent2(@Payload Event2Example payload) {
        log.info("received Event2 {}", payload);
    }
}
