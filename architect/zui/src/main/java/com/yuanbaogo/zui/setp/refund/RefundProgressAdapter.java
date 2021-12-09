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

import java.util.List;

public class RefundProgressAdapter extends RecyclerView.Adapter<RefundProgressAdapter.TimelineViewHolder> {

    private static final String TAG = "TimelineAdapter";
    private final List<RefundProgressModel> mRefundProgressModels;
    private int mOrientation;

    /**
     * 只传数据的构造方法，默认为竖的
     *
     * @param logisticsModels /
     */
    public RefundProgressAdapter(List<RefundProgressModel> logisticsModels) {
        this(logisticsModels, LinearLayout.VERTICAL);
    }

    /**
     * 构造方法
     *
     * @param logisticsModels /
     * @param orientation     LinearLayout.horizontal = 0   LinearLayout.vertical = 1
     */
    public RefundProgressAdapter(List<RefundProgressModel> logisticsModels, int orientation) {
        mRefundProgressModels = logisticsModels;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline, parent, false);
        return new TimelineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        RefundProgressModel logisticsModel = mRefundProgressModels.get(position);
        RefundStatus refundStatus = logisticsModel.getOrderStatus();
        Context context = holder.mTimeline.getContext();
        if (refundStatus == RefundStatus.NO_FINISH) {
            // 处理中
            holder.mTimeline.setMarker(AppCompatResources.getDrawable(context, R.drawable.icon_timeline_small_marker));
        } else if (refundStatus == RefundStatus.FINISH) {
            // 处理完成
            holder.mTimeline.setMarker(AppCompatResources.getDrawable(context, R.drawable.icon_refund_finish));
        }
        holder.mLogisticsItemTvStatus.setText(logisticsModel.getStatus());
        holder.mLogisticsItemTvTime.setVisibility(View.GONE);
        holder.mLogisticsItemTvMsg.setText(logisticsModel.getDate());
        if (mRefundProgressModels.size() < 1) return;
        if (position == mRefundProgressModels.size() - 1) {
            // 最后一个不显示下面的线
            holder.mTimeline.initLine(TimelineView.LineType.END);
        }
    }

    @Override
    public int getItemCount() {
        return mRefundProgressModels.size();
    }

    public class TimelineViewHolder extends RecyclerView.ViewHolder {

        private TimelineView mTimeline;
        private TextView mLogisticsItemTvStatus;
        private TextView mLogisticsItemTvTime;
        private TextView mLogisticsItemTvMsg;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public TimelineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimeline = itemView.findViewById(R.id.timeline);
            mLogisticsItemTvStatus = itemView.findViewById(R.id.logistics_item_tv_status);
            mLogisticsItemTvTime = itemView.findViewById(R.id.logistics_item_tv_time);
            mLogisticsItemTvMsg = itemView.findViewById(R.id.logistics_item_tv_msg);
            mTimeline.setStartLineColor(itemView.getContext().getColor(R.color.colorE4E4E4), viewType);
            mTimeline.setEndLineColor(itemView.getContext().getColor(R.color.colorE4E4E4), viewType);
        }
    }
}
