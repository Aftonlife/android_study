package com.zx.app.study_notes.view.flowLayout

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

/**
 * author Afton
 * date 2020/7/4
 */
class FlowLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val mHorizontalSpacing = dp2px(16f) //item横向间距
    private val mVerticalSpacing = dp2px(8f) //item纵向间距

    var allLines = ArrayList<ArrayList<View>>() //每一行的子View
    var lineHeights = ArrayList<Int>() //每一行的行高

    private fun measureClear(){
        allLines.clear()
        lineHeights.clear()
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //防止内存抖动
        measureClear()

        //测量子View
        var selfWidth = MeasureSpec.getSize(widthMeasureSpec)//FlowLayout的父布局给的宽度
        var selfHeight = MeasureSpec.getSize(heightMeasureSpec)//FlowLayout的父布局给的高度

        var childPara: LayoutParams
        var lineWidthUsed = 0//记录一行使用了多宽
        var lineHeight = 0//行高

        var lineViews = ArrayList<View>()

        var parentNeedWidth = 0//FlowLayout要求父ViewGroup的宽
        var parentNeedHeight = 0//FlowLayout要求父ViewGroup的高

        for (index in 0 until childCount) {
            val childView = getChildAt(index)
            childPara = childView.layoutParams
            if (childView.visibility != View.GONE) {
                //layoutParams 转变为measureSpec 测量子View 宽高
                var childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                        childPara.width)
                var childHeightMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingTop + paddingBottom,
                        childPara.height)

                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec)

                //获取子View的宽高
                var childMeasureWidth = childView.measuredWidth
                var childMeasureHeight = childView.measuredHeight

                //如果需要换行（当前子View需要的宽度超过剩余宽度）
                if (childMeasureWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {
                    allLines.add(lineViews)
                    lineHeights.add(lineHeight)

                    parentNeedWidth = parentNeedWidth.coerceAtLeast(lineWidthUsed + mHorizontalSpacing)
                    parentNeedHeight += lineHeight + mVerticalSpacing

                    lineViews = ArrayList<View>()
                    lineWidthUsed = 0
                    lineHeight = 0
                }
                lineViews.add(childView)
                //每一行的宽和高
                lineWidthUsed += childMeasureWidth + mHorizontalSpacing
                lineHeight = lineHeight.coerceAtLeast(childMeasureHeight)

                //最后一行
                if (index == childCount - 1) {
                    allLines.add(lineViews)
                    lineHeights.add(lineHeight)

                    parentNeedWidth = parentNeedWidth.coerceAtLeast(lineWidthUsed + mHorizontalSpacing)
                    parentNeedHeight += lineHeight + mVerticalSpacing

                }
            }
        }

        //测量FlowLayout,保持
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var realWidth = if (widthMode == MeasureSpec.EXACTLY) selfWidth else parentNeedWidth
        var realHeight = if (heightMode == MeasureSpec.EXACTLY) selfHeight else parentNeedHeight

        setMeasuredDimension(realWidth, realHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var lineCount = allLines.size
        var curL = paddingLeft
        var curT = paddingTop
        for (i in 0 until lineCount) {
            var lineViews = allLines[i]
            for (view in lineViews) {
                var left = curL
                var top = curT
                //onLayout 中使用measureWidth measuredHeight
                var right = left + view.measuredWidth
                var bottom = top + view.measuredHeight

                view.layout(left, top, right, bottom)

                curL = right + mHorizontalSpacing
            }
            curT += lineHeights[i] + mVerticalSpacing
            curL = paddingLeft
        }
    }

    fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    }
}