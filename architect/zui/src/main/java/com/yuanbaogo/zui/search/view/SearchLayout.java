package com.yuanbaogo.zui.search.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanbaogo.zui.R;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 自定义历史搜索控件
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/6/21 3:08 PM
 * @Modifier:
 * @Modify:
 */
public class SearchLayout extends ViewGroup {

    /**
     * 存储每一行的剩余的空间
     */
    private List<Integer> lineSpaces = new ArrayList<>();
    /**
     * 存储每一行的高度
     */
    private List<Integer> lineHeights = new ArrayList<>();
    /**
     * 存储每一行的view
     */
    private List<List<View>> lineViews = new ArrayList<>();
    /**
     * 提供添加view
     */
    private List<View> children = new ArrayList<>();
    /**
     * 每一行是否平分空间
     */
    private boolean isAverageInRow = false;
    /**
     * 每一列是否垂直居中
     */
    private boolean isAverageInColumn = true;

    private boolean isLimit; //是否有行限制

    private int limitLineCount; //默认显示3行 断词条显示3行，长词条显示2行

    public int getLineCount() {
        return mLineCount;
    }

    public int getTwoLineViewCount() {
        return mTwoLineViewCount;
    }

    private int mLineCount = 0;//行数

    private int mTwoLineViewCount = 0;//前两行里面view的个数

    public void setLimitLineCount(int limitLineCount) {
        this.limitLineCount = limitLineCount;
    }

    public SearchLayout(Context context) {
        this(context, null);
    }

    public SearchLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        limitLineCount = ta.getInt(R.styleable.TagFlowLayout_limit_line_count, 2);
        isLimit = ta.getBoolean(R.styleable.TagFlowLayout_is_limit, true);
        ta.recycle();
    }

    /**
     * 设置是否每列垂直居中
     *
     * @param averageInColumn 是否垂直居中
     */
    public void setAverageInColumn(boolean averageInColumn) {
        if (isAverageInColumn != averageInColumn) {
            isAverageInColumn = averageInColumn;
            requestLayout();
        }
    }

    /**
     * 设置是否每一行居中
     *
     * @param averageInRow 是否水平平分
     */
    public void setAverageInRow(boolean averageInRow) {
        if (isAverageInRow != averageInRow) {
            isAverageInRow = averageInRow;
            requestLayout();
        }
    }

    /**
     * 动态添加view
     */
    public void setChildren(List<View> children) {
        if (children == null) return;
        this.children = children;
        mLineCount = 0;
        mTwoLineViewCount = 0;
        this.removeAllViews();
        for (int i = 0; i < children.size(); i++) {
            this.addView(children.get(i));
            if (children.get(i) instanceof View) {
                int finalI = i;
                children.get(i).setOnClickListener(v -> {
                    if (mOnTagClickListener != null) {
                        mOnTagClickListener.onTagClick(children.get(finalI), finalI);
                    }
                });
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //清除记录数据
        lineSpaces.clear();
        lineHeights.clear();
        lineViews.clear();
        //测量view的宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        //计算children的数量
        int count = this.getChildCount();
        //统计子view总共高度
        int childrenTotalHeight = 0;

        //一行中剩余的空间
        int lineLeftSpace = 0;
        int lineRealWidth = 0;
        int lineRealHeight = 0;

        List<View> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //不可见的View不作处理
            if (child.getVisibility() == GONE) continue;
            //对子view进行测量
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获取子view的间距
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            //获取view占据的空间大小
            int childViewWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childViewHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            if (childViewWidth + lineRealWidth <= viewWidth) {// 一行
                //已占用的空间
                lineRealWidth += childViewWidth;
                //剩余的空间
                lineLeftSpace = viewWidth - lineRealWidth;
                //一行的最大高度
                lineRealHeight = Math.max(lineRealHeight, childViewHeight);
                //将一行中的view加到同意个集合
                list.add(child);
            } else {// 下一行
                if (list.size() != 0) {
                    // 统计上一行的总高度
                    childrenTotalHeight += lineRealHeight;
                    //上一行的高度
                    lineHeights.add(lineRealHeight);
                    //上一行剩余的空间
                    lineSpaces.add(lineLeftSpace);
                    //将上一行的元素保存起来
                    lineViews.add(list);
                }
                //重置一行中已占用的空间
                lineRealWidth = childViewWidth;
                //重置一行中剩余的空间
                lineLeftSpace = viewWidth - lineRealWidth;
                //重置一行中的高度
                lineRealHeight = childViewHeight;
                //更换新的集合存储下一行的元素
                list = new ArrayList<>();
                list.add(child);
            }

            if (i == count - 1) {// 最后一个元素
                childrenTotalHeight += lineRealHeight;
                // 将最后一行的信息保存下来
                lineViews.add(list);
                lineHeights.add(lineRealHeight);
                lineSpaces.add(lineLeftSpace);
            }
        }
        // 宽度可以不用考虑 主要考虑高度
        if (heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(viewWidth, viewHeight);
        } else {
            setMeasuredDimension(viewWidth, childrenTotalHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // View最开始左边
        int viewLeft = 0;
        // View最开始上边
        int viewTop = 0;

        // 每一个view layout的位置
        int vl;
        int vt;
        int vr;
        int vb;

        // 每一行中每一个view多平分的空间
        float averageInRow;
        // 每一列中每一个view距离顶部的高度
        float averageInColumn;

        // 列数
        int columns = lineViews.size();
        mLineCount = columns;

        for (int i = 0; i < columns; i++) {
            // 该行剩余的空间
            int lineSpace = lineSpaces.get(i);
            // 该行的高度
            int lineHeight = lineHeights.get(i);
            // 该行的所有元素
            List<View> list = lineViews.get(i);
            // 每一行的view的个数
            int rows = list.size();
            if (isLimit) {
                if (i == 0 || i == 1) {
                    mTwoLineViewCount = mTwoLineViewCount + rows;
                }
            } else {
                mTwoLineViewCount = mTwoLineViewCount + rows;
            }

            // view layout的位置
            // 每一行中每一个view多平分的空间<一行只有一个不管>
            if (isAverageInRow && rows > 1) {
                averageInRow = lineSpace * 1.0f / (rows + 1);
            } else {
                averageInRow = 0;
            }

            // 获取View的间距属性
            MarginLayoutParams params;
            for (int j = 0; j < rows; j++) {
                // 对应位置的view元素
                View child = list.get(j);
                params = (MarginLayoutParams) child.getLayoutParams();
                // 是否计算每一列中的元素垂直居中的时候多出的距离
                if (isAverageInColumn && rows > 1) {
                    averageInColumn = (lineHeight - child.getMeasuredHeight() - params.topMargin - params.bottomMargin) / 2;
                } else {
                    averageInColumn = 0;
                }

                // 左边位置 =起始位置+view左间距+多平分的空间
                vl = (int) (viewLeft + params.leftMargin + averageInRow);
                // 上面的位置 = 起始位置+view上间距+多平分的空间
                vt = (int) (viewTop + params.topMargin + averageInColumn);
                vr = vl + child.getMeasuredWidth();
                vb = vt + child.getMeasuredHeight();
                child.layout(vl, vt, vr, vb);
                viewLeft += child.getMeasuredWidth() + params.leftMargin + params.rightMargin + averageInRow;
            }
            viewLeft = 0;
            viewTop += lineHeight;
        }
    }

    /**
     * 重新方法用来获取子view的margin值
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    private OnTagClickListener mOnTagClickListener;

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener {
        void onTagClick(View view, int position);
    }

}