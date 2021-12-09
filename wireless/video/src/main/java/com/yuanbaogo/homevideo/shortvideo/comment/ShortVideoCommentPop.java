package com.yuanbaogo.homevideo.shortvideo.comment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.shortvideo.comment.adapter.CommentDialogListAdapter;
import com.yuanbaogo.homevideo.shortvideo.comment.model.CommentListBean;
import com.yuanbaogo.homevideo.shortvideo.comment.view.InputTextMsgDialog;
import com.yuanbaogo.libbase.baseutil.ScreenUtils;
import com.yuanbaogo.zui.dialog.PopupWindowWithMask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ShortVideoCommentPop extends PopupWindowWithMask {

    private Context mContext;
    private InputTextMsgDialog inputTextMsgDialog;
    private RecyclerView mRecyclerView;
    private CommentDialogListAdapter mListAdapter;
    private LinearLayout ll_empty;
    private int allTotal = 0;

    private int offsetY;
    private RefreshLayout mRefreshLayout;
    TextView tv_comment_title;
    private String mBusinessId;

    private View mContentView;
//    private LinearLayout ll_no_more_loaded;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeleteSuccess();

        void onReplySuccess();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ShortVideoCommentPop(Context context, String businessId) {
        super(context, false);
        mBusinessId = businessId;
        mContext = context;
        initSheetDialog();
    }

    private void initSheetDialog() {
        mRefreshLayout = (RefreshLayout) mContentView.findViewById(R.id.refreshLayout);
        ImageView iv_dialog_close = (ImageView) mContentView.findViewById(R.id.dialog_bottomsheet_iv_close);
        tv_comment_title = (TextView) mContentView.findViewById(R.id.tv_comment_title);
        ll_empty = (LinearLayout) mContentView.findViewById(R.id.ll_empty);
        mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.dialog_bottomsheet_rv_lists);
        RelativeLayout rl_comment = mContentView.findViewById(R.id.rl_comment);
//        ll_no_more_loaded = mContentView.findViewById(R.id.ll_no_more_loaded);
        iv_dialog_close.setOnClickListener(v -> dismiss());
        mRefreshLayout.setEnableRefresh(false)
                .setEnablePureScrollMode(false)
                .setFooterHeight(47)
                .setEnableLoadMore(true)
                .setHeaderHeight(47)
                .setEnableClipHeaderWhenFixedBehind(true);

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                page = page + 1;
                getCommentList(mBusinessId, "");
            }
        });

        rl_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserStore.isLogin()) {
                    RouterHelper.toLogin();
                    return;
                }
                //添加一级评论
                initInputTextMsgDialog(null, "", null, null, null, "");
            }
        });

        mListAdapter = new CommentDialogListAdapter(mContext, mBusinessId);
        mListAdapter.setOnItemClickListener(new CommentDialogListAdapter.OnItemClickListener() {
            @Override
            public void onReplyClick(String commentId, LinearLayout secondViewGroup,
                                     LinearLayout rv_hide, LinearLayout ll_open_group, String replyUser, Integer position) {
                //添加二级评论
                initInputTextMsgDialog(null, commentId, secondViewGroup, rv_hide, ll_open_group, replyUser);
            }

            @Override
            public void onlikeClick(String likeId, String likeState) {
                clickLike(likeState, likeId);
            }

            @Override
            public void onDeleteSuccess() {
                allTotal -= 1;
                tv_comment_title.setText(allTotal + "条评论");
                if (onItemClickListener != null)
                    onItemClickListener.onDeleteSuccess();
            }

            @Override
            public void onReplySuccess() {
                if (allTotal == 0) {
                    ll_empty.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
//                    ll_no_more_loaded.setVisibility(View.VISIBLE);
                }
                allTotal += 1;
                tv_comment_title.setText(allTotal + "条评论");
                if (onItemClickListener != null)
                    onItemClickListener.onReplySuccess();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mListAdapter);

    }

    private void initInputTextMsgDialog(View view, String commentId, LinearLayout secondViewGroup, LinearLayout rv_hide, LinearLayout ll_open_group, String replyUser) {
        dismissInputDialog();
        if (view != null) {
            this.offsetY = view.getTop();
            scrollLocation(offsetY);
        }
        if (inputTextMsgDialog == null) {
            inputTextMsgDialog = new InputTextMsgDialog(mContext, R.style.dialog);
            if (!replyUser.isEmpty()) {
                inputTextMsgDialog.setText("@" + replyUser);
            }
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    mListAdapter.toReply(commentId, msg, secondViewGroup, rv_hide, ll_open_group, null);
//                    addComment(isReply, item, position, msg);
                }

                @Override
                public void dismiss() {
                    //item滑动到原位
                    scrollLocation(-offsetY);
                }
            });
        }
        showInputTextMsgDialog();
    }

    private void dismissInputDialog() {
        if (inputTextMsgDialog != null) {
            if (inputTextMsgDialog.isShowing()) inputTextMsgDialog.dismiss();
            inputTextMsgDialog.cancel();
            inputTextMsgDialog = null;
        }
    }

    private void showInputTextMsgDialog() {
        inputTextMsgDialog.show();
    }

    // item滑动
    public void scrollLocation(int offsetY) {
        try {
            mRecyclerView.smoothScrollBy(0, offsetY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int page = 1;
    int size = 20;

    public void getCommentList(String businessId, String pid) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("size", size + "");
        params.put("businessId", businessId);
        params.put("pid", pid);
        NetWork.getInstance().get(mContext, ChildUrl.commentlist, params, new RequestSateListener<CommentListBean>() {

            @Override
            public void onSuccess(int var1, CommentListBean bean) {
                if (bean != null && bean.getRows() != null && bean.getRows().size() > 0) {
                    allTotal = bean.getAllTotal();
                    tv_comment_title.setText(bean.getAllTotal() + "条评论");
                    mListAdapter.addData(bean.getRows());
                    mRefreshLayout.finishLoadMore();
//                    ll_no_more_loaded.setVisibility(View.VISIBLE);
                } else {
                    if (page == 1) {
                        ll_empty.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
//                        ll_no_more_loaded.setVisibility(View.GONE);
                    } else {
                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    public void clickLike(String likeState, String videoId) {
        Map<String, String> params = new HashMap<>();
        params.put("likeState", likeState);
        params.put("likeId", videoId);
        NetWork.getInstance().post(context, ChildUrl.clickcommentLike, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int var1, String var2) {
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        }, false);
    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_comment, null, false);
        return mContentView;
    }

    @Override
    protected int initHeight() {
        int screenHeight = ScreenUtils.getScreenHeight(context);
        return screenHeight / 2 + screenHeight / 8;
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    public void show() {
        page = 1;
        showAtLocation(((Activity) mContext).getWindow().getDecorView(),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        getCommentList(mBusinessId, "");

    }
}
