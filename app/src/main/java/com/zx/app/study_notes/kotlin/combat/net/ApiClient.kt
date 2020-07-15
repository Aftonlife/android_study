package com.zx.app.study_notes.kotlin.combat.net

import com.zx.app.study_notes.kotlin.combat.config.Flag
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * author Afton
 * date 2020/6/26
 */
class ApiClient {
    private object Holder {
        var INSTANCE = ApiClient()
    }

    companion object {
        val instance = Holder.INSTANCE
    }

    //WanAndroidApi 实例化 泛型
    fun <T> instanceRetrofit(apiInterface: Class<T>): T {
        //OkHttp
        val mOkHttpClient = OkHttpClient.Builder()
                //读取超时
                .readTimeout(10000, TimeUnit.SECONDS)
                //连接超时
                .connectTimeout(10000, TimeUnit.SECONDS)
                //写入超时
                .writeTimeout(10000, TimeUnit.SECONDS)
                .build()

        //请求方
        var retrofit = Retrofit.Builder()
                .baseUrl(Flag.BASE_URL)
                .client(mOkHttpClient)
                //响应方
                .addConverterFactory(GsonConverterFactory.create())//Gson解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Rxjava
                .build()

        return retrofit.create(apiInterface)
    }
}