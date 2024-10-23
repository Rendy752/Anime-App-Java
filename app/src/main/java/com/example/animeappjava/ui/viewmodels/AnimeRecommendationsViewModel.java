package com.example.animeappjava.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animeappjava.models.AnimeRecommendationResponse;
import com.example.animeappjava.repository.AnimeRecommendationsRepository;
import com.example.animeappjava.utils.Resource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnimeRecommendationsViewModel extends ViewModel {

    private final AnimeRecommendationsRepository animeRecommendationsRepository;
    private final MutableLiveData<Resource<AnimeRecommendationResponse>> animeRecommendations;
    private int animeRecommendationsPage = 1;

    public AnimeRecommendationsViewModel(AnimeRecommendationsRepository animeRecommendationsRepository) {
        this.animeRecommendationsRepository = animeRecommendationsRepository;
        this.animeRecommendations = new MutableLiveData<>(Resource.loading());
        fetchAnimeRecommendations();
    }

    public LiveData<Resource<AnimeRecommendationResponse>> getAnimeRecommendations() {
        return animeRecommendations;
    }

    private void fetchAnimeRecommendations() {
        animeRecommendations.postValue(Resource.loading());

        Single<AnimeRecommendationResponse> recommendationsSingle = animeRecommendationsRepository.getAnimeRecommendations(animeRecommendationsPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        recommendationsSingle.subscribe(new SingleObserver<AnimeRecommendationResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                // Handle the subscription if needed
            }

            @Override
            public void onSuccess(@NonNull AnimeRecommendationResponse animeRecommendationResponse) {
                animeRecommendations.postValue(Resource.success(animeRecommendationResponse));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                animeRecommendations.postValue(Resource.error(e.getMessage(), null));
            }
        });
    }

    public void refreshData() {
        fetchAnimeRecommendations();
    }
}