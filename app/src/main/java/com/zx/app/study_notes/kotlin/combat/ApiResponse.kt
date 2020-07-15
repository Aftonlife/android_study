package com.zx.app.study_notes.kotlin.combat

import android.content.Context
import com.zx.app.study_notes.kotlin.combat.entity.LoginResponseWrapper
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author Afton
 * date 2020/6/27
 * 自定义Rxjava 操作符 分解包装bean
 *
 */
abstract class ApiResponse<T>(val context: Context)//主构造
    : Observer<LoginResponseWrapper<T>> {

    private var isShow: Boolean = true

    //次构造
    constructor(context: Context, isShow: Boolean = false) : this(context) {
        this.isShow = isShow
    }

    //成功
    abstract fun success(data: T?)

    //失败
    abstract fun failure(errMessage: String?)

    //请求完成
    override fun onComplete() {
        LoadingDialog.cancel()
    }

    //请求开始
    override fun onSubscribe(d: Disposable) {
        //loading
        if (isShow)
            LoadingDialog.show(context)
    }

    //执行Response
    override fun onNext(t: LoginResponseWrapper<T>) {
        if (t.data == null) {
            failure("登录失败，请检测原因${t.errorMsg}")
        } else {

            success(t.data)
        }
    }

    //请求报错
    override fun onError(e: Throwable) {
        LoadingDialog.cancel()
        failure(e.toString())
    }
}