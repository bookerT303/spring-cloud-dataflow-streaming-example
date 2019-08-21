package com.example.models;

import java.time.LocalDateTime;
import java.util.Objects;

/*
 * headers: contentType: application/json
 * Payload: { "greeting":"hello world" }
 */
public class Greeting {
    String value;
    LocalDateTime dateTime;

    public Greeting () {
        this(null, null);
    }

    public Greeting(String value, LocalDateTime dateTime) {
        this.value = value;
        this.dateTime = dateTime;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Greeting greeting = (Greeting) o;
        return Objects.equals(value, greeting.value) &&
                Objects.equals(dateTime, greeting.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, dateTime);
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "value='" + value + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}

