package com.yuanbaogo.login.bindphone.presenter;

import android.os.Handler;
import android.os.Message;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.ValidatePhoneUtil;
import com.yuanbaogo.login.bindphone.contract.BindPhoneContract;
import com.yuanbaogo.zui.toast.ToastView;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/16/21 1:19 PM
 * @Modifier:
 * @Modify:
 */
public class BindPhonePresenter extends MvpBasePersenter<BindPhoneContract.View> implements BindPhoneContract.Presenter {

    private int mInviteTime = 0;//验证码间隔时间

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    if (mInviteTime > 0) {
                        if (isActive()) {
                            getView().bindPhoneCodeColor(false);
                        }
                    } else {
                        if (isActive()) {
                            mInviteTime = 0;
                            getView().bindPhoneCodeColor(true);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void verification(int mCanGetCode) {
        switch (mCanGetCode) {
            case 0:
                //TODO guwei 获得验证码接口
                ToastView.showToast(getContext(), "假装你收到验证码了");
                mInviteTime = 60;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (mInviteTime = 6; mInviteTime == 0; mInviteTime--) {
                            handler.sendEmptyMessage(100);
                            try {
                                Thread.sleep(1000);//休眠1秒
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case 1:
                //TODO guwei 弹窗提示有问题
            case 2:
                ToastView.showToast(getContext(), "请输入正确的手机号！");
                break;
            case 3:
                ToastView.showToast(getContext(), "请等待" + mInviteTime + "秒后重新获取验证码！");
                break;
        }
    }

    @Override
    public int changeCanBind(String bindPhoneNum, String bindVerificationCode) {
        if (isActive()) { //必须加
            getView().bindPhoneColor(false);
            if (bindPhoneNum.length() < 11) {//手机位数小于11
                return 1;
            }
            if (!ValidatePhoneUtil.validateMobileNumber(bindPhoneNum)) {//判断是否是手机号
                return 2;
            }
            if (bindVerificationCode.length() != 6) {//判断验证码是否合规
                return 3;
            }
            getView().bindPhoneColor(true);
        }
        return 0;
    }

    @Override
    public int changeCanGetCode(String loginPhoneNum) {
        if (isActive()) { //必须加
            getView().bindPhoneCodeColor(false);
            if (loginPhoneNum.length() < 11) {//手机位数小于11
                return 1;
            }
            if (!ValidatePhoneUtil.validateMobileNumber(loginPhoneNum)) {//判断是否是手机号
                return 2;
            }
            getView().bindPhoneCodeColor(true);
        }
        return 0;
    }

    @Override
    public void bindPhone(int canBind, String bindPhoneNum, String bindVerificationCode) {
        switch (canBind) {
            case 0:
                //TODO HG 调用绑定接口
                requestBindPhone(ChildUrl.login, bindPhoneNum, bindVerificationCode);
                break;
            case 1:
                //TODO guwei 弹窗提示有问题
            case 2:
                ToastView.showToast(getContext(), "请输入正确的手机号！");
                break;
            case 3:
                ToastView.showToast(getContext(), "请输入正确的验证码！");
                break;
        }
    }

    private void requestBindPhone(String login, String bindPhoneNum, String bindVerificationCode) {
        ToastView.showToast(getContext(), "调用绑定接口,并执行登录");
    }

}
