package com.zx.app.study_notes.frame.rxjava;

import android.annotation.SuppressLint;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * author Afton
 * date 2020/6/1
 */
public class RxjavaStudy {

    public static final String TAG = "RxjavaStudy";

    /*Rxjava*/
    private Disposable disposable;

    public static void main(String[] args) {
        startRxJava();
    }

    @SuppressLint("CheckResult")
    public static void startRxJava() {
        String[] names = {"a", "b", "c"};

        //Hook
//        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
//            @Override
//            public Scheduler apply(Scheduler scheduler) throws Exception {
//                //静态全局监听
//                System.out.println(TAG + " 全局 监听 scheduler" + scheduler);
//                return scheduler;
//            }
//        });
//        RxJavaPlugins.setInitIoSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
//            @Override
//            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
//                //静态全局监听
//                System.out.println(TAG + " 全局 监听 初始化 scheduler" + schedulerCallable);
//                return schedulerCallable.call();
//            }
//        });

        Observable.create(
                //自定义Source
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        //线程池执行 异步
                        emitter.onNext("a");
                        System.out.println(TAG + " subscribe: " + Thread.currentThread().getName());
//                        emitter.onNext("b");
//                        emitter.onNext("c");
                    }
                })
                //todo 第二部 ObservableCreate。subscribeOn
                //给上面代码代码用
                //subscribeOn(1) 上层代码 1线程执行
                //subscribeOn(2)
                //subscribeOn(3)
                .subscribeOn(
                        //todo 第一部（new IOScheduler ---> 线程池）
                        Schedulers.io()
                )
                //给下面代码用
                //observeOn（A）
                //observeOn（B）
                //observeOn（C）xian
                .observeOn(Schedulers.newThread())
                //A线程 .subscribe
                //ObservableSubscribeOn.subscribe
                .subscribe(
                        //终点
                        new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                //A线程
                                System.out.println(TAG + " onSubscribe: " + Thread.currentThread().getName());
                            }

                            @Override
                            public void onNext(String s) {
                                //终点 相当于 emitter.onNext("xxx"); 线程池
                                System.out.println(TAG + " onNext: " + Thread.currentThread().getName());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });


    }
}
