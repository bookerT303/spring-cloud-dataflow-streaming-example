package com.example.models;

import java.time.LocalDateTime;
import java.util.Objects;

/*
 * headers: contentType: application/json
 * Payload: { "greeting":"hello world" }
 */
public class Greeting {
    private static long counter = 1;
    private long id;
    String value;
    LocalDateTime dateTime;

    public Greeting() {
        this(null, null);
    }

    public Greeting(String value, LocalDateTime dateTime) {
        this.id = ++counter;
        this.value = value;
        this.dateTime = dateTime;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Greeting)) return false;
        Greeting greeting = (Greeting) o;
        return id == greeting.id &&
                Objects.equals(value, greeting.value) &&
                Objects.equals(dateTime, greeting.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, dateTime);
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}

