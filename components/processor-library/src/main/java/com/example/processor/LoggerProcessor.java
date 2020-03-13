package com.example.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;

@EnableBinding(Processor.class)
public class LoggerProcessor {
    final private static Logger log = LoggerFactory.getLogger(LoggerProcessor.class);

    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public String transform(GreetingMessage greetingMessage) {
        if (greetingMessage.getId() % 3 == 0) {
            throw new ArrayIndexOutOfBoundsException("Example of a failure");
        }
        log.info("Processor received " + greetingMessage);
        return String.format("%d: %s at %s response!",
                greetingMessage.getId(), greetingMessage.getValue(), greetingMessage.getDateTime());
    }

    @StreamListener("errorChannel")
    public void error(Message<?> message) {
        if (message instanceof ErrorMessage) {
            ErrorMessage asErrorMessage = (ErrorMessage) message;
            GreetingMessage asGreeting = (GreetingMessage)asErrorMessage.getOriginalMessage();
            Throwable cause = asErrorMessage.getPayload();
            log.warn("Failure handling: {}\n    cause: {}", asGreeting, cause);
            return;
        }
        log.warn("Unexpected ERROR: " + message);
    }
}
