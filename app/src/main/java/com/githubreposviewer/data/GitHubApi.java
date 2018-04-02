package com.githubreposviewer.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.logging.Level;

public class GitHubApi {

    private static final String BASE_URL = "https://api.github.com/";

    private GitHubService service;

    public GitHubApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        service= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubService.class);
    }

    public GitHubService getService() {
        return service;
    }
}
