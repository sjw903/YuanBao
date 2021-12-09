package com.yuanbaogo.zui.alert;

import android.app.ProgressDialog;
import android.content.Context;

public class BaseChildLoadIng implements BaseLoadIng{

    private ProgressDialog progressDialog;

    private Context context;

    private String title="标题";

    private String message="正在加载";

    public BaseChildLoadIng context(Context context) {
        this.context=context;
        return this;
    }

    public BaseChildLoadIng title(String title) {
        this.title=title;
        return  this;
    }

    public BaseChildLoadIng message(String message) {
        this.message=message;
        return  this;
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
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

}
