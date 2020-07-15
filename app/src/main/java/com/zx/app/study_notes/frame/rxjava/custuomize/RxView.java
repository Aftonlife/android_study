package com.zx.app.study_notes.frame.rxjava.custuomize;

import android.view.View;

import io.reactivex.Observable;

/**
 * author Afton
 * date 2020/6/2
 * 自定义Rxjava 操作符 防抖动
 */
public class RxView {
    /*操作符*/
    public static Observable<Object>click(View view){
        return new ViewClickObservable(view);
    }
}
