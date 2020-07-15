package com.zx.app.study_notes.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author Afton
 * date 2020/6/28
 */
public class MyLinearLayoutManager extends LinearLayoutManager {
    private RecyclerView recyclerView;
    private boolean mCanAutoScroll=true;//默认不可以自动滑动，可通过setCanAutoScroll(boolean)来设置

    public MyLinearLayoutManager(Context context,RecyclerView recyclerView) {
        super(context);
        this.recyclerView=recyclerView;
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        // 先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
        // 实际就是把View放到了Recycler中的一个集合中。
        if(mCanAutoScroll){
            detachAndScrapAttachedViews(recycler);
            calculateChildrenSite(recycler);
           recyclerView.post(new Runnable() {
               @Override
               public void run() {
                   scrollToPositionWithOffset(9,0);
               }
           });

        }

    }

    int totalHeight = 0;

    private void calculateChildrenSite(RecyclerView.Recycler recycler) {
        totalHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            // 遍历Recycler中保存的View取出来
            View view = recycler.getViewForPosition(i);
            addView(view); // 因为刚刚进行了detach操作，所以现在可以重新添加
            measureChildWithMargins(view, 0, 0); // 通知测量view的margin值
            int width = getDecoratedMeasuredWidth(view); // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
            int height = getDecoratedMeasuredHeight(view);

            Rect mTmpRect = new Rect();
            //调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
            calculateItemDecorationsForChild(view, mTmpRect);

            // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
            //包括ItemDecorator设置的距离。
            layoutDecorated(view, 0, totalHeight, width, totalHeight + height);
            totalHeight += height;
        }

    }

    public int getTotalHeight(){
        return totalHeight;
    }

    public void setCanAutoScroll(boolean canAutoScroll){
        mCanAutoScroll = canAutoScroll;
    }
    @Override
    public int scrollVerticallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int consumedY = 0;
        // Do not let auto scroll
        if (mCanAutoScroll||recyclerView == null||recyclerView.getScrollState()!= RecyclerView.SCROLL_STATE_SETTLING){
            consumedY = super.scrollVerticallyBy(dx, recycler, state);
        }
        return consumedY;
    }
}
