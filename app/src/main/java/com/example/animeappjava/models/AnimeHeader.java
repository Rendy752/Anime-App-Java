package com.example.animeappjava.models;

public class AnimeHeader {
    private final int mal_id;
    private final String url;
    private final Images images;
    private final String title;

    public AnimeHeader(int mal_id, String url, Images images, String title) {
        this.mal_id = mal_id;
        this.url = url;
        this.images = images;
        this.title = title;
    }

    public AnimeHeader(int mal_id, String url) {
        this(mal_id, url, new Images(
                new ImageUrl("", "", "", "", ""),
                new ImageUrl("", "", "", "", "")
        ), "");
    }

    public int getMal_id() {
        return mal_id;
    }

    public String getUrl() {
        return url;
    }

    public Images getImages() {
        return images;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimeHeader that = (AnimeHeader) o;

        if (mal_id != that.mal_id) return false;
        if (!url.equals(that.url)) return false;
        if (!images.equals(that.images)) return false;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        int result = mal_id;
        result = 31 * result + url.hashCode();
        result = 31 * result + images.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AnimeHeader{" +
                "mal_id=" + mal_id +
                ", url='" + url + '\'' +
                ", images=" + images +
                ", title='" + title + '\'' +
                '}';
    }
}