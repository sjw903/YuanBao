package com.yuanbaogo.finance.pay.weight;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yuanbaogo.finance.R;
import com.yuanbaogo.zui.dialog.view.DialogsFragment;

public class PayDialogFragment extends DialogsFragment implements View.OnClickListener {

    private TextView payDialogNotOpen;
    private TextView payDialogKeepOn;
    private TextView tv_subtitle;
    private TextView tv_title;
    private OnNextOpenPayPassword onNextOpenPayPassword;

    private String mTitle, mSubtitle, mLefttxt, mRighttxt;


    public PayDialogFragment(String title, String subtitle, String lefttxt, String righttxt){
        mTitle = title;
        mSubtitle = subtitle;
        mLefttxt = lefttxt;
        mRighttxt = righttxt;
    }

    @Override
    public int getLayout() {
        return R.layout.pay_dialog_pay_dialog;
    }

    @Override
    protected void intViews(View view) {
        payDialogNotOpen = view.findViewById(R.id.pay_dialog_not_open);
        payDialogKeepOn = view.findViewById(R.id.pay_dialog_keep_on);
        tv_title = view.findViewById(R.id.tv_title);
        tv_subtitle = view.findViewById(R.id.tv_subtitle);
    }

    @Override
    protected void setTexts() {
        tv_title.setText(mTitle);
        tv_subtitle.setText(mSubtitle);
        tv_subtitle.setText(mSubtitle);
        payDialogNotOpen.setText(mLefttxt);
        payDialogKeepOn.setText(mRighttxt);
    }

    @Override
    protected void setOnClicks() {
        payDialogNotOpen.setOnClickListener(this);
        payDialogKeepOn.setOnClickListener(this);
    }

    public void setOnNextOpenPayPassword(OnNextOpenPayPassword onNextOpenPayPassword) {
        this.onNextOpenPayPassword = onNextOpenPayPassword;
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (int) (dm.widthPixels * 0.8);
    }


    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pay_dialog_not_open) {
            dismiss();
        } else if (id == R.id.pay_dialog_keep_on) {
            dismiss();
            if (onNextOpenPayPassword != null) {
                onNextOpenPayPassword.onNextOpen();
            }
        }
    }

    public interface OnNextOpenPayPassword {
        void onNextOpen();
    }

}
