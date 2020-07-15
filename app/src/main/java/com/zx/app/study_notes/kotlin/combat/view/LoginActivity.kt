package com.zx.app.study_notes.kotlin.combat.view

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zx.app.study_notes.R
import com.zx.app.study_notes.kotlin.combat.ApiResponse
import com.zx.app.study_notes.kotlin.combat.api.WanAndroidApi
import com.zx.app.study_notes.kotlin.combat.entity.LoginResponseBean
import com.zx.app.study_notes.kotlin.combat.entity.LoginResponseWrapper
import com.zx.app.study_notes.kotlin.combat.net.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

/**
 * author Afton
 * date 2020/6/26
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        ApiClient.getInstance().instanceRetrofit(WanAndroidApi::class.java)
        btn_login.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> {
                val userName = et_account.text.toString()
                val userPassword = et_password.text.toString()
                println("userName:$userName, userPassword:$userPassword")

                //Rxjava
                ApiClient.instance.instanceRetrofit(WanAndroidApi::class.java)
                        .loginAction(userName, userPassword)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(object : Consumer<LoginResponseWrapper<LoginResponseBean>> {
//                            override fun accept(t: LoginResponseWrapper<LoginResponseBean>?) {
//                                var toast: Toast
//                                if (t?.data == null) {
//                                    toast = Toast.makeText(this@LoginActivity, "登录失败~ 呜呜呜", Toast.LENGTH_SHORT)
//                                    toast.setGravity(Gravity.CENTER, 0, 0)
//                                    toast.show()
//                                } else {
//                                    toast =   Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT)
//                                    toast .setGravity(Gravity.CENTER, 0, 0)
//                                    toast.show()
//                                }
//                            }
//                        })
                        .subscribe(object : ApiResponse<LoginResponseBean>(this@LoginActivity) {
                            lateinit var toast: Toast
                            override fun success(data: LoginResponseBean?) {
                                toast = Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT)
                                    toast .setGravity(Gravity.CENTER, 0, 0)
                                    toast.show()
                            }

                            override fun failure(errMessage: String?) {
                                toast = Toast.makeText(this@LoginActivity, "登录失败~ 呜呜呜 $errMessage", Toast.LENGTH_SHORT)
                                    toast.setGravity(Gravity.CENTER, 0, 0)
                                    toast.show()
                            }

                        })
            }
        }
    }
}