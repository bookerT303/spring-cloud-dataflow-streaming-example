package com.example.source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PublisherSourceTest {

    @Mock
    Source source;
    @Mock
    MessageChannel messageChannel;

    PublisherSource publisher;

    @Before
    public void setUp() throws Exception {
        publisher = new PublisherSource(source);
        when(source.output()).thenReturn(messageChannel);
    }

    @Test
    public void source() {
        publisher.source("This is a test");

        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(messageChannel).send(captor.capture());
        assertThat(String.valueOf(captor.getValue().getPayload())).isEqualTo("This is a test");
        verify(source).output();
        verifyNoMoreInteractions(source, messageChannel);
    }
}