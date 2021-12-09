package com.yuanbaogo.mine.setting.common;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.NullImplContract;
import com.yuanbaogo.mine.setting.NullImplPresenter;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.dialog.NormalDialogFragment;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.SettingItem;

@Route(path = YBRouter.COMMON_ACTIVITY)
public class CommonActivity extends MvpBaseActivityImpl<NullImplContract.View, NullImplPresenter> implements View.OnClickListener {

    private static final String TAG = "CommonActivity";
    private SettingItem mCommonSiClearCache;
    private NormalDialogFragment mDialog;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_common;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mCommonSiClearCache = findViewById(R.id.common_si_clear_cache);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mCommonSiClearCache.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initCache();
    }

    private void initCache() {
        String cacheSize = "0M";
        try {
            cacheSize = CleanDataUtils.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "initCache: " + e.getMessage());
        }
        mCommonSiClearCache.setSubNameText(cacheSize);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.common_si_clear_cache) {
            if (mCommonSiClearCache.getSubNameText().contains("0.00")) {
                ToastView.showToast("当前无可清除缓存");
            } else {
                mDialog = new NormalDialogFragment("清除本地缓存", "是否要清除本地缓存？",
                        getString(R.string.delete_address_left),
                        "清除");
                mDialog.show(getSupportFragmentManager(), "payPassword");
                mDialog.setOnNormalListener(() -> {
                    CleanDataUtils.clearAllCache(this);
                    initCache();
                    ToastView.showToast("清理缓存成功");
                });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}