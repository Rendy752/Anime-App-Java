package com.example.animeappjava.models;

public class AnimeDetailResponse {
    private final AnimeDetail data;

    public AnimeDetailResponse(AnimeDetail data) {
        this.data = data;
    }

    public AnimeDetail getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimeDetailResponse that = (AnimeDetailResponse) o;

        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return "AnimeDetailResponse{" +
                "data=" + data +
                '}';
    }
}