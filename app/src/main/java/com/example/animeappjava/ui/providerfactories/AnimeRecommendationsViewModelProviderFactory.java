package com.example.animeappjava.ui.providerfactories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.animeappjava.repository.AnimeRecommendationsRepository;
import com.example.animeappjava.ui.viewmodels.AnimeRecommendationsViewModel;


public class AnimeRecommendationsViewModelProviderFactory implements ViewModelProvider.Factory {

    private final AnimeRecommendationsRepository animeRecommendationsRepository;

    public AnimeRecommendationsViewModelProviderFactory(AnimeRecommendationsRepository animeRecommendationsRepository) {
        this.animeRecommendationsRepository = animeRecommendationsRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AnimeRecommendationsViewModel.class)) {
            return (T) new AnimeRecommendationsViewModel(animeRecommendationsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}