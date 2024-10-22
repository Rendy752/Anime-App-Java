package com.example.animeappjava.models;

public class NameAndUrl {
    private final String name;
    private final String url;

    public NameAndUrl(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NameAndUrl that = (NameAndUrl) o;

        if (!name.equals(that.name)) return false;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NameAndUrl{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}