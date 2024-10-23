package com.example.animeappjava.repository;

import com.example.animeappjava.data.local.dao.AnimeDetailDao;
import com.example.animeappjava.data.remote.api.AnimeAPI;
import com.example.animeappjava.data.remote.api.RetrofitInstance;
import com.example.animeappjava.models.AnimeDetail;
import com.example.animeappjava.models.AnimeDetailResponse;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnimeDetailRepository {

    private final AnimeDetailDao animeDetailDao;
    private final AnimeAPI animeAPI;

    public AnimeDetailRepository(AnimeDetailDao animeDetailDao) {
        this.animeDetailDao = animeDetailDao;
        this.animeAPI = RetrofitInstance.api;
    }

    public Single<AnimeDetailResponse> getAnimeDetail(int id) {
        return Single.fromCallable(() -> {
                    AnimeDetail cachedAnimeDetail = animeDetailDao.getAnimeDetailById(id);
                    if (cachedAnimeDetail != null) {
                        return new AnimeDetailResponse(cachedAnimeDetail);
                    }

                    AnimeDetailResponse response = animeAPI.getAnimeDetail(id).execute().body();
                    if (response != null) {
                        return response;
                    } else {
                        throw new Exception("API response is null");
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public AnimeDetailResponse getCachedAnimeDetail(int id) {
        AnimeDetail cachedAnimeDetail = animeDetailDao.getAnimeDetailById(id);
        return cachedAnimeDetail != null ? new AnimeDetailResponse(cachedAnimeDetail) : null;
    }

    public void cacheAnimeDetail(AnimeDetailResponse animeDetailResponse) {
        Single.fromCallable(() -> {
                    animeDetailDao.insertAnimeDetail(animeDetailResponse.getData());
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}