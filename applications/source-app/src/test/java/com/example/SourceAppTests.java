package com.example;

import com.example.models.Greeting;
import com.example.source.PublisherSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SourceAppTests {
    final private static Logger log = LoggerFactory.getLogger(SourceAppTests.class);
    private static final String TEXT_PAYLOAD = "Payload";
    Date startTime = new Date();

    @Autowired
    private Source channels;

    @Autowired
    private MessageCollector collector;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PublisherSource publisher;

    @Test
    public void testSource() throws InterruptedException {
        sleep(2000);

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.output());

        assertThat(messages).isNotEmpty();
        LocalDateTime readMessagesTime = LocalDateTime.now();

        List<String> expectedMessagesText = asList("Hello from Spring Cloud Stream", TEXT_PAYLOAD);
        messages.forEach(
                message -> {
                    String payload = String.valueOf(message.getPayload());
                    try {
                        Greeting greeting = objectMapper.readValue(payload, Greeting.class);
                        assertThat(expectedMessagesText).contains(greeting.getValue());
                        assertThat(greeting.getDateTime()).isLessThan(readMessagesTime);
                    } catch (IOException ex) {
                        fail("Could not parse \"" + payload + "\"");
                    }
                }
        );
    }

    @Test
    public void testPublish() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/publish").content(TEXT_PAYLOAD))
                .andExpect(status().isCreated());

        BlockingQueue<Message<?>> messages = collector.forChannel(channels.output());

        assertThat(messages).isNotEmpty();
        assertThat(findPayload(messages)).isTrue();
    }

    private boolean findPayload(BlockingQueue<Message<?>> messages) {
        for (Message<?> message : messages) {
            String payload = String.valueOf(message.getPayload());
            if (payload.contains(TEXT_PAYLOAD)) {
                return true;
            }
        }
        return false;
    }
}
