package com.yuanbaogo.zui.head;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.head.call.OnCallHeadBack;
import com.yuanbaogo.zui.textview.ADTextView;

/**
 * @Description: 导航栏显示
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/3/21 12:58 PM
 * @Modifier:
 * @Modify:
 */
public class HeadView extends RelativeLayout implements View.OnClickListener {

    View view;

    Activity activity;

    /**
     * 父控件
     */
    RelativeLayout libHeadRlTopNavigaBar;

    /**
     * 搜索框
     */
    RelativeLayout libHeadRlSearch;

    /**
     * 搜索图标
     */
    ImageView libHeadImgSearch;

    /**
     * 搜索内容推荐 -  上下轮播
     */
    ADTextView libHeadTvSearch;

    /**
     * 搜索内容
     */
    EditText libHeadEditSearch;

    /**
     * 左上角按钮
     */
    ImageView libHeadImgLeft;

    /**
     * 左上角标题
     */
    TextView libHeadTvLeftTitle;

    /**
     * 中间标题
     */
    TextView libHeadTvTitle;

    /**
     * 右上角按钮( 最右边 )
     */
    ImageView libHeadImgRight;

    /**
     * 右上角按钮 - 依附于最右边的按钮 (右边 )
     */
    ImageView libHeadImgToleft;

    /**
     * 右上角按钮 - 依附于右边的按钮
     */
    ImageView libHeadImgToleft2;

    /**
     * 返回按钮回调
     */
    OnCallHeadBack onCallHeadBack;

    /**
     * 搜索内容列表
     */
    RecyclerView libHeadRecyclerSearch;

    TextView libHeadTvRight;

    public void setOnCallHeadBack(OnCallHeadBack onCallHeadBack) {
        this.onCallHeadBack = onCallHeadBack;
    }

    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        activity = (Activity) context;
        initView(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.include_layout_head, this);
        libHeadRlTopNavigaBar = view.findViewById(R.id.lib_head_rl_top_naviga_bar);
        libHeadRlSearch = view.findViewById(R.id.lib_head_rl_search);
        libHeadImgSearch = view.findViewById(R.id.lib_head_img_search);
        libHeadTvSearch = view.findViewById(R.id.lib_head_tv_search);
        libHeadEditSearch = view.findViewById(R.id.lib_head_edit_search);
        libHeadImgLeft = view.findViewById(R.id.lib_head_img_left);
        libHeadTvLeftTitle = view.findViewById(R.id.lib_head_tv_left_title);
        libHeadTvTitle = view.findViewById(R.id.lib_head_tv_title);
        libHeadImgRight = view.findViewById(R.id.lib_head_img_right);
        libHeadImgToleft = view.findViewById(R.id.lib_head_img_toleft);
        libHeadImgToleft2 = view.findViewById(R.id.lib_head_img_toleft2);
        libHeadRecyclerSearch = view.findViewById(R.id.lib_head_recycler_search);
        libHeadTvRight = view.findViewById(R.id.lib_head_tv_right);

        libHeadImgLeft.setOnClickListener(this);//右上角按钮

