package com.zx.app.study_notes.frame.rxjava.custuomize;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * author Afton
 * date 2020/6/2
 */
public class ViewClickObservable extends Observable<Object> {
    private final View view;
    private static final Object EVENT=new Object();

    public ViewClickObservable(View view) {
        this.view = view;
    }

    @Override
    protected void subscribeActual(Observer<? super Object> observer) {
        //做自己的处理

        /*包装observer*/
        MyListener myListener = new MyListener(this.view, observer);
        /*首先执行onSubscribe*/
        observer.onSubscribe(myListener);

        //设置点击监听
        this.view.setOnClickListener(myListener);
    }

    static class MyListener implements View.OnClickListener, Disposable {

        final View view;
        final Observer<Object> observer;

        final AtomicBoolean isDispose = new AtomicBoolean();

        public MyListener(View view, Observer<? super Object> observer) {
            this.view = view;
            this.observer = observer;

        }

        @Override
        public void onClick(View v) {
            //没有中断才执行
            if (isDisposed() == false) {
                observer.onNext(v);
            }
        }

        //取消订阅 这里执行取消点击事件 view.setOnClickListener(null);
        @Override
        public void dispose() {
            //只有第一次调用dispose才执行
            if (isDispose.compareAndSet(false, true)) {
                //判断当前线程是不是主线程
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    view.setOnClickListener(null);
                } else {
                    //切换到主线程
                    //常用方式Handler 切换
//                    new Handler(Looper.getMainLooper()){
//                        @Override
//                        public void handleMessage(@NonNull Message msg) {
//                            super.handleMessage(msg);
//                            view.setOnClickListener(null);
//                        }
//                    };
                    //Rxjava Schedulers切换
                    AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                        @Override
                        public void run() {
                            view.setOnClickListener(null);
                        }
                    });
                }
            }
        }

        @Override
        public boolean isDisposed() {
            return isDispose.get();
        }
    }
}
