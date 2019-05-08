package com.example.model;

import java.util.Objects;

public class Event2Example {
    private Long id;
    private String name;
    private String notes;

    public Event2Example() {
        this(0L, "", "");
    }

    public Event2Example(Long id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event2Example that = (Event2Example) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, notes);
    }

    @Override
    public String toString() {
        return "Event2Example{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
