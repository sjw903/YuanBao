package com.yuanbaogo.im.group;

import android.content.Context;
import android.os.Handler;

import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfoResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.yuanbaogo.datacenter.local.user.UserInfo;
import com.yuanbaogo.datacenter.local.user.UserStore;

public class GroupManagerImpl {

    /**
     * 函数级公共Callback定义
     */
    public interface Callback {
        void onError(int code, String errInfo);

        void onSuccess(String nickName, String text, String groupID);
    }

    private Handler mHandler;

    private Context mContext;

    public GroupManagerImpl(final Context context) {
        this.mContext = context.getApplicationContext();
        this.mHandler = new Handler(this.mContext.getMainLooper());
    }

    public void runOnHandlerThread(Runnable runnable) {
        Handler handler = mHandler;
        if (handler != null) {
            handler.post(runnable);
        } else {
//            Log.e("im", "runOnHandlerThread -> Handler == null");
        }
    }

    public void joinGroup(String groupId) {

        int loginStatus = V2TIMManager.getInstance().getLoginStatus();

        if (V2TIMManager.V2TIM_STATUS_LOGOUT == loginStatus) {
            UserInfo user = UserStore.getUser();
            if (user != null) {
                V2TIMManager.getInstance().login(UserStore.getYbCode(), UserStore.getImSign(), new V2TIMCallback() {
                    @Override
                    public void onSuccess() {
//                        Log.e("network", "===im=login=onSuccess====");
                        toJoinGroup(groupId);
                    }

                    @Override
                    public void onError(int code, String desc) {
//                        Log.e("network", "===im=login=onError====" + desc);
                    }
                });
            }
        } else {
            toJoinGroup(groupId);
        }
    }

    public void toJoinGroup(String groupId) {
        runOnHandlerThread(new Runnable() {
            @Override
            public void run() {
                V2TIMManager.getInstance().joinGroup(groupId, "who care?", new V2TIMCallback() {
                    @Override
                    public void onError(int i, String s) {
//                        Log.e("network", "===im==joinGroup==onError==" + s);

                    }

                    @Override
                    public void onSuccess() {
//                        Log.e("network", "===im==joinGroup==onSuccess==" + groupId);

                    }
                });
            }
        });
    }

    public static void quitGroup(String groupID) {
        V2TIMManager.getInstance().quitGroup(groupID, new V2TIMCallback() {
            @Override
            public void onSuccess() {
//                Log.e("network", "===im==quitGroup==onSuccess==" + groupID);
            }

            @Override
            public void onError(int code, String desc) {
//                Log.e("network", "===im==quitGroup==onError==" + desc);
            }
        });
    }


    public static int getGroupOnlineMemberCount(String groupID) {
        int count = 0;
        V2TIMManager.getGroupManager().getGroupOnlineMemberCount(groupID, new V2TIMValueCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
//                Log.e("network", "===im==getGroupOnlineMemberCount==onSuccess==" + integer);
            }

            @Override
            public void onError(int code, String desc) {
//                Log.e("network", "===im==getGroupOnlineMemberCount==onError==" + desc);
            }
        });
        return count;
    }

    public static void getGroupMemberList(String groupID, long page) {

        V2TIMManager.getInstance().getGroupManager().getGroupMemberList(groupID, V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_COMMON, page, new V2TIMValueCallback<V2TIMGroupMemberInfoResult>() {
            @Override
            public void onSuccess(V2TIMGroupMemberInfoResult v2TIMGroupMemberInfoResult) {
//                Log.e("network", "===im==getGroupMemberList==onSuccess==" + v2TIMGroupMemberInfoResult.getMemberInfoList());
//                Log.e("network", "===im==getGroupMemberList==onSuccess==" + v2TIMGroupMemberInfoResult.getMemberInfoList().get(0).getCustomInfo());
            }

            @Override
            public void onError(int code, String desc) {
//                Log.e("network", "===im==getGroupMemberList==onError==" + desc);
            }
        });
    }

    public static void sendGroupMessage(String text, String groupID, Callback callback) {
        V2TIMManager.getInstance().sendGroupTextMessage(text, groupID, V2TIMMessage.V2TIM_PRIORITY_HIGH, new V2TIMValueCallback<V2TIMMessage>() {
            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                if (callback != null) {
                    callback.onSuccess(v2TIMMessage.getNickName(), text, groupID);
                }
            }

            @Override
            public void onError(int code, String desc) {
            }
        });
    }


//    public static void addMsgListener(MLiveRoomSimpleMsgListener listener) {
//        V2TIMManager.getInstance().addSimpleMsgListener(listener);
//    }
//
//
//    public static void removeMsgListener(MLiveRoomSimpleMsgListener listener) {
//        V2TIMManager.getInstance().removeSimpleMsgListener(listener);
//    }


}
