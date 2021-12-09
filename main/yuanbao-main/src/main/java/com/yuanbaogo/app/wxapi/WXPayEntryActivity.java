package com.yuanbaogo.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanbaogo.libpay.pay.Constant;


public class WXPayEntryActivity extends Activity implements
        IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			View view = LayoutInflater.from(this).inflate(
//					R.layout.pay_dialog_info, null);
//			TextView tv_message = (TextView) view.findViewById(R.id.tv_content);
//			TextView btn_ok = (TextView) view.findViewById(R.id.btn_confirm);
//
//			final Dialog dialog = new Dialog(WXPayEntryActivity.this,R.style.UnifiedDialogTheme);
//			dialog.setContentView(view);
//			dialog.show();
////			dialog.setView(view, 0, 0, 0, 0);
////			dialog.show();
//			WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//			Display display = windowManager.getDefaultDisplay();
//			Window window = dialog.getWindow();
//			if (window != null) {
//				WindowManager.LayoutParams lp = window.getAttributes();
//				lp.width = display.getWidth() - DensityUtil.dip2px(WXPayEntryActivity.this,48); //设置宽度
//				window.setAttributes(lp);
//			}
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
//				tv_message.setText("微信支付成功！");
//				try {
//					Thread.sleep(2000);// 2秒自动退出界面
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
                Intent msgIntent = new Intent(Constant.WX_MSG_OK);
                msgIntent.putExtra("code", resp.errCode);
                sendBroadcast(msgIntent);
//				dialog.dismiss();

            } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                Intent msgIntent = new Intent(Constant.WX_MSG_CANCEL);
                msgIntent.putExtra("code", resp.errCode);
                sendBroadcast(msgIntent);
//				tv_message.setText("微信支付取消！");
            } else {
                Intent msgIntent = new Intent(Constant.WX_MSG_ERR);
                msgIntent.putExtra("code", resp.errCode);
                sendBroadcast(msgIntent);
//				tv_message.setText("微信支付失败！");
            }

            finish();
//			btn_ok.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					dialog.dismiss();
//					finish();
//				}
//			});
        }
    }
}