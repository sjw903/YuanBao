package com.yuanbaogo.zui.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.commonlib.utils.array.ArrayTools;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.libshare.ShareUtil;
import com.yuanbaogo.libshare.model.ShareBean;
import com.yuanbaogo.libshare.model.ShareParamete;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.adapter.ShareDialogAdapter;
import com.yuanbaogo.zui.dialog.adapter.ShareDialogSiteAdapter;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 分享弹窗
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 5:01 PM
 * @Modifier:
 * @Modify:
 */
public class ShareDialogView extends DialogsFragment implements OnCallRecyclerItem,
        View.OnClickListener, ShareUtil.OnCallShare {

    View mView;

    /**
     * 分享范围：朋友圈 微信 好友 社群
     */
    RecyclerView dialogShareViewRecyclerShare;

    /**
     * 分享范围数据
     */
    List<ArrayItemBean> shareList;

    /**
     * 分享范围
     */
    RecyclerView dialogShareViewRecyclerSite;

    List<ArrayItemBean> siteList;

    TextView dialogShareViewTvCancel;

    IDeleteVideoListener deleteVideoListener;

    ReportVideoListener mReportVideoListener;

    UninterestedVideoListener mUninterestedVideoListener;

    public void setDeleteVideoListener(IDeleteVideoListener deleteVideoListener) {
        this.deleteVideoListener = deleteVideoListener;
    }

    public void setReportVideoListener(ReportVideoListener reportVideoListener) {
        mReportVideoListener = reportVideoListener;
    }

    public void setUninterestedVideoListener(UninterestedVideoListener uninterestedVideoListener) {
        mUninterestedVideoListener = uninterestedVideoListener;
    }

    private ShareBean shareBean;

    public ShareDialogView(ShareBean shareBean) {
        this.shareBean = shareBean;
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_share_view;
    }

    @Override
    protected void intViews(View view) {
        this.mView = view;
        dialogShareViewRecyclerShare = mView.findViewById(R.id.dialog_share_view_recycler_share);
        dialogShareViewRecyclerSite = mView.findViewById(R.id.dialog_share_view_recycler_site);
        dialogShareViewTvCancel = mView.findViewById(R.id.dialog_share_view_tv_cancel);

        ArrayTools.share.WEIXIN_CIRCLE.setShow(shareBean.isWeixinCircle());
        ArrayTools.share.WEIXIN.setShow(shareBean.isWeixin());
        ArrayTools.share.FRIEND.setShow(shareBean.isFriend());
        ArrayTools.share.COMMUNITY.setShow(shareBean.isCommunity());
        ArrayTools.site.REPORT.setShow(shareBean.isReport());
        ArrayTools.site.NOT_INTERESTED.setShow(shareBean.isNotInterested());
        ArrayTools.site.DELETE.setShow(shareBean.isDelete());

        startUpAnimation(mView);
    }

    @Override
    protected void setTexts() {
        initData();
    }

    private void initData() {
        shareList = new ArrayList<>();
        for (ArrayTools.share airlineTypeEnum : ArrayTools.share.values()) {
            if (airlineTypeEnum.isShow()) {
                shareList.add(new ArrayItemBean()
                        .setId(airlineTypeEnum.getId())
                        .setName(airlineTypeEnum.getName())
                        .setIcon(airlineTypeEnum.getIcon())
                        .setVisibility(airlineTypeEnum.isShow()));
            }
        }
        siteList = new ArrayList<>();
        for (ArrayTools.site airlineTypeEnum : ArrayTools.site.values()) {
            if (airlineTypeEnum.isShow()) {
                siteList.add(new ArrayItemBean()
                        .setId(airlineTypeEnum.getId())
                        .setName(airlineTypeEnum.getName())
                        .setIcon(airlineTypeEnum.getIcon())
                        .setVisibility(airlineTypeEnum.isShow()));
            }
        }
        if (shareList.size() != 0) {
            initRecyclerShare();
        } else {
            dialogShareViewRecyclerShare.setVisibility(View.GONE);
        }
        if (siteList.size() != 0) {
            initRecyclerSite();
        } else {
            dialogShareViewRecyclerSite.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setOnClicks() {
        dialogShareViewTvCancel.setOnClickListener(this);
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getGravity() {
        return Gravity.BOTTOM;
    }

    private void initRecyclerShare() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        dialogShareViewRecyclerShare.setLayoutManager(linearLayoutManager);
        ShareDialogAdapter shareDialogAdapter = new ShareDialogAdapter(
                getActivity(), shareList, R.layout.item_share_view);
        shareDialogAdapter.setOnCallback(this);
        dialogShareViewRecyclerShare.setAdapter(shareDialogAdapter);
    }

    private void initRecyclerSite() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        dialogShareViewRecyclerSite.setLayoutManager(linearLayoutManager);
        ShareDialogSiteAdapter shareDialogSiteAdapter = new ShareDialogSiteAdapter(
                getActivity(), siteList, R.layout.item_site_view);
        shareDialogSiteAdapter.setOnCallback(this);
        dialogShareViewRecyclerSite.setAdapter(shareDialogSiteAdapter);
    }

    @Override
    public void onCallItem(View view, int postion) {
        int id = view.getId();
        if (!UserStore.isLogin()) {
            RouterHelper.toLogin();
            return;
        }
        if (id == R.id.item_share_view_rl) {
            initShare(shareList.get(postion).getId());
        } else if (id == R.id.item_site_view_rl) {
            initSite(siteList.get(postion).getId());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_share_view_tv_cancel) {
            startDownAnimation(mView);
        }
    }

    private void initShare(int id) {
        switch (id) {
            case 1://朋友圈
                ShareUtil
                        .getShareUtils()
                        .shareUtils(getActivity())
                        .setOnCallShare(this)
                        .setUmWeb(ShareUtil.getShareUtils().umWeb(shareBean), ShareParamete.WEIXIN_CIRCLE);
                break;

            case 2://微信
                ShareUtil
                        .getShareUtils()
                        .shareUtils(getActivity())
                        .setOnCallShare(this)
                        .setText(shareBean, ShareParamete.WEIXIN);
                break;

            case 3://好友
                ToastView.showToast(getActivity(), 0, getString(R.string.toast_view_in_development_msg));
                break;

            case 4://社群
                ToastView.showToast(getActivity(), 0, getString(R.string.toast_view_in_development_msg));
                break;
        }
    }

    private void initSite(int id) {
        switch (id) {
            case 1://举报
                mReportVideoListener.onReportVideo();
                break;
            case 2://不感兴趣
                mUninterestedVideoListener.onUninterestedVideo();
                break;

            case 3://删除
                deleteVideoListener.onDeleteVideo();
                break;
        }
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

    //删除视频回调
    public interface IDeleteVideoListener {
        void onDeleteVideo();
    }
    //举报视频回调
    public interface ReportVideoListener {
        void onReportVideo();
    }
    //不感兴趣回调
    public interface UninterestedVideoListener {
        void onUninterestedVideo();
    }
}
