package com.yuanbaogo.libpay.pay;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEx {
    private IWXAPI wxapi;
    private static final String APP_KEY = "";

    public WXPayEx() {
    }

    public void initWXAPI(Context context) {
        this.wxapi = WXAPIFactory.createWXAPI(context, Constant.WX_APP_ID);
        this.wxapi.registerApp(Constant.WX_APP_ID);
    }

    public int doStartWXPay(Context context, String prepayId, String nonceStr, String timeStamp, String packageValue, String sign, String partnerId) {
        if (!this.wxapi.isWXAppInstalled()) {
            Toast.makeText(context,"请安装微信客户端！", Toast.LENGTH_SHORT).show();
            return 1;
        } else if (TextUtils.isEmpty(partnerId)) {
            Toast.makeText(context,"支付失败，商户号为空！", Toast.LENGTH_SHORT).show();
            return 2;
        } else {
            boolean isPaySupported = this.wxapi.getWXAppSupportAPI() >= 570425345;
            if (isPaySupported) {
                PayReq req = new PayReq();
                req.appId = Constant.WX_APP_ID;
                req.partnerId = partnerId;
                req.prepayId = prepayId;
                req.nonceStr = nonceStr;
                req.timeStamp = timeStamp;
                req.packageValue = packageValue;
                req.sign = sign;
//                Log.i("WXPayEx", req.toString());
//                Log.i("WXPayEx", "partnerId = " + partnerId + ",prepayId = " + prepayId + ",nonceStr = " + nonceStr + ",timeStamp = " + timeStamp + ",packageValue = " + packageValue + ",sign = " + sign);
                this.wxapi.sendReq(req);
                return 0;
            } else {
                return 3;
            }
        }
    }
}
