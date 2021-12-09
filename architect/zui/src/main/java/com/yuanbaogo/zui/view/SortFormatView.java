package com.yuanbaogo.zui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.model.SkuBean;
import com.yuanbaogo.zui.search.view.SearchLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 规格选择布局
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/1/21 4:27 PM
 * @Modifier:
 * @Modify:
 */
public class SortFormatView extends LinearLayout {

    Context context;

    List<View> mViewList = new ArrayList<>();

    /**
     * 存储规格选中的item
     */
    public static Set<Integer> colorPositionSet;

    TextView dialogSortTvTitle;

    SearchLayout dialogSortRecycler;

    /**
     * 规格item
     */
    RelativeLayout itemSortViewRl;

    /**
     * 规格图片
     */
    ImageView itemSortViewImgColor;

    /**
     * 规格文字
     */
    TextView itemSortViewTvColor;

    SkuBean.SkuSpecBean skuSpecBean;

    int type;

    String skuName;

    public void setSkuSpecBean(SkuBean.SkuSpecBean skuSpecBean, int type) {
        this.skuSpecBean = skuSpecBean;
        this.type = type;
        dialogSortTvTitle.setText(skuSpecBean.getSpecName());
        initRecycler(skuSpecBean.getSpecValue());
    }

    public void setSkuSpecBean(SkuBean.SkuSpecBean skuSpecBean, int type, String skuName) {
        this.skuSpecBean = skuSpecBean;
        this.type = type;
        this.skuName = skuName;
        dialogSortTvTitle.setText(skuSpecBean.getSpecName());
        if (!TextUtils.isEmpty(skuName)) {
            for (int i = 0; i < skuSpecBean.getSpecValue().size(); i++) {
                if (skuSpecBean.getSpecValue().get(i).getValue().equals(skuName.trim())) {
                    colorPositionSet.add(i);
                }
            }
        }
        initRecycler(skuSpecBean.getSpecValue());
    }

    public SortFormatView(Context context) {
        this(context, null);
    }

    public SortFormatView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortFormatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sort_view_format, this);
        dialogSortTvTitle = view.findViewById(R.id.dialog_sort_tv_title);
        dialogSortRecycler = view.findViewById(R.id.dialog_sort_recycler);
        colorPositionSet = new HashSet<>();
    }

    private void initRecycler(List<SkuBean.SkuSpecBean.SkuSpecValueBean> specValue) {
        mViewList.clear();
        Set<Integer> positionSet = colorPositionSet;
        for (int i = 0; i < specValue.size(); i++) {
            if (TextUtils.isEmpty(specValue.get(i).getValue())) {
                break;
            }
            View mContentView = LayoutInflater.from(context)
                    .inflate(R.layout.item_sort_color_view, dialogSortRecycler, false);
            itemSortViewRl = mContentView.findViewById(R.id.item_sort_view_rl);
            itemSortViewImgColor = mContentView.findViewById(R.id.item_sort_view_img_color);
            itemSortViewTvColor = mContentView.findViewById(R.id.item_sort_view_tv_color);
            if (TextUtils.isEmpty(specValue.get(i).getSpecImageUrl())) {
                itemSortViewImgColor.setVisibility(View.GONE);
            } else {
                itemSortViewImgColor.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(specValue.get(i).getSpecImageUrl())
                        .into(itemSortViewImgColor);
            }
            itemSortViewTvColor.setText(specValue.get(i).getValue());
            if (positionSet.contains(i)) {
                itemSortViewTvColor.setTextColor(getResources().getColor(R.color.colorEA5504));//选中
                itemSortViewRl.setBackground(getResources().getDrawable(R.drawable.shape_line_ea5504_5));
            } else {
                itemSortViewTvColor.setTextColor(getResources().getColor(R.color.color212121));//未选中
                itemSortViewRl.setBackground(getResources().getDrawable(R.drawable.shape_bg_f6f6f6_5));
            }
            mViewList.add(mContentView);
        }
        dialogSortRecycler.setChildren(mViewList);
        dialogSortRecycler.setOnTagClickListener(new SearchLayout.OnTagClickListener() {
            @Override
            public void onTagClick(View view, int position) {
                if (!colorPositionSet.contains(position)) {
                    colorPositionSet.clear();
                }
                addOrRemoveColor(position);
            }
        });
    }

    private void addOrRemoveColor(int position) {
        if (colorPositionSet.contains(position)) {
            colorPositionSet.remove(position);
        } else {
            colorPositionSet.add(position);
        }
        initRecycler(skuSpecBean.getSpecValue());
        String select = skuSpecBean.getSpecValue().get(position).getIndex() + "";
        onCallSelect.onSelect(type, select);
        colorPositionSet.clear();
    }

    OnCallSelect onCallSelect;

    public void setOnCallSelect(OnCallSelect onCallSelect) {
        this.onCallSelect = onCallSelect;
    }

    public interface OnCallSelect {
        void onSelect(int type, String select);
    }

}
