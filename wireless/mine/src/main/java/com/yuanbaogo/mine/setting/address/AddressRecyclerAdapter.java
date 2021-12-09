package com.yuanbaogo.mine.setting.address;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.model.AddressBean;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.List;

public class AddressRecyclerAdapter extends BaseRecycleAdapter<AddressBean> {

    private OnDeleteAddressListener mOnDeleteAddressListener;

    public AddressRecyclerAdapter(Context context, List<AddressBean> mDataList) {
        this(context, mDataList, 0);
    }

    public AddressRecyclerAdapter(Context context, List<AddressBean> mDataList, int layoutId) {
        super(context, mDataList, layoutId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_layout_address_item;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        AddressBean addressBean = getDataList().get(position);
        TextView addressItemTvArea = holder.getView(R.id.address_item_tv_area);
        TextView addressItemTvDetail = holder.getView(R.id.address_item_tv_detail);
        TextView addressItemTvName = holder.getView(R.id.address_item_tv_name);
        TextView addressItemTvPhone = holder.getView(R.id.address_item_tv_phone);
        TextView addressItemTvDelete = holder.getView(R.id.address_item_tv_delete);
        ImageView addressItemIvUpdate = holder.getView(R.id.address_item_iv_update);
        TextView addressItemTvDefault = holder.getView(R.id.address_item_tv_default);
        ImageView addressItemIvDefault = holder.getView(R.id.address_item_iv_default);
        if (addressBean.getDefaulted()) {
            addressItemTvDefault.setVisibility(View.VISIBLE);
        } else {
            addressItemTvDefault.setVisibility(View.GONE);
        }
        Log.e("===========", addressBean.isSelect() + "");
        if (addressBean.isSelect()) {
            addressItemIvDefault.setVisibility(View.VISIBLE);
        } else {
            addressItemIvDefault.setVisibility(View.GONE);
        }
        addressItemTvArea.setText(String.format("%s%s%s",
                addressBean.getProvince() == null ? "" : addressBean.getProvince(),
                addressBean.getCity() == null ? "" : addressBean.getCity(),
                addressBean.getCountry() == null ? "" : addressBean.getCountry()));
        addressItemTvDetail.setText(addressBean.getAddressDetails());
        addressItemTvName.setText(addressBean.getContactsName());
        addressItemTvPhone.setText(addressBean.getContactsPhone().substring(0, 3) + "****" + addressBean.getContactsPhone().substring(7));
        addressItemIvUpdate.setOnClickListener(v -> RouterHelper.toUpdateAddress(GsonUtil.GsonString(addressBean)));
        addressItemTvDelete.setOnClickListener(v -> {
            if (mOnDeleteAddressListener != null) {
                if (addressBean.getAddressId() == null) {
                    ToastView.showToast("收货地址id为空");
                } else {
                    mOnDeleteAddressListener.onDelete(addressBean.getAddressId());
                }
            }
        });
        //TODO HG item点击事件
        RelativeLayout addressItemRlItem = holder.getView(R.id.address_item_rl_item);
        addressItemRlItem.setOnClickListener(v -> mOnDeleteAddressListener.onItem(v, getDataList().get(position)));
    }

    public void setOnDeleteAddressListener(OnDeleteAddressListener onDeleteAddressListener) {
        this.mOnDeleteAddressListener = onDeleteAddressListener;
    }

    interface OnDeleteAddressListener {
        void onDelete(long addressId);

        void onItem(View view, AddressBean bean);//TODO HG 确认订单页面需要回调选中的地址信息
    }

}
