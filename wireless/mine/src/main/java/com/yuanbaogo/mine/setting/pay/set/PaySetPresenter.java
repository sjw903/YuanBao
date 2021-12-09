package com.yuanbaogo.mine.setting.pay.set;

import android.text.TextUtils;
import android.util.Log;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.login.jiyan.JiYan;
import com.yuanbaogo.login.login.model.JiyanBean;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.order.utils.OrderNetworkUtils;
import com.yuanbaogo.zui.toast.ToastView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaySetPresenter extends MvpBasePersenter<PaySetContract.View> implements PaySetContract.Presenter {

    private static final String TAG = "PaySetPresenter";

    private JiYan jiYan;

    @Override
    public void setJiYan() {
        jiYan = new JiYan(getContext());
        jiYan.setJiYanListener(new JiYan.JiYanListener() {
            @Override
            public void jiyanButtonClick() {
                if (!isActive()) {
                    return;
                }
                checkJiYan();
            }

            @Override
            public void jiyanSuccess(String result) {
                jiYan.showSuccessDialog(true);
                try {
                    JSONObject object = new JSONObject(result);
                    String geetest_challenge = object.getString("geetest_challenge");
                    String geetest_seccode = object.getString("geetest_seccode");
                    String geetest_validate = object.getString("geetest_validate");
                    getVerificationCode(geetest_challenge, geetest_seccode, geetest_validate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void jiyanFailed() {
                jiYan.showSuccessDialog(false);
            }
        });
    }

    /**
     * 验证极验
     */
    public void checkJiYan() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", UserStore.getUserPhone());
        NetWork.getInstance().post(getContext(), ChildUrl.checkJiYan, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String str) {
                if (TextUtils.isEmpty(str)) {
                    return;
                } //必须加
                try {
                    JSONObject object = new JSONObject(str);
                    jiYan.continueJY(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }

    private void getVerificationCode(String geetest_challenge, String geetest_seccode, String geetest_validate) {
        Map<String, Object> map = new HashMap<>();
        map.put("userPhone", UserStore.getUserPhone());
//        map.put("state", "2");//1-注册账号 2-验证码登录 3-微信绑定手机号 4-忘记密码
        map.put("challenge", geetest_challenge);
        map.put("seccode", geetest_seccode);
        map.put("validate", geetest_validate);
        NetWork.getInstance().post(getContext(), ChildUrl.checkJiYan_second, map, new RequestSateListener<JiyanBean>() {
            @Override
            public void onSuccess(int code, JiyanBean bean) {
                if (!isActive() || bean == null) {
                    return;
                }
                getView().startTimer();
            }

            @Override
            public void onFailure(Throwable var1) {
                if (!isActive()) {
                    return;
                }
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }


    @Override
    public void getVerificationCode() {
        jiYan.startJY();
    }

    @Override
    public void sendCode() {
        NetWork.getInstance().get(getContext(), ChildUrl.SEND_CODE + "/" + UserStore.getUserPhone(), null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                Log.e(TAG, "onSuccess: code:" + code + "  bean:" + bean);
                if (!isActive() || bean == null) {
                    return;
                }
                if (Boolean.parseBoolean(bean)) {
                    getView().startTimer();
                } else {
                    ToastView.showToast(R.string.pay_code_error);
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                if (!isActive()) {
                    return;
                }
                Log.e(TAG, "onFailure: Throwable:" + var1.getMessage());
                OrderNetworkUtils.disposeError(var1);
            }
        });
    }

    @Override
    public void checkCaptcha(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("phone", UserStore.getUserPhone());
        NetWork.getInstance().post(getContext(), ChildUrl.CHECK_CAPTCHA, map, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String bean) {
                if (!isActive() || bean == null) {
                    return;
                }
                getView().checkCaptcha(Boolean.parseBoolean(bean));
            }

            @Override
            public void onFailure(Throwable var1) {
                if (!isActive()) {
                    return;
                }
                getView().checkCaptcha(false);
            }
        });
    }

}
