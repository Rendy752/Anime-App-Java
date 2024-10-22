package com.example.animeappjava.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.animeappjava.R;
import com.example.animeappjava.databinding.FragmentDetailBinding;
import com.example.animeappjava.models.AnimeDetail;
import com.example.animeappjava.models.AnimeDetailResponse;
import com.example.animeappjava.models.CommonIdentity;
import com.example.animeappjava.ui.activities.MainActivity;
import com.example.animeappjava.ui.adapters.TitleSynonymsAdapter;
import com.example.animeappjava.ui.viewmodels.AnimeDetailViewModel;
import com.example.animeappjava.utils.Resource;
import com.example.animeappjava.utils.TextUtils;

import java.util.Arrays;

public class AnimeDetailFragment extends Fragment implements MenuProvider {

    private FragmentDetailBinding binding;
    private AnimeDetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = ((MainActivity) requireActivity()).getAnimeDetailViewModel();
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupMenu();
        setupViewModel();
        observeAnimeDetail();
    }

    private void setupMenu() {
        requireActivity().addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void setupViewModel() {
        int animeId = getArguments() != null ? getArguments().getInt("id") : 0;
        if (animeId != 0) {
            viewModel.getAnimeDetail(animeId);
        }
    }

    private void observeAnimeDetail() {
        viewModel.getAnimeDetail().observe(getViewLifecycleOwner(), new Observer<Resource<AnimeDetailResponse>>() {
            @Override
            public void onChanged(Resource<AnimeDetailResponse> resource) {
                if (resource instanceof Resource.Success) {
                    handleSuccess(resource.getData());
                } else if (resource instanceof Resource.Error) {
                    handleError(resource.getMessage());
                } else if (resource instanceof Resource.Loading) {
                    handleLoading();
                } else {
                    handleEmpty();
                }
            }
        });
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.detail_fragment_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_share) {
            Resource<AnimeDetailResponse> resource = viewModel.getAnimeDetail().getValue();

            if (resource instanceof Resource.Success) {
                AnimeDetailResponse response = resource.getData();
                if (response != null && response.getData() != null) {
                    AnimeDetail detail = response.getData();

                    String animeUrl = detail.getUrl();
                    String animeTitle = detail.getTitle();
                    String animeScore = String.valueOf(detail.getScore());
                    String animeGenres = TextUtils.joinOrNA(detail.getGenres(), g -> g.getName());

                    String animeSynopsis = TextUtils.formatSynopsis(detail.getSynopsis());
                    String animeTrailerUrl = detail.getTrailer().getUrl() != null ? detail.getTrailer().getUrl() : "";
                    int malId = detail.getMal_id();
                    String customUrl = "animeappjava://anime/detail/" + malId;

                    String trailerSection = !animeTrailerUrl.isEmpty() ? "\n-------\nTrailer\n-------\n" + animeTrailerUrl : "";

                    String sharedText = "Check out this anime on animeappjava!\n\n" +
                            "Title: " + animeTitle + "\n" +
                            "Score: " + animeScore + "\n" +
                            "Genres: " + animeGenres + "\n\n" +
                            "--------\nSynopsis\n--------\n" +
                            animeSynopsis +
                            trailerSection + "\n\n" +
                            "Web URL: " + animeUrl + "\n" +
                            "App URL: " + customUrl + "\n" +
                            "Download the app now: https://play.google.com/store/apps/details?id=com.example.animeappjava";

                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, sharedText);
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }
            }
            return true;
        }
        return false;
    }

    private void handleSuccess(AnimeDetailResponse response) {
        if (response != null && response.getData() != null) {
            AnimeDetail detail = response.getData();

            binding.shimmerViewContainer.stopShimmer();
            binding.shimmerViewContainer.hideShimmer();
            binding.tvError.setVisibility(View.GONE);

            Glide.with(this).load(detail.getImages().getJpg().getLargeImageUrl()).into(binding.ivAnimeImage);
            binding.tvTitle.setText(detail.getTitle());

            binding.tvTitle.setOnClickListener(v -> {
                Resource<AnimeDetailResponse> currentResource = viewModel.getAnimeDetail().getValue();
                if (currentResource instanceof Resource.Success) {
                    AnimeDetailResponse currentResponse = currentResource.getData();
                    if (currentResponse != null && currentResponse.getData() != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentResponse.getData().getUrl()));
                        startActivity(intent);
                    }
                }
            });

            binding.tvEnglishTitle.setText(detail.getTitle_english());
            binding.tvJapaneseTitle.setText(detail.getTitle_japanese());

            binding.rvTitleSynonyms.setAdapter(new TitleSynonymsAdapter(Arrays.asList(detail.getTitle_synonyms())));
            binding.rvTitleSynonyms.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

            binding.ivApproved.setVisibility(detail.isApproved() ? View.VISIBLE : View.GONE);
            binding.ivAired.setVisibility(detail.isAiring() ? View.VISIBLE : View.GONE);
            binding.ivNotAired.setVisibility(detail.isAiring() ? View.GONE : View.VISIBLE);

            binding.tvStatus.setText(detail.getStatus());
            binding.tvType.setText(detail.getType());
            binding.tvSource.setText(detail.getSource());
            binding.tvSeason.setText(detail.getSeason() != null ? detail.getSeason() : "-");
            binding.tvReleased.setText(detail.getYear() != null ? detail.getYear().toString() : "-");
            binding.tvAired.setText(detail.getAired().getString());
            binding.tvRating.setText(detail.getRating());
            binding.tvGenres.setText(TextUtils.joinOrNA(detail.getGenres(), CommonIdentity::getName));
            binding.tvEpisodes.setText(String.valueOf(detail.getEpisodes()));

            binding.tvStudios.setText(TextUtils.joinOrNA(detail.getStudios(), CommonIdentity::getName));
            binding.tvProducers.setText(TextUtils.joinOrNA(detail.getProducers(), CommonIdentity::getName));
            binding.tvLicensors.setText(TextUtils.joinOrNA(detail.getLicensors(), CommonIdentity::getName));
            binding.tvBroadcast.setText(detail.getBroadcast().getString() != null ? detail.getBroadcast().getString() : "-");
            binding.tvDuration.setText(detail.getDuration());

            String embedUrl = detail.getTrailer().getEmbedUrl() != null ? detail.getTrailer().getEmbedUrl() : "";
            if (!embedUrl.isEmpty()) {
                binding.llYoutubePreview.setVisibility(View.VISIBLE);
                binding.youtubePlayerView.playVideo(embedUrl);
            }

            binding.tvScore.setText(String.valueOf(detail.getScore()));
            binding.tvScoredBy.setText(getResources().getString(R.string.scored_by_users, detail.getScoredBy()));
            binding.tvRanked.setText(getResources().getString(R.string.ranked_number, detail.getRank()));
            binding.tvPopularity.setText(getResources().getString(R.string.popularity_number, detail.getPopularity()));
            binding.tvMembers.setText(String.valueOf(detail.getMembers()));
            binding.tvFavorites.setText(String.valueOf(detail.getFavorites()));

            if (detail.getBackground() != null && !detail.getBackground().trim().isEmpty()) {
                binding.llBackground.setVisibility(View.VISIBLE);
                binding.tvBackground.setText(detail.getBackground());
            } else {
                binding.llBackground.setVisibility(View.GONE);
            }

            binding.tvSynopsis.setText(detail.getSynopsis());
        }
    }

    @SuppressLint("SetTextI18n")
    private void handleError(String message) {
        binding.shimmerViewContainer.stopShimmer();
        binding.shimmerViewContainer.hideShimmer();
        binding.tvError.setVisibility(View.VISIBLE);
        binding.tvError.setText("An error occurred: " + message);
    }

    private void handleLoading() {
        binding.shimmerViewContainer.showShimmer(true);
        binding.shimmerViewContainer.startShimmer();
    }

    @SuppressLint("SetTextI18n")
    private void handleEmpty() {
        binding.shimmerViewContainer.stopShimmer();
        binding.shimmerViewContainer.hideShimmer();
        binding.tvError.setVisibility(View.VISIBLE);
        binding.tvError.setText("An error occurred while fetching the anime detail.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}