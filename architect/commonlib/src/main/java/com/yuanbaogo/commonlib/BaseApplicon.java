package com.yuanbaogo.commonlib;

import android.content.Intent;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.tencent.imsdk.v2.V2TIMGroupChangeInfo;
import com.tencent.imsdk.v2.V2TIMGroupListener;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.ugc.TXUGCBase;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.yuanbaogo.datacenter.remote.netmonitor.NetWorkMonitorManager;
import com.yuanbaogo.im.contract.IMContract;
import com.yuanbaogo.im.login.LoginManager;
import com.yuanbaogo.libbase.GroupSystemMessage;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;
import com.yuanbaogo.libbase.config.EnvironmentConfig;

import java.util.List;

public class BaseApplicon extends MultiDexApplication {


    public static BaseApplicon baseApplicon;

    private String ugcKey = "8c714d11042b14e515a18db82285955c";
    private String ugcLicenceUrl = "https://license.vod2.myqcloud.com/license/v2/1305625675_1/v_cube.license";

    private String ugcKeylive = "8c714d11042b14e515a18db82285955c";
    private String ugcLicenceUrllive = "http://license.vod2.myqcloud.com/license/v1/737eaf132516c1ff7e0042893bb6cf18/TXLiveSDK.licence";


    @Override
    public void onCreate() {
        super.onCreate();
        baseApplicon = this;
        initBase();

        initARouter();

        initIM();
        initVideo();
        initUM();
//        initWeb();
        NetWorkMonitorManager.getInstance().init(this);
    }

    /**
     * 初始化ARouter
     */
    private void initARouter() {
        if (EnvironmentConfig.ENVIRONMENT_DEV_TEST == EnvironmentConfig.getsEnvironment()) {//版本上线需要关闭，否则有安全风险
            //如下两行必须写在init之前，否则配置在init过程中将无效
            ARouter.openLog();//打印日志
            ARouter.openDebug();//调试模式（如果在InstantRun模式下运行，必须开启调试模式）
        }
        //官方建议在Application里初始化使用
        ARouter.init(this);
    }

    private void initUM() {
        UMConfigure.init(this, "60f680af2a1a2a58e7de3738", "元宝", UMConfigure.DEVICE_TYPE_PHONE, "");
        // 微信设置
        PlatformConfig.setWeixin("wxda43b1b489d58d62", "6d80794e4f00ab528ee5d83bca6a3bb2");
        PlatformConfig.setWXFileProvider("com.yuanbaogo.app.fileprovider");
        if (EnvironmentConfig.ENVIRONMENT_DEV_TEST == EnvironmentConfig.getsEnvironment()) {//版本上线需要关闭，否则有安全风险
            UMConfigure.setLogEnabled(true);
        }
        //设置测试版
//        Config.setMiniTest();
        //设置预览版
//        Config.setMiniPreView();
    }

//    /**
//     *初始化X5浏览器
//     */
//    private void initWeb() {
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is " + arg0);
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
//        //x5内核初始化接口
//        QbSdk.initX5Environment(getApplicationContext(),  cb);
//    }

    /**
     * 初始化video
     */
    private void initVideo() {
        // 短视频licence设置
        TXUGCBase.getInstance().setLicence(this, ugcLicenceUrl, ugcKey);
        TXLiveBase.getInstance().setLicence(this, ugcLicenceUrllive, ugcKeylive);
    }

    /**
     * 初始化全局变量
     */
    private void initBase() {
        ApplicationConfigHelper.mApplication = this;
        ApplicationConfigHelper.versionCode = BuildConfig.VERSION_CODE;
        EnvironmentConfig.setsEnvironment(BuildConfig.ENV);
    }

    //时间紧，暂时先放着，后面优化

