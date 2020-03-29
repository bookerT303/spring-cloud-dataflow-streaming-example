package com.example.sink;

import org.springframework.stereotype.Component;

@Component
public class Counter {
    private int count;

    public void add(int number) {
        count += number;
    }

    public int getCount() {
        return count;
    }

    public void reset() {
        count = 0;
    }
}
