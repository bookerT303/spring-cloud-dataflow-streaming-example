package com.example.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import java.io.IOException;

public class GreetingMessageConverter extends AbstractMessageConverter {

    private final ObjectMapper objectMapper;

    public GreetingMessageConverter(ObjectMapper objectMapper) {
        super(MimeType.valueOf("application/json"));
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.equals(GreetingMessage.class);
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        try {
            return objectMapper.readValue((byte[]) message.getPayload(), GreetingMessage.class);
        } catch (IOException e) {
            return null;
        }
    }
}