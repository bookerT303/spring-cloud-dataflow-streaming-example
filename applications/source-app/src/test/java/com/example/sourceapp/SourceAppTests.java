package com.example.sourceapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SourceAppTests {
    final private static Logger log = LoggerFactory.getLogger(SourceAppTests.class);

    @Autowired
    private Source channels;

    @Autowired
    private MessageCollector collector;

    @Test
    public void testSource() throws InterruptedException {
        Date startTime = new Date();
        sleep(2000);

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.output());

        assertThat(messages).isNotEmpty();
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        messages.forEach(
                message -> {
                    String payload = String.valueOf(message.getPayload());
                    try {
                        Date timestamp = formatter.parse(payload);
                        assertThat(timestamp).isAfter(startTime);
                    } catch (ParseException e) {
                        fail("Could not parse \""+payload+"\"");
                    }
                }
        );
    }
}
