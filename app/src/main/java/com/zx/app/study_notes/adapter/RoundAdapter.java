package com.zx.app.study_notes.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zx.app.study_notes.R;
import com.zx.app.study_notes.bean.TestRoundBean;

/**
 * author Afton
 * date 2020/6/15
 */
public class RoundAdapter extends BaseQuickAdapter<TestRoundBean, BaseViewHolder> {
    public RoundAdapter() {
        super(R.layout.item_round);
    }

    @Override
    protected void convert(BaseViewHolder holder, TestRoundBean item) {
        holder.setText(R.id.tv_name, item.getName());
        RecyclerView recyclerView = holder.getView(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        TestAdapter adapter = new TestAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setNewData(item.getItems());
    }
}
