package com.example.animeappjava.ui.viewmodels;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animeappjava.models.AnimeDetailResponse;
import com.example.animeappjava.repository.AnimeDetailRepository;
import com.example.animeappjava.utils.Resource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeDetailViewModel extends ViewModel {

    private final AnimeDetailRepository animeDetailRepository;
    private final Executor executor;
    private final MutableLiveData<Resource<AnimeDetailResponse>> animeDetail;
    private final Handler mainHandler;

    public AnimeDetailViewModel(AnimeDetailRepository animeDetailRepository) {
        this.animeDetailRepository = animeDetailRepository;
        this.executor = Executors.newSingleThreadExecutor();
        this.animeDetail = new MutableLiveData<>();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public LiveData<Resource<AnimeDetailResponse>> getAnimeDetail() {
        return animeDetail;
    }

    public void getAnimeDetail(int id) {
        executor.execute(() -> {
            Resource<AnimeDetailResponse> cachedResponse = getCachedAnimeDetail(id);
            if (cachedResponse != null) {
                // Post to main thread using Handler
                mainHandler.post(() -> animeDetail.setValue(cachedResponse));
                return;
            }

            // Post loading state to main thread
            mainHandler.post(() -> animeDetail.setValue(Resource.loading()));

            // Get the Call object from the repository
            Call<AnimeDetailResponse> call = animeDetailRepository.getAnimeDetail(id);

            // Enqueue the call
            call.enqueue(new Callback<AnimeDetailResponse>() {
                @Override
                public void onResponse(Call<AnimeDetailResponse> call, Response<AnimeDetailResponse> response) {
                    // Post result to main thread
                    mainHandler.post(() -> animeDetail.setValue(handleAnimeDetailResponse(response)));
                }

                @Override
                public void onFailure(Call<AnimeDetailResponse> call, Throwable t) {
                    // Post error to main thread
                    mainHandler.post(() -> animeDetail.setValue(Resource.error(t.getMessage(), null)));
                }
            });
        });
    }

    private Resource<AnimeDetailResponse> handleAnimeDetailResponse(Response<AnimeDetailResponse> response) {
        if (response.isSuccessful()) {
            AnimeDetailResponse resultResponse = response.body();
            if (resultResponse != null) {
                cacheAnimeDetail(resultResponse);
                return Resource.success(resultResponse);
            }
        }
        return Resource.error(response.message(), null);
    }

    private Resource<AnimeDetailResponse> getCachedAnimeDetail(int id) {
        AnimeDetailResponse cachedAnimeDetail = animeDetailRepository.getCachedAnimeDetail(id);
        return cachedAnimeDetail != null ? Resource.success(cachedAnimeDetail) : null;
    }

    private void cacheAnimeDetail(AnimeDetailResponse animeDetailResponse) {
        executor.execute(() -> {
            animeDetailRepository.cacheAnimeDetail(animeDetailResponse);
        });
    }
}