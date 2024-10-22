package com.example.animeappjava.models;

public class CommonIdentity {
    private final int mal_id;
    private final String type;
    private final String name;
    private final String url;

    public CommonIdentity(int mal_id, String type, String name, String url) {
        this.mal_id = mal_id;
        this.type = type;
        this.name = name;
        this.url = url;
    }

    public int getMal_id() {
        return mal_id;
    }

    public String getType() {
        return type;
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

        CommonIdentity that = (CommonIdentity) o;

        if (mal_id != that.mal_id) return false;
        if (!type.equals(that.type)) return false;
        if (!name.equals(that.name)) return false;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        int result = mal_id;
        result = 31 * result + type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CommonIdentity{" +
                "mal_id=" + mal_id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}