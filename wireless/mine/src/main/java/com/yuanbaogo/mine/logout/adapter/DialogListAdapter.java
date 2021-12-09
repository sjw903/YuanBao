package com.yuanbaogo.mine.logout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.logout.dialog.MyDialog;

import java.util.List;

public class DialogListAdapter extends BaseAdapter {

    List<MyDialog.ItemBean> list;

    Context context;

    public DialogListAdapter(List<MyDialog.ItemBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.mine_item_dialog_cancellation_choose, null);
        TextView textView = view.findViewById(R.id.title);
        MyDialog.ItemBean bean = list.get(position);
        if (bean.isChoose()) {
            textView.setTextColor(context.getResources().getColor(R.color.colorEA5504));
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.color212121));
        }
        textView.setText(bean.getTitle());
        return view;
    }

}
