package com.zx.app.study_notes.kotlin.combat.api

import com.zx.app.study_notes.kotlin.combat.entity.LoginResponseBean
import com.zx.app.study_notes.kotlin.combat.entity.LoginResponseWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*


/**
 * author Afton
 * date 2020/6/26
 */
interface WanAndroidApi {

    /**
     * 登录
     * 返回包装bean
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun loginAction(@Field("username") username: String,
                    @Field("password") password: String)
    :Observable<LoginResponseWrapper<LoginResponseBean>>
    
    
}