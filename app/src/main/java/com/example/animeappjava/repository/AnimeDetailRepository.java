package com.example.animeappjava.repository;

import com.example.animeappjava.data.local.dao.AnimeDetailDao;
import com.example.animeappjava.data.remote.api.RetrofitInstance;
import com.example.animeappjava.models.AnimeDetail;
import com.example.animeappjava.models.AnimeDetailResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;

public class AnimeDetailRepository {

    private final AnimeDetailDao animeDetailDao;
    private final Executor executor;

    public AnimeDetailRepository(AnimeDetailDao animeDetailDao) {
        this.animeDetailDao = animeDetailDao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public Call<AnimeDetailResponse> getAnimeDetail(int id) {
        return RetrofitInstance.api.getAnimeDetail(id);
    }

    public AnimeDetailResponse getCachedAnimeDetail(int id) {
        AnimeDetail cachedAnimeDetail = animeDetailDao.getAnimeDetailById(id);
        return cachedAnimeDetail != null ? new AnimeDetailResponse(cachedAnimeDetail) : null;
    }

    public void cacheAnimeDetail(AnimeDetailResponse animeDetailResponse) {
        executor.execute(() -> {
            animeDetailDao.insertAnimeDetail(animeDetailResponse.getData());
        });
    }
}