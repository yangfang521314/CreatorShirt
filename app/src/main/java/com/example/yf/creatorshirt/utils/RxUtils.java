package com.example.yf.creatorshirt.utils;

import com.example.yf.creatorshirt.http.HttpResponse;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yang on 02/06/2017.
 * 主线程和分线程的切换
 */

public class RxUtils {


    /**
     * 统一线程处理（Flowable）
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {//compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> flowable) {
                return flowable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理针对Observable
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxObScheduleHelper() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一处理服务器数据
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResponse<T>, T> handleResult() {
        return new FlowableTransformer<HttpResponse<T>, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<HttpResponse<T>> flowable) {
                return flowable.flatMap(new Function<HttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(@NonNull HttpResponse<T> tHttpResponse) throws Exception {
                        if (tHttpResponse.getStatus() == 1) {
                            return createData(tHttpResponse.getResult());
                        } else {
                            return Flowable.error(new Throwable("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 统一处理服务器数据
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResponse<T>, T> handleObservableResult() {
        return new ObservableTransformer<HttpResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<HttpResponse<T>> upstream) {
                return upstream.flatMap(new Function<HttpResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull HttpResponse<T> tHttpResponse) throws Exception {
                        if (tHttpResponse.getStatus() == 1) {
                            return createObservablData(tHttpResponse.getResult());
                        } else {
                            return Observable.error(new Throwable("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    private static <T> ObservableSource<T> createObservablData(final T result) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(result);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * 生成Flowable,数据量大的时候背压处理
     *
     * @param result
     * @param <T>
     * @return
     */
    private static <T> Flowable<T> createData(final T result) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(result);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
