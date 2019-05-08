package com.example.sink;

import com.example.model.Event1Example;
import com.example.model.Event2Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding({LoggerSink.SinkEvent1.class, LoggerSink.SinkEvent2.class})
public class LoggerSink {
    final private static Logger log = LoggerFactory.getLogger(LoggerSink.class);

//    @StreamListener(Sink.INPUT)
//    public void logger(String payload) {
//        log.info("received {}", payload);
//    }
//
    @StreamListener(value = SinkEvent1.INPUT)
    public void logEvent(@Payload Event1Example payload) {
        log.info("received Event {}", payload);
    }

    @StreamListener(target = SinkEvent2.INPUT)
    public void logEvent2(@Payload Event2Example payload) {
        log.info("received Event2 {}", payload);
    }

    interface SinkEvent1 {

        String INPUT = "input_event1";

        @Input(INPUT)
        SubscribableChannel input();

    }

    interface SinkEvent2 {

        String INPUT = "input_event2";

        @Input(INPUT)
        SubscribableChannel input();

    }

}
