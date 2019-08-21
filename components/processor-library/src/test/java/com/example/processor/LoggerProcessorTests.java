package com.example.processor;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoggerProcessorTests {

    @Mock
    Appender mockAppender;

    @Test
    public void testProcessor() {

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        when(mockAppender.getName()).thenReturn("MOCK");
        root.addAppender(mockAppender);

        LoggerProcessor processor = new LoggerProcessor();

        String transformed = processor.transform(new GreetingMessage("Hello", LocalDateTime.now()));

        assertThat(transformed).startsWith("Hello at ");

        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("Processor received GreetingMessage{value='Hello',");
            }
        }));
    }
}
