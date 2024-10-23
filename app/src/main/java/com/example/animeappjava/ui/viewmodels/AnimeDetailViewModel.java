package com.example.animeappjava.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animeappjava.models.AnimeDetailResponse;
import com.example.animeappjava.repository.AnimeDetailRepository;
import com.example.animeappjava.utils.Resource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnimeDetailViewModel extends ViewModel {

    private final AnimeDetailRepository animeDetailRepository;
    private final MutableLiveData<Resource<AnimeDetailResponse>> animeDetail;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public AnimeDetailViewModel(AnimeDetailRepository animeDetailRepository) {
        this.animeDetailRepository = animeDetailRepository;
        this.animeDetail = new MutableLiveData<>();
    }

    public LiveData<Resource<AnimeDetailResponse>> getAnimeDetail() {
        return animeDetail;
    }

    public void getAnimeDetail(int id) {
        disposables.add(
                Single.fromCallable(() -> {
                            Resource<AnimeDetailResponse> cachedResponse = getCachedAnimeDetail(id);
                            if (cachedResponse != null) {
                                return cachedResponse;
                            } else {
                                return Resource.error("Anime detail not found in cache", null);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (response instanceof Resource.Success) {
                                animeDetail.setValue((Resource.Success<AnimeDetailResponse>) response);
                            } else if (response instanceof Resource.Error) {
                                fetchAnimeDetailFromNetwork(id);
                            }
                        }, throwable -> {
                            animeDetail.postValue(Resource.error(throwable.getMessage(), null));
                        })
        );
    }

    private void fetchAnimeDetailFromNetwork(int id) {
        animeDetail.postValue(Resource.loading());

        animeDetailRepository.getAnimeDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AnimeDetailResponse>() {
                    private Disposable d;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        this.d = d;
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull AnimeDetailResponse animeDetailResponse) {
                        cacheAnimeDetail(animeDetailResponse);
                        animeDetail.postValue(Resource.success(animeDetailResponse));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        animeDetail.postValue(Resource.error(e.getMessage(), null));
                    }
                });
    }

    private Resource<AnimeDetailResponse> getCachedAnimeDetail(int id) {
        AnimeDetailResponse cachedAnimeDetail = animeDetailRepository.getCachedAnimeDetail(id);
        return cachedAnimeDetail != null ? Resource.success(cachedAnimeDetail) : null;
    }

    private void cacheAnimeDetail(AnimeDetailResponse animeDetailResponse) {
        disposables.add(
                Single.fromCallable(() -> {
                            animeDetailRepository.cacheAnimeDetail(animeDetailResponse);
                            return true;
                        })
                        .subscribeOn(Schedulers.io())
                        .subscribe()
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}