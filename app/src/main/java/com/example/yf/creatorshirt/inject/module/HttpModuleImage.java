package com.example.yf.creatorshirt.inject.module;


import com.example.yf.creatorshirt.http.RequestImageApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModuleImage {

    /**
     * 提供retrofit2单列
     *
     * @param builder
     * @param client
     * @return
     */
    @Singleton
    @Provides
    Retrofit providerRequest(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, RequestImageApi.host2);
    }

    /**
     * 提供Build单列
     *
     * @return
     */
    @Singleton
    @Provides
    Retrofit.Builder providerRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    RequestImageApi providerRequestImageApi(Retrofit retrofit) {
        return retrofit.create(RequestImageApi.class);
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient providerOkHttpClient(OkHttpClient.Builder builder) {

    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String host) {
        return builder.baseUrl(host)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
