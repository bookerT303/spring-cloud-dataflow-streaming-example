package com.example.processor;

import com.example.event.GreetingEvent;
import org.apache.avro.generic.GenericData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@EnableBinding(Processor.class)
public class LoggerProcessor {
    final private static Logger log = LoggerFactory.getLogger(LoggerProcessor.class);
    private boolean errorEnabled;

    public LoggerProcessor(@Value("${com.example.processor.errorEnabled:false}") boolean errorEnabled) {
        this.errorEnabled = errorEnabled;
    }

    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public String transform(GenericData.Record data) {
        log.info("Processor received raw " + data);
        GreetingEvent greetingMessage = GreetingEvent.newBuilder()
                .setId((Long) data.get("id"))
                .setValue(String.valueOf(data.get("value")))
                .setDateTime((Long) data.get("dateTime"))
                .build();
        return transform(greetingMessage);
    }

    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public String transform(GreetingEvent greetingMessage) {
//        try {
        log.info("Processor received " + greetingMessage);
        if (errorEnabled && greetingMessage.getId() % 3 == 0) {
            throw new ArrayIndexOutOfBoundsException("Example of a failure");
        }
        return String.format("%d: %s at %s response!",
                greetingMessage.getId(), greetingMessage.getValue(), LocalDateTime.ofEpochSecond(
                        greetingMessage.getDateTime(), 0, ZoneOffset.UTC));
//        } catch (Exception ex) {
//            throw new CannotProcessException(greetingMessage, "Unable to process", ex);
//        }
    }

//    @StreamListener("errorChannel")
//    public void error(ErrorMessage message) {
//        log.info("Error Handling {}", message);
//        MessageHandlingException messageException = (MessageHandlingException) message.getPayload();
//        Throwable cause = messageException.getCause();
//        if (cause instanceof CannotProcessException) {
//            CannotProcessException ex = (CannotProcessException) cause;
//            log.info("Message that caused the failure is {}",
//                    ex.getFailedMessage(), ex.getCause());
//        }
//    }

//    static class CannotProcessException extends RuntimeException {
//        private GreetingMessage failedMessage;
//
//        public CannotProcessException(GreetingMessage failedMessage, String message, Throwable cause) {
//            super(message, cause);
//            this.failedMessage = failedMessage;
//        }
//
//        public GreetingMessage getFailedMessage() {
//            return failedMessage;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (!(o instanceof CannotProcessException)) return false;
//            CannotProcessException that = (CannotProcessException) o;
//            return Objects.equals(failedMessage, that.failedMessage)
//                    && super.equals(o);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(failedMessage);
//        }
//
//        @Override
//        public String toString() {
//            return "CannotProcessException{" +
//                    "failedMessage=" + failedMessage +
//                    "} " + super.toString();
//        }
//    }
}