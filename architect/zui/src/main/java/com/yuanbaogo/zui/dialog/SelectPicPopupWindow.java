package com.yuanbaogo.zui.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuanbaogo.zui.R;

import java.util.Arrays;
import java.util.List;


/**
 * 底下弹出的pop
 */
public class SelectPicPopupWindow extends PopupWindowWithMask {

    private View mContentView;
    private TextView btn_cancel;

    private ListView mListView;
    protected Activity mContext;
    private String reportTitle;
    AdapterView.OnItemClickListener mItemClickListener;
    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.pop_bottom_select_pic, null, false);
        return mContentView;
    }

    @Override
    protected int initHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int initWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    public interface OnCancelListener {
        void onCancel();
    }

    private OnCancelListener onCancelListener;


    public SelectPicPopupWindow(Activity activity,String reportTitle, AdapterView.OnItemClickListener itemClickListener, List<String> datas) {
        super(activity, true);
        setAnimationStyle(R.style.pop_anim_bottom);
        mContext = activity;
        this.reportTitle = reportTitle;
        initView();
        mItemClickListener=itemClickListener;
        initList(activity, datas);
    }
    public SelectPicPopupWindow(Activity activity,String reportTitle, String[] datas) {
        this(activity,reportTitle,null,Arrays.asList(datas));
    }
    protected void initView() {
        mListView = (ListView) mContentView.findViewById(R.id.lv_list);
        btn_cancel = (TextView) mContentView.findViewById(R.id.tv_btn_cancel);
        //取消按钮
        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //销毁弹出框

                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                }

                dismiss();
            }
        });
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mContentView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mContentView.findViewById(R.id.ll_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void setCancelText(int text) {
        if (btn_cancel != null) {
            btn_cancel.setText(text);
        }

    }

    public void setCancelTextColor(int color) {
        if (btn_cancel != null) {
            btn_cancel.setTextColor(mContext.getResources().getColor(color));
        }
    }

    public void setCancelVisible(boolean b) {
        if (btn_cancel != null) {
            btn_cancel.setVisibility(b ? View.VISIBLE : View.GONE);
        }

    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        if(mItemClickListener!=null&&mListView!=null){
            mListView.setOnItemClickListener(mItemClickListener);
        }

    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }


    private void initList(Activity context,  List<String> datas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.item_select_pop, datas);
        mListView.setAdapter(adapter);
        if(mItemClickListener!=null){
            mListView.setOnItemClickListener(mItemClickListener);
        }

    }

}