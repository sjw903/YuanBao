package com.yuanbaogo.homevideo.shortvideo.comment;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.main.view.RecommendVideoFragment;
import com.yuanbaogo.homevideo.shortvideo.comment.adapter.CommentDialogListAdapter;
import com.yuanbaogo.homevideo.shortvideo.comment.model.CommentListBean;
import com.yuanbaogo.homevideo.shortvideo.comment.view.InputTextMsgDialog;
import com.yuanbaogo.homevideo.shortvideo.comment.view.SoftKeyBoardListener;
import com.yuanbaogo.homevideo.shortvideo.utils.RecyclerViewUtil;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 评论弹窗
 */
public class ShortVideoCommentDialog extends BottomSheetDialog implements CommentDialogListAdapter.IDelete {

    private Context mContext;

    private int offsetY;

    private RecyclerViewUtil mRecyclerViewUtil;

    public SoftKeyBoardListener mKeyBoardListener;

    private String mBusinessId;

    private InputTextMsgDialog inputTextMsgDialog;

    private RecyclerView mRecyclerView;
    private CommentDialogListAdapter mListAdapter;

    private RefreshLayout mRefreshLayout;

    private LinearLayout ll_empty;

    TextView tv_comment_title;

    private int allTotal = 0;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeleteSuccess();

        void onReplySuccess();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ShortVideoCommentDialog(@NonNull @NotNull Context context, String businessId) {
        super(context, R.style.dialog);
        mContext = context;
        mBusinessId = businessId;
        initSheetDialog();
        getCommentList(businessId, "");
    }

    //底部评论弹窗
    private void initSheetDialog() {
        mRecyclerViewUtil = new RecyclerViewUtil();
        View mContentView = View.inflate(mContext, R.layout.dialog_bottom_comment, null);
        mRefreshLayout = mContentView.findViewById(R.id.refreshLayout);
        ImageView iv_dialog_close = mContentView.findViewById(R.id.dialog_bottomsheet_iv_close);
        tv_comment_title = mContentView.findViewById(R.id.tv_comment_title);
        ll_empty = mContentView.findViewById(R.id.ll_empty);
        mRecyclerView = mContentView.findViewById(R.id.dialog_bottomsheet_rv_lists);
        RelativeLayout rl_comment = mContentView.findViewById(R.id.rl_comment);
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
                initInputTextMsgDialog(
                        null, "", null, null, null, "", null);
            }
        });

        mListAdapter = new CommentDialogListAdapter(mContext, mBusinessId);
        mListAdapter.setiDelete(this);
        mListAdapter.setOnItemClickListener(new CommentDialogListAdapter.OnItemClickListener() {
            @Override
            public void onReplyClick(String commentId, LinearLayout secondViewGroup,
                                     LinearLayout rv_hide, LinearLayout ll_open_group, String replyUser, Integer position) {
                //添加二级评论
                initInputTextMsgDialog(null, commentId, secondViewGroup, rv_hide, ll_open_group, replyUser, position);
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
                }
                allTotal += 1;
                tv_comment_title.setText(allTotal + "条评论");
                if (onItemClickListener != null)
                    onItemClickListener.onReplySuccess();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mListAdapter);
        setContentView(mContentView);
        setCanceledOnTouchOutside(true);
        if (mRecyclerViewUtil != null) mRecyclerViewUtil.initScrollListener(mRecyclerView);
        mKeyBoardListener = new SoftKeyBoardListener((Activity) mContext,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
                    }

                    @Override
                    public void keyBoardHide(int height) {
                        dismissInputDialog();
                    }
                });
    }

    private void initInputTextMsgDialog(View view, String commentId,
                                        LinearLayout secondViewGroup, LinearLayout rv_hide,
                                        LinearLayout ll_open_group, String replyUser, Integer position) {
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
                    mListAdapter.toReply(commentId, msg, secondViewGroup, rv_hide, ll_open_group,position);
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
                } else {
                    if (page == 1) {
                        ll_empty.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
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
        NetWork.getInstance().post(mContext, ChildUrl.clickcommentLike, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int var1, String var2) {
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        }, false);
    }

    @Override
    public void onDeleteItem(CommentListBean.RowsBean bean, LinearLayout secondViewGroup) {
        iDeleteDialog.onDeleteItem(bean, secondViewGroup);
    }

    public void deleteOneComment(CommentListBean.RowsBean bean, LinearLayout secondViewGroup) {
        mListAdapter.deleteOneComment(bean, secondViewGroup);
    }

    IDeleteDialog iDeleteDialog;

    public void setiDeleteDialog(IDeleteDialog iDeleteDialog) {
        this.iDeleteDialog = iDeleteDialog;
    }

    public interface IDeleteDialog {
        void onDeleteItem(CommentListBean.RowsBean bean, LinearLayout secondViewGroup);
    }

}
