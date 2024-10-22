package com.example.animeappjava.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animeappjava.data.StateFlowLiveData;
import com.example.animeappjava.models.AnimeRecommendationResponse;
import com.example.animeappjava.repository.AnimeRecommendationsRepository;
import com.example.animeappjava.utils.Resource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRecommendationsViewModel extends ViewModel {

    private final AnimeRecommendationsRepository animeRecommendationsRepository;
    private final Executor executor;
    private final MutableLiveData<Resource<AnimeRecommendationResponse>> animeRecommendations;
    private int animeRecommendationsPage = 1;

    public AnimeRecommendationsViewModel(AnimeRecommendationsRepository animeRecommendationsRepository) {
        this.animeRecommendationsRepository = animeRecommendationsRepository;
        this.executor = Executors.newSingleThreadExecutor();
        this.animeRecommendations = new MutableLiveData<>(Resource.loading());
        getAnimeRecommendations(animeRecommendations::postValue);
    }

    public LiveData<Resource<AnimeRecommendationResponse>> getAnimeRecommendations() {
        return animeRecommendations;
    }

    private void getAnimeRecommendations(StateFlowLiveData.ValueSetter<Resource<AnimeRecommendationResponse>> setter) {
        setter.setValue(Resource.loading());
        animeRecommendationsRepository.getAnimeRecommendations(animeRecommendationsPage).enqueue(new Callback<AnimeRecommendationResponse>() {
            @Override
            public void onResponse(Call<AnimeRecommendationResponse> call, Response<AnimeRecommendationResponse> response) {
                setter.setValue(handleAnimeRecommendationsResponse(response));
            }

            @Override
            public void onFailure(Call<AnimeRecommendationResponse> call, Throwable t) {
                setter.setValue(Resource.error(t.getMessage(), null));
            }
        });
    }

    private Resource<AnimeRecommendationResponse> handleAnimeRecommendationsResponse(Response<AnimeRecommendationResponse> response) {
        if (response.isSuccessful()) {
            AnimeRecommendationResponse resultResponse = response.body();
            if (resultResponse != null) {
                return Resource.success(resultResponse);
            } else {
                return Resource.error("Response body is null", null);
            }
        } else {
            return Resource.error(response.message(), null);
        }
    }

    public void refreshData() {
        animeRecommendations.postValue(Resource.loading());

        animeRecommendationsRepository.getAnimeRecommendations(animeRecommendationsPage).enqueue(new Callback<AnimeRecommendationResponse>() {
            @Override
            public void onResponse(Call<AnimeRecommendationResponse> call, Response<AnimeRecommendationResponse> response) {
                animeRecommendations.postValue(handleAnimeRecommendationsResponse(response));
            }

            @Override
            public void onFailure(Call<AnimeRecommendationResponse> call, Throwable t) {
                animeRecommendations.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }
}