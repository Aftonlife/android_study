package com.zx.app.study_notes.adapter;

import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zx.app.study_notes.R;
import com.zx.app.study_notes.bean.TestBean;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * author Afton
 * date 2020/6/15
 */
public class TestAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    private OnKeyboardChangeListener listener;
    public TestAdapter() {
        super(R.layout.item_test);
        addChildClickViewIds(R.id.tv_test);
    }

    @Override
    protected void convert(BaseViewHolder holder, TestBean testBean) {
        holder.setText(R.id.tv_test, testBean.getName());
        EditText textView = holder.getView(R.id.tv_test);
        textView.setInputType(InputType.TYPE_NULL);
        ImageView imageView=holder.getView(R.id.iv_logo);
        textView.setOnClickListener(v -> {
            holder.setGone(R.id.iv_logo, imageView.getVisibility()== View.VISIBLE);
            if(null!=listener){
                listener.keyboardChange();
            }
        });
//        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                holder.setGone(R.id.iv_logo, imageView.getVisibility()== View.VISIBLE);
//
//            }
//        });
    }

    public void setOnKeyboardChangeListener(OnKeyboardChangeListener listener){
        this.listener=listener;
    }
    public interface OnKeyboardChangeListener{
        void keyboardChange();
    }
}
