package com.yuanbaogo.zui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.databinding.LayoutSettingItemBinding;

public class SettingItem extends LinearLayout {

    private final LayoutSettingItemBinding binding;
    private TextView mSettingTvName;
    private String mNameText;
    private TextView mSettingTvSubName;
    private String mSubNameText;

    public SettingItem(Context context) {
        this(context, null);
    }

    public SettingItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        binding = LayoutSettingItemBinding.inflate(LayoutInflater.from(context), this, true);
        initAttribute(context, attrs);
        initView();
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItem);
        mNameText = typedArray.getString(R.styleable.SettingItem_nameText);
        mSubNameText = typedArray.getString(R.styleable.SettingItem_subNameText);
        typedArray.recycle();
    }

    private void initView() {
        mSettingTvName = binding.settingTvName;
        mSettingTvName.setText(mNameText);
        mSettingTvSubName = binding.settingTvSubName;
        mSettingTvSubName.setText(mSubNameText);
    }

    /**
     * 设置标题文字
     *
     * @param title /
     */
    public void setNameText(@Nullable String title) {
        if (!TextUtils.isEmpty(title)) {
            mSettingTvName.setText(title);
        }
    }

    /**
     * 设置副标题文字
     *
     * @param title /
     */
    public void setSubNameText(@Nullable String title) {
        if (!TextUtils.isEmpty(title)) {
            mSettingTvSubName.setText(title);
            getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            int lineCount = mSettingTvSubName.getLineCount();
                            if (lineCount <= 1) {
                                // 1行居中
                                mSettingTvSubName.setGravity(Gravity.RIGHT);
                            } else {
                                // 2行居左
                                mSettingTvSubName.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                            }
                        }
                    });
        }
    }

    /**
     * 获取标题内容
     *
     * @return /
     */
    public String getNameText() {
        return mSettingTvName.getText().toString().trim();
    }

    /**
     * 获取副标题内容
     *
     * @return /
     */
    public String getSubNameText() {
        return mSettingTvSubName.getText().toString().trim();
    }
}
