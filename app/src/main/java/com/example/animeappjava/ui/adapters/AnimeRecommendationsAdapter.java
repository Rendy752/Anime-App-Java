package com.example.animeappjava.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animeappjava.databinding.AnimeRecommendationItemBinding;
import com.example.animeappjava.models.AnimeRecommendation;
import com.example.animeappjava.utils.DateUtils;

import java.util.List;

public class AnimeRecommendationsAdapter extends RecyclerView.Adapter<AnimeRecommendationsAdapter.AnimeRecommendationViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private boolean isLoading = false;
    private OnItemClickListener onItemClickListener;

    public AnimeRecommendationsAdapter() {
        differ = new AsyncListDiffer<>(this, new DiffUtil.ItemCallback<AnimeRecommendation>() {
            @Override
            public boolean areItemsTheSame(@NonNull AnimeRecommendation oldItem, @NonNull AnimeRecommendation newItem) {
                return oldItem.getMal_id() == newItem.getMal_id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull AnimeRecommendation oldItem, @NonNull AnimeRecommendation newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        notifyDataSetChanged();
    }


    public void submitList(List<AnimeRecommendation> list) {
        differ.submitList(list);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public AnimeRecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnimeRecommendationItemBinding binding = AnimeRecommendationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AnimeRecommendationViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return (isLoading && position < 5) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeRecommendationViewHolder holder, int position) {
        AnimeRecommendation animeRecommendation = (isLoading || position >= differ.getCurrentList().size()) ?
                null : differ.getCurrentList().get(position);

        holder.bind(animeRecommendation, isLoading && position < 5);
    }

    @Override
    public int getItemCount() {
        return isLoading ? 5 : differ.getCurrentList().size();
    }


    public interface OnItemClickListener {
        void onItemClick(int malId);
    }


    class AnimeRecommendationViewHolder extends RecyclerView.ViewHolder {

        private final AnimeRecommendationItemBinding binding;

        public AnimeRecommendationViewHolder(AnimeRecommendationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AnimeRecommendation animeRecommendation, boolean isLoading) {
            if (isLoading) {
                binding.shimmerViewContainer.startShimmer();
            } else {
                binding.shimmerViewContainer.stopShimmer();
                binding.shimmerViewContainer.hideShimmer();

                if (animeRecommendation != null) {
                    bindAnimeData(binding, animeRecommendation);
                    setupClickListeners(binding, animeRecommendation);
                    resetBackgrounds(binding);
                }
            }
        }

        private void bindAnimeData(AnimeRecommendationItemBinding itemBinding, AnimeRecommendation recommendation) {
            Glide.with(itemView.getContext())
                    .load(recommendation.getEntry().get(0).getImages().getJpg().getImageUrl())
                    .into(itemBinding.ivFirstAnimeImage);
            itemBinding.tvFirstAnimeTitle.setText(recommendation.getEntry().get(0).getTitle());
            Glide.with(itemView.getContext())
                    .load(recommendation.getEntry().get(1).getImages().getJpg().getImageUrl())
                    .into(itemBinding.ivSecondAnimeImage);
            itemBinding.tvSecondAnimeTitle.setText(recommendation.getEntry().get(1).getTitle());
            itemBinding.tvContent.setText(recommendation.getContent());
            itemBinding.tvRecommendedBy.setText("recommended by " + recommendation.getUser().getUsername());
            itemBinding.tvDate.setText("~ " + DateUtils.formatDateToAgo(recommendation.getDate()));
        }

        private void setupClickListeners(AnimeRecommendationItemBinding itemBinding, AnimeRecommendation recommendation) {
            itemBinding.tvFirstAnimeTitle.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(recommendation.getEntry().get(0).getMal_id());
                }
            });

            itemBinding.tvSecondAnimeTitle.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(recommendation.getEntry().get(1).getMal_id());
                }
            });
        }

        private void resetBackgrounds(AnimeRecommendationItemBinding itemBinding) {
            itemBinding.ivFirstAnimeImage.setBackground(null);
            itemBinding.tvFirstAnimeTitle.setBackground(null);
            itemBinding.ivSecondAnimeImage.setBackground(null);
            itemBinding.tvSecondAnimeTitle.setBackground(null);
            itemBinding.tvContent.setBackground(null);
            itemBinding.tvRecommendedBy.setBackground(null);
            itemBinding.tvDate.setBackground(null);
        }
    }

    private final AsyncListDiffer<AnimeRecommendation> differ;
}