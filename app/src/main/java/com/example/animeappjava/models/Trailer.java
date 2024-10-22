package com.example.animeappjava.models;

public class Trailer {
    private final String youtube_id;
    private final String url;
    private final String embed_url;
    private final ImageUrl images;

    public Trailer(String youtube_id, String url, String embed_url, ImageUrl images) {
        this.youtube_id = youtube_id;
        this.url = url;
        this.embed_url = embed_url;
        this.images = images;
    }

    public String getYoutubeId() {
        return youtube_id;
    }

    public String getUrl() {
        return url;
    }

    public String getEmbedUrl() {
        return embed_url;
    }

    public ImageUrl getImages() {
        return images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trailer trailer = (Trailer) o;

        if (youtube_id != null ? !youtube_id.equals(trailer.youtube_id) : trailer.youtube_id != null) return false;
        if (url != null ? !url.equals(trailer.url) : trailer.url != null) return false;
        if (embed_url != null ? !embed_url.equals(trailer.embed_url) : trailer.embed_url != null) return false;
        return images != null ? images.equals(trailer.images) : trailer.images == null;
    }

    @Override
    public int hashCode() {
        int result = youtube_id != null ? youtube_id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (embed_url != null ? embed_url.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "youtube_id='" + youtube_id + '\'' +
                ", url='" + url + '\'' +
                ", embed_url='" + embed_url + '\'' +
                ", images=" + images +
                '}';
    }
}