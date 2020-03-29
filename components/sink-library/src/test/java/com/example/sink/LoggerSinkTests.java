package com.example.sink;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoggerSinkTests {

    @Mock
    Appender mockAppender;
    @Mock
    Counter counter;

    @Test
    public void testSink() {

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        when(mockAppender.getName()).thenReturn("MOCK");
        root.addAppender(mockAppender);

        LoggerSink sink = new LoggerSink(counter);

        sink.logger("Hello");

        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("received Hello");
            }
        }));

        verify(counter).add(1);
        verifyNoMoreInteractions(counter);
    }
}
