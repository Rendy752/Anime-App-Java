package com.example.animeappjava.data.remote.api;

import com.example.animeappjava.models.AnimeDetailResponse;
import com.example.animeappjava.models.AnimeRecommendationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeAPI {

    @GET("v4/recommendations/anime")
    Call<AnimeRecommendationResponse> getAnimeRecommendations(
            @Query("page") int page
    );

    @GET("v4/anime/{id}/full")
    Call<AnimeDetailResponse> getAnimeDetail(
            @Path("id") int id
    );
}