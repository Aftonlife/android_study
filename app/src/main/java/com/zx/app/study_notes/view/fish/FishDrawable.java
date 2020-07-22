package com.zx.app.study_notes.view.fish;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author Afton
 * date 2020/7/18
 */
public class FishDrawable extends Drawable {

    // 透明度
    private static final int OTHER_ALPHA = 110;
    // 鱼身的透明度
    private static final int BODY_ALPHA = 160;

    /*** 鱼的各部位长度 */
    // 鱼头半径
    private static final float HEAD_RADIUS = 100;
    // 身体的长度
    private static final float BODY_LENGHT = HEAD_RADIUS * 3.2f;
    // ----------鱼鳍----------------
    // 寻找鱼鳍开始点的线长
    private static final float FIND_FINS_LENGTH = HEAD_RADIUS * 0.9f;
    // 鱼鳍的长度
    private final float FINS_LENGTH = HEAD_RADIUS * 1.3f;
    // -------------鱼尾---------------
    // 尾部大圆的半径(圆心就是身体底部的中点)
    private final float BIG_CIRCLE_RADIUS = HEAD_RADIUS * 0.7f;
    // --寻找尾部中圆圆心的线长
    private final float FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS * (0.6f + 1);
    // 尾部中圆的半径
    private final float MIDDLE_CIRCLE_RADIUS = BIG_CIRCLE_RADIUS * 0.6f;
    // --寻找尾部小圆圆心的线长
    private final float FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    // 尾部小圆的半径
    private final float SMALL_CIRCLE_RADIUS = MIDDLE_CIRCLE_RADIUS * 0.4f;
    // --寻找大三角形底边中心点的线长
    private final float FIND_TRIANGLE_LENGTH = FIND_SMALL_CIRCLE_LENGTH;

    // 鱼的初始角度,0表示朝x轴正方向，90表示朝y轴负方向
    private float startAngle = 0;

    private Paint mPaint;//画笔
    private Path mPath;//路径
    private PointF middlePoint;//中心
    private PointF headPoint;//鱼头圆心

    //------动画属性------
    //变化值
    private final float CHANGE_VALUE = 360F;
    //动画持续时间
    private final int ANIMATOR_DURATION = 1 * 1000;

    private float currentValue = 0;

    public FishDrawable() {
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //画笔类型填充
        mPaint.setStyle(Paint.Style.FILL);
        //防抖
        mPaint.setDither(true);
        //设置颜色
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71);
        // 重心位于整个控件的中心，保证鱼旋转的空间
        middlePoint = new PointF(4.18f * HEAD_RADIUS, 4.18f * HEAD_RADIUS);

