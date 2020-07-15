package com.zx.app.study_notes.view.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.zx.app.study_notes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author Afton
 * date 2020/7/3
 * 自定义TabLayout
 */
public class SimpleTabLayout extends TabLayout {
    private OnTabSelectedListener mListener;
    private List<String> mTitles;

    private int mIndicatorId;
    private float mTextSize;
    private boolean isBold;

    public SimpleTabLayout(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SimpleTabLayout);
        mIndicatorId = ta.getResourceId(R.styleable.SimpleTabLayout_stl_indicator, R.drawable.tablayout_item_indicator);
        isBold=ta.getBoolean(R.styleable.SimpleTabLayout_stl_selected_text_bold,false);
        mTextSize=ta.getDimension(R.styleable.SimpleTabLayout_stl_text_size,20);
        initListener();
    }

    private void initListener() {
        mTitles = new ArrayList<>();
        this.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(Tab tab) {
                /**
                 * 设置当前选中的Tab为特殊高亮样式。
                 */
                if (tab != null && tab.getCustomView() != null) {
                    TextView tab_text = tab.getCustomView().findViewById(R.id.tv_tab_title);
                    if(isBold){
                        TextPaint paint = tab_text.getPaint();
                        paint.setFakeBoldText(true);
                    }
                    tab_text.setTextColor(getTabTextColors());

                    ImageView tab_layout_indicator = tab.getCustomView().findViewById(R.id.iv_tab_indicator);
                    tab_layout_indicator.setBackgroundResource(mIndicatorId);
                    if(null!=mListener){
                        mListener.onTabSelected(tab);
                    }
                }
            }

            @Override
            public void onTabUnselected(Tab tab) {
                /**
                 * 重置所有未选中的Tab颜色、字体、背景恢复常态(未选中状态)。
                 */
                if (tab != null && tab.getCustomView() != null) {
                    TextView tab_text = tab.getCustomView().findViewById(R.id.tv_tab_title);

                    tab_text.setTextColor(getTabTextColors());
                    if(isBold){
                        TextPaint paint = tab_text.getPaint();
                        paint.setFakeBoldText(false);
                    }

                    ImageView tab_indicator = tab.getCustomView().findViewById(R.id.iv_tab_indicator);
                    tab_indicator.setBackgroundResource(0);
                    if(null!=mListener){
                        mListener.onTabUnselected(tab);
                    }
                }
            }

            @Override
            public void onTabReselected(Tab tab) {
                if(null!=mListener){
                    mListener.onTabReselected(tab);
                }
            }
        });
    }

    public void setTitle(List<String> titles) {
        this.mTitles = titles;

        /**
         * 开始添加切换的Tab。
         */
        for (String title : this.mTitles) {
            Tab tab = newTab();
            tab.setCustomView(R.layout.tablayout_item);

            if (tab.getCustomView() != null) {
                TextView tab_text = tab.getCustomView().findViewById(R.id.tv_tab_title);
                tab_text.setText(title);
                tab_text.setTextSize(mTextSize);
            }

            this.addTab(tab);
        }
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.mListener = listener;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(Tab tab);

        void onTabUnselected(Tab tab);

        void onTabReselected(Tab tab);
    }
}
