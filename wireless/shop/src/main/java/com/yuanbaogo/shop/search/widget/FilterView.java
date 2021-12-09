package com.yuanbaogo.shop.search.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yuanbaogo.shop.R;

/**
 * @Description: 自定义 筛选控件
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/6/21 5:05 PM
 * @Modifier:
 * @Modify:
 */
public class FilterView extends LinearLayout implements View.OnClickListener {

    private TextView priceView;

    private boolean isUp;//判断升序还是降序  判断网格还是列表

    /**
     * 点击回调
     */
    private Callback callback;

    int type; //1 价格  2  销量 3 视图

    public void setCallback(Callback callback, int type) {
        this.callback = callback;
        this.type = type;
    }

    public FilterView(Context context) {
        this(context, null);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.price_up_down, this);
        priceView = findViewById(R.id.price_id);
        priceView.setOnClickListener(this);
    }

    public void setPriceView(String title, Integer mipmap, boolean isUp) {
        this.isUp = isUp;
        priceView.setText(title);
        if (mipmap != null) {
            Drawable drawable = getResources().getDrawable(mipmap);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            priceView.setCompoundDrawables(null, null, drawable, null);
            priceView.setCompoundDrawablePadding(8);
        }
        priceView.setTextColor(getResources().getColor(R.color.color424242));
    }

    public TextView getPriceView() {
        return priceView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.price_id) {
            setDrawable();
            callback.getStatus(isUp, type);
        }
    }

    private void setDrawable() {
        Drawable drawable = null;
        if (type == 1 || type == 3) {
            if (isUp) {
                isUp = false;
                drawable = getResources().getDrawable(type == 1 ? R.mipmap.icon_search_price_drop : R.mipmap.icon_head_sort_gray);
            } else {
                isUp = true;
                drawable = getResources().getDrawable(type == 1 ? R.mipmap.icon_search_price_rise : R.mipmap.icon_head_sort_list_gray);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            priceView.setCompoundDrawables(null, null, drawable, null);
            priceView.setCompoundDrawablePadding(8);
        }
        priceView.setTextColor(getResources().getColor(R.color.colorF63C09));
    }

    public interface Callback {
        void getStatus(boolean isUp, int type);
    }

}
