package com.yuanbaogo.zui.search;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yuanbaogo.datacenter.local.search.SearchSPUtils;
import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.search.call.OnCallHistory;
import com.yuanbaogo.zui.search.view.SearchLayout;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 历史搜索
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/14/21 9:37 AM
 * @Modifier:
 * @Modify:
 */
public class SearchHistoryFragment extends Fragment implements View.OnClickListener, OnCallDialog {

    View view;

    RelativeLayout searchHistoryRl;

    RelativeLayout searchHistoryRlHistory;

    /**
     * 删除
     */
    ImageView searchHistoryImgDelete;

    /**
     * 全部删除 |  完成
     */
    RelativeLayout searchHistoryRlDelete;

    /**
     * 完成
     */
    TextView searchHistoryTvCarryOut;

    /**
     * 全部删除
     */
    TextView searchHistoryTvDeleteAll;

    /**
     * 历史搜索
     */
    SearchLayout searchHistoryViewGroup;

    /**
     * 全部删除 二次确认弹窗
     */
    ConfirmDialogView confirmDialogView;

    /**
     * 历史搜索是否打开 默认未打开
     */
    boolean isOpenUp = false;

    /**
     * 默认显示3行 超过三行自行折叠
     */
    public int limitLineCount = 3;

    /**
     * 默认存储商城历史搜索
     */
    public String spName = SearchSPUtils.SEARCH_HISTORY_SHOP;

    OnCallHistory onCallHistory;

