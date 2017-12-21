package com.example.processorapp;

import com.example.processor.LoggerProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessorAppTests {

    @Autowired
    private Processor channels;

    @Autowired
    private MessageCollector collector;

    @Test
    public void testProcessor() {
        channels.input().send(new GenericMessage<>("Message for Processor"));

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.output());

        assertThat(messages).isNotEmpty();
        messages.forEach(message -> assertThat(String.valueOf(message.getPayload())).startsWith("Message for Processor processed at "));
    }
}
