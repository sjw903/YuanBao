package com.yuanbaogo.homevideo.shortvideo.comment.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.zui.toast.ToastView;

/**
 * @author ganhuanhui
 * 时间：2019/12/11 0011
 * 描述：https://blog.csdn.net/qq_32518491/article/details/85000421
 */
public class InputTextMsgDialog extends AppCompatDialog {
    private Context mContext;
    private InputMethodManager imm;
    private EditText messageTextView;
    private TextView tv_input_message;
    private RelativeLayout rlDlg;
    private int mLastDiff = 0;
    private int maxNumber = 100;
    private static String content;

    public interface OnTextSendListener {

        void onTextSend(String msg);

        void dismiss();
    }

    private OnTextSendListener mOnTextSendListener;

    public InputTextMsgDialog(@NonNull Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        init();
        setLayout();
    }

    /**
     * 最大输入字数  默认100
     */
    @SuppressLint("SetTextI18n")
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * 设置输入提示文字
     */
    public void setHint(String text) {
        messageTextView.setHint(text);
    }
    public void setText(String text) {
        tv_input_message.setText(text);
    }

    private void init() {
        setContentView(R.layout.dialog_input_text_msg);
        messageTextView = (EditText) findViewById(R.id.et_input_message);
        tv_input_message = (TextView) findViewById(R.id.tv_input_message);
        final LinearLayout rldlgview = (LinearLayout) findViewById(R.id.rl_inputdlg_view);
        messageTextView.requestFocus();
        messageTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > maxNumber){
                    messageTextView.setText(charSequence.toString().substring(0,maxNumber));
                    messageTextView.setSelection(maxNumber);
                    ToastView.showToast("已超过最大字数");
                }else{
                    content = charSequence.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        Button btConfirm = findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserStore.isLogin()) {
                    RouterHelper.toLogin();
                    return;
                }
                String msg = messageTextView.getText().toString().trim();
//                if (msg.length() > maxNumber) {
//                    Toast.makeText(mContext, "超过最大字数限制" + maxNumber, Toast.LENGTH_LONG).show();
//                    return;
//                }
                if (!TextUtils.isEmpty(msg)) {
                    mOnTextSendListener.onTextSend(msg);
                    imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                    imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                    messageTextView.setText("");
                    InputTextMsgDialog.this.dismiss();
                } else {
                    InputTextMsgDialog.this.dismiss();
//                    Toast.makeText(mContext, "请输入文字", Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, "", Toast.LENGTH_LONG).show();
                }
                messageTextView.setText(null);
            }
        });

        messageTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case KeyEvent.KEYCODE_ENDCALL:
                    case KeyEvent.KEYCODE_ENTER:
//                        if (messageTextView.getText().length() > maxNumber) {
//                            Toast.makeText(mContext, "超过最大字数限制", Toast.LENGTH_LONG).show();
//                            return true;
//                        }
                        if (messageTextView.getText().length() > 0) {
                            imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                            InputTextMsgDialog.this.dismiss();
                        } else {
                            Toast.makeText(mContext, "请输入文字", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    case KeyEvent.KEYCODE_BACK:
                        InputTextMsgDialog.this.dismiss();
                        return false;
                    default:
                        return false;
                }
            }
        });

        messageTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.d("My test", "onKey " + keyEvent.getCharacters());
                return false;
            }
        });

        rldlgview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                Rect r = new Rect();
                //获取当前界面可视部分
                InputTextMsgDialog.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = InputTextMsgDialog.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;

                if (heightDifference <= 0 && mLastDiff > 0) {
                    InputTextMsgDialog.this.dismiss();
                }
                mLastDiff = heightDifference;
            }
        });

        setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
                    InputTextMsgDialog.this.dismiss();
                }
                return false;
            }
        });
    }

    private void setLayout() {
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    public void setmOnTextSendListener(OnTextSendListener onTextSendListener) {
        this.mOnTextSendListener = onTextSendListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //dismiss之前重置mLastDiff值避免下次无法打开
        mLastDiff = 0;
        if (mOnTextSendListener!=null) mOnTextSendListener.dismiss();

    }

    @Override
    public void show() {
        messageTextView.setText(content);
        super.show();
    }
}
