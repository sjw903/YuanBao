package com.yuanbaogo.mine.order.finish.refund;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.adapter.AfterOrderGoodsAdapter;
import com.yuanbaogo.mine.order.finish.refund.dialog.RefundReasonBottomDialog;
import com.yuanbaogo.mine.order.finish.refund.dialog.SalesStatusBottomDialog;
import com.yuanbaogo.mine.order.finish.refund.model.UploadListBean;
import com.yuanbaogo.mine.order.model.GoodsDetail;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.picture.SelectPictureAdapter;
import com.yuanbaogo.zui.picture.SelectPictureInternalAdapter;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

@Route(path = YBRouter.SALES_RETURN_REFUND_ACTIVITY)
public class SalesReturnRefundActivity extends MvpBaseActivityImpl<SalesReturnRefundContract.View, SalesReturnRefundPresenter> implements
        SalesReturnRefundContract.View, View.OnClickListener, RefundReasonBottomDialog.OnReasonSelect, SalesStatusBottomDialog.OnStatusSelect, SelectPictureInternalAdapter.OnUploadImageListener, SelectPictureAdapter.OnDeleteItemListener {

    private static final String TAG = "SalesReturnRefundActivity";
    public static final int REFUND_TYPE = 1;
    public static final int SALES_RETURN_REFUND_TYPE = 2;

    private TitleBar mSalesTitleBar;
    private RecyclerView mOrderRvGoodsList;
    private LinearLayout mSalesLlStatus;
    private TextView mSalesTvStatus;
    private LinearLayout mSalesLlReason;
    private TextView mSalesTvReason;
    private TextView mSalesTvMoney;
    private EditText mSalesEtDesc;
    private RecyclerView mSalesRvImg;
    private TextView mSalesTvSubmit;
    private RefundReasonBottomDialog mRefundReasonBottomDialog;
    private SalesStatusBottomDialog mSalesStatusBottomDialog;
    private List<LocalMedia> mLocalMediaList = new ArrayList<>();
    private List<String> imgUrlList = new ArrayList<>();
    private SelectPictureInternalAdapter mAdapter;
    private GoodsDetail mGoodsDetail;
    private int mReasonPosition = 1;

    @Autowired(name = YBRouter.OrderDetailsActivityParams.totalOrderId)
    String totalOrderId = "";
    @Autowired(name = YBRouter.SalesReturnRefundActivityParams.pageType)
    int pageType = 0;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_sales_return_refund;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mSalesTitleBar = findViewById(R.id.sales_title_bar);
        mSalesLlStatus = findViewById(R.id.sales_ll_status);
        mSalesTvStatus = findViewById(R.id.sales_tv_status);
        mSalesLlReason = findViewById(R.id.sales_ll_reason);
        mSalesTvReason = findViewById(R.id.sales_tv_reason);
        mSalesTvMoney = findViewById(R.id.sales_tv_money);
        mSalesEtDesc = findViewById(R.id.sales_et_desc);
        mSalesRvImg = findViewById(R.id.sales_rv_img);
        mSalesTvSubmit = findViewById(R.id.sales_tv_submit);
        mOrderRvGoodsList = findViewById(R.id.order_rv_goods_list);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mSalesLlStatus.setOnClickListener(this);
        mSalesLlReason.setOnClickListener(this);
        mSalesTvSubmit.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPresenter.getOrderDetail(totalOrderId);
        if (pageType == REFUND_TYPE) {
            // 只退款不退货
            mSalesTitleBar.setTitle(R.string.sales_title_refund);
            mSalesLlStatus.setVisibility(View.VISIBLE);
        } else {
            // 退款退货
            mSalesTitleBar.setTitle(R.string.sales_title_refund_good);
            mSalesLlStatus.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAdapter = new SelectPictureInternalAdapter(this, mLocalMediaList);
        mAdapter.setOnUploadImageListener(this);
        mAdapter.setOnDeleteItemListener(this);
        mSalesRvImg.setLayoutManager(layoutManager);
        mSalesRvImg.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sales_ll_status) {
            // 货物状态
            mSalesStatusBottomDialog = new SalesStatusBottomDialog(this);
            mSalesStatusBottomDialog.setOnStatusSelect(this);
            mSalesStatusBottomDialog.show();
        } else if (id == R.id.sales_ll_reason) {
            // 退款原因
            mRefundReasonBottomDialog = new RefundReasonBottomDialog(this);
            mRefundReasonBottomDialog.setOnReasonSelect(this);
            mRefundReasonBottomDialog.show();
        } else if (id == R.id.sales_tv_submit) {
            // 提交退款信息
            String des = mSalesEtDesc.getText().toString().trim();
            if (TextUtils.isEmpty(des)) {
                ToastView.showToast("请填写补充描述");
                return;
            }
            if (mLocalMediaList.size() != 0) {
                mPresenter.uploadImageList(mLocalMediaList);
            } else {
                mPresenter.returnRefund(mGoodsDetail.getRealPayedPrice(), des,
                        pageType, totalOrderId, mReasonPosition, pageType == REFUND_TYPE ? 2 : 1, imgUrlList);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mSalesStatusBottomDialog != null) {
            mSalesStatusBottomDialog.cancel();
        }
        if (mRefundReasonBottomDialog != null) {
            mRefundReasonBottomDialog.cancel();
        }
    }

    @Override
    public void onReasonSelectListener(String reason, int position) {
        mSalesTvReason.setText(reason);
        mReasonPosition = position + 1;
    }

    @Override
    public void onStatusSelectListener(String status) {
        mSalesTvStatus.setText(status);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showOrderDetail(GoodsDetail goodsDetail) {
        mGoodsDetail = goodsDetail;
        AfterOrderGoodsAdapter adapter = new AfterOrderGoodsAdapter(this, goodsDetail.getGoodsVOList());
        mOrderRvGoodsList.setAdapter(adapter);
        mSalesTvMoney.setText("¥" + Integer.parseInt(goodsDetail.getRealPayedPrice()) / 100);
    }

    @Override
    public void returnRefundResult(boolean result) {
        if (result) {
            ToastUtil.showToast(R.string.apply_submit_success);
            RouterHelper.toSalesReturnRefundResult(this, 401);
            finish();
        } else {
            ToastUtil.showToast(R.string.apply_submit_fail);
        }
    }

    @Override
    public void showImageList(List<UploadListBean> bean) {
        if (imgUrlList.size() > 0) {
            imgUrlList.clear();
        }
        for (int i = 0; i < bean.size(); i++) {
            imgUrlList.add(bean.get(i).getFileUrl());
        }
        mPresenter.returnRefund(mGoodsDetail.getRealPayedPrice(), mSalesEtDesc.getText().toString().trim(),
                pageType, totalOrderId, mReasonPosition, pageType == REFUND_TYPE ? 2 : 1, imgUrlList);
    }

    @Override
    public void onUploadImage() {
//        mPresenter.uploadImageList(mLocalMediaList);
    }

    @Override
    public void OnDeleteItem(int position) {
        if (imgUrlList.size() >= position) {
            imgUrlList.remove(position);
        }
    }
}