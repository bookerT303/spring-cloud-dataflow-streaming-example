package com.example.source;

import com.example.models.Greeting;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import java.nio.charset.StandardCharsets;

public class GreetingConverter extends AbstractMessageConverter {

    private final ObjectMapper objectMapper;

    protected GreetingConverter(ObjectMapper objectMapper) {
        super(MimeType.valueOf("application/json"));
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.equals(Greeting.class);
    }

    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        try {
//  for xml          return objectMapper.writer().withRootName("greeting").writeValueAsString(payload)
            return objectMapper.writeValueAsString(payload)
                    .getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}