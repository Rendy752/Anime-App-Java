package com.example.animeappjava.ui.providerfactories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.animeappjava.repository.AnimeDetailRepository;
import com.example.animeappjava.ui.viewmodels.AnimeDetailViewModel;

public class AnimeDetailViewModelProviderFactory implements ViewModelProvider.Factory {

    private final AnimeDetailRepository animeDetailRepository;

    public AnimeDetailViewModelProviderFactory(AnimeDetailRepository animeDetailRepository) {
        this.animeDetailRepository = animeDetailRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AnimeDetailViewModel.class)) {
            return (T) new AnimeDetailViewModel(animeDetailRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}