package com.example.processorapp;

import com.example.processor.GreetingMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessorAppTests {

    @Autowired
    private Processor channels;

    @Autowired
    private MessageCollector collector;

    @Test
    public void testProcessor() {
//        channels.input().send(new GenericMessage<>("Message for Processor"));
        channels.input().send(new GenericMessage<>(
                new GreetingMessage(1,"Message for Processor", LocalDateTime.now())));

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.output());

        assertThat(messages).isNotEmpty();
        messages.forEach(message -> assertThat(String.valueOf(message.getPayload())).contains("Message for Processor at "));
    }
}
