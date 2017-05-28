package com.phicomm.prototype.manager;

import android.content.Context;

import com.phicomm.prototype.BuildConfig;
import com.phicomm.prototype.utils.LogUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by rongwei.huang on 2017/5/2.
 */

public class MessagePushManager {
    public static void initUmengMessagePush(Context context) {
        PushAgent mPushAgent = PushAgent.getInstance(context);
        mPushAgent.setPushCheck(BuildConfig.isDebug);
        mPushAgent.setDebugMode(BuildConfig.isDebug);

        //设置通知数量最多三条
        mPushAgent.setDisplayNotificationNumber(3);

        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(final String deviceToken) {
                LogUtils.d("deviceToken: " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.e("s: " + s + " s1: " + s1);
            }
        });

    }
}
