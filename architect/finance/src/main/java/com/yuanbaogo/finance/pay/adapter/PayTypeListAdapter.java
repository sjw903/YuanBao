package com.yuanbaogo.finance.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.finance.R;
import com.yuanbaogo.finance.pay.model.PayTypeBean;
import com.yuanbaogo.finance.pay.presenter.PayCenter;
import com.yuanbaogo.libbase.baseutil.MoneyUtil;

import java.util.List;

import static com.yuanbaogo.finance.pay.presenter.PayCenter.PAYTYPE_WX;
import static com.yuanbaogo.finance.pay.presenter.PayCenter.PAYTYPE_YB;


public class PayTypeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<PayTypeBean> mTreeList;
    private OnItemClickListener onItemClickListener;

    private int mPayType;
    private String mPayAwayCode;
    private String mPrice;

    public interface OnItemClickListener {
        void onPayGroupClick(String yuanbaoBalance);

        void onPayCheckStatus(String payAwayCode, int payType);
    }

    public PayTypeListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.pay_item_pay_type, parent, false);
        return new ItemNodeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {

        PayTypeBean bean = mTreeList.get(position);

        if (bean == null) {
            return;
        }
        ItemNodeViewHolder holder = (ItemNodeViewHolder) h;

        Glide.with(mContext)
                .load(bean.getAndroidIconUrl())
                .thumbnail(0.1f).into(holder.tv_type_icon);

        if (bean.getPayAwayCode().equals(PAYTYPE_YB)) {
            holder.tv_suggest.setVisibility(View.VISIBLE);
            if (MoneyUtil.moneyComp(mPrice, bean.getBalance())) { //需要组合支付
//            if ("1".equals(bean.getIsCombination())) {
                if (bean.getBalance().equals("0")) {
                    holder.tv_suggest.setText("");
                    holder.tv_make_right.setVisibility(View.GONE);
                } else {
                    holder.tv_suggest.setText("(当前" + bean.getBalance() + ",需要组合支付)");
                    holder.tv_make_right.setVisibility(View.VISIBLE);
                }
                holder.v_root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null)
                            onItemClickListener.onPayGroupClick(bean.getBalance());
                    }
                });
            } else { //积分支付
                holder.tv_suggest.setText("(当前" + bean.getBalance() + ")");
                holder.tv_make_right.setVisibility(View.GONE);
            }
        } else {
            holder.tv_suggest.setVisibility(View.GONE);
            holder.tv_make_right.setVisibility(View.GONE);
        }
        holder.tv_type.setText(bean.getPayAwayName());
        holder.checkbox_select.setTag(bean.getPayAwayCode());
        holder.checkbox_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    mPayAwayCode = (String) holder.checkbox_select.getTag();
                    if (bean.getPayAwayCode().equals(PAYTYPE_WX)) {
                        mPayType = PayCenter.PAYCHANNEL_WX;
                    } else {
                        if (MoneyUtil.moneyComp(mPrice, bean.getBalance())) {
                            mPayType = PayCenter.PAYCHANNEL_GROUP;
                        } else {
                            mPayType = PayCenter.PAYCHANNEL_JF;
                        }
                    }
                }
                if (onItemClickListener != null)
                    onItemClickListener.onPayCheckStatus(mPayAwayCode, mPayType);
            }
        });
        if (bean.getPayAwayCode().equals(mPayAwayCode)) {
            holder.checkbox_select.setChecked(true);
        } else {
            holder.checkbox_select.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        int count = mTreeList == null ? 0 : mTreeList.size();
        return count;
    }

    public void setData(List<PayTypeBean> list, String price) {
        this.mPrice = price;
        this.mTreeList = list;
        mPayAwayCode = list.get(0).getPayAwayCode();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ItemNodeViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkbox_select;
        private TextView tv_suggest;
        private TextView tv_type;
        private ImageView tv_type_icon;
        private ImageView tv_make_right;
        private View v_root;

        public ItemNodeViewHolder(View itemView) {
            super(itemView);
            v_root = itemView;
            checkbox_select = (CheckBox) itemView.findViewById(R.id.checkbox_select);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_type_icon = (ImageView) itemView.findViewById(R.id.tv_type_icon);
            tv_make_right = (ImageView) itemView.findViewById(R.id.tv_make_right);
            tv_suggest = (TextView) itemView.findViewById(R.id.tv_suggest);
        }
    }


}
