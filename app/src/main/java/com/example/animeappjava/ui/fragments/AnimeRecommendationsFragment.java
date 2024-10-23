package com.example.animeappjava.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.animeappjava.R;
import com.example.animeappjava.data.local.database.AnimeRecommendationsDatabase;
import com.example.animeappjava.databinding.FragmentRecommendationBinding;
import com.example.animeappjava.models.AnimeRecommendationResponse;
import com.example.animeappjava.repository.AnimeRecommendationsRepository;
import com.example.animeappjava.ui.adapters.AnimeRecommendationsAdapter;
import com.example.animeappjava.ui.providerfactories.AnimeRecommendationsViewModelProviderFactory;
import com.example.animeappjava.ui.viewmodels.AnimeRecommendationsViewModel;
import com.example.animeappjava.utils.Resource;

public class AnimeRecommendationsFragment extends Fragment {

    private FragmentRecommendationBinding binding;
    private AnimeRecommendationsViewModel viewModel;
    private AnimeRecommendationsAdapter animeRecommendationsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecommendationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupRecyclerView();
        observeAnimeRecommendations();
        setupItemClickListeners();
        setupRefreshButton();
    }

    private void setupViewModel() {
        AnimeRecommendationsRepository repository = new AnimeRecommendationsRepository(AnimeRecommendationsDatabase.getDatabase(requireActivity()));
        AnimeRecommendationsViewModelProviderFactory factory = new AnimeRecommendationsViewModelProviderFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(AnimeRecommendationsViewModel.class);
    }

    private void setupRecyclerView() {
        animeRecommendationsAdapter = new AnimeRecommendationsAdapter();
        binding.rvAnimeRecommendations.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvAnimeRecommendations.setAdapter(animeRecommendationsAdapter);
    }

    private void observeAnimeRecommendations() {
        viewModel.getAnimeRecommendations().observe(getViewLifecycleOwner(), resource -> {
            if (resource instanceof Resource.Success) {
                AnimeRecommendationResponse response = resource.getData();
                if (response != null) {
                    animeRecommendationsAdapter.submitList(response.getData());
                }
            }
            animeRecommendationsAdapter.setLoading(resource instanceof Resource.Loading);
        });
    }

    private void setupItemClickListeners() {
        animeRecommendationsAdapter.setOnItemClickListener(animeId -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", animeId);
            NavOptions navOptions = new NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right)
                    .build();
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_animeRecommendationsFragment_to_animeDetailFragment, bundle, navOptions);
        });
    }

    private void setupRefreshButton() {
        binding.fabRefresh.setOnClickListener(v -> viewModel.refreshData());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}