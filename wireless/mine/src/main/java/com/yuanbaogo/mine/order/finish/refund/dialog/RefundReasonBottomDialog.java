package com.yuanbaogo.mine.order.finish.refund.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yuanbaogo.datacenter.remote.NetConfig;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.after.logistics.LogisticsCompanyModel;
import com.yuanbaogo.mine.order.finish.refund.adapter.RefundConditionAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 商品退款-选择退款原因
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/13/21 4:55 PM
 * @Modifier:
 * @Modify:
 */
public class RefundReasonBottomDialog extends BottomSheetDialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,
        OnCallRecyclerItem {

    private OnReasonSelect mOnReasonSelect;
    private RadioGroup salesDlRg;
    private int checkId;

    /**
     * 退款/退货条件列表
     */
    RecyclerView salesRecyclerCondition;
    /**
     * 适配器
     */
    RefundConditionAdapter refundConditionAdapter;
    /**
     * 选择下标
     */
    public static Set<Integer> positionSet = new HashSet<>();
    /**
     * 退货退款原因类型
     */
    int reasonType = 0;
    /**
     * 退货退款原因内容
     */
    String reasonName = "";

    public RefundReasonBottomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.mine_layout_dialog_refund, null);
        positionSet.clear();
        setContentView(view);
        initView(view);
        setFindSalesBasicsInfo();
    }

    private void initView(View view) {
        salesDlRg = view.findViewById(R.id.sales_dl_rg);
        TextView salesTvSure = view.findViewById(R.id.sales_tv_sure);
        salesTvSure.setOnClickListener(this);
        salesDlRg.setOnCheckedChangeListener(this);
        salesRecyclerCondition = view.findViewById(R.id.sales_recycler_condition);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sales_tv_sure) { //确定
            if (reasonType <= 0) {
                ToastView.showToast(R.string.sales_reason_toast);
                return;
            }
            cancel();
            mOnReasonSelect.onReasonSelectListener(reasonName, reasonType);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        checkId = checkedId;
    }

    public void setOnReasonSelect(OnReasonSelect onReasonSelect) {
        this.mOnReasonSelect = onReasonSelect;
    }

    public interface OnReasonSelect {
        void onReasonSelectListener(String reason, int position);
    }

    List<LogisticsCompanyModel> logisticsCompanyModels;

    public void setFindSalesBasicsInfo() {
        NetWork.getInstance().get(getContext(),
                ChildUrl.SELECT_LOGISTICS_COMPANY + "/2",
                null,
                new RequestSateListener<List<LogisticsCompanyModel>>() {
                    @Override
                    public void onSuccess(int code, List<LogisticsCompanyModel> bean) {
                        logisticsCompanyModels = bean;
                        if (logisticsCompanyModels.isEmpty()) {
                            ToastView.showToast("获取信息失败");
                        } else {
                            initRecycler();
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {

                    }
                }, false);
    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        salesRecyclerCondition.setLayoutManager(linearLayoutManager);
        refundConditionAdapter = new RefundConditionAdapter(getContext(), logisticsCompanyModels, R.layout.item_refund_condition);
        refundConditionAdapter.setOnCallBack(this);
        salesRecyclerCondition.setAdapter(refundConditionAdapter);
    }

    @Override
    public void onCallItem(View view, int postion) {
        positionSet.clear();
        addOrRemove(postion);
    }

    private void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            reasonType = logisticsCompanyModels.get(position).getCode();
            reasonName = logisticsCompanyModels.get(position).getName();
        } else {
            // 如果不包含，则添加
            positionSet.add(position);
            reasonType = logisticsCompanyModels.get(position).getCode();
            reasonName = logisticsCompanyModels.get(position).getName();
        }
        refundConditionAdapter.notifyDataSetChanged();
    }

}
