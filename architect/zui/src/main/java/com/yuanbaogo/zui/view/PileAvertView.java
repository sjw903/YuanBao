package com.yuanbaogo.zui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yuanbaogo.libbase.baseutil.glide.GlideRoundTransform;
import com.yuanbaogo.zui.R;

import java.util.List;

public class PileAvertView extends LinearLayout {

    PileView pileView;

    private Context context = null;
    public static final int VISIBLE_COUNT = 3;//默认显示个数

    public PileAvertView(Context context) {
        this(context, null);
        this.context = context;
    }

    public PileAvertView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_group_pile_avert, this);
        pileView = view.findViewById(R.id.pile_view);
    }

    public void setAvertImages(List<String> imageList) {
        setAvertImages(imageList,VISIBLE_COUNT);
    }

    //如果imageList>visiableCount,显示List最上面的几个
    public void setAvertImages(List<String> imageList, int visibleCount) {
        List<String> visibleList = null;
        if (imageList.size() > visibleCount) {
            visibleList = imageList.subList(imageList.size() - 1 - visibleCount, imageList.size() - 1);
        }
        pileView.removeAllViews();
        for (int i = 0; i < imageList.size(); i++) {
            de.hdodenhof.circleimageview.CircleImageView image= (de.hdodenhof.circleimageview.CircleImageView) LayoutInflater.from(context).
                    inflate(R.layout.item_group_round_avert, pileView, false);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(100));
            Glide.with(context).load(imageList.get(i)).apply(options).into(image);
            pileView.addView(image);
        }
    }

}

