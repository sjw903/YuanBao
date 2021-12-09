package com.yuanbaogo.video.mainui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.qcloud.ugckit.module.record.draft.RecordDraftInfo;
import com.tencent.qcloud.ugckit.module.record.draft.RecordDraftManager;
import com.tencent.qcloud.ugckit.utils.FileUtils;
import com.tencent.qcloud.ugckit.utils.NetworkUtil;
import com.tencent.qcloud.ugckit.utils.TCUserMgr;
import com.yuanbaogo.video.R;
import com.yuanbaogo.video.common.ShortVideoDialog;
import com.yuanbaogo.video.login.TCLoginActivity;
import com.yuanbaogo.video.mainui.list.TCUGCListFragment;
import com.yuanbaogo.video.userinfo.TCUserInfoFragment;
import com.yuanbaogo.video.videorecord.TCVideoRecordActivity;

import java.util.List;

/**
 * 主界面: 短视频列表，用户信息页
 */
public class TCMainActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "TCMainActivity";

    private Button mBtnVideo, mBtnSelect, mBtnUser;
    private Fragment mCurrentFragment;
    private Fragment mTCLiveListFragment, mTCUserInfoFragment;

    private long mLastClickPubTS = 0;

    private ShortVideoDialog mShortVideoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();

        showVideoFragment();

        if (checkPermission()) {
            return;
        }

        checkLastRecordPart();
    }

    private void checkLastRecordPart() {
        final RecordDraftManager recordDraftManager = new RecordDraftManager(this);
        RecordDraftInfo lastDraftInfo = recordDraftManager.getLastDraftInfo();
        if (lastDraftInfo == null) {
            return;
        }
        final List<RecordDraftInfo.RecordPart> recordPartList = lastDraftInfo.getPartList();
        if (recordPartList != null && recordPartList.size() > 0) {
            TXCLog.i(TAG, "checkLastRecordPart, recordPartList");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alertDialog = builder.setCancelable(false).setMessage(R.string.record_part_exist)
                    .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(TCMainActivity.this, TCVideoRecordActivity.class));
                        }
                    })
                    .setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            recordDraftManager.deleteLastRecordDraft();
                            for (final RecordDraftInfo.RecordPart recordPart : recordPartList) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FileUtils.deleteFile(recordPart.getPath());
                                    }
                                }).start();
                            }
                        }
                    }).create();
            alertDialog.show();
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return true;
                }
            }
        }
        return false;
    }

    private void initView() {
        mShortVideoDialog = new ShortVideoDialog();

        mBtnVideo = (Button) findViewById(R.id.btn_home_left);
        mBtnSelect = (Button) findViewById(R.id.btn_home_select);
        mBtnUser = (Button) findViewById(R.id.btn_home_right);

        mBtnUser.setOnClickListener(this);
        mBtnVideo.setOnClickListener(this);
        mBtnSelect.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (TextUtils.isEmpty(TCUserMgr.getInstance().getUserToken())) {
            if (NetworkUtil.isNetworkAvailable(this) && TCUserMgr.getInstance().hasUser()) {
                TCUserMgr.getInstance().autoLogin(null);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_home_left) {
            showVideoFragment();
        } else if (id == R.id.btn_home_select) {
            showSelect();
        } else if (id == R.id.btn_home_right) {
            showUserFragment();
        }
    }

    private void showSelect() {
        if (!TCUserMgr.getInstance().hasUser()) {
            Intent intent = new Intent(TCMainActivity.this, TCLoginActivity.class);
            startActivity(intent);
        } else {
            // 防止多次点击
            if (System.currentTimeMillis() - mLastClickPubTS > 1000) {
                mLastClickPubTS = System.currentTimeMillis();
                if (mShortVideoDialog.isAdded()) {
                    mShortVideoDialog.dismiss();
                } else {
                    mShortVideoDialog.show(getFragmentManager(), "");
                }
            }
        }
    }

    private void showUserFragment() {
        mBtnVideo.setBackgroundResource(R.drawable.ic_home_video_normal);
        mBtnUser.setBackgroundResource(R.drawable.ic_user_selected);
        if (mTCUserInfoFragment == null) {
            mTCUserInfoFragment = new TCUserInfoFragment();
        }
        showFragment(mTCUserInfoFragment, "user_fragment");
    }

    private void showVideoFragment() {
        mBtnVideo.setBackgroundResource(R.drawable.ic_home_video_selected);
        mBtnUser.setBackgroundResource(R.drawable.ic_user_normal);
        if (mTCLiveListFragment == null) {
            mTCLiveListFragment = new TCUGCListFragment();
        }
        showFragment(mTCLiveListFragment, "live_list_fragment");
    }

    private void showFragment(Fragment fragment, String tag) {
        if (fragment == mCurrentFragment) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }
        if (!fragment.isAdded()) {
            transaction.add(R.id.contentPanel, fragment, tag);
        } else {
            transaction.show(fragment);
        }
        mCurrentFragment = fragment;
        transaction.commit();
    }

}
