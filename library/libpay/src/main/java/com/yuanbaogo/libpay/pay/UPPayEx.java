package com.yuanbaogo.libpay.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class UPPayEx {
    private static String mMode = "00";
    private static final int PLUGIN_VALID = 0;
    private static final int PLUGIN_NOT_INSTALLED = -1;
    private static final int PLUGIN_NEED_UPGRADE = 2;

    public UPPayEx() {
    }

    public static void doStartUnionPayPlugin(final Activity act, final String tn) {
        act.runOnUiThread(new Runnable() {
            public void run() {
//                UPPayAssistEx.startPay(act, (String)null, (String)null, tn, UPPayEx.mMode);
            }
        });
    }

    public static void doUnionPayResult(Context context, int requestCode, int resultCode, Intent data, IBasePay iBasePay, int type) {
        if (data != null) {
            String msg = "";
            String str = data.getExtras().getString("pay_result");
            if ("success".equalsIgnoreCase(str)) {
                msg = "支付成功！";
                iBasePay.onSuccess(type, new String[]{"支付成功"});
            } else if ("fail".equalsIgnoreCase(str)) {
                msg = "支付失败！";
                iBasePay.onFail(type, new String[]{"支付失败"});
            } else if ("cancel".equalsIgnoreCase(str)) {
                msg = "用户取消了支付";
                iBasePay.onCancel(type, new String[]{"用户取消了支付"});
            }

        }
    }

    public void doStartUnionPayAPK(final Activity act, String tn) {
//        final int ret = UPPayAssistEx.startPay(act, (String)null, (String)null, tn, mMode);
        final int ret = 0;
        if (ret == 2 || ret == -1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setTitle("提示");
            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @SuppressLint("WrongConstant")
                public void onClick(DialogInterface dialog, int which) {
//                    if (ret == -1 && Utilities.copyApkFromAssets(act, "UPPayPluginEx.apk", Environment.getExternalStorageDirectory().getAbsolutePath() + "/UPPayPluginEx.apk")) {
//                        Intent intent = new Intent("android.intent.action.VIEW");
//                        intent.addFlags(268435456);
//                        intent.setDataAndType(Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/UPPayPluginEx.apk"), "application/vnd.android.package-archive");
//                        act.startActivity(intent);
//                    }

                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    public static void doUnionPayResult(Context context, int requestCode, int resultCode, Intent data, final UPPayEx.PayBack back) {
        if (data != null) {
            String msg = "";
            final String str = data.getExtras().getString("pay_result");
            if ("success".equalsIgnoreCase(str)) {
                msg = "支付成功！";
            } else if ("fail".equalsIgnoreCase(str)) {
                msg = "支付失败！";
            } else if ("cancel".equalsIgnoreCase(str)) {
                msg = "用户取消了支付";
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (str.equalsIgnoreCase("success")) {
                        back.doBack();
                    }

                }
            });
            builder.create().show();
        }
    }

    public static void doUnionPayResult(Context context, int requestCode, int resultCode, Intent data, final UPPayEx.UPPPayBack back) {
        if (data != null) {
            String msg = "";
            final String str = data.getExtras().getString("pay_result");
            if ("success".equalsIgnoreCase(str)) {
                msg = "支付成功！";
            } else if ("fail".equalsIgnoreCase(str)) {
                msg = "支付失败！";
            } else if ("cancel".equalsIgnoreCase(str)) {
                msg = "用户取消了支付";
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    back.doBack(str);
                }
            });
            builder.create().show();
        }
    }

    public interface UPPPayBack {
        void doBack(String var1);
    }

    public interface PayBack {
        void doBack();
    }
}
