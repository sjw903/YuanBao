package com.yuanbaogo.zui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowLayout extends ViewGroup {
    private int mHorizontalSpacing = dp2px(16); //每个item横向间距
    private int mVerticalSpacing = dp2px(8); //每个item横向间距


    private List<List<View>> allLines = new ArrayList<>(); // 记录所有的行，一行一行的存储，用于layout
    List<Integer> lineHeights = new ArrayList<>(); // 记录每一行的行高，用于layout


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void clearMeasureParams() {
        allLines.clear();
        lineHeights.clear();
    }


    /*
     * 1、度量子view
     * 2、获取子view的宽、高、换行等
     * 3、向父类索要宽高，判断是哪种MeasureSpecMode,根据不同的mode给出不同的区域
     * 4、保存记录
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        clearMeasureParams();

        List<View> lineViews = new ArrayList<>(); //保存一行中的所有的view
        int lineWidthUsed = 0; //记录这行已经使用了多宽的size
        int lineHeight = 0; // 一行的行高

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);  //父view给我的宽度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec); //父view给我的高度

        int flowLayoutNeedWidth = 0;  // measure过程中，FlowLayout要求的父ViewGroup的宽
        int flowLayoutNeedHeight = 0; // measure过程中，FlowLayout要求的父ViewGroup的高

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        // 获取子view数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            // 获取子view
            View childView = getChildAt(i);
            // 获取子view的layoutParams,通过layoutParams可得到子view的宽高具体值或者MATCH_PARENT还是WRAP_CONTENT
            LayoutParams childLP = childView.getLayoutParams();
            if (childView.getVisibility() != View.GONE) {
                //将layoutParams转变成为 measureSpec  即设置子view的measureSpec
                /*
                 * widthMeasureSpec表示父view给予FlowLayout的宽度
                 * paddingLeft + paddingRight表示父view所设置的左右padding值
                 * childLP.width  表示 子view的宽度
                 * */
                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLP.width);
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childLP.height);
                // 通过子view的measureSpec度量子view
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

                //获取子view的度量宽高
                int childMeasureWidth = childView.getMeasuredWidth();
                int childMeasureHeight = childView.getMeasuredHeight();

                //换行
                if(lineWidthUsed + mHorizontalSpacing + childMeasureWidth > selfWidth ){
                    //一旦换行，我们就可以判断当前行需要的宽和高，所以此时要记录下来
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);
                    //判断flowLayout到底需要多宽、多高
                    flowLayoutNeedWidth = Math.max(flowLayoutNeedWidth, lineWidthUsed);
                    flowLayoutNeedHeight = flowLayoutNeedHeight + lineHeight + mVerticalSpacing;

                    // 换行后初始化
                    // 此处不能用clear，用clear则allLines里面的item所指向的就是同一个内存地址了
                    lineViews = new ArrayList<>();
                    lineWidthUsed = 0;
                    lineHeight = 0;
                }

                //每行的设置
                lineViews.add(childView);
                lineWidthUsed = lineWidthUsed + mHorizontalSpacing + childMeasureWidth;
                lineHeight = Math.max(lineHeight, childMeasureHeight);

                //最后一行数据（因为最后一行的时候到不了换行的那句代码，所以不会显示，因此要单独判断）
                if(i == childCount -1){
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);
                    //判断flowLayout到底需要多宽、多高
                    flowLayoutNeedWidth = Math.max(flowLayoutNeedWidth, lineWidthUsed);
                    flowLayoutNeedHeight = flowLayoutNeedHeight + lineHeight + mVerticalSpacing;
                }

            }
        }

        //根据子View的度量结果，来重新度量自己ViewGroup
        // 作为一个ViewGroup，它自己也是一个View,它的大小也需要根据它的父view给它提供的宽高来度量
        //首先获取到父view的MeasureSpec的mode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 如果父view的MeasureSpec的mode是EXACTLY表示宽度是确切的，则selfWidth为最终宽度,否则为
        int flowLayoutWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : flowLayoutNeedWidth;
        int flowLayoutHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : flowLayoutNeedHeight;

        // 保存记录
        setMeasuredDimension(flowLayoutWidth, flowLayoutHeight);

    }

    // 布局（每一行每一行的布局）
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 获取行数
        int lineCount = allLines.size();
        // 获取flowLayout所设置的pandding值,布局从左上角开始
        int curL = getPaddingLeft();
        int curT = getPaddingTop();

        for (int i = 0; i < lineCount; i++) {
            // 获取到每一行的所有view
            List<View> lineViews = allLines.get(i);

            for (int j = 0; j < lineViews.size(); j++){
                //获取单个view
                View view = lineViews.get(j);
                //设置view的视图坐标系，
                int left = curL;
                int top = curT;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                // view添加到布局
                view.layout(left,top,right,bottom);

                // 计算下一个view的宽度的开始位置
                curL = right + mHorizontalSpacing;
            }

            // 计算下一行view的高度的开始位置
            curT = curT + lineHeights.get(i) + mVerticalSpacing;
            // 宽度位置初始化
            curL = getPaddingLeft();
        }

    }


    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}