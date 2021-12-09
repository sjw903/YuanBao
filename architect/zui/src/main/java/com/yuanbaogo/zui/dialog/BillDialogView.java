package com.yuanbaogo.zui.dialog;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;
import com.yuanbaogo.zui.toast.ToastView;

/**
 * @Description: 选择发票弹窗
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 5:01 PM
 * @Modifier:
 * @Modify:
 */
public class BillDialogView extends DialogsFragment implements View.OnClickListener {

    View mView;

    /**
     * 关闭弹窗
     */
    ImageView dialogBillImgDelete;

    Button dialogBillBut;

    TextView dialogBillTvInvoicePersonal;

    LinearLayout dialogBillLlInvoicePersonal;

    TextView dialogBillTvInvoiceUnit;

    LinearLayout dialogBillLlInvoiceUnit;

    /**
     * 1 个人   2 单位
     */
    int mInvoice = 1;

    @Override
    public int getLayout() {
        return R.layout.dialog_bill_view;
    }

    @Override
    protected void intViews(View view) {
        this.mView = view;
        dialogBillImgDelete = mView.findViewById(R.id.dialog_bill_img_delete);
        dialogBillBut = mView.findViewById(R.id.dialog_bill_but);
        dialogBillTvInvoicePersonal = mView.findViewById(R.id.dialog_bill_tv_invoice_personal);
        dialogBillLlInvoicePersonal = mView.findViewById(R.id.dialog_bill_ll_invoice_personal);
        dialogBillTvInvoiceUnit = mView.findViewById(R.id.dialog_bill_tv_invoice_unit);
        dialogBillLlInvoiceUnit = mView.findViewById(R.id.dialog_bill_ll_invoice_unit);
        startUpAnimation(mView);
    }

    @Override
    protected void setTexts() {

    }

    @Override
    protected void setOnClicks() {
        dialogBillImgDelete.setOnClickListener(this);
        dialogBillBut.setOnClickListener(this);
        dialogBillTvInvoicePersonal.setOnClickListener(this);
        dialogBillTvInvoiceUnit.setOnClickListener(this);
    }

    public int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_bill_img_delete) {
            startDownAnimation(mView);
        } else if (id == R.id.dialog_bill_but) {
            ToastView.showToast(getActivity(), "请选择商品规格");
        } else if (id == R.id.dialog_bill_tv_invoice_personal) {
            mInvoice = 1;
            selectInvoice();
        } else if (id == R.id.dialog_bill_tv_invoice_unit) {
            mInvoice = 2;
            selectInvoice();
        }
    }

    /**
     * 设置
     */
    public void selectInvoice() {
        dialogBillTvInvoicePersonal.setBackground(mInvoice == 1 ?
                getActivity().getResources().getDrawable(R.drawable.icon_add_bill_type)
                :
                getActivity().getResources().getDrawable(R.drawable.icon_add_bill_normal_type));
        dialogBillTvInvoicePersonal.setTextColor(mInvoice == 1 ?
                getActivity().getResources().getColor(R.color.colorEA5504)
                :
                getActivity().getResources().getColor(R.color.color424242));
        dialogBillLlInvoicePersonal.setVisibility(mInvoice == 1 ? View.VISIBLE : View.GONE);

        dialogBillTvInvoiceUnit.setBackground(mInvoice == 2 ?
                getActivity().getResources().getDrawable(R.drawable.icon_add_bill_type)
                :
                getActivity().getResources().getDrawable(R.drawable.icon_add_bill_normal_type));
        dialogBillTvInvoiceUnit.setTextColor(mInvoice == 2 ?
                getActivity().getResources().getColor(R.color.colorEA5504)
                :
                getActivity().getResources().getColor(R.color.color424242));
        dialogBillLlInvoiceUnit.setVisibility(mInvoice == 2 ? View.VISIBLE : View.GONE);
    }

}
