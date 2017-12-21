package com.example.sourceapp;

import com.example.source.PublisherSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.config.EnablePublisher;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@ComponentScan("com.example")
@EnableAsync
public class SourceApp {

    public static void main(String[] args) {
        SpringApplication.run(SourceApp.class, args);
    }

    @Autowired
    PublisherSource publisher;

    @Scheduled(initialDelay = 1000L, fixedDelay = 1000L)
    public void scheduleFixedDelayTask() {
        publisher.source(new Date().toString());
    }
}
