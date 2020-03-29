package com.example.sink;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counter")
public class CountController {


    private Counter counter;

    public CountController(Counter counter) {

        this.counter = counter;
    }

    @GetMapping
    public Integer getCounter() {
        return counter.getCount();
    }

    @DeleteMapping
    public void zeroCounter() {
        counter.reset();
    }
}
