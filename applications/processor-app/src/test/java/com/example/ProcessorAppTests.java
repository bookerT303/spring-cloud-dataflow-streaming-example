package com.example;

import com.example.event.GreetingEvent;
import org.apache.avro.generic.GenericData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

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
        GreetingEvent event = new GreetingEvent(1L, "Message for Processor", System.currentTimeMillis());

        GenericData.Record data = new GenericData.Record(event.getSchema());
        data.put("id", event.getId());
        data.put("value", event.getValue());
        data.put("dateTime", event.getDateTime());

        channels.input().send(new GenericMessage<>(data));

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.output());

        assertThat(messages).isNotEmpty();
        messages.forEach(message -> assertThat(String.valueOf(message.getPayload())).contains("Message for Processor at "));
    }
}
