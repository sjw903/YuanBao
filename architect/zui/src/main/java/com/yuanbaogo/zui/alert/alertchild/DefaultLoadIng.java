package com.yuanbaogo.zui.alert.alertchild;

import android.app.ProgressDialog;
import android.content.Context;

import com.yuanbaogo.zui.alert.BaseChildLoadIng;

public class DefaultLoadIng extends BaseChildLoadIng {

    private ProgressDialog progressDialog;

    private Context context;

    private String title = "标题2";

    private String message = "其他样式";

    public DefaultLoadIng context(Context context) {
        this.context = context;
        return this;
    }

    public DefaultLoadIng title(String title) {
        this.title = title;
        return this;
    }

    public DefaultLoadIng message(String message) {
        this.message = message;
        return this;
    }

    @Override
    public void show() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    public void dissim() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