    private void initIM() {
// 1. 从 IM 控制台获取应用 SDKAppID，详情请参考 SDKAppID。
// 2. 初始化 config 对象
        V2TIMSDKConfig config = new V2TIMSDKConfig();
// 3. 指定 log 输出级别，详情请参考 SDKConfig。
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_INFO);
// 4. 初始化 SDK 并设置 V2TIMSDKListener 的监听对象。
// initSDK 后 SDK 会自动连接网络，网络连接状态可以在 V2TIMSDKListener 回调里面监听。

//        1400556104  正式
//        1400555030  测试
        V2TIMManager.getInstance().initSDK(baseApplicon, 1400556104, config, new V2TIMSDKListener() {
            // 5. 监听 V2TIMSDKListener 回调
            @Override
            public void onConnecting() {
                // 正在连接到腾讯云服务器
//                Log.e("network", "===im==onConnecting====");
            }

            @Override
            public void onConnectSuccess() {
                // 已经成功连接到腾讯云服务器
//                Log.e("network", "===im==onConnectSuccess====");
            }

            @Override
            public void onKickedOffline() {
                // 当前用户被踢下线
//                Log.e("network", "===im==onKickedOffline====");
            }

            @Override
            public void onUserSigExpired() {
                super.onUserSigExpired();
//                Log.e("network", "===im==onUserSigExpired====");
                LoginManager.getUserSig(getApplicationContext());
            }

            @Override
            public void onSelfInfoUpdated(V2TIMUserFullInfo info) {
                super.onSelfInfoUpdated(info);
//                Log.e("network", "===im==onSelfInfoUpdated====");
            }

            @Override
            public void onConnectFailed(int code, String error) {
                // 连接腾讯云服务器失败
//                Log.e("network", "===im==onConnectFailed====");
            }
        });

        V2TIMManager.getInstance().setGroupListener(new V2TIMGroupListener() {

            @Override
            public void onMemberEnter(String groupID, List<V2TIMGroupMemberInfo> memberList) {

                if (memberList != null && memberList.size() > 0) {
//                    Log.e("network", "===im==onMemberEnter====" + memberList.get(0).getNickName());
//                    Log.e("network", "===im==onMemberEnter====" + memberList.get(0).getFaceUrl());
                    joinMessage(memberList, groupID);

                }
            }

            @Override
            public void onMemberLeave(String groupID, V2TIMGroupMemberInfo member) {
//                Log.e("network", "===im==onMemberLeave====" + member.getNickName());
            }


            @Override
            public void onGroupDismissed(String groupID, V2TIMGroupMemberInfo opUser) {
//                Log.e("network", "===im==onGroupDismissed====" + opUser);
            }

            @Override
            public void onGroupInfoChanged(String groupID, List<V2TIMGroupChangeInfo> changeInfos) {
                super.onGroupInfoChanged(groupID, changeInfos);

//                Log.e("network", "*********onGroupInfoChanged**********" + changeInfos.get(0).getValue());
                Intent intent = new Intent(IMContract.IM_GROUPINFOCHANGED);
                intent.putExtra("groupID", groupID);
                intent.putExtra("GroupChangeInfo", changeInfos.get(0).getValue());
//
//                for (V2TIMGroupChangeInfo info : changeInfos) {
//                    intent.putExtra(info.getKey(), info.getValue());
//                }

                sendBroadcast(intent);
            }

            @Override
            public void onReceiveRESTCustomData(String groupID, byte[] customData) {
                super.onReceiveRESTCustomData(groupID, customData);
//                GroupSystemMessage bean = JSON.parseObject(customData, GroupSystemMessage.class);
//                Log.e("network", "*********groupID**********" + groupID);
                GroupSystemMessage bean = JSON.parseObject(customData, GroupSystemMessage.class);
//                Log.e("network", "===im==onReceiveRESTCustomData====" + bean.getCode());
//                Log.e("network", "===im==onReceiveRESTCustomData==getData==" + bean.getData());
//                Log.e("network", "===im==onReceiveRESTCustomData==Anchor==" + bean.getAnchorData());
//                Log.e("network", "===im==onReceiveRESTCustomData==Audience==" + bean.getAudienceData());

                Intent msgIntent = new Intent(IMContract.IM_RECEIVERESTCUSTOMDATA);
                msgIntent.putExtra("groupID", groupID);
                msgIntent.putExtra("code", bean.getCode());
                msgIntent.putExtra("data", bean.getData());
                msgIntent.putExtra("Anchor", bean.getAnchorData());
                msgIntent.putExtra("Audience", bean.getAudienceData());
                sendBroadcast(msgIntent);
            }
        });
    }

    private void joinMessage(List<V2TIMGroupMemberInfo> memberList, String groupID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (V2TIMGroupMemberInfo info : memberList) {
                        Intent intent = new Intent(IMContract.IM_MEMBERENTER);
                        intent.putExtra("groupID", groupID);
                        intent.putExtra("memberEnterName", info.getNickName());
                        intent.putExtra("memberEnterHeadurl", info.getFaceUrl());
                        sendBroadcast(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static BaseApplicon getApp() {
        return baseApplicon;
    }


}
