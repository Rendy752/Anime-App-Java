package com.example.animeappjava.models;

public class Prop {
    private final DateObject from;
    private final DateObject to;

    public Prop(DateObject from, DateObject to) {
        this.from = from;
        this.to = to;
    }

    public DateObject getFrom() {
        return from;
    }

    public DateObject getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prop prop = (Prop) o;

        if (!from.equals(prop.from)) return false;
        return to.equals(prop.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Prop{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}