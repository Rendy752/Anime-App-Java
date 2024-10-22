package com.example.animeappjava.data.remote.api;

import com.example.animeappjava.utils.Const;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitInstance {

    private static final String BASE_URL = Const.BASE_URL;
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static AnimeAPI api = retrofit.create(AnimeAPI.class);

    public static void addInterceptor(Interceptor interceptor) {
        if (!okHttpClient.interceptors().contains(interceptor)) {
            okHttpClient = okHttpClient.newBuilder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = retrofit.newBuilder()
                    .client(okHttpClient)
                    .build();

            api = retrofit.create(AnimeAPI.class);
        }
    }
}