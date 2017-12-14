package com.example.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;

import java.util.Date;

@EnableBinding(Source.class)
public class DateSource {
    final private static Logger log = LoggerFactory.getLogger(DateSource.class);

    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(maxMessagesPerPoll = "1", fixedDelay = "3000"))
    public String source() {
        return new Date().toString();
    }
}
