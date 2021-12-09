package com.yuanbaogo.zui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.databinding.LayoutTitleBinding;


public class TitleBar extends LinearLayout implements View.OnClickListener {

    private final LayoutTitleBinding binding;
    private TextView mTitleBarTvRight;
    private TextView mTitleBarTvTitle;
    private String mTitleName;
    private String mRightText;
    private int mTitleNameColor;
    private int mRightTextColor;
    private Drawable mLeftImageRes;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        binding = LayoutTitleBinding.inflate(LayoutInflater.from(context), this, true);
        initAttribute(context, attrs);
        initView(context);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mTitleName = typedArray.getString(R.styleable.TitleBar_titleName);
        mRightText = typedArray.getString(R.styleable.TitleBar_rightText);
        mTitleNameColor = typedArray.getColor(R.styleable.TitleBar_titleNameColor, context.getColor(R.color.title_bar_text_color));
        mRightTextColor = typedArray.getColor(R.styleable.TitleBar_rightTextColor, context.getColor(R.color.title_bar_text_color));
        mLeftImageRes = typedArray.getDrawable(R.styleable.TitleBar_leftImageRes);
        typedArray.recycle();
    }

    private void initView(Context context) {
        // 设置状态栏的高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        binding.getRoot().setPadding(0, statusBarHeight, 0, 0);
        mTitleBarTvTitle = binding.titleBarTvTitle;
        mTitleBarTvRight = binding.titleBarTvRight;
        binding.titleBarIbBack.setOnClickListener(this);
        binding.titleBarTvTitle.setText(mTitleName);
        binding.titleBarTvRight.setText(mRightText);
        if (!TextUtils.isEmpty(binding.titleBarTvRight.getText())) {
            mTitleBarTvRight.setVisibility(VISIBLE);
        }
        if (mLeftImageRes != null) {
            binding.titleBarIbBack.setImageDrawable(mLeftImageRes);
        }
        binding.titleBarTvTitle.setTextColor(mTitleNameColor);
        binding.titleBarTvRight.setTextColor(mRightTextColor);
    }

    /**
     * 设置标题文字
     *
     * @param title 标题
     */
    public void setTitle(@Nullable String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        mTitleBarTvTitle.setText(title);
    }

    /**
     * 设置标题文字
     *
     * @param title 标题
     */
    public void setTitle(@StringRes int title) {
        mTitleBarTvTitle.setText(title);
    }


    /**
     * 设置右边文字
     *
     * @param rightText 设置文字
     */
    public void setRightText(@Nullable String rightText) {
        if (!TextUtils.isEmpty(rightText)) {
            mTitleBarTvRight.setText(rightText);
            mTitleBarTvRight.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置右边颜色
     *
     * @param rightTextColor 设置文字颜色
     */
    public void setRightTextColor(int rightTextColor) {
        if (rightTextColor <= 0) return;
        mTitleBarTvRight.setTextColor(rightTextColor);
    }

    /**
     * 设置右边文字点击事件
     *
     * @param l 点击事件
     */
    public void setRightTextOnClickListener(@Nullable OnClickListener l) {
        if (mTitleBarTvRight.getVisibility() == View.VISIBLE) {
            mTitleBarTvRight.setOnClickListener(l);
        }
    }

    /**
     * 设置标题点击事件
     *
     * @param l 点击事件
     */
    public void setTitleOnClickListener(@Nullable OnClickListener l) {
        mTitleBarTvTitle.setOnClickListener(l);
    }

    /**
     * 设置左边返回按钮图片资源
     *
     * @param imageRes 图片
     */
    public void setLeftImageRes(@Nullable int imageRes) {
        if (imageRes <= 0) {
            return;
        }
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), imageRes, null);
        binding.titleBarIbBack.setImageDrawable(drawable);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_bar_ib_back) {
            Activity activity = (Activity) v.getContext();
            activity.finish();
        }
    }

}