    public void setOnCallHistory(OnCallHistory onCallHistory) {
        this.onCallHistory = onCallHistory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_history, container, false);
        initView();
        initHistory(1);
        return view;
    }

    private void initView() {
        searchHistoryRl = view.findViewById(R.id.search_history_rl);
        searchHistoryRlHistory = view.findViewById(R.id.search_history_rl_history);
        searchHistoryImgDelete = view.findViewById(R.id.search_history_img_delete);
        searchHistoryRlDelete = view.findViewById(R.id.search_history_rl_delete);
        searchHistoryTvCarryOut = view.findViewById(R.id.search_history_tv_carry_out);
        searchHistoryTvDeleteAll = view.findViewById(R.id.search_history_tv_delete_all);
        searchHistoryViewGroup = view.findViewById(R.id.search_history_view_group);
        searchHistoryImgDelete.setOnClickListener(this);
        searchHistoryTvCarryOut.setOnClickListener(this);
        searchHistoryTvDeleteAll.setOnClickListener(this);
        searchHistoryViewGroup.setLimitLineCount(limitLineCount);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.search_history_img_delete) {//删除按钮
            if (!TextUtils.isEmpty(SearchSPUtils.getInstance(getActivity())
                    .getHistoryList(spName).get(0))) {
                searchHistoryRlDelete.setVisibility(View.VISIBLE);
                searchHistoryImgDelete.setVisibility(View.GONE);
                isOpenUp = true;
                initHistory(2);
            }
        } else if (id == R.id.search_history_tv_carry_out) {//完成
            searchHistoryRlDelete.setVisibility(View.GONE);
            searchHistoryImgDelete.setVisibility(View.VISIBLE);
            initHistory(1);
        } else if (id == R.id.search_history_tv_delete_all) {//删除全部
            confirmDialogView = new ConfirmDialogView("取消", "是否清空历史记录");
            confirmDialogView.show(getActivity().getSupportFragmentManager(), "search_history");
            confirmDialogView.setOnCallDialog(this);
        }
    }

    private LayoutInflater inflater;

    private List<View> mViewList = new ArrayList<>();

    /**
     * 历史记录列表
     */
    public void initHistory(int type) {
        final List<String> data = SearchSPUtils.getInstance(getActivity())
                .getHistoryList(spName);
        inflater = LayoutInflater.from(getActivity());
        mViewList.clear();
        for (int i = 0; i < data.size(); i++) {
            if (TextUtils.isEmpty(data.get(i))) {
                break;
            }
            TextView textView = (TextView) inflater.inflate(R.layout.item_search_history,
                    searchHistoryViewGroup, false);
            textView.setText(data.get(i));
            if (type == 2) {
                Drawable drawable = getResources().getDrawable(R.mipmap.icon_search_delete_item);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(null, null, drawable, null);
                textView.setCompoundDrawablePadding(8);
            }
            mViewList.add(textView);
        }
        searchHistoryViewGroup.setChildren(mViewList);

        searchHistoryViewGroup.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        searchHistoryViewGroup.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                        int lineCount = searchHistoryViewGroup.getLineCount();
                        int twoLineViewCount = searchHistoryViewGroup.getTwoLineViewCount();
                        if (lineCount > limitLineCount) {
                            if (!isOpenUp) {
                                initImageView2(data, twoLineViewCount, type);
                            } else {
                                initImageView(data, twoLineViewCount, type);
                            }
                        }
                    }
                });

        searchHistoryViewGroup.setOnTagClickListener(new SearchLayout.OnTagClickListener() {
            @Override
            public void onTagClick(View view, int position) {
                if (view.getId() == R.id.iv_condition) {
                    int twoLineViewCount = searchHistoryViewGroup.getTwoLineViewCount();
                    if (isOpenUp) {
                        initImageView2(data, twoLineViewCount, type);
                    } else {
                        initImageView(data, twoLineViewCount, type);
                    }
                    return;
                } else if (view.getId() == R.id.tv_condition) {
                    if (type == 2) {
                        SearchSPUtils.getInstance(getActivity())
                                .getHistoryDeleteItem(spName, data.get(position));
                        searchHistoryViewGroup.removeView(view);
                        return;
                    }
                    onCallHistory.onCallHistoryItem(view, data.get(position));
                }
            }
        });

    }

    private void initImageView(List<String> searchHistory, final int twoLineViewCount, int type) {
        isOpenUp = true;
        mViewList.clear();
        for (int i = 0; i < searchHistory.size(); i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_search_history,
                    searchHistoryViewGroup, false);
            textView.setText(searchHistory.get(i));
            if (type == 2) {
                Drawable drawable = getResources().getDrawable(R.mipmap.icon_search_delete_item);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(null, null, drawable, null);
                textView.setCompoundDrawablePadding(8);
            }
            mViewList.add(textView);
        }
        ImageView imageView = (ImageView) inflater.inflate(R.layout.item_search_history_img,
                searchHistoryViewGroup, false);
        imageView.setImageResource(R.mipmap.icon_search_down_close);
        mViewList.add(imageView);
        searchHistoryViewGroup.setChildren(mViewList);
    }

    private void initImageView2(List<String> searchHistory, final int twoLineViewCount, int type) {
        isOpenUp = false;
        mViewList.clear();
        for (int i = 0; i < twoLineViewCount; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_search_history,
                    searchHistoryViewGroup, false);
            textView.setText(searchHistory.get(i));
            if (type == 2) {
                Drawable drawable = getResources().getDrawable(R.mipmap.icon_search_delete_item);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(null, null, drawable, null);
                textView.setCompoundDrawablePadding(8);
            }
            mViewList.add(textView);
        }
        ImageView imageView = (ImageView) inflater.inflate(R.layout.item_search_history_img,
                searchHistoryViewGroup, false);
        imageView.setImageResource(R.mipmap.icon_search_down);
        mViewList.add(imageView);
        searchHistoryViewGroup.setChildren(mViewList);
        searchHistoryViewGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        searchHistoryViewGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int lineCount = searchHistoryViewGroup.getLineCount();
                        int twoLineViewCount = searchHistoryViewGroup.getTwoLineViewCount();
                        if (lineCount > limitLineCount) {
                            initImageView2(searchHistory, twoLineViewCount - 1, type);
                        }
                    }
                });
    }

    @Override
    public void onCallDetermine() {
        SearchSPUtils.getInstance(getActivity()).cleanHistory(spName);
        confirmDialogView.dismiss();
        initHistory(1);
        searchHistoryRlDelete.setVisibility(View.GONE);
        searchHistoryImgDelete.setVisibility(View.VISIBLE);
    }

}
