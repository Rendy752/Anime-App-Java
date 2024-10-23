package com.example.animeappjava.repository;

import com.example.animeappjava.data.local.database.AnimeRecommendationsDatabase;
import com.example.animeappjava.data.remote.api.AnimeAPI;
import com.example.animeappjava.data.remote.api.RetrofitInstance;
import com.example.animeappjava.models.AnimeRecommendationResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRecommendationsRepository {

    private final AnimeRecommendationsDatabase db;
    private final AnimeAPI animeAPI;

    public AnimeRecommendationsRepository(AnimeRecommendationsDatabase db) {
        this.db = db;
        this.animeAPI = RetrofitInstance.api;
    }

    public Single<AnimeRecommendationResponse> getAnimeRecommendations(int page) {
        return Single.create(emitter -> {
            Call<AnimeRecommendationResponse> call = animeAPI.getAnimeRecommendations(page);
            call.enqueue(new Callback<AnimeRecommendationResponse>() {
                @Override
                public void onResponse(Call<AnimeRecommendationResponse> call, Response<AnimeRecommendationResponse> response) {
                    if (response.isSuccessful()) {
                        emitter.onSuccess(response.body());
                    } else {
                        emitter.onError(new Exception(response.message()));
                    }
                }

                @Override
                public void onFailure(Call<AnimeRecommendationResponse> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });
    }
}