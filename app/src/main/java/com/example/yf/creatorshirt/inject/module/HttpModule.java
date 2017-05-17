package com.example.yf.creatorshirt.inject.module;

import android.support.compat.BuildConfig;

import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.model.db.RequestApi;
import com.example.yf.creatorshirt.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yang on 17/05/2017.
 * 依赖的网络请求框架
 */
@Module
public class HttpModule {

    @Singleton
    @Provides
    Retrofit providerRequest(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, RequestApi.HOST);
    }

    /**
     * 将适配器请求的地址加入retrofit
     * 提供RequestApi的对象
     *
     * @param retrofit
     * @return
     */
    @Singleton
    @Provides
    RequestApi providerNetWorkApi(Retrofit retrofit) {
        return retrofit.create(RequestApi.class);
    }

    /**
     * 提供retrofit单列
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
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    /**
     * 设置OkHttp的client
     *
     * @param builder
     * @return
     */
    @Singleton
    @Provides
    OkHttpClient providerClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        //配置缓存路径
        File cacheFile = new File(FileUtils.getCacheFile() + "/NetCache");
        final Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isNetWorkConnected()) {
                    //无网络强制使用缓存
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isNetWorkConnected()) {
                    int maxAge = 0;
                    response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    /**
     * 创建retrofit适配器
     *
     * @param builder
     * @param client
     * @param url
     * @return
     */
    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
