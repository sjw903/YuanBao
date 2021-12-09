package com.yuanbaogo.shop.publics.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yuanbaogo.commonlib.utils.array.ArrayItemBean;
import com.yuanbaogo.commonlib.utils.array.ArrayTools;
import com.yuanbaogo.shop.R;
import com.yuanbaogo.shop.publics.adapter.ShopGroupJoiningZoneAdapter;
import com.yuanbaogo.zui.recycler.call.OnCallRecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 专区
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/22/21 10:52 AM
 * @Modifier:
 * @Modify:
 */
public class GroupJoiningZoneFragment extends Fragment implements OnCallRecyclerItem {

    View mView;

    RelativeLayout shopGroupJoiningZoneRl;

    ImageView shopGroupJoiningZoneImg;

    ImageView shopGroupJoiningZoneImg2;

    RecyclerView shopGroupJoiningZoneRecycler;

    int type;

    public void setType(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_grout_joining_zone_view, container, false);
        shopGroupJoiningZoneRl = mView.findViewById(R.id.shop_group_joining_zone_rl);
        shopGroupJoiningZoneImg = mView.findViewById(R.id.shop_group_joining_zone_img);
        shopGroupJoiningZoneImg2 = mView.findViewById(R.id.shop_group_joining_zone_img2);
        shopGroupJoiningZoneRecycler = mView.findViewById(R.id.shop_group_joining_zone_recycler);
        initData();
        if (type == 1) {
            if (shopGroupJoiningZoneRl != null) {
                shopGroupJoiningZoneRl.setBackground(
                        getResources().getDrawable(R.drawable.shape_bg_ed4d16_to_ffa365));
                shopGroupJoiningZoneImg.setVisibility(View.GONE);
                shopGroupJoiningZoneImg2.setVisibility(View.GONE);
            }
        }
        return mView;
    }

    private void initData() {
        List<ArrayItemBean> groupJoiningZoneArray = new ArrayList<>();
        for (ArrayTools.shopGroupJoiningZone airlineTypeEnum : ArrayTools.shopGroupJoiningZone.values()) {
            if (airlineTypeEnum.isShow()) {
                groupJoiningZoneArray.add(new ArrayItemBean()
                        .setId(airlineTypeEnum.getId())
                        .setName(airlineTypeEnum.getName())
                        .setIcon(airlineTypeEnum.getIcon())
                        .setVisibility(airlineTypeEnum.isShow()));
            }
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        shopGroupJoiningZoneRecycler.setLayoutManager(gridLayoutManager);
        ShopGroupJoiningZoneAdapter shopGroupJoiningZoneAdapter = new ShopGroupJoiningZoneAdapter(
                getActivity(), groupJoiningZoneArray, R.layout.item_shop_group_joining_zone);
        shopGroupJoiningZoneAdapter.setOnCallback(this);
        shopGroupJoiningZoneRecycler.setAdapter(shopGroupJoiningZoneAdapter);
    }

    @Override
    public void onCallItem(View view, int postion) {
        if (postion == 0) {
//            RouterHelper.toShopProductDetailsList(new SearchBean().setType(6));
        } else if (postion == 1) {
//            RouterHelper.toShopProductDetailsList(new SearchBean().setType(7));
        } else if (postion == 2) {
//            RouterHelper.toShopProductDetailsList(new SearchBean().setType(8));
        }
    }

}
