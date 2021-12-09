package com.yuanbaogo.mine.logout.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.logout.adapter.DialogListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 提示弹窗
 * @Params: 可以自定义显示内容
 * @Author: HG
 * @Date: 1/20/21 10:55 AM
 */
public class MyDialog extends Dialog implements View.OnClickListener {

    static int mTheme = R.style.CustomizeDialog;//自定义dialog样式

    TextView tvTitle;

    TextView tvContent;

    TextView tvCancel;

    TextView tvDefine;

    ListView choose;

    View viewLine;

    public List<ItemBean> list;

    private OnClickLoginInvalid mListener;

    private boolean states = true;

    String[] title = new String[]{"不喜欢了", "被封号了", "很无奈要注销"};

    DialogListAdapter adapter;

    public MyDialog(Context context) {
        this(context, mTheme);
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 给弹窗设置点击事件
     *
     * @param listener 需要实现的自定义点击事件
     * @return 返回弹窗本身
     */
    public MyDialog setOnClickLoginInvalid(OnClickLoginInvalid listener) {
        mListener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_dialog_cancellation_choose);
        getWindow().setGravity(Gravity.CENTER);//设置显示在中间
        initView();
    }

    private void initView() {
        choose = findViewById(R.id.dialog_lis);
        tvCancel = findViewById(R.id.tv_cancel);
        viewLine = findViewById(R.id.view_line);
        tvDefine = findViewById(R.id.tv_define);
        list = new ArrayList<>();
        for (String str : title) {
            ItemBean bean = new ItemBean(str, false);
            list.add(bean);
        }
        adapter = new DialogListAdapter(list, this.getContext());
        choose.setAdapter(adapter);
        tvCancel.setOnClickListener(this);
        tvDefine.setOnClickListener(this);
        choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list != null) {
                    for (ItemBean bean : list) {
                        bean.setChoose(false);
                    }
                    list.get(position).setChoose(true);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_define) {
            mListener.setDefineOnclick();
        } else if (id == R.id.tv_cancel) {
            mListener.setCancelOnclick();
        }
    }

    public MyDialog setCancelables(boolean state) {
        states = state;
        setCancelable(states);
        return this;
    }

    /**
     * 自定义点击事件接口
     */
    public interface OnClickLoginInvalid {
        void setDefineOnclick();

        void setCancelOnclick();
    }

    public class ItemBean {

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }

        String title;

        boolean isChoose;

        public ItemBean(String title, boolean isChoose) {
            this.title = title;
            this.isChoose = isChoose;
        }

    }

}
