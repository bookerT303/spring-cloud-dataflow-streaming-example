package com.example.controller;

import com.example.source.PublisherSource;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublisherController {

    private PublisherSource source;

    public PublisherController(PublisherSource source) {
        this.source = source;
    }

    @RequestMapping("/publish")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void publish(@RequestBody String payload) {
        LoggerFactory.getLogger(PublisherController.class).info("Publishing {}", payload);
        source.source(payload);
    }

}
