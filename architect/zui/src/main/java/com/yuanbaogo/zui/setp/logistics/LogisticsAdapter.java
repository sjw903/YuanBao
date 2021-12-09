package com.yuanbaogo.zui.setp.logistics;

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
import com.yuanbaogo.zui.setp.Utils;

import java.util.List;

public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.TimelineViewHolder> {

    private static final String TAG = "TimelineAdapter";
    private final List<LogisticsModel> mLogisticsModels;
    private int mOrientation;

    /**
     * 只传数据的构造方法，默认为竖的
     *
     * @param logisticsModels /
     */
    public LogisticsAdapter(List<LogisticsModel> logisticsModels) {
        this(logisticsModels, LinearLayout.VERTICAL);
    }

    /**
     * 构造方法
     *
     * @param logisticsModels /
     * @param orientation    LinearLayout.horizontal = 0   LinearLayout.vertical = 1
     */
    public LogisticsAdapter(List<LogisticsModel> logisticsModels, int orientation) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline, parent, false);
        return new TimelineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        LogisticsModel logisticsModel = mLogisticsModels.get(position);
        LogisticsStatus logisticsStatus = logisticsModel.getOrderStatus();
        Context context = holder.mTimeline.getContext();
        if (logisticsStatus == LogisticsStatus.OUTBOUND) {
            // 正在出库
            holder.mTimeline.setMarker(AppCompatResources.getDrawable(context, R.mipmap.icon_order_outbound));
        } else if (logisticsStatus == LogisticsStatus.RECEIVED) {
            // 已揽收
            holder.mTimeline.setMarker(AppCompatResources.getDrawable(context, R.mipmap.icon_order_received));
        } else if (logisticsStatus == LogisticsStatus.IN_TRANSIT) {
            // 运输中
            holder.mTimeline.setMarker(AppCompatResources.getDrawable(context, R.drawable.icon_timeline_small_marker));
            holder.mTimeline.setMarkerSize(Utils.dpToPx(18f, context));
            holder.mTimeline.setMarkerPaddingTop(Utils.dpToPx(7.5f, context));
            holder.mTimeline.setMarkerPaddingBottom(Utils.dpToPx(7.5f, context));
            holder.mTimeline.setMarkerPaddingLeft(Utils.dpToPx(7.5f, context));
            holder.mTimeline.setMarkerPaddingRight(Utils.dpToPx(7.5f, context));
        } else if (logisticsStatus == LogisticsStatus.ON_THE_WAY) {
            // 派送中
            holder.mTimeline.setMarker(AppCompatResources.getDrawable(context, R.mipmap.icon_order_on_the_way));
        } else if (logisticsStatus == LogisticsStatus.SIGNED_FOR) {
            // 已签收
            holder.mTimeline.setMarker(AppCompatResources.getDrawable(context, R.mipmap.icon_order_signed_for));
        }
        holder.mLogisticsItemTvStatus.setText(logisticsModel.getStatus());
        holder.mLogisticsItemTvTime.setText(logisticsModel.getDate());
        holder.mLogisticsItemTvMsg.setText(logisticsModel.getMessage());
        if (mLogisticsModels.size() < 1) return;
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
