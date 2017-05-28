package com.phicomm.prototype;

import android.app.Application;
import android.content.Context;

import com.phicomm.prototype.manager.DataStatisticsManager;
import com.phicomm.prototype.manager.MessagePushManager;
import com.phicomm.prototype.utils.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;

import org.xutils.x;


/**
 * Application基类
 * Created by QiSheng on 2017/4/13.
 */
public class MyApplication extends Application {

    private static Context context;
    public static boolean isUseCloud;
    public static boolean isJunitTest;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initThirdParty();
    }

    /**
     * 第三方框架初始化
     */
    private void initThirdParty() {
        //XLog
        LogUtils.init();
        //XUtils
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        //init data statistics razor and umeng
        DataStatisticsManager.init(this);
        //Umeng message push
        MessagePushManager.initUmengMessagePush(this);
        //bugly
        CrashReport.initCrashReport(getApplicationContext());
    }


    public static Context getContext() {
        return context;
    }

}
