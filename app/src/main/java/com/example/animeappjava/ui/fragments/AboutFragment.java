package com.example.animeappjava.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.animeappjava.databinding.FragmentAboutBinding;
import com.example.animeappjava.ui.activities.MainActivity;
import com.example.animeappjava.ui.viewmodels.AnimeRecommendationsViewModel;

public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;
    private AnimeRecommendationsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        viewModel = ((MainActivity) requireActivity()).getAnimeRecommendationsViewModel();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}