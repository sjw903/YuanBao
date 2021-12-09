package com.yuanbaogo.homevideo.shortvideo.comment.adapter;

/**
 * 功能：
 * 时间 ： 2021/8/20:.
 * 作者：11190
 */

//public class CommentDialogMutiAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
//
//    public CommentDialogMutiAdapter(List list) {
//        super(list);
//        addItemType(CommentEntity.TYPE_COMMENT_PARENT, R.layout.item_comment_new);
//        addItemType(CommentEntity.TYPE_COMMENT_CHILD, R.layout.item_comment_child_new);
//        addItemType(CommentEntity.TYPE_COMMENT_MORE, R.layout.item_comment_new_more);
//        addItemType(CommentEntity.TYPE_COMMENT_EMPTY, R.layout.item_comment_empty);
//    }
//
//    @Override
//    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
//        switch (item.getItemType()) {
//            case CommentEntity.TYPE_COMMENT_PARENT:
//                bindCommentParent(helper, (FirstLevelBean) item);
//                break;
//            case CommentEntity.TYPE_COMMENT_CHILD:
//                bindCommentChild(helper, (SecondLevelBean) item);
//                break;
//            case CommentEntity.TYPE_COMMENT_MORE:
//                bindCommonMore(helper, (CommentMoreBean) item);
//                break;
//            case CommentEntity.TYPE_COMMENT_EMPTY:
//                bindEmpty(helper, item);
//                break;
//        }
//    }
//
//    private void bindCommentParent(BaseViewHolder helper, FirstLevelBean item) {
//        RelativeLayout ll_like = helper.getView(R.id.ll_like);
//        RelativeLayout rl_group = helper.getView(R.id.rl_group);
//        RoundImageView iv_header = helper.getView(R.id.iv_header);
//        ImageView iv_like = helper.getView(R.id.iv_like);
//        TextView tv_like_count = helper.getView(R.id.tv_like_count);
//        TextView tv_content = helper.getView(R.id.tv_content);
//
//        ll_like.setOnClickListener(null);
//        ll_like.setTag(item.getItemType());
//        helper.addOnClickListener(R.id.ll_like);
//
//        rl_group.setTag(item.getItemType());
//        helper.addOnClickListener(R.id.rl_group);
//        iv_like.setImageResource(item.getIsLike() == 0 ? R.mipmap.icon_comment_like_unchioce : R.mipmap.icon_comment_like_chioce);
//        tv_like_count.setText(item.getLikeCount() + "");
//        tv_like_count.setVisibility(item.getLikeCount() <= 0 ? View.GONE : View.VISIBLE);
//
//        helper.setText(R.id.tv_time, DateUtils.getDateMonthDay(item.getCreateTime()));
//        helper.setText(R.id.tv_user_name, TextUtils.isEmpty(item.getUserName()) ? " " : item.getUserName());
//
//        String contents = TextUtils.isEmpty(item.getContent()) ? " " : item.getContent();
//        tv_content.setText(contents);
//
//        Glide.with(mContext).load(item.getHeadImg()).into(iv_header);
//
//    }
//
//    private void bindCommentChild(final BaseViewHolder helper, SecondLevelBean item) {
//        RelativeLayout ll_like = helper.getView(R.id.ll_like);
//        RelativeLayout rl_group = helper.getView(R.id.rl_group);
//        RoundImageView iv_header = helper.getView(R.id.iv_header);
//        TextView tv_content = helper.getView(R.id.tv_content);
//
//        ll_like.setOnClickListener(null);
//        ll_like.setTag(item.getItemType());
//        helper.addOnClickListener(R.id.ll_like);
//
//        rl_group.setTag(item.getItemType());
//        helper.addOnClickListener(R.id.rl_group);
//        Glide.with(mContext).load(item.getHeadImg()).into(iv_header);
//        final TextMovementMethods movementMethods = new TextMovementMethods();
//        if (item.getIsReply() == 0) {
//            tv_content.setText(item.getContent());
//            tv_content.setMovementMethod(null);
//        } else {
//            SpannableString stringBuilder = makeReplyCommentSpan(item.getReplyUserName(), item.getReplyUserId(), item.getContent());
//            tv_content.setText(stringBuilder);
//            tv_content.setMovementMethod(movementMethods);
//
//        }
//        tv_content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (movementMethods.isSpanClick()) return;
//                helper.itemView.performClick();
//            }
//        });
//
//
//        helper.setText(R.id.tv_time, DateUtils.getDateMonthDay(item.getCreateTime()) );
//        helper.setText(R.id.tv_user_name, TextUtils.isEmpty(item.getUserName()) ? " " : item.getUserName());
//
//
//    }
//
//    private void bindCommonMore(BaseViewHolder helper, CommentMoreBean item) {
//
//        LinearLayout ll = helper.getView(R.id.ll_group);
//        LinearLayout mRvHide = helper.getView(R.id.rv_hide);
//        if(item.isOpen()){
//            ll.setVisibility(View.INVISIBLE);
//            mRvHide.setVisibility(View.VISIBLE);
//        }else {
//            ll.setVisibility(View.VISIBLE);
//            mRvHide.setVisibility(View.INVISIBLE);
//        }
//        ll.setTag(item.getItemType());
//        helper.addOnClickListener(R.id.ll_group);
//        mRvHide.setTag(item.getItemType());
//        helper.addOnClickListener(R.id.rv_hide);
//    }
//
//    private void bindEmpty(BaseViewHolder helper, MultiItemEntity item) {
//
//    }
//
//    public SpannableString makeReplyCommentSpan(final String atSomeone, final String id, String commentContent) {
//        String richText = String.format("回复 %s : %s", atSomeone, commentContent);
//        SpannableString builder = new SpannableString(richText);
//        if (!TextUtils.isEmpty(atSomeone)) {
//            int childStart = 2;
//            int childEnd = childStart + atSomeone.length() + 1;
//            builder.setSpan(new TextClickSpans() {
//
//                @Override
//                public void onClick(@NonNull View widget) {
//                    Toast.makeText(mContext, atSomeone + " id: " + id, Toast.LENGTH_LONG).show();
//                }
//            }, childStart, childEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        return builder;
//    }

//}