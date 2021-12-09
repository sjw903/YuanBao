package com.yuanbaogo.homevideo.bringgoods.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.bringgoods.adapter.AnchorGoodsListAdapter;
import com.yuanbaogo.homevideo.live.push.model.CartGoodsListBean;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.zui.dialog.CommonRemindPop;
import com.yuanbaogo.zui.dialog.PopupWindowWithMask;
import com.yuanbaogo.zui.swipe.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;


//主播购物车
public class AnchorGoodsListPop extends PopupWindowWithMask implements PopupWindow.OnDismissListener, View.OnClickListener {

    private View mContentView;

    Activity mActivity;

    private SwipeRecyclerView mRvList;
    private boolean isSort = false;

    AnchorGoodsListAdapter mAdapter;
    List<CartGoodsListBean> mList;
    TextView tv_goods_title_num;
    ImageView iv_anchor_back;

    @Override
    public void onDismiss() {
    }

    public AnchorGoodsListPop(Activity activity) {
        super(activity, true);
        this.mActivity = activity;
        initView();
    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.pop_anchor_goods_list, null, false);
        return mContentView;
    }

    @Override
    protected int initHeight() {
        int screenHeight = ScreenUtils.getScreenHeight(context);
        return (int) (screenHeight * 0.6);
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    private void initView() {
        mAdapter = new AnchorGoodsListAdapter(mActivity);
        mRvList = mContentView.findViewById(R.id.recyclerView);
        iv_anchor_back = mContentView.findViewById(R.id.iv_anchor_back);
        tv_goods_title_num = mContentView.findViewById(R.id.tv_goods_title_num);
        mRvList.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new AnchorGoodsListAdapter.OnClickListener() {
            @Override
            public void onExplainClick(int pos, String liveGoodsId) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onExplainClick(pos, liveGoodsId);
            }
            @Override
            public void onSort(int pos, String type) {
                isSort = true;
            }

            @Override
            public void onItemClick(CartGoodsListBean bean) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onGoodsClick(bean);
            }
        });
        iv_anchor_back.setOnClickListener(this);

    }

    CommonRemindPop pop;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_anchor_back) {
            startRemindPop();
        }
    }

    private OnClickListener mOnItemClickListener;

    public interface OnClickListener {
        void onExplainClick(int pos, String liveGoodsId);
        void onGoodsClick(CartGoodsListBean bean);
        void onSubmitShoppingCartGoods(List<String> list);
    }

    public void setOnClickListener(OnClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public boolean OnKeyListener() {
        startRemindPop();
        return false;
    }

    public void setExplainStaus(int pos) {
        mAdapter.setExplainStaus(pos);
    }

    public void setData(List<CartGoodsListBean> list){
        this.mList = list;
        mAdapter.setData(mList);
        isSort = false;
        tv_goods_title_num.setText("全部商品("+list.size()+")");
    }

    public void startRemindPop() {
        if(isSort) {
            pop = new CommonRemindPop(mActivity, "是否按照当前商品排序进行展示", "取消", "确定",
                    new CommonRemindPop.OnClickListener() {
                        @Override
                        public void onLeftClick() {
                            pop.dismiss();
                            dismiss();
                        }

                        @Override
                        public void onRightClick() {
                            pop.dismiss();
                            dismiss();
                            if (mOnItemClickListener != null){
                                List<String> list = new ArrayList<>();
                                for (CartGoodsListBean bean : mList){
                                    if(!bean.isExplainStatus()){
                                        list.add(bean.getShoppingId());
                                    }
                                }
                                mOnItemClickListener.onSubmitShoppingCartGoods(list);
                            }

                        }
                    });
            pop.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        }else{
            dismiss();
        }

    }


}