        //动态设置标题栏高度
        ViewGroup.LayoutParams params = libHeadRlTopNavigaBar.getLayoutParams();
        params.height = getStatusBarHeight(activity) + 140;
        libHeadRlTopNavigaBar.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lib_head_img_left) {
            if (onCallHeadBack != null) {//如果有其他操作 回调到onClickBack中
                onCallHeadBack.onClickBack();
            } else {//直接关闭当前页面
                activity.finish();
            }
        }
    }

    /**
     * 设置页面显示
     *
     * @param headBean
     */
    public void setHead(HeadBean headBean) {
        //导航栏背景颜色
        if (headBean.getRlTopNavigaBarBg() != null) {
            libHeadRlTopNavigaBar.setBackgroundColor(getResources().getColor(headBean.getRlTopNavigaBarBg()));
        }

        //搜索框是否显示
        libHeadRlSearch.setVisibility(headBean.isRlSearch() ? VISIBLE : GONE);

        //搜索框颜色
        if (headBean.getRlSearchBg() != null) {
            libHeadRlSearch.setBackground(getResources().getDrawable(headBean.getRlSearchBg()));
        }

        //搜索图标是否显示
        libHeadImgSearch.setVisibility(headBean.isTvSearch() ? VISIBLE : GONE);

        //推荐搜索内容是否显示（上下滚动 自定义TextSwitcher）
        libHeadTvSearch.setVisibility(headBean.isTvSearch() ? VISIBLE : GONE);

        //搜索框输入框是否显示
        libHeadEditSearch.setVisibility(headBean.isEditSearch() ? VISIBLE : GONE);

        //左边按钮是否显示
        libHeadImgLeft.setVisibility(headBean.isImgLeft() ? VISIBLE : GONE);

        //设置左边按钮图标
        if (headBean.getImgLeftIcon() != null) {
            libHeadImgLeft.setImageDrawable(getResources().getDrawable(headBean.getImgLeftIcon()));
        }

        //设置左边按钮背景颜色
        if (headBean.getImgLeftIconBg() != null) {
            libHeadImgLeft.setBackground(getResources().getDrawable(headBean.getImgLeftIconBg()));
        } else {
            libHeadImgLeft.setBackground(null);
        }

        //左边标题是否显示
        libHeadTvLeftTitle.setVisibility(headBean.isTvLeftTitle() ? VISIBLE : GONE);

        //设置左边标题
        if (headBean.getTvLeftTitles() != null) {
            libHeadTvLeftTitle.setText(headBean.getTvLeftTitles());
        }

        //中间标题是否显示
        libHeadTvTitle.setVisibility(headBean.isTvCenterTitle() ? VISIBLE : GONE);

        //设置中间标题内容
        if (headBean.getTvCenterTitles() != null) {
            libHeadTvTitle.setText(headBean.getTvCenterTitles());
        }

        //右边按钮是否显示
        libHeadImgRight.setVisibility(headBean.isImgRight() ? VISIBLE : GONE);

        //设置右边按钮图标
        if (headBean.getImgRightIcon() != null) {
            libHeadImgRight.setImageDrawable(getResources().getDrawable(headBean.getImgRightIcon()));
        }

        //设置右边按钮背景颜色
        if (headBean.getImgRightIconBg() != null) {
            libHeadImgRight.setBackground(getResources().getDrawable(headBean.getImgRightIconBg()));
        } else {
            libHeadImgRight.setBackground(null);
        }

        //右边按钮（第二个）是否显示
        libHeadImgToleft.setVisibility(headBean.isImgToleft() ? VISIBLE : GONE);

        //设置右边按钮（第二个）图标
        if (headBean.getImgToleftIcon() != null) {
            libHeadImgToleft.setImageDrawable(getResources().getDrawable(headBean.getImgToleftIcon()));
        }

        //设置右边按钮（第二个）背景颜色
        if (headBean.getImgToleftIconBg() != null) {
            libHeadImgToleft.setBackground(getResources().getDrawable(headBean.getImgToleftIconBg()));
        } else {
            libHeadImgToleft.setBackground(null);
        }

        //设置右边按钮（第三个）是否显示
        libHeadImgToleft2.setVisibility(headBean.isImgToleft2() ? VISIBLE : GONE);

        //设置右边按钮（第三个）图标
        if (headBean.getImgToleftIcon2() != null) {
            libHeadImgToleft2.setImageDrawable(getResources().getDrawable(headBean.getImgToleftIcon2()));
        }

        //设置右边按钮（第三个）背景颜色
        if (headBean.getImgToleftIconBg2() != null) {
            libHeadImgToleft2.setBackground(getResources().getDrawable(headBean.getImgToleftIconBg2()));
        } else {
            libHeadImgToleft2.setBackground(null);
        }

        //搜索框显示范围（右边第二个、第三个按钮显示）
        if (libHeadImgToleft2.isShown() || libHeadImgToleft.isShown() && libHeadRlSearch.isShown()) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) libHeadRlSearch.getLayoutParams();
            params.addRule(RelativeLayout.LEFT_OF, libHeadImgToleft.isShown() ? R.id.lib_head_img_toleft : R.id.lib_head_img_toleft2);
        }

    }

    /**
     * 导航栏背景
     *
     * @return
     */
    public RelativeLayout getLibHeadRlTopNavigaBar() {
        return libHeadRlTopNavigaBar;
    }

    /**
     * 搜索框背景
     *
     * @return
     */
    public RelativeLayout getLibHeadRlSearch() {
        return libHeadRlSearch;
    }

    /**
     * 输入框
     *
     * @return
     */
    public EditText getLibHeadEditSearch() {
        return libHeadEditSearch;
    }

    /**
     * 左上角按钮按钮
     *
     * @return
     */
    public ImageView getLibHeadImgLeft() {
        return libHeadImgLeft;
    }

    /**
     * 左上角标题
     *
     * @return
     */
    public TextView getLibHeadTvLeftTitle() {
        return libHeadTvLeftTitle;
    }

    /**
     * 右上角按钮
     *
     * @return
     */
    public ImageView getLibHeadImgRight() {
        return libHeadImgRight;
    }

    /**
     * 右上角按钮 - 依附于最右边的按钮
     *
     * @return
     */
    public ImageView getLibHeadImgToleft() {
        return libHeadImgToleft;
    }

    /**
     * 搜索 - 广告滚动
     *
     * @return
     */
    public ADTextView getLibHeadTvSearch() {
        return libHeadTvSearch;
    }

    /**
     * 搜索列表
     *
     * @return
     */
    public RecyclerView getLibHeadRecyclerSearch() {
        return libHeadRecyclerSearch;
    }

    /**
     * 右上角文字
     *
     * @return
     */
    public TextView getLibHeadTvRight() {
        return libHeadTvRight;
    }

    /**
     * 中间标题
     *
     * @return
     */
    public TextView getLibHeadTvTitle() {
        return libHeadTvTitle;
    }

    /**
     * 获取导航栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

}
