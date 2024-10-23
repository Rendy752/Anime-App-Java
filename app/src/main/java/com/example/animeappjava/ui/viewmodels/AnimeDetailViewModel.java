package com.example.animeappjava.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animeappjava.models.AnimeDetailResponse;
import com.example.animeappjava.repository.AnimeDetailRepository;
import com.example.animeappjava.utils.Resource;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnimeDetailViewModel extends ViewModel {

    private final AnimeDetailRepository animeDetailRepository;
    private final MutableLiveData<Resource<AnimeDetailResponse>> animeDetail = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public AnimeDetailViewModel(AnimeDetailRepository animeDetailRepository) {
        this.animeDetailRepository = animeDetailRepository;
    }

    public LiveData<Resource<AnimeDetailResponse>> getAnimeDetail() {
        return animeDetail;
    }

    public void getAnimeDetail(int id) {
        disposables.add(
                Single.fromCallable(() -> getCachedAnimeDetail(id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (response instanceof Resource.Success) {
                                animeDetail.setValue(response);
                            } else if (response instanceof Resource.Error) {
                                fetchAnimeDetailFromNetwork(id);
                            }
                        }, throwable -> {
                            animeDetail.postValue(Resource.error(Objects.requireNonNull(throwable.getMessage()), null));
                        })
        );
    }

    private void fetchAnimeDetailFromNetwork(int id) {
        animeDetail.postValue(Resource.loading());

        disposables.add(
                animeDetailRepository.getAnimeDetail(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                animeDetailResponse -> {
                                    cacheAnimeDetail(animeDetailResponse);
                                    animeDetail.postValue(Resource.success(animeDetailResponse));
                                },
                                e -> animeDetail.postValue(Resource.error(Objects.requireNonNull(e.getMessage()), null))
                        )
        );
    }

    private Resource<AnimeDetailResponse> getCachedAnimeDetail(int id) {
        AnimeDetailResponse cachedAnimeDetail = animeDetailRepository.getCachedAnimeDetail(id);
        return cachedAnimeDetail != null ? Resource.success(cachedAnimeDetail) : Resource.error("Anime detail not found in cache", null);
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