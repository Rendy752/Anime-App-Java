package com.example.animeappjava.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeappjava.databinding.TitleSynonymItemBinding;

import java.util.List;

public class TitleSynonymsAdapter extends RecyclerView.Adapter<TitleSynonymsAdapter.SynonymViewHolder> {

    private final List<String> synonyms;

    public TitleSynonymsAdapter(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    @NonNull
    @Override
    public SynonymViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TitleSynonymItemBinding binding = TitleSynonymItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SynonymViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SynonymViewHolder holder, int position) {
        holder.binding.tvTitleSynonym.setText(synonyms.get(position));
    }

    @Override
    public int getItemCount() {
        return synonyms.size();
    }

    static class SynonymViewHolder extends RecyclerView.ViewHolder {

        public final TitleSynonymItemBinding binding;

        public SynonymViewHolder(TitleSynonymItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}