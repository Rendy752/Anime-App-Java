package com.example.animeappjava.data.remote.api;

import com.example.animeappjava.models.AnimeDetailResponse;
import com.example.animeappjava.models.AnimeRecommendationResponse;
import org.mockito.Mockito;
import retrofit2.Call;

public class MockAnimeAPI implements AnimeAPI {

    private AnimeRecommendationResponse mockResponse; // Initialize with your mock data

    public MockAnimeAPI(AnimeRecommendationResponse mockResponse) {
        this.mockResponse = mockResponse;
    }

    @Override
    public Call<AnimeRecommendationResponse> getAnimeRecommendations(int page) {
        return new MockCall<>(mockResponse); // Return your custom MockCall
    }

    @Override
    public Call<AnimeDetailResponse> getAnimeDetail(int id) {
        // You can implement similar mocking for getAnimeDetail if needed
        return Mockito.mock(Call.class);
    }
}