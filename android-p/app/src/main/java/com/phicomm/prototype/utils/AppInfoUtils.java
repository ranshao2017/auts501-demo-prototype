package com.phicomm.prototype.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.phicomm.prototype.BuildConfig;

/**
 *
 */
public class AppInfoUtils {

    /**
     * 获取当前包的版本信息
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String strVer = null;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            strVer = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strVer;
    }


    /**
     * 获取当前包的版本编号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int nVer = 0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            nVer = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nVer;
    }

    /**
     * 获取渠道标志符
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        return (String) CommonUtils.getMetaDataByKey(context, "UMENG_CHANNEL");
    }

    /**
     * 获取当前targetSdkVersion
     */

    public static int getTargetSdkVersion(Context context) {
        int version = 0;
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(context.getPackageName(), 0);
            if (applicationInfo != null) {
                version = applicationInfo.targetSdkVersion;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 代表当前的环境是正式环境还是测试环境,true代表正式环境
     *
     * @return
     */
    public static boolean isInProductionEnvironment() {
        return BuildConfig.IS_IN_PRODUCTION_ENVIRONMENT;
    }

}
