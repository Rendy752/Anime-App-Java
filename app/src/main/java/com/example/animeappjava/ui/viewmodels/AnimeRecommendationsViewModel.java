package com.example.animeappjava.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animeappjava.models.AnimeRecommendationResponse;
import com.example.animeappjava.repository.AnimeRecommendationsRepository;
import com.example.animeappjava.utils.Resource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnimeRecommendationsViewModel extends ViewModel {

    private final AnimeRecommendationsRepository animeRecommendationsRepository;
    private final MutableLiveData<Resource<AnimeRecommendationResponse>> animeRecommendations = new MutableLiveData<>(Resource.loading());
    private int animeRecommendationsPage = 1;

    public AnimeRecommendationsViewModel(AnimeRecommendationsRepository animeRecommendationsRepository) {
        this.animeRecommendationsRepository = animeRecommendationsRepository;
        fetchAnimeRecommendations();
    }

    public LiveData<Resource<AnimeRecommendationResponse>> getAnimeRecommendations() {
        return animeRecommendations;
    }

    private void fetchAnimeRecommendations() {
        animeRecommendations.postValue(Resource.loading());

        animeRecommendationsRepository.getAnimeRecommendations(animeRecommendationsPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AnimeRecommendationResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // Handle subscription if needed
                    }

                    @Override
                    public void onSuccess(@NonNull AnimeRecommendationResponse response) {
                        animeRecommendations.postValue(Resource.success(response));
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