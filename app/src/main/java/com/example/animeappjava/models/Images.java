package com.example.animeappjava.models;

public class Images {
    private final ImageUrl jpg;
    private final ImageUrl webp;

    public Images(ImageUrl jpg, ImageUrl webp) {
        this.jpg = jpg;
        this.webp = webp;
    }

    public ImageUrl getJpg() {
        return jpg;
    }

    public ImageUrl getWebp() {
        return webp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Images images = (Images) o;

        if (!jpg.equals(images.jpg)) return false;
        return webp.equals(images.webp);
    }

    @Override
    public int hashCode() {
        int result = jpg.hashCode();
        result = 31 * result + webp.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Images{" +
                "jpg=" + jpg +
                ", webp=" + webp +
                '}';
    }
}