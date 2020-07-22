package com.zx.app.study_notes.frame.rxjava.custuomize

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.zx.app.study_notes.R
import com.zx.app.study_notes.view.fish.FishDrawable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_rxactivity.*
import java.util.concurrent.TimeUnit

/**
 * author Afton
 * date 2020/6/2
 */
class RxActivity : AppCompatActivity() {
    private var btn: Button? = null
    private var decline: ImageView? = null;
    private var rise: ImageView? = null;
    private var odds1: TextView? = null;
    private var odds2: TextView? = null;
    private val disposable: Disposable? = null
    private var isPause = false

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_rxactivity)
//        checkPermission()
        initLiveVideo()

        btn = findViewById(R.id.button)
        decline = findViewById(R.id.iv_decline)
        rise = findViewById(R.id.iv_rise)
        odds1 = findViewById(R.id.tv_odds_1)
        odds2 = findViewById(R.id.tv_odds_2)


        iv_fish.setImageDrawable(FishDrawable())

        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        RxView.click(btn)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe {
                    Observable.create<Any> { emitter -> emitter.onNext("RxView") }.subscribe { o ->
                        Log.e("RxActivity", "终点打印$o")
                        val intent = Intent("monkey.test")
                        intent.setPackage("com.zx.app.study_notes")
                        intent.addCategory("android.intent.category.MONKEY.TEST")
//                        startActivity(intent);
//                        MQTTService.startService(this@RxActivity)
//                        CommonUtil.flicker(this,decline, odds1, 0)
//                        CommonUtil.flicker(this,rise, odds2, 1)
                        //暂停
                        isPause = !isPause
                        if (isPause) {
                            player.pause()
                        } else {
                            player.start()
                        }
                    }
                }
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_PHONE_STATE), 0)
        }

    }

    fun initLiveVideo() {
        player.setVideoPath("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8")
        player.setOnPreparedListener { player.start() }
    }
}