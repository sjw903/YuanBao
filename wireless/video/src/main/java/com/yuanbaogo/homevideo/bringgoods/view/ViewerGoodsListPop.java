package com.yuanbaogo.homevideo.bringgoods.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.bringgoods.adapter.ViewerGoodsListAdapter;
import com.yuanbaogo.homevideo.live.push.model.CartGoodsListBean;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.zui.dialog.PopupWindowWithMask;

import java.util.List;

//观众购物车
public class ViewerGoodsListPop extends PopupWindowWithMask implements PopupWindow.OnDismissListener, View.OnClickListener {

    private View mContentView;

    Activity mActivity;

    private RecyclerView mRvList;
    private boolean isSort = false;

    ViewerGoodsListAdapter mAdapter;
    List<CartGoodsListBean> mList;

    ImageView iv_anchor_back;
    TextView tv_goods_title_num;

    private OnClickListener mOnItemClickListener;

    public interface OnClickListener {
        void onBuyClick(CartGoodsListBean bean);

        void onItemClick(CartGoodsListBean bean);
    }

    public void setOnClickListener(OnClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onDismiss() {
    }

    public ViewerGoodsListPop(Activity activity) {
        super(activity, true);
        this.mActivity = activity;
        initView();
    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.pop_viewer_goods_list, null, false);
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
        mAdapter = new ViewerGoodsListAdapter(mActivity);
        mRvList = mContentView.findViewById(R.id.recyclerView);
        iv_anchor_back = mContentView.findViewById(R.id.iv_anchor_back);
        tv_goods_title_num = mContentView.findViewById(R.id.tv_goods_title_num);
        mRvList.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new ViewerGoodsListAdapter.OnClickListener() {
            @Override
            public void onBuyClick(CartGoodsListBean bean) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onBuyClick(bean);
            }

            @Override
            public void onItemClick(CartGoodsListBean bean) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(bean);
            }
        });
        iv_anchor_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_anchor_back) {
            dismiss();
        }
    }

    public void setData(List<CartGoodsListBean> list) {
        this.mList = list;
        mAdapter.setData(mList);
        isSort = false;
        tv_goods_title_num.setText("全部商品("+list.size()+")");
    }


}

