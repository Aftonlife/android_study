package com.zx.app.study_notes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.zx.app.study_notes.adapter.RoundAdapter;
import com.zx.app.study_notes.adapter.TestAdapter;
import com.zx.app.study_notes.bean.TestBean;
import com.zx.app.study_notes.bean.TestRoundBean;
import com.zx.app.study_notes.view.MaxHeightView;
import com.zx.app.study_notes.view.MyLinearLayoutManager;
import com.zx.app.study_notes.view.tablayout.SimpleTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonkeyActivity extends AppCompatActivity {


    public static final String[] tabs = {"全局", "第一局", "第二局", "第三局"};

    private Toolbar mToolbar;
    private RecyclerView mRecycler;
    private SimpleTabLayout mTabLayout;
    private RecyclerView mBottomRecycler;
    private MaxHeightView maxHeightView;
    private ImageView mLogo1;
    private ImageView mLogo2;
    private TextView mBottom1;
    private TextView mBottom2;
    private TextView mBottom3;
    RoundAdapter roundAdapter1 = new RoundAdapter();
    TestAdapter testAdapter;
    private int count = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*去掉标题栏*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_monkey);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(v -> {
//            finish();
            count++;
            List<TestRoundBean> list = new ArrayList<>();
            TestRoundBean bean = new TestRoundBean();
            List<TestBean> items = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                TestBean testBean = new TestBean("test" + i);
                items.add(testBean);
            }
            bean.setItems(items);
            list.add(bean);
            roundAdapter1.setNewData(list);
            heightChange();

        });

        initStatusBar();

        mRecycler = findViewById(R.id.recycler);
        mTabLayout = findViewById(R.id.tabLayout);

        mBottomRecycler = findViewById(R.id.rv_bottom);
        maxHeightView = findViewById(R.id.fl_bottom);
        mLogo1 = findViewById(R.id.iv_logo1);
        mLogo2 = findViewById(R.id.iv_logo2);
        mBottom1 = findViewById(R.id.tv_bottom1);
        mBottom2 = findViewById(R.id.tv_bottom2);
        mBottom3 = findViewById(R.id.tv_bottom3);

        initRoundAdapter();

        mLogo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottom1.getVisibility() == View.VISIBLE) {
                    mBottom1.setVisibility(View.GONE);
                    mBottom3.setVisibility(View.GONE);

                } else {
                    mBottom1.setVisibility(View.VISIBLE);
                    mBottom3.setVisibility(View.VISIBLE);
                }
                myLinearLayoutManager.setCanAutoScroll(true);
                heightChange();
            }
        });
        mLogo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottom2.getVisibility() == View.VISIBLE) {
                    mBottom2.setVisibility(View.GONE);
                } else {
                    mBottom2.setVisibility(View.VISIBLE);
                }
                myLinearLayoutManager.setCanAutoScroll(true);
                heightChange();
            }
        });
    }

    private void heightChange() {
        mBottomRecycler.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = mBottomRecycler.getLayoutParams();
                int height = maxHeightView.getHeightSize()
                        - (mBottom1.getVisibility() == View.VISIBLE ? mBottom1.getHeight() : 0)
                        - (mBottom2.getVisibility() == View.VISIBLE ? mBottom2.getHeight() : 0)
                        - (mBottom3.getVisibility() == View.VISIBLE ? mBottom3.getHeight() : 0);
               params.height=Math.min(myLinearLayoutManager.getTotalHeight(),height);

//                if (mBottom1.getVisibility() == View.VISIBLE || mBottom2.getVisibility() == View.VISIBLE) {
//                    params.height = height;
//                } else {
//                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                }
                mBottomRecycler.requestLayout();
            }
        });
    }

    Boolean isRecyclerScroll = true;
    Boolean canScroll = false;
    int lastPos = 0;
    int scrollToPosition = 0;
    LinearLayoutManager linearManager;
    MyLinearLayoutManager myLinearLayoutManager;

    private void initRoundAdapter() {
        RoundAdapter roundAdapter = new RoundAdapter();
        linearManager = new LinearLayoutManager(MonkeyActivity.this);
        mRecycler.setLayoutManager(linearManager);
        mRecycler.setAdapter(roundAdapter);
        myLinearLayoutManager = new MyLinearLayoutManager(MonkeyActivity.this,mBottomRecycler);
        mBottomRecycler.setLayoutManager(myLinearLayoutManager);
        testAdapter=new TestAdapter();
        mBottomRecycler.setAdapter(testAdapter);

        testAdapter.setNewData(getList(1).get(0).getItems());
        testAdapter.setOnKeyboardChangeListener(new TestAdapter.OnKeyboardChangeListener() {
            @Override
            public void keyboardChange() {
                myLinearLayoutManager.setCanAutoScroll(false);
            }
        });

        initListener();
    }

    private List<TestRoundBean> getList(int count) {
        List<TestRoundBean> roundBeans = new ArrayList<>();

        if (tabs.length > 5) {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        for (int i = 0; i < count; i++) {
            TestRoundBean bean = new TestRoundBean();
            bean.setName(tabs[i]);
//            mTabLayout.addTab(mTabLayout.newTab().setText(tabs[i]));
            List<TestBean> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                TestBean testBean = new TestBean(bean.getName() + ":" + j);
                list.add(testBean);
            }
            bean.setItems(list);
            roundBeans.add(bean);
        }
        mTabLayout.setTitle(Arrays.asList(tabs));
        return roundBeans;
    }

    private void initListener() {
        mRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //滑动由RecyclerView 触发 isRecyclerScroll为true;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isRecyclerScroll = true;
                }
                return false;
            }
        });

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isRecyclerScroll) {
                    //第一个完全可见的view的位置，即tablayou需定位的位置
                    int position = linearManager.findFirstCompletelyVisibleItemPosition();
                    int firstItem = linearManager.findFirstVisibleItemPosition();
                    if (position < 0) {
                        position = firstItem;
                    }
                    if (position != lastPos) {
                        mTabLayout.setScrollPosition(position, 0, true);
                    }
                    lastPos = position;
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (canScroll) {
                    canScroll = false;
                    moveToPosition(linearManager, recyclerView, scrollToPosition);
                }
            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                isRecyclerScroll = false;
                moveToPosition(linearManager, mRecycler, pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void moveToPosition(LinearLayoutManager manager, RecyclerView recyclerView, int position) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            //跳转位置在第一个可见之前（不在当前屏幕），直接跳转
            recyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            //跳转位置在当前屏幕显示，滚动一定的距离
            int top = recyclerView.getChildAt(position - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            // 如果要跳转的位置在lastItem 之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用当前moveToPosition方法，执行上一个判断中的方法
            recyclerView.smoothScrollToPosition(position);
            scrollToPosition = position;
            canScroll = true;

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //ToolBar的navigation
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //设置透明状态栏
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (mToolbar != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
            layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin + getStatusBarHeight(this),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
        }
    }

    /**
     * 设置沉浸式
     */
    public void setTranslucentNavigationBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


    }


    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