        //摆动动画
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, CHANGE_VALUE);
        valueAnimator.setDuration(ANIMATOR_DURATION);
        valueAnimator.setRepeatCount(valueAnimator.INFINITE);//无限循环
        valueAnimator.setInterpolator(new LinearInterpolator());//插值器
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) animation.getAnimatedValue();
                //重绘
                invalidateSelf();
            }
        });
        valueAnimator.start();

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        makeFish(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    //设置起始角度
    public void setStartAngle(float angle) {
        this.startAngle = angle;
        init();
    }

    //画鱼
    private void makeFish(Canvas canvas) {
        //鱼的摆动是周期性的，刚好用sin cos处理
        float fishAngle = (float) (startAngle + Math.sin(Math.toRadians(currentValue)) * 10);
        //鱼头
        headPoint = calculatePoint(middlePoint, 2.2f * HEAD_RADIUS, fishAngle);
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint);
        //右鱼鳍
        PointF rightFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 100);
        makeFins(canvas, rightFinsPoint, FINS_LENGTH, fishAngle, true);
        //左鱼鳍
        PointF leftFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 100);
        makeFins(canvas, leftFinsPoint, FINS_LENGTH, fishAngle, false);

        //肢节1
        //身体底部中心
        PointF bodyBottomCenterPoint = calculatePoint(headPoint, BODY_LENGHT, fishAngle - 180);
        //尾部大圆
        canvas.drawCircle(bodyBottomCenterPoint.x, bodyBottomCenterPoint.y, BIG_CIRCLE_RADIUS, mPaint);
        //尾部中间圆
        PointF middleCirclePoint = calculatePoint(bodyBottomCenterPoint, FIND_MIDDLE_CIRCLE_LENGTH, fishAngle - 180);
        canvas.drawCircle(middleCirclePoint.x, middleCirclePoint.y, MIDDLE_CIRCLE_RADIUS, mPaint);
        //大梯形
        makeTrapezoid(canvas, bodyBottomCenterPoint, FIND_MIDDLE_CIRCLE_LENGTH, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS, fishAngle, true);
        //小梯形
        PointF smallCirclePoint = calculatePoint(middleCirclePoint, FIND_SMALL_CIRCLE_LENGTH, fishAngle - 180);
        canvas.drawCircle(smallCirclePoint.x, smallCirclePoint.y, SMALL_CIRCLE_RADIUS, mPaint);
        makeTrapezoid(canvas, middleCirclePoint, FIND_SMALL_CIRCLE_LENGTH, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS, fishAngle, false);
        //尾部大三角
        makeTriangle(canvas, middleCirclePoint, FIND_TRIANGLE_LENGTH * 0.8f, fishAngle);
        //尾部小三角
        makeTriangle(canvas, middleCirclePoint, FIND_TRIANGLE_LENGTH * 0.6f, fishAngle);
        //身体
        makeBody(canvas, headPoint, bodyBottomCenterPoint, HEAD_RADIUS, BIG_CIRCLE_RADIUS, fishAngle);
    }


    /**
     * 根据起始点计算点
     *
     * @param startPoint
     * @param length
     * @param angle
     * @return
     */
    private PointF calculatePoint(PointF startPoint, float length, float angle) {
        //Math.toRadians(angle) 角度转弧度  sin cos 是用的弧度值
        float deltaX = (float) Math.cos(Math.toRadians(angle)) * length;
        //Android坐标系 y朝下要取反
        float deltaY = (float) Math.sin(Math.toRadians(angle - 180)) * length;

        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }

    /**
     * 画鱼鳍 二阶贝塞尔曲线
     *
     * @param startPoint 起始点
     */
    private void makeFins(Canvas canvas, PointF startPoint, float finsLength, float fishAngle, boolean isRight) {

        //鱼鳍三角控制角度
        float controlAngle = 115;
        // 鱼鳍二阶贝塞尔曲线的控制点
        PointF controlPoint = calculatePoint(startPoint, finsLength * 1.8f, isRight ? fishAngle - controlAngle : fishAngle + controlAngle);
        PointF endPoint = calculatePoint(startPoint, finsLength, fishAngle - 180);
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        //二阶贝塞尔曲线
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);

    }

    /**
     * 画梯形
     *
     * @param topPoint     长边中点
     * @param length       长度
     * @param topRadius    长边半径
     * @param bottomRadius 短边半径
     * @param fishAngle    鱼头角度
     */
    private void makeTrapezoid(Canvas canvas, PointF topPoint, float length, float topRadius, float bottomRadius, float fishAngle, boolean isBig) {
        float changeAngle = fishAngle + 15;
        if (isBig) {
            changeAngle += Math.cos(Math.toRadians(currentValue * 2) * 20);
        } else {
            changeAngle += Math.sin(Math.toRadians(currentValue * 2) * 30);
        }
        PointF bottomPoint = calculatePoint(topPoint, topRadius + bottomRadius, changeAngle - 180);
        PointF topRightPoint = calculatePoint(topPoint, topRadius, changeAngle - 90);
        PointF topLeftPoint = calculatePoint(topPoint, topRadius, changeAngle + 90);
        PointF bottomRightPoint = calculatePoint(bottomPoint, bottomRadius, changeAngle - 90);
        PointF bottomLeftPoint = calculatePoint(bottomPoint, bottomRadius, changeAngle + 90);

        mPath.reset();
        mPath.moveTo(topRightPoint.x, topRightPoint.y);
        mPath.lineTo(topLeftPoint.x, topLeftPoint.y);
        mPath.lineTo(bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 画尾部三角
     *
     * @param startPoint 顶角
     * @param length     长度
     * @param fishAngle  鱼头角度
     */
    private void makeTriangle(Canvas canvas, PointF startPoint, float length, float fishAngle) {
        PointF centerPoint = calculatePoint(startPoint, length, fishAngle - 180);
        PointF rightPoint = calculatePoint(centerPoint, length / 2, fishAngle - 90);
        PointF leftPoint = calculatePoint(centerPoint, length / 2, fishAngle + 90);
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.lineTo(leftPoint.x, leftPoint.y);
        mPath.lineTo(rightPoint.x, rightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 二阶贝塞尔曲线画身体
     *
     * @param topPoint    顶部中点
     * @param bottomPoint 底部中点
     * @param fishAngle   鱼头角度
     */
    private void makeBody(Canvas canvas, PointF topPoint, PointF bottomPoint, float topRadius, float bottomRadius, float fishAngle) {
        float controlAngle = 130;
        float controlLength = BODY_LENGHT * 0.56f;
        PointF topRightPoint = calculatePoint(topPoint, topRadius, fishAngle - 90);
        PointF topLeftPoint = calculatePoint(topPoint, topRadius, fishAngle + 90);
        PointF bottomRightPoint = calculatePoint(bottomPoint, bottomRadius, fishAngle - 90);
        PointF bottomLeftPoint = calculatePoint(bottomPoint, bottomRadius, fishAngle + 90);
        PointF controlRightPoint = calculatePoint(topPoint, controlLength, fishAngle - controlAngle);
        PointF controlLeftPoint = calculatePoint(topPoint, controlLength, fishAngle + controlAngle);
        mPath.reset();
        mPath.moveTo(topRightPoint.x, topRightPoint.y);
        mPath.quadTo(controlRightPoint.x, controlRightPoint.y, bottomRightPoint.x, bottomRightPoint.y);
        mPath.lineTo(bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.quadTo(controlLeftPoint.x, controlLeftPoint.y, topLeftPoint.x, topLeftPoint.y);
        mPaint.setAlpha(BODY_ALPHA);
        canvas.drawPath(mPath, mPaint);
    }
}
