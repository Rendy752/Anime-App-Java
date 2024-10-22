package com.example.animeappjava.models;

import java.util.List;

public class AnimeRecommendationResponse {
    private final Pagination pagination;
    private final List<AnimeRecommendation> data;

    public AnimeRecommendationResponse(Pagination pagination, List<AnimeRecommendation> data) {
        this.pagination = pagination;
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<AnimeRecommendation> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimeRecommendationResponse that = (AnimeRecommendationResponse) o;

        if (!pagination.equals(that.pagination)) return false;
        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        int result = pagination.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AnimeRecommendationResponse{" +
                "pagination=" + pagination +
                ", data=" + data +
                '}';
    }
}