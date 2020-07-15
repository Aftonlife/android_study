package com.zx.app.study_notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

/**
 * author Afton
 * date 2020/6/17
 */
public class CommonUtil {
    public static void flicker(Context context, ImageView imageView, TextView textView, int status) {
        /*
    闪烁动画
    开始闪烁
    setDuration 设置闪烁一次的时间
    setRepeatCount 设置闪烁次数 可以是具体数值，也可以是Animation.INFINITE（重复多次）
    setRepeatMode 动画结束后从头开始或从末尾开始
        Animation.REVERSE（从末尾开始） Animation.RESTART（从头开始）
    setAnimation将设置的动画添加到view上
 */
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setRepeatCount(3);
        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                textView.setTextColor(ContextCompat.getColor(context, status == 0 ? R.color.color_green : R.color.color_red));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setTextColor(ContextCompat.getColor(context, R.color.color_white));
                imageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textView.startAnimation(alphaAnimation);

    }


    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    public static int dp2px(Context context, int dp) {
        return (int)((float)dp * context.getResources().getDisplayMetrics().density);
    }
}
