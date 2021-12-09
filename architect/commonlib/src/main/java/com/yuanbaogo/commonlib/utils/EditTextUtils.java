package com.yuanbaogo.commonlib.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class EditTextUtils {

    public static void focusEditText(EditText view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        //调用系统输入法
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 没有办法，showSoftInput 打不开输入法，只能强制打开了。初步怀疑和自定义View PwdEditText 有关。时间问题暂不做深层次处理。
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        view.requestFocus();
        view.findFocus();
    }
}
