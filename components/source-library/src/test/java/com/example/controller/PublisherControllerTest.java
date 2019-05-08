package com.example.controller;

import com.example.source.PublisherSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PublisherControllerTest {
    @Mock
    PublisherSource source;

    PublisherController controller;

    @Before
    public void setUp() throws Exception {
        controller = new PublisherController(source, resolver);
    }

    @Test
    public void publish() throws Exception {
        controller.publish("payload");

        verify(source).source("payload");
    }

}