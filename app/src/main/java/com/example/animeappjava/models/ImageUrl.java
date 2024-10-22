package com.example.animeappjava.models;

public class ImageUrl {
    private final String image_url;
    private final String small_image_url;
    private final String medium_image_url;
    private final String large_image_url;
    private final String maximum_image_url;

    public ImageUrl(String image_url, String small_image_url, String medium_image_url, String large_image_url, String maximum_image_url) {
        this.image_url = image_url;
        this.small_image_url = small_image_url;
        this.medium_image_url = medium_image_url;
        this.large_image_url = large_image_url;
        this.maximum_image_url = maximum_image_url;
    }

    public String getImageUrl() {
        return image_url;
    }

    public String getSmallImageUrl() {
        return small_image_url;
    }

    public String getMediumImageUrl() {
        return medium_image_url;
    }

    public String getLargeImageUrl() {
        return large_image_url;
    }

    public String getMaximumImageUrl() {
        return maximum_image_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageUrl imageUrl = (ImageUrl) o;

        if (!image_url.equals(imageUrl.image_url)) return false;
        if (!small_image_url.equals(imageUrl.small_image_url)) return false;
        if (medium_image_url != null ? !medium_image_url.equals(imageUrl.medium_image_url) : imageUrl.medium_image_url != null)
            return false;
        if (!large_image_url.equals(imageUrl.large_image_url)) return false;
        return maximum_image_url != null ? maximum_image_url.equals(imageUrl.maximum_image_url) : imageUrl.maximum_image_url == null;
    }

    @Override
    public int hashCode() {
        int result = image_url.hashCode();
        result = 31 * result + small_image_url.hashCode();
        result = 31 * result + (medium_image_url != null ? medium_image_url.hashCode() : 0);
        result = 31 * result + large_image_url.hashCode();
        result = 31 * result + (maximum_image_url != null ? maximum_image_url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ImageUrl{" +
                "image_url='" + image_url + '\'' +
                ", small_image_url='" + small_image_url + '\'' +
                ", medium_image_url='" + medium_image_url + '\'' +
                ", large_image_url='" + large_image_url + '\'' +
                ", maximum_image_url='" + maximum_image_url + '\'' +
                '}';
    }
}