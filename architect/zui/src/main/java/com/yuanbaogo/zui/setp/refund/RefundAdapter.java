package com.yuanbaogo.zui.setp.refund;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.setp.TimelineView;
import com.yuanbaogo.zui.view.FlowLayout;

import java.util.List;

import static com.yuanbaogo.libbase.baseutil.ScreenUtils.getScreenWidth;

public class RefundAdapter extends RecyclerView.Adapter<RefundAdapter.TimelineViewHolder> {

    private static final String TAG = "TimelineAdapter";
    private final List<RefundModel> mLogisticsModels;
    private int mOrientation;

    /**
     * 只传数据的构造方法，默认为竖的
     *
     * @param logisticsModels /
     */
    public RefundAdapter(List<RefundModel> logisticsModels) {
        this(logisticsModels, LinearLayout.VERTICAL);
    }

    /**
     * 构造方法
     *
     * @param logisticsModels /
     * @param orientation     LinearLayout.horizontal = 0   LinearLayout.vertical = 1
     */
    public RefundAdapter(List<RefundModel> logisticsModels, int orientation) {
        mLogisticsModels = logisticsModels;
        mOrientation = orientation;
    }

    /**
     * 设置横竖
     *
     * @param orientation LinearLayout.horizontal = 0   LinearLayout.vertical = 1
     */
    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
        notifyDataSetChanged();
    }

    public int getOrientation() {
        return mOrientation;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_horizontal, parent, false);
        int width = getScreenWidth(parent.getContext()) - FlowLayout.dp2px(52);
        view.getLayoutParams().width = width / mLogisticsModels.size();
        return new TimelineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        RefundModel logisticsModel = mLogisticsModels.get(position);
        RefundStatus logisticsStatus = logisticsModel.getOrderStatus();
        Context context = holder.mTimeline.getContext();
        if (logisticsStatus == RefundStatus.NO_FINISH) {
            // 正在出库
            holder.mTimeline.setMarker(AppCompatResources.getDrawable(context, R.mipmap.icon_order_null));
            holder.mLogisticsItemTvMsg.setTextColor(context.getColor(R.color.wing_sub_title));
            holder.mTimeline.setStartLineColor(context.getColor(R.color.colorE4E4E4), holder.viewType);
            holder.mTimeline.setEndLineColor(context.getColor(R.color.colorE4E4E4), holder.viewType);
        } else {
            // 已签收
            holder.mTimeline.setMarker(AppCompatResources.getDrawable(context, R.mipmap.icon_order_signed_for));
            holder.mLogisticsItemTvMsg.setTextColor(context.getColor(R.color.wing_selected_title));
            holder.mTimeline.setStartLineColor(context.getColor(R.color.wing_selected_title), holder.viewType);
            holder.mTimeline.setEndLineColor(context.getColor(R.color.wing_selected_title), holder.viewType);
        }
        holder.mLogisticsItemTvMsg.setText(logisticsModel.getMessage());
        if (mLogisticsModels.size() < 1) return;
        if (position == 0) {
            // 第一个不显示前面的线
            holder.mTimeline.initLine(TimelineView.LineType.START);
        }
        if (position == mLogisticsModels.size() - 1) {
            // 最后一个不显示下面的线
            holder.mTimeline.initLine(TimelineView.LineType.END);
        }
    }

    @Override
    public int getItemCount() {
        return mLogisticsModels.size();
    }

    public class TimelineViewHolder extends RecyclerView.ViewHolder {

        private TimelineView mTimeline;
        private TextView mLogisticsItemTvMsg;
        private int viewType;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public TimelineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            mTimeline = itemView.findViewById(R.id.refund_timeline);
            mLogisticsItemTvMsg = itemView.findViewById(R.id.refund_item_tv_msg);
            mTimeline.setStartLineColor(itemView.getContext().getColor(R.color.colorE4E4E4), viewType);
            mTimeline.setEndLineColor(itemView.getContext().getColor(R.color.colorE4E4E4), viewType);
        }
    }
}
