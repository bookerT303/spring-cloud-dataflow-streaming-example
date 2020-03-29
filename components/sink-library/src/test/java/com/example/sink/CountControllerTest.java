package com.example.sink;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountControllerTest {

    Counter counter = new Counter();
    CountController controller = new CountController(counter);

    @Test
    public void testGet() {

        counter.add(5);
        int response = controller.getCounter();

        assertThat(response).isEqualTo(5);
    }

}