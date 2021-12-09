package com.yuanbaogo.homevideo.shortvideo.comment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.homevideo.shortvideo.comment.model.CommentListBean;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.RoundImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDialogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CommentListBean.RowsBean> mList;
    private String mBusinessId;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onReplyClick(String commentId, LinearLayout secondViewGroup,
                          LinearLayout rv_hide, LinearLayout ll_open_group, String replyUser, Integer position);

        void onlikeClick(String likeId, String likeState);

        void onDeleteSuccess();

        void onReplySuccess();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommentDialogListAdapter(Context context, String businessId) {
        this.context = context;
        mBusinessId = businessId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_comment_new, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        ItemViewHolder holder = (ItemViewHolder) h;
        final CommentListBean.RowsBean bean = mList.get(position);
        if (bean == null) {
            return;
        }

        CommentListBean.RowsBean.CommentUserBean commentUserBean = bean.getCommentUser();
        if (commentUserBean != null) {
            Glide.with(context)
                    .load(commentUserBean.getPortraitUrl())
                    .thumbnail(0.1f).into(holder.iv_header);
            holder.tv_user_name.setText(commentUserBean.getNickName());
        }
        holder.tv_content.setText(bean.getContent());
        holder.tv_time.setText(bean.getCreateTimeStr());
        holder.tv_like_count.setText(bean.getLikeCount() + "");
        if (bean.isHaveLike()) {
            holder.iv_like.setImageResource(R.mipmap.icon_comment_like_chioce);
        } else {
            holder.iv_like.setImageResource(R.mipmap.icon_comment_like_unchioce);
        }
        if (bean.isCanDelete()) {
            holder.tv_delete.setVisibility(View.VISIBLE);
        } else {
            holder.tv_delete.setVisibility(View.GONE);
        }
        if (bean.getReplyCount() > 0) {
            holder.rl_group_more.setVisibility(View.VISIBLE);
            holder.tv_more.setText("展开" + bean.getReplyCount() + "条回复");
            holder.rv_hide.setVisibility(View.GONE);
        } else {
            holder.rl_group_more.setVisibility(View.GONE);
        }

        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserStore.isLogin()) {
                    if (bean.isHaveLike()) {
                        holder.iv_like.setImageResource(R.mipmap.icon_comment_like_unchioce);
                        bean.setHaveLike(false);
                        bean.setLikeCount(bean.getLikeCount() - 1);
                        holder.tv_like_count.setText(bean.getLikeCount() + "");
                        if (onItemClickListener != null)
                            onItemClickListener.onlikeClick(bean.getId(), "0");
                    } else {
                        bean.setHaveLike(true);
                        bean.setLikeCount(bean.getLikeCount() + 1);
                        holder.iv_like.setImageResource(R.mipmap.icon_comment_like_chioce);
                        holder.tv_like_count.setText(bean.getLikeCount() + "");
                        if (onItemClickListener != null)
                            onItemClickListener.onlikeClick(bean.getId(), "1");
                    }
                } else {
                    RouterHelper.toLogin();
                }
            }
        });
        holder.rv_lists_second.setTag(new ArrayList<>());
        //展开
        holder.ll_open_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCommentList(holder.rv_lists_second, bean.getId(), holder.rv_hide, holder.ll_open_group);
            }
        });
        //收起
        holder.rv_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.rv_hide.setVisibility(View.GONE);
                holder.ll_open_group.setVisibility(View.VISIBLE);
                holder.rv_lists_second.setVisibility(View.GONE);
            }
        });
        //回复
        holder.tv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserStore.isLogin()) {
                    RouterHelper.toLogin();
                    return;
                }
                if (onItemClickListener != null)
                    onItemClickListener.onReplyClick(bean.getId(), holder.rv_lists_second,
                            holder.rv_hide, holder.ll_open_group, commentUserBean.getNickName(), null);
            }
        });
        //删除
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDelete.onDeleteItem(bean, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = mList == null ? 0 : mList.size();
        return count;
    }

    public void addData(List<CommentListBean.RowsBean> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.addAll(list);
        notifyDataSetChanged();
//        notifyItemInserted(mList.size());//通知插入了数据
    }

    public void insertData(CommentListBean.RowsBean bean) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.add(0, bean);
        notifyItemInserted(0);//通知插入了数据
    }

    public void insertSecondData(LinearLayout secondViewGroup, LinearLayout rv_hide, LinearLayout ll_open_group) {
        if (rv_hide != null) {
            rv_hide.setVisibility(View.VISIBLE);
        }
        if (rv_hide != null) {
            ll_open_group.setVisibility(View.GONE);
        }
        secondViewGroup.setVisibility(View.VISIBLE);
        addSecondView(secondViewGroup);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        RoundImageView iv_header;
        ImageView iv_like;
        TextView tv_like_count;
        TextView tv_content;
        TextView tv_user_name;
        TextView tv_reply;
        TextView tv_time;
        TextView tv_delete;

        TextView tv_more;
        RelativeLayout rl_group_more;
        LinearLayout ll_open_group;
        LinearLayout rv_hide;
        LinearLayout rv_lists_second;

        public ItemViewHolder(View itemView) {
            super(itemView);

            iv_header = itemView.findViewById(R.id.iv_header);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_like_count = itemView.findViewById(R.id.tv_like_count);
            iv_like = itemView.findViewById(R.id.iv_like);
            tv_reply = itemView.findViewById(R.id.tv_reply);

            rl_group_more = itemView.findViewById(R.id.rl_group_more);
            ll_open_group = itemView.findViewById(R.id.ll_open_group);
            tv_more = itemView.findViewById(R.id.tv_more);
            rv_hide = itemView.findViewById(R.id.rv_hide);

            rv_lists_second = itemView.findViewById(R.id.rv_lists_second);
        }
    }

    private void addSecondView(LinearLayout secondViewGroup) {
        secondViewGroup.removeAllViews();
        List<CommentListBean.RowsBean> secondList = (List<CommentListBean.RowsBean>) secondViewGroup.getTag();
        for (CommentListBean.RowsBean bean : secondList) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_comment_child_new, null, false);
            RoundImageView iv_header = itemView.findViewById(R.id.iv_header);
            TextView tv_user_name = itemView.findViewById(R.id.tv_user_name);
            TextView tv_content = itemView.findViewById(R.id.tv_content);
            TextView tv_delete = itemView.findViewById(R.id.tv_delete);
            TextView tv_time = itemView.findViewById(R.id.tv_time);
            TextView tv_like_count = itemView.findViewById(R.id.tv_like_count);
            ImageView iv_like = itemView.findViewById(R.id.iv_like);
            TextView tv_reply = itemView.findViewById(R.id.tv_reply);

            CommentListBean.RowsBean.CommentUserBean commentUserBean = bean.getCommentUser();
            if (commentUserBean != null) {
                Glide.with(context)
                        .load(commentUserBean.getPortraitUrl())
                        .thumbnail(0.1f).into(iv_header);
                tv_user_name.setText(commentUserBean.getNickName());
            }
            if (bean.isHaveToUser()) {
                if (bean.getToUser() != null) {
                    tv_content.setText("回复 " + bean.getToUser().getNickName() + ": " + bean.getContent());
                }
            } else {
                tv_content.setText(bean.getContent());
            }

            tv_time.setText(bean.getCreateTimeStr());
            tv_like_count.setText(bean.getLikeCount() + "");
            if (bean.isHaveLike()) {
                iv_like.setImageResource(R.mipmap.icon_comment_like_chioce);
            } else {
                iv_like.setImageResource(R.mipmap.icon_comment_like_unchioce);
            }
            iv_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UserStore.isLogin()) {
                        if (bean.isHaveLike()) {
                            iv_like.setImageResource(R.mipmap.icon_comment_like_unchioce);
                            bean.setHaveLike(false);
                            bean.setLikeCount(bean.getLikeCount() - 1);
                            tv_like_count.setText(bean.getLikeCount() + "");
                            if (onItemClickListener != null)
                                onItemClickListener.onlikeClick(bean.getId(), "0");
                        } else {
                            bean.setHaveLike(true);
                            iv_like.setImageResource(R.mipmap.icon_comment_like_chioce);
                            bean.setLikeCount(bean.getLikeCount() + 1);
                            tv_like_count.setText(bean.getLikeCount() + "");
                            if (onItemClickListener != null)
                                onItemClickListener.onlikeClick(bean.getId(), "1");
                        }
                    } else {
                        RouterHelper.toLogin();
                    }
                }
            });
            if (bean.isCanDelete()) {
                tv_delete.setVisibility(View.VISIBLE);
            } else {
                tv_delete.setVisibility(View.GONE);
            }
            tv_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null)
                        onItemClickListener.onReplyClick(bean.getId(), secondViewGroup,
                                null, null, commentUserBean.getNickName(),
                                0);
                }
            });
            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iDelete.onDeleteItem(bean, secondViewGroup);
                }
            });
            secondViewGroup.addView(itemView);
        }
    }

    public void getCommentList(LinearLayout secondViewGroup, String pid, LinearLayout rv_hide, LinearLayout ll_open_group) {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("size", "500");
        params.put("businessId", mBusinessId);
        params.put("pid", pid);
        NetWork.getInstance().get(context, ChildUrl.commentlist, params, new RequestSateListener<CommentListBean>() {

            @Override
            public void onSuccess(int var1, CommentListBean bean) {
                if (bean != null && bean.getRows() != null && bean.getRows().size() > 0) {
                    rv_hide.setVisibility(View.VISIBLE);
                    ll_open_group.setVisibility(View.GONE);
                    secondViewGroup.setVisibility(View.VISIBLE);
                    secondViewGroup.setTag(bean.getRows());
                    addSecondView(secondViewGroup);
                }
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        });
    }

    public void deleteOneComment(CommentListBean.RowsBean beanComment, LinearLayout secondViewGroup) {
        NetWork.getInstance().post(context, ChildUrl.deletecomment.replace("{commentId}", beanComment.getId()),
                null, new RequestSateListener<String>() {
                    @Override
                    public void onSuccess(int var1, String bean) {
                        if (secondViewGroup != null) {
                            List<CommentListBean.RowsBean> secondList = (List<CommentListBean.RowsBean>) secondViewGroup.getTag();
                            secondList.remove(beanComment);
                            addSecondView(secondViewGroup);
                        } else {
                            mList.remove(beanComment);
                            notifyDataSetChanged();
                        }
                        if (onItemClickListener != null)
                            onItemClickListener.onDeleteSuccess();
                        ToastView.showToast("删除成功");
                    }

                    @Override
                    public void onFailure(Throwable var1) {

                    }
                });
    }

    public void toReply(String commentId, String commentContent,
                        LinearLayout secondViewGroup, LinearLayout rv_hide, LinearLayout ll_open_group, Integer position) {
        Map<String, String> params = new HashMap<>();
        params.put("commentId", commentId);
        params.put("businessId", mBusinessId);
        params.put("commentContent", commentContent);
        NetWork.getInstance().post(context, ChildUrl.vodcomment, params, new RequestSateListener<CommentListBean.RowsBean>() {
            @Override
            public void onSuccess(int var1, CommentListBean.RowsBean bean) {
                if (secondViewGroup == null) {
                    insertData(bean);
                } else {//添加二级评论
                    List<CommentListBean.RowsBean> secondList = (List<CommentListBean.RowsBean>) secondViewGroup.getTag();
                    //TODO HG 二级评论添加
//                    if (secondList.size() == 0) {
                    secondList.add(0, bean);
//                    } else {
//                        secondList.add(position + 1, bean);
//                    }
                    insertSecondData(secondViewGroup, rv_hide, ll_open_group);
                }
                if (onItemClickListener != null)
                    onItemClickListener.onReplySuccess();
            }

            @Override
            public void onFailure(Throwable var1) {

            }
        });
    }

    IDelete iDelete;

    public void setiDelete(IDelete iDelete) {
        this.iDelete = iDelete;
    }

    public interface IDelete {
        /**
         * 删除评论
         *
         * @param bean
         * @param secondViewGroup
         */
        void onDeleteItem(CommentListBean.RowsBean bean, LinearLayout secondViewGroup);
    }

}