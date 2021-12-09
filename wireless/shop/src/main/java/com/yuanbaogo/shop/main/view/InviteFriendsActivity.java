package com.yuanbaogo.shop.main.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libshare.ShareUtil;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.libshare.model.ShareParamete;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.main.adapter.InviteFriendsPromoteAdapter;
import com.yuanbaogo.shop.main.contract.InviteFriendsContract;
import com.yuanbaogo.shop.main.presenter.InviteFriendsPresenter;
import com.yuanbaogo.zui.dialog.GenerateMitoDialogView;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/2/21 9:34 AM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.INVITE_FRIENDS_ACTIVITY)
public class InviteFriendsActivity
        extends MvpBaseActivityImpl<InviteFriendsContract.View, InviteFriendsPresenter>
        implements View.OnClickListener, ShareUtil.OnCallShare {

    HeadView inviteFriendsHeadView;

    RecyclerView inviteFriendsRecyclerPromoteDetail;

    InviteFriendsPromoteAdapter inviteFriendsPromoteAdapter;

    List<String> list = new ArrayList<>();

    TextView inviteFriendsTvCode;

    TextView inviteFriendsTvGenerateMito;

    TextView inviteFriendsTvWX;

    TextView inviteFriendsTvMorePromote;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_invite_friends;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        inviteFriendsHeadView = findViewById(R.id.invite_friends_head_view);
        inviteFriendsRecyclerPromoteDetail = findViewById(R.id.invite_friends_recycler_promote_detail);
        inviteFriendsTvCode = findViewById(R.id.invite_friends_tv_code);
        inviteFriendsTvGenerateMito = findViewById(R.id.invite_friends_tv_generate_mito);
        inviteFriendsTvWX = findViewById(R.id.invite_friends_tv_wx);
        inviteFriendsTvMorePromote = findViewById(R.id.invite_friends_tv_more_promote);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        inviteFriendsTvCode.setOnClickListener(this);
        inviteFriendsTvGenerateMito.setOnClickListener(this);
        inviteFriendsTvWX.setOnClickListener(this);
        inviteFriendsTvMorePromote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.invite_friends_tv_code) {
            if (inviteFriendsTvCode.getText().toString().length() > 0) {
                //获取剪贴板管理器
                ClipboardManager clipboardManager1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData1 = ClipData.newPlainText("邀请码", UserStore.getInviteCode());
                // 将ClipData内容放到系统剪贴板里。
                clipboardManager1.setPrimaryClip(mClipData1);
                ToastView.showToast("邀请码已复制");
            } else {
                ToastView.showToast("未获取到邀请码，请刷新页面");
            }
        } else if (id == R.id.invite_friends_tv_generate_mito) {
            ShareBean shareBean = new ShareBean()
                    .setTitle("我要分享图片到好友")
                    .setUmImgUrl("https://ybsjds-oss-shanghai.oss-cn-shanghai.aliyuncs.com/material/picture/mall-management/JHAD118FNZ/mall-management-picture/JHAD118FNZ.jpeg?1629705662");
            GenerateMitoDialogView generateMitoDialogView = new GenerateMitoDialogView(shareBean);
            generateMitoDialogView.show(getSupportFragmentManager(), "generate_mito");
        } else if (id == R.id.invite_friends_tv_wx) {
            ShareBean shareBean = new ShareBean()
                    .setUmMinUrl("http://123")
                    .setUmMinPath("/path/123")
                    .setUmMinUserName("gh_d43f693ca31f");
            ShareUtil.getShareUtils()
                    .shareUtilss(this)
                    .setOnCallShare(this)
                    .setUmMin(ShareUtil.getShareUtils().umMin(shareBean), ShareParamete.WEIXIN);
        } else if (id == R.id.invite_friends_tv_more_promote) {
            RouterHelper.toInviteFriendsPromote();
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        inviteFriendsTvCode.setText("我的邀请码：" + UserStore.getInviteCode() + "（点击复制）");
        initHeadView();
        initRecyclerPromote();
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setRlTopNavigaBarBg(R.color.colorFFFFFF)
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_back)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("元宝有礼")
                .setImgRight(false);
        inviteFriendsHeadView.setHead(headBean);
    }

    private void initRecyclerPromote() {
        for (int i = 1; i < 5; i++) {
            list.add("元宝用户00" + i);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        inviteFriendsRecyclerPromoteDetail.setLayoutManager(linearLayoutManager);
        inviteFriendsPromoteAdapter = new InviteFriendsPromoteAdapter(
                this, list, R.layout.item_invite_friends_promote_detail);
        inviteFriendsRecyclerPromoteDetail.setAdapter(inviteFriendsPromoteAdapter);
    }

    @Override
    public void onStartShares() {

    }

    @Override
    public void onResultShares() {

    }

    @Override
    public void onErrorShares() {

    }

    @Override
    public void onCancelShares() {

    }
}