package com.example.animeappjava.repository;

import com.example.animeappjava.data.local.database.AnimeRecommendationsDatabase;
import com.example.animeappjava.data.remote.api.RetrofitInstance;
import com.example.animeappjava.models.AnimeRecommendationResponse;

import retrofit2.Call;

public class AnimeRecommendationsRepository {

    private final AnimeRecommendationsDatabase db;

    public AnimeRecommendationsRepository(AnimeRecommendationsDatabase db) {
        this.db = db;
    }

    public Call<AnimeRecommendationResponse> getAnimeRecommendations(int page) {
        return RetrofitInstance.api.getAnimeRecommendations(page);
    }
}