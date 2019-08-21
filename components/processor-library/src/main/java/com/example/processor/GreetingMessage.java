package com.example.processor;

import java.time.LocalDateTime;
import java.util.Objects;

public class GreetingMessage {
    private String value;
    private LocalDateTime dateTime;

    public GreetingMessage() {
    }

    public GreetingMessage(String value, LocalDateTime dateTime) {
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
        GreetingMessage that = (GreetingMessage) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, dateTime);
    }

    @Override
    public String toString() {
        return "GreetingMessage{" +
                "value='" + value + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}