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
import com.example.animeappjava.models.AnimeRecommendation;
import com.example.animeappjava.models.AnimeRecommendationResponse;
import com.example.animeappjava.repository.AnimeRecommendationsRepository;
import com.example.animeappjava.ui.activities.MainActivity;
import com.example.animeappjava.ui.adapters.AnimeRecommendationsAdapter;
import com.example.animeappjava.ui.providerfactories.AnimeRecommendationsViewModelProviderFactory;
import com.example.animeappjava.ui.viewmodels.AnimeRecommendationsViewModel;
import com.example.animeappjava.utils.Resource;

import java.util.List;

public class AnimeRecommendationsFragment extends Fragment {

    private FragmentRecommendationBinding binding;
    private AnimeRecommendationsViewModel viewModel;
    private AnimeRecommendationsAdapter animeRecommendationsAdapter;

    @Nullable
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
        setupObservers();
        setupClickListeners();
        setupRefreshFloatingActionButton();
    }

    private void setupRecyclerView() {
        animeRecommendationsAdapter = new AnimeRecommendationsAdapter();
        binding.rvAnimeRecommendations.setAdapter(animeRecommendationsAdapter);
        binding.rvAnimeRecommendations.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupViewModel() {
        AnimeRecommendationsRepository repository = new AnimeRecommendationsRepository(AnimeRecommendationsDatabase.getDatabase(getActivity()));
        AnimeRecommendationsViewModelProviderFactory factory = new AnimeRecommendationsViewModelProviderFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(AnimeRecommendationsViewModel.class);
    }

    private void setupObservers() {
        viewModel.getAnimeRecommendations().observe(getViewLifecycleOwner(), resource -> {
            if (resource instanceof Resource.Success) {
                AnimeRecommendationResponse response = resource.getData();

                if (response != null) {
                    List<AnimeRecommendation> recommendations = response.getData();
                    animeRecommendationsAdapter.submitList(recommendations);
                }

                animeRecommendationsAdapter.setLoading(false);
            } else if (resource instanceof Resource.Error) {
                animeRecommendationsAdapter.setLoading(false);
            } else if (resource instanceof Resource.Loading) {
                animeRecommendationsAdapter.setLoading(true);
            }
        });
    }

    private void setupClickListeners() {
        animeRecommendationsAdapter.setOnItemClickListener(animeId -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", animeId);
            NavOptions navOptions = new NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right)
                    .build();
            NavHostFragment.findNavController(AnimeRecommendationsFragment.this)
                    .navigate(R.id.action_animeRecommendationsFragment_to_animeDetailFragment, bundle, navOptions);
        });
    }

    private void setupRefreshFloatingActionButton() {
        binding.fabRefresh.setOnClickListener(v -> viewModel.refreshData());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}