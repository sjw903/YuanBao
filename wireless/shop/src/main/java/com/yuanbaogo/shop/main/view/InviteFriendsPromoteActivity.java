package com.yuanbaogo.shop.main.view;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.main.adapter.InviteFriendsPromoteAdapter;
import com.yuanbaogo.shop.main.contract.InviteFriendsPromoteContract;
import com.yuanbaogo.shop.main.presenter.InviteFriendsPromotePresenter;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 9/2/21 6:38 PM
 * @Modifier:
 * @Modify:
 */
@Route(path = YBRouter.INVITE_FRIENDS_PROMOTE_ACTIVITY)
public class InviteFriendsPromoteActivity
        extends MvpBaseActivityImpl<InviteFriendsPromoteContract.View, InviteFriendsPromotePresenter> {

    HeadView inviteFriendsPromoteHeadView;

    RecyclerView inviteFriendsPromoteRecycler;

    InviteFriendsPromoteAdapter inviteFriendsPromoteAdapter;

    List<String> list = new ArrayList<>();

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_invite_friends_promote;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        inviteFriendsPromoteHeadView = findViewById(R.id.invite_friends_promote_head_view);
        inviteFriendsPromoteRecycler = findViewById(R.id.invite_friends_promote_recycler);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
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
                .setTvCenterTitles("推广明细")
                .setImgRight(false);
        inviteFriendsPromoteHeadView.setHead(headBean);
    }

    private void initRecyclerPromote() {
        for (int i = 1; i < 15; i++) {
            list.add("元宝用户00" + i);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        inviteFriendsPromoteRecycler.setLayoutManager(linearLayoutManager);
        inviteFriendsPromoteAdapter = new InviteFriendsPromoteAdapter(
                this, list, R.layout.item_invite_friends_promote_detail);
        inviteFriendsPromoteRecycler.setAdapter(inviteFriendsPromoteAdapter);
    }

}