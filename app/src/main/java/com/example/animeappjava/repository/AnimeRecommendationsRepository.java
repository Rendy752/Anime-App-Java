package com.example.animeappjava.repository;

import com.example.animeappjava.data.local.database.AnimeRecommendationsDatabase;
import com.example.animeappjava.data.remote.api.AnimeAPI;
import com.example.animeappjava.models.AnimeRecommendationResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public class AnimeRecommendationsRepository {

    private final AnimeRecommendationsDatabase db;
    private final AnimeAPI animeAPI;

    public AnimeRecommendationsRepository(AnimeAPI animeAPI, AnimeRecommendationsDatabase db) {
        this.db = db;
        this.animeAPI = animeAPI;
    }

    public Single<AnimeRecommendationResponse> getAnimeRecommendations(int page) {
        return Single.fromCallable(() -> {
            Response<AnimeRecommendationResponse> response = animeAPI.getAnimeRecommendations(page).execute(); // Using injected API
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new Exception(response.message());
            }
        });
    }
}