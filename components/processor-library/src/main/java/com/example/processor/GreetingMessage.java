package com.example.processor;

import java.time.LocalDateTime;
import java.util.Objects;

public class GreetingMessage {
    private long id;
    private String value;
    private LocalDateTime dateTime;

    public GreetingMessage() {
    }

    public GreetingMessage(long id, String value, LocalDateTime dateTime) {
        this.id = id;
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
        if (!(o instanceof GreetingMessage)) return false;
        GreetingMessage that = (GreetingMessage) o;
        return id == that.id &&
                Objects.equals(value, that.value) &&
                Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, dateTime);
    }

    @Override
    public String toString() {
        return "GreetingMessage{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}