package com.example.animeappjava.models;

public class Aired {
    private final String from;
    private final String to;
    private final Prop prop;
    private final String string;

    public Aired(String from, String to, Prop prop, String string) {
        this.from = from;
        this.to = to;
        this.prop = prop;
        this.string = string;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Prop getProp() {
        return prop;
    }

    public String getString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aired aired = (Aired) o;

        if (!from.equals(aired.from)) return false;
        if (!to.equals(aired.to)) return false;
        if (!prop.equals(aired.prop)) return false;
        return string.equals(aired.string);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + prop.hashCode();
        result = 31 * result + string.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Aired{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", prop=" + prop +
                ", string='" + string + '\'' +
                '}';
    }
}