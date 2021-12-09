package com.yuanbaogo.finance.bindBankCard.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.finance.R;
import com.yuanbaogo.finance.bindBankCard.contract.BindBankCardContract;
import com.yuanbaogo.finance.bindBankCard.model.BankCardInfoBean;
import com.yuanbaogo.finance.bindBankCard.presenter.BindBankCardPresenter;
import com.yuanbaogo.libbase.baseutil.KeyBoardUtils;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.dialog.ConfirmDialogView;
import com.yuanbaogo.zui.dialog.call.OnCallDialog;
import com.yuanbaogo.zui.head.HeadView;
import com.yuanbaogo.zui.head.bean.HeadBean;
import com.yuanbaogo.zui.head.call.OnCallHeadBack;
import com.yuanbaogo.zui.view.TitleBar;

@Route(path = YBRouter.BIND_BAND_CARD_ACTIVITY)
public class BindBankCardActivity extends MvpBaseActivityImpl<BindBankCardContract.View, BindBankCardPresenter>
        implements View.OnClickListener, OnCallHeadBack, BindBankCardContract.View {

    private Button mBindBankCardNextBt;//下一步按钮
    private EditText mBindBankCardNameEt;//姓名输入框
    private EditText mBindBankCardNumberEt;//银行卡号输入框
    private TextView mBindBankCardBankTv;//银行名字
    private ImageView mBindBankCardNameDeleteIv;//姓名删除
    private ImageView mBindBankCardNumberDeleteIv;//银行卡号删除

    private HeadView bindBankCardHeadView;

    String accountBank;
    String bankAccountName;
    String cardNo;
    int cardType;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mBindBankCardNextBt = findViewById(R.id.bind_bank_card_next_bt);
        mBindBankCardNameEt = findViewById(R.id.bind_bank_card_name_et);
        mBindBankCardNumberEt = findViewById(R.id.bind_bank_card_number_et);
        mBindBankCardBankTv = findViewById(R.id.bind_bank_card_bank_tv);
        mBindBankCardNameDeleteIv = findViewById(R.id.bind_bank_card_name_delete_iv);
        mBindBankCardNumberDeleteIv = findViewById(R.id.bind_bank_card_number_delete_iv);
        bindBankCardHeadView = findViewById(R.id.bind_bank_card_head_view);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mBindBankCardNextBt.setOnClickListener(this);
        mBindBankCardNameDeleteIv.setOnClickListener(this);
        mBindBankCardNumberDeleteIv.setOnClickListener(this);
        mBindBankCardNumberEt.addTextChangedListener(textWatcher);
        bindBankCardHeadView.setOnCallHeadBack(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bind_bank_card_name_delete_iv) {//姓名删除
            if (!TextUtils.isEmpty(mBindBankCardNameEt.getText().toString())) {
                mBindBankCardNameEt.setText("");
            }
        } else if (id == R.id.bind_bank_card_number_delete_iv) {//银行卡号删除
            if (!TextUtils.isEmpty(mBindBankCardNumberEt.getText().toString())) {
                mBindBankCardNumberEt.setText("");
            }
        } else if (id == R.id.bind_bank_card_next_bt) {//下一步
            mPresenter.getBindBankCard(accountBank, bankAccountName, cardNo, cardType);
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        mBindBankCardNameDeleteIv.setVisibility(View.GONE);
        mBindBankCardNumberDeleteIv.setVisibility(View.GONE);
        mBindBankCardNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                initViewUI();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mBindBankCardNumberEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                getBankCardInfo();
                return false;
            }
        });
    }

    private void initHeadView() {
        HeadBean headBean = new HeadBean()
                .setImgLeft(true)
                .setImgLeftIcon(R.mipmap.icon_left_close)
                .setRlSearch(false)
                .setTvCenterTitle(true)
                .setTvCenterTitles("绑定银行卡");
        bindBankCardHeadView.setHead(headBean);
    }

    @Override
    public void onClickBack() {
        if (mBindBankCardNameEt.getText().toString().length() > 0
                || mBindBankCardNumberEt.getText().toString().length() > 0) {
            ConfirmDialogView confirmDialogView = new ConfirmDialogView("取消",
                    "确定退出绑定银行卡？");
            confirmDialogView.show(getSupportFragmentManager(), "search_history");
            confirmDialogView.setOnCallDialog(new OnCallDialog() {
                @Override
                public void onCallDetermine() {
                    confirmDialogView.dismiss();
                    finish();
                }
            });
        } else {
            finish();
        }
    }

    TextWatcher textWatcher = new TextWatcher() {

        int beforeTextLength = 0;
        int onTextLength = 0;
        boolean isChanged = false;
        int location = 0;// 记录光标的位置
        private char[] tempChar;
        private StringBuffer buffer = new StringBuffer();
        int konggeNumberB = 0;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            beforeTextLength = charSequence.length();
            if (buffer.length() > 0) {
                buffer.delete(0, buffer.length());
            }
            konggeNumberB = 0;
            for (int i = 0; i < charSequence.length(); i++) {
                if (charSequence.charAt(i) == ' ') {
                    konggeNumberB++;
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            initViewUI();
            onTextLength = charSequence.length();
            buffer.append(charSequence.toString());
            if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                isChanged = false;
                return;
            }
            isChanged = true;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (isChanged) {
                location = mBindBankCardNumberEt.getSelectionEnd();
                int index = 0;
                while (index < buffer.length()) {
                    if (buffer.charAt(index) == ' ') {
                        buffer.deleteCharAt(index);
                    } else {
                        index++;
                    }
                }
                index = 0;
                int konggeNumberC = 0;
                while (index < buffer.length()) {
                    if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                        buffer.insert(index, ' ');
                        konggeNumberC++;
                    }
                    index++;
                }
                if (konggeNumberC > konggeNumberB) {
                    location += (konggeNumberC - konggeNumberB);
                }
                tempChar = new char[buffer.length()];
                buffer.getChars(0, buffer.length(), tempChar, 0);
                String str = buffer.toString();
                if (location > str.length()) {
                    location = str.length();
                } else if (location < 0) {
                    location = 0;
                }
                mBindBankCardNumberEt.setText(str);
                Editable etable = mBindBankCardNumberEt.getText();
                Selection.setSelection(etable, location);
                isChanged = false;
            }
        }
    };

    private void initViewUI() {
        mBindBankCardNameDeleteIv.setVisibility(mBindBankCardNameEt.getText().toString().length() > 0 ?
                View.VISIBLE : View.GONE);
        mBindBankCardNumberDeleteIv.setVisibility(mBindBankCardNumberEt.getText().toString().length() > 0 ?
                View.VISIBLE : View.GONE);
        mBindBankCardNextBt.setEnabled(mBindBankCardNameEt.getText().toString().length() > 0
                && mBindBankCardNumberEt.getText().toString().length() > 0
                && mBindBankCardBankTv.getText().toString().length() > 0);
    }

    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (KeyBoardUtils.isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                KeyBoardUtils.hintKeyboard(BindBankCardActivity.this);
                getBankCardInfo();
            }
        }
        return super.dispatchTouchEvent(me);
    }

    /**
     * 获取银行卡信息
     */
    private void getBankCardInfo() {
        if (mBindBankCardNameEt.getText().toString().length() > 0
                && mBindBankCardNumberEt.getText().toString().length() > 0) {
            mPresenter.getBankCardInfo(mBindBankCardNameEt.getText().toString(),
                    mBindBankCardNumberEt.getText().toString().replace(" ", ""));
        }
    }

    @Override
    public void setBankCardInfo(BankCardInfoBean bean) {
        accountBank = bean.getAccountBank();
        bankAccountName = mBindBankCardNameEt.getText().toString();
        cardNo = mBindBankCardNumberEt.getText().toString().replace(" ", "");
        cardType = bean.getAccountType();
        mBindBankCardBankTv.setText(bean.getAccountBank());
        initViewUI();
    }

    @Override
    public void setBindBankCard() {
        Intent intent = new Intent(BindBankCardActivity.this, BindBankCardSuccessActivity.class);
        startActivity(intent);
        finish();
    }

}