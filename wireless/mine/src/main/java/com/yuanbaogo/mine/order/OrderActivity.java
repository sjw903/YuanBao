package com.yuanbaogo.mine.order;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.callcenter.CallCenter;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.adapter.FragmentAdapter;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;
import com.yuanbaogo.router.YBRouter;

@Route(path = YBRouter.ORDER_ACTIVITY)
public class OrderActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> implements View.OnClickListener, TabLayout.OnTabSelectedListener {

    private static final String TAG = "OrderActivity";
    public static final int ALL_TYPE = 0;
    public static final int PAY_TYPE = 1;
    public static final int RECEIPT_TYPE = 2;
    public static final int FINISH_TYPE = 3;
    public static final int CANCEL_TYPE = 4;
    @Autowired(name = YBRouter.OrderActivityParams.orderType)
    public static int orderType;
    private LinearLayout mOrderLlTitleBar;
    private TabLayoutMediator mMediator;
    private ImageButton mOrderIbBack;
    public LinearLayout mOrderLlSearch;
    private ImageButton mOrderIbMessage;
    private static TabLayout mOrderTabLayout;
    private ViewPager2 mOrderViewPager2;

    /**
     * 定义一个Handler用于接收黄色碎片给Activity发出来的指令
     */
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg!=null){
                switch (msg.what) {
                    case 100:
                        /**
                         * 接收到黄色碎片发来的指令,Activity执行替换操作
                         */
                        mOrderTabLayout.selectTab(mOrderTabLayout.getTabAt(orderType), true);
                        setSearchVisibility();
                        break;

                    default:
                        break;
                }
            }
        }

    };

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_order;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mOrderLlTitleBar = findViewById(R.id.order_ll_title_bar);
        mOrderIbBack = findViewById(R.id.order_ib_back);
        mOrderLlSearch = findViewById(R.id.order_ll_search);
        mOrderIbMessage = findViewById(R.id.order_ib_message);
        mOrderTabLayout = findViewById(R.id.order_tab_layout);
        mOrderViewPager2 = findViewById(R.id.order_view_pager2);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mOrderViewPager2.setUserInputEnabled(false); //true:滑动，false：禁止滑动
    }

    @Override
    public void onResume() {
        super.onResume();
        // 设置默认进来的tab
        mOrderTabLayout.selectTab(mOrderTabLayout.getTabAt(orderType), true);
        setSearchVisibility();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        mOrderLlTitleBar.setPadding(0, statusBarHeight, 0, 0);
        mOrderIbBack.setOnClickListener(this);
        mOrderIbMessage.setOnClickListener(this);
        mOrderLlSearch.setOnClickListener(this);
        mOrderViewPager2.setAdapter(new FragmentAdapter(this));
        int[] tabTexts = new int[]{R.string.order_tab_tv_all, R.string.order_tab_tv_pay,
                R.string.order_tab_tv_receipt, R.string.order_tab_tv_finish, R.string.order_tab_tv_cancel};
        mMediator = new TabLayoutMediator(mOrderTabLayout, mOrderViewPager2,
                (tab, position) -> {
                    tab.setText(tabTexts[position]);
                });
        mMediator.attach();
        mOrderTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.order_ib_back) {
            finish();
        } else if (id == R.id.order_ib_message) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            CallCenter.toService(null, null, null, null, null);
        } else if (id == R.id.order_ll_search) {
            if (mOrderLlSearch.getVisibility() == View.VISIBLE) {
                RouterHelper.toOrderSearch(orderType);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediator.detach();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        orderType = tab.getPosition();
        mOrderViewPager2.setCurrentItem(orderType, false);
        setSearchVisibility();
    }

    private void setSearchVisibility() {
        if (orderType == ALL_TYPE) {
            mOrderLlSearch.setVisibility(View.INVISIBLE);
        } else {
            mOrderLlSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}