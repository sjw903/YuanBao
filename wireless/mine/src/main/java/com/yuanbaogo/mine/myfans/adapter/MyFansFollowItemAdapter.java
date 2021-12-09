package com.yuanbaogo.mine.myfans.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.myfans.model.MyFansFollowItem;
import com.yuanbaogo.zui.recycler.adapter.BaseRecycleAdapter;
import com.yuanbaogo.zui.recycler.viewholder.BaseViewHolder;

import java.util.List;

public class MyFansFollowItemAdapter extends BaseRecycleAdapter<MyFansFollowItem> implements View.OnClickListener {
    private MyFansFollowItem myFansFollowItem;
    private ImageView myFansHeadIv;//粉丝头像
    private TextView myFansUserNameTv;//粉丝用户名
    private TextView myFansIntroduceTv;//粉丝签名
    private ImageView myFansStateIv;//关注粉丝状态
    private LinearLayout myFansInformationLl;

    private String ybCode;//用户ID

    public MyFansFollowItemAdapter(Context context, List<MyFansFollowItem> mDataList) {
        super(context, mDataList, 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_my_fans_item;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        initView(holder);
        myFansFollowItem = getDataList().get(position);
        Glide.with(mContext)
                .load(myFansFollowItem.getPortraitUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(myFansHeadIv);
        myFansUserNameTv.setText(myFansFollowItem.getNickName());
        myFansIntroduceTv.setText(myFansFollowItem.getSignature());
        if (myFansFollowItem.getFansState().equals("0")){//0:陌生人，显示关注
            Glide.with(mContext)
                    .load(R.mipmap.icon_follow)
                    .into(myFansStateIv);
        }else if (myFansFollowItem.getFansState().equals("1")){//1:关注，显示已关注
            Glide.with(mContext)
                    .load(R.mipmap.icon_followed)
                    .into(myFansStateIv);
        }else if (myFansFollowItem.getFansState().equals("2")) {//2:被关注，显示回粉
            Glide.with(mContext)
                    .load(R.mipmap.icon_return_follow)
                    .into(myFansStateIv);
        } else if (myFansFollowItem.getFansState().equals("3")) {//3：相互关注，显示相互关注
            Glide.with(mContext)
                    .load(R.mipmap.icon_mutual_attention)
                    .into(myFansStateIv);
        }
        ybCode = myFansFollowItem.getYbCode();
        myFansStateIv.setTag(R.id.my_fans_state_iv,position);
        myFansHeadIv.setTag(R.id.my_fans_head_iv,position);
        myFansInformationLl.setTag(R.id.my_fans_information_ll,position);
    }

    private void initView(BaseViewHolder holder) {
        myFansHeadIv = holder.getView(R.id.my_fans_head_iv);
        myFansUserNameTv = holder.getView(R.id.my_fans_user_name_tv);
        myFansIntroduceTv = holder.getView(R.id.my_fans_introduce_tv);
        myFansStateIv = holder.getView(R.id.my_fans_state_iv);
        myFansInformationLl = holder.getView(R.id.my_fans_information_ll);

        myFansStateIv.setOnClickListener(this);
        myFansHeadIv.setOnClickListener(this);
        myFansInformationLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.my_fans_state_iv) {
            if (getDataList().get((int)view.getTag(R.id.my_fans_state_iv)).getFansState().equals("0")) {//0：陌生人，显示关注
                onCall.onClickImg("0", getDataList().get((int)view.getTag(R.id.my_fans_state_iv)).getYbCode(), (int)view.getTag(R.id.my_fans_state_iv));//点击实现关注对方
            } else if (getDataList().get((int)view.getTag(R.id.my_fans_state_iv)).getFansState().equals("1")) {//1：关注，显示已关注
                onCall.onClickImg("1", getDataList().get((int)view.getTag(R.id.my_fans_state_iv)).getYbCode(), (int)view.getTag(R.id.my_fans_state_iv));//点击显示弹框，是否取关
            } else if (getDataList().get((int)view.getTag(R.id.my_fans_state_iv)).getFansState().equals("2")) {//2:被关注，显示回粉
                onCall.onClickImg("2", getDataList().get((int)view.getTag(R.id.my_fans_state_iv)).getYbCode(), (int)view.getTag(R.id.my_fans_state_iv));//点击实现回粉，关注对方
            } else if (getDataList().get((int)view.getTag(R.id.my_fans_state_iv)).getFansState().equals("3")) {//3：相互关注，显示相互关注
                onCall.onClickImg("3", getDataList().get((int)view.getTag(R.id.my_fans_state_iv)).getYbCode(), (int)view.getTag(R.id.my_fans_state_iv));//点击显示弹框，是否取关
            }
        }else if (id == R.id.my_fans_head_iv ){//点击其他人的信息跳转他的个人主页
            RouterHelper.toMineActivity(getDataList().get((int)view.getTag(R.id.my_fans_head_iv)).getYbCode());
        } else if (id == R.id.my_fans_information_ll) {
            RouterHelper.toMineActivity(getDataList().get((int)view.getTag(R.id.my_fans_information_ll)).getYbCode());
        }
    }

    FollowStateOnCall onCall;

    public void setOnCall(FollowStateOnCall onCall) {
        this.onCall = onCall;
    }

    public interface FollowStateOnCall {
        void onClickImg(String fansState, String followerYbCode, int position);
    }


}
