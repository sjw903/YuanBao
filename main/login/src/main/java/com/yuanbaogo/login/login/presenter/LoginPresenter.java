package com.yuanbaogo.login.login.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.yuanbaogo.datacenter.constant.BroadcastProtocol;
import com.yuanbaogo.datacenter.local.user.UserInfo;
import com.yuanbaogo.datacenter.local.user.UserStore;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.im.login.LoginManager;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.ValidatePhoneUtil;
import com.yuanbaogo.login.jiyan.JiYan;
import com.yuanbaogo.login.login.contract.LoginContract;
import com.yuanbaogo.login.login.model.JiyanBean;
import com.yuanbaogo.login.login.model.LoginBean;
import com.yuanbaogo.zui.toast.ToastView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenter extends MvpBasePersenter<LoginContract.View> implements LoginContract.Presenter {

    private int mInviteTime = 0;//验证码间隔时间
    private int what_change = 1;

    private int mCanGetCode = 3;//是否能获取验证码
    private int mCanLogin = -1;//是否可登录

    private JiYan jiYan;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == what_change) {
                mInviteTime--;
                if (mInviteTime > 0) {
                    if (isActive()) {
                        getView().changeVerificationTimer(mInviteTime);
                    }
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(what_change, 1000);
                    }
                } else {
                    if (isActive()) {
                        getView().changeVerificationButton();
                    }
                }
            }
        }
    };

    /**
     * 判断是否可点击获取验证码
     *
     * @return
     */
    public void changeCanGetCode(String loginPhoneNum) {
        if (isActive()) { //必须加
            getView().changeCodeColor(false);
            if (loginPhoneNum.length() < 1) {//手机位数小于11
                mCanGetCode = 3;
            } else if (loginPhoneNum.length() < 11) {//判断是否是手机号
                mCanGetCode = 1;
            } else if (!ValidatePhoneUtil.validateMobileNumber(loginPhoneNum)) {//判断是否是手机号
                mCanGetCode = 2;
            } else {
                mCanGetCode = 0;
                getView().changeCodeColor(true);
            }
        }
    }

    @Override
    public void verification() {
        switch (mCanGetCode) {
            case 0:
                jiYan.startJY();
                break;
            case 1:
                ToastView.showToast(getContext(), "请输入正确的手机号码");
                break;
            case 2:
                ToastView.showToast(getContext(), "手机号有误，请核对后重新输入");
                break;
            case 3:
                ToastView.showToast(getContext(), "手机号不能为空，请输入手机号");
//                ToastView.showToast(getContext(), "请等待" + mInviteTime + "秒后重新获取验证码！");
//                jiYan.startJY();
                break;
        }
    }

    @Override
    public void setJiYan() {
        jiYan = new JiYan(getContext());
        jiYan.setJiYanListener(new JiYan.JiYanListener() {
            @Override
            public void jiyanButtonClick() {
                if (isActive()) {
                    checkJiYan(getView().getLoginPhoneNum());
                }
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
//              jiYan.showSuccessDialog(false); // 极验要求注释掉
            }
        });
    }

    /**
     * 验证极验
     *
     * @param phone
     */
    public void checkJiYan(String phone) {
        checkJiYan(phone, 1);
    }

    public void checkJiYan(String phone,int t) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);

        NetWork.getInstance().post(getContext(), ChildUrl.checkJiYan, params, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String str) {
                JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(str);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                jiYan.continueJY(jsonObject);
            }

            @Override
            public void onFailure(Throwable var1) {
                jiYan.continueJY(null);
                jiYan.dismiss();
            }

            @Override
            public boolean onHandleMessage(Throwable var1, String code, String msg) {
                try {
                    Thread.sleep(Math.min(t * 1000, 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                checkJiYan(phone,t + 1);
                return super.onHandleMessage(var1, code, msg);
            }
        });
    }

    private void getVerificationCode(String geetest_challenge, String geetest_seccode, String geetest_validate) {
        Map<String, Object> map = new HashMap<>();
        if (isActive()) {
            map.put("phone", getView().getLoginPhoneNum());
        }
//        map.put("state", "2");//1-注册账号 2-验证码登录 3-微信绑定手机号 4-忘记密码
        map.put("challenge", geetest_challenge);
        map.put("challenge", geetest_challenge);
        map.put("seccode", geetest_seccode);
        map.put("validate", geetest_validate);
        NetWork.getInstance().post(getContext(), ChildUrl.checkJiYan_second, map, new RequestSateListener<JiyanBean>() {
            @Override
            public void onSuccess(int code, JiyanBean bean) {
                if (isActive() && bean != null) {
                    checkPhone(getView().getLoginPhoneNum());
                    //TODO guwei 获得验证码接口

                    mInviteTime = 60;
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(what_change, 1000);
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
            }
        });
    }

    /**
     * 判断是否可点击登录按钮
     *
     * @return
     */
    @Override
    public void changeCanLogin(String loginPhoneNum, String loginEditVerificationCode, boolean privacyChecked) {
        if (isActive()) { //必须加
            getView().changeLoginColor(false);
            if (loginPhoneNum.length() < 1) {//手机位数小于11
                mCanLogin = -1;
            } else if (loginPhoneNum.length() < 11) {
                mCanLogin = 1;
            } else if (!ValidatePhoneUtil.validateMobileNumber(loginPhoneNum)) {//判断是否是手机号
                mCanLogin = 2;
            } else if (loginEditVerificationCode.length() < 1) {//判断验证码是否合规
                mCanLogin = 3;
            } else if (loginEditVerificationCode.length() != 6) {//判断验证码是否合规
                mCanLogin = 5;
            } else if (!privacyChecked) {//没有选中已读协议
                mCanLogin = 4;
            } else {
                mCanLogin = 0;
                getView().changeLoginColor(true);
            }
        }
    }

    @Override
    public void login(String loginPhoneNum, String loginEditVerificationCode, String loginEditInviteCode) {
        switch (mCanLogin) {
            case 0:
                requestLogin(loginPhoneNum, loginEditVerificationCode, loginEditInviteCode);
                break;
            case 1:
            case 2:
                ToastView.showToast(getContext(), "手机号有误，请核对后重新输入");
                break;
            case 3:
                ToastView.showToast(getContext(), "验证码不能为空，请输入验证码");
                break;
            case 5:
                ToastView.showToast(getContext(), "请输入正确的验证码");
                break;
            case 4:
                ToastView.showToast(getContext(), "请阅读并勾选用户协议");
                break;
            case -1:
                ToastView.showToast(getContext(), "手机号不能为空，请输入手机号");
                break;
        }
    }

    public void requestLogin(String loginPhoneNum, String loginEditVerificationCode, String loginEditInviteCode) {
        Map<String, Object> params = new HashMap<>();

        params.put("invitationCode", loginEditInviteCode);
        params.put("phone", loginPhoneNum);
        params.put("code", loginEditVerificationCode);

        NetWork.getInstance().post(getContext(), ChildUrl.login, params, new RequestSateListener<LoginBean>() {
            @Override
            public void onSuccess(int code, LoginBean bean) {

                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(bean.getYbCode());
                userInfo.setPhone(bean.getPhone());
                userInfo.setNickName(bean.getNickName());
                userInfo.setToken(bean.getToken());
                userInfo.setImSign(bean.getUsersig());
                userInfo.setInvitationCode(bean.getInvitationCode());
                UserStore.saveUser(userInfo);
                ToastView.showToast(getContext(), "登录成功");
                LoginManager.login();
                Intent intent = new Intent();
                intent.setAction(BroadcastProtocol.BROAD_LOGIN_SUCCESS);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                if (isActive()) { //必须加
                    getView().close();
                }

            }

            @Override
            public void onFailure(Throwable e) {
            }
        });
    }

    public void checkPhone(String phone) {
        String url = ChildUrl.checkPhone + "/" + phone;
        NetWork.getInstance().get(getContext(), url, null, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String str) {
                if (isActive() && !TextUtils.isEmpty(str)) { //必须加
                    if (Boolean.parseBoolean(str)) {
                        getView().changeInviteView(false);
                    } else {
                        getView().changeInviteView(true);
                    }
                }
            }

            @Override
            public void onFailure(Throwable var1) {
                if (isActive()) { //必须加
                    getView().changeInviteView(true);
                }
            }
        });
    }


    public TextWatcher getTextWatcher(int type) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (type == 1) {
                    String editable = getView().getLoginPhoneNum();
                    if (editable.length() == 1 && !editable.equals("1")) {
                        if (isActive()) {
                            getView().clearPhoneNum();
                        }
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isActive()) {
                    changeCanGetCode(getView().getLoginPhoneNum());
                    changeCanLogin(getView().getLoginPhoneNum(),
                            getView().getLoginEditVerificationCode(),
                            getView().getPrivacyChecked());
                }
            }
        };
        return textWatcher;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages((Object) null);
        jiYan.destroy();
    }

    public void callNoNet() {
//        ToastView.showToast("网络异常,请检查网络后重试");
        if(jiYan != null){
//            jiYan.continueJY(null);
            jiYan.dismiss();
        }
    }
}
