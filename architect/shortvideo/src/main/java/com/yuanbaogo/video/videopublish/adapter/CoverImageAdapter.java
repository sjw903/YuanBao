package com.yuanbaogo.video.videopublish.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.yuanbaogo.video.R;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

/**
 * 功能：  封面帧图片列表适配器
 * 时间 ： 2021/8/16:.
 * 作者：11190
 */

public class CoverImageAdapter extends BaseRecycleAdapter<Bitmap>  {


    private List<Bitmap> datas;
    private int mChioceposition=0;
    private OnCallRecyclerItem mCallRecyclerItem;

    private int layoutId;


    public CoverImageAdapter(Context context, List<Bitmap> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = datas;
        this.layoutId = layoutId;
    }


    public void setCallRecyclerItem(OnCallRecyclerItem callRecyclerItem) {
        mCallRecyclerItem = callRecyclerItem;
    }

    public int getChioceposition() {
        return mChioceposition;
    }

    public void setChioceposition(int chioceposition) {
        mChioceposition = chioceposition;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        ImageView mIvCover=holder.getView(R.id.iv_cover);
        View mVProspect=holder.getView(R.id.v_prospect);
        mIvCover.setImageBitmap(datas.get(position));
        if(mChioceposition==position){
            mVProspect.setAlpha(1f);
            mVProspect.setBackgroundResource(R.drawable.image_prospect_no_radius);
        }else {
            mVProspect.setBackgroundColor(mContext.getColor(R.color.white));
            mVProspect.setAlpha(0.6f);
        }
        mIvCover.setOnClickListener(v->{
            mChioceposition=position;
            notifyDataSetChanged();
            if(mCallRecyclerItem!=null){
                mCallRecyclerItem.onCallItem(mIvCover,position);
            }
        });

    }

}
