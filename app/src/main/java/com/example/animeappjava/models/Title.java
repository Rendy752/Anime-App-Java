package com.example.animeappjava.models;

public class Title {
    private final String type;
    private final String title;

    public Title(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Title title1 = (Title) o;

        if (!type.equals(title1.type)) return false;
        return title.equals(title1.title);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Title{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}