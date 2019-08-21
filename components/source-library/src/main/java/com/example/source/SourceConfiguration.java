package com.example.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
public class SourceConfiguration {
    @Bean
    @StreamMessageConverter
    public MessageConverter greetingConverter(ObjectMapper objectMapper) {
        return new GreetingConverter(objectMapper);
    }

}
