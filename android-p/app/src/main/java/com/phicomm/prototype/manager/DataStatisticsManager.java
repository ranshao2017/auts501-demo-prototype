package com.phicomm.prototype.manager;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.prototype.MyApplication;
import com.phicomm.prototype.bean.NetStatistics;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.constants.UrlConfig;
import com.phicomm.prototype.utils.AppInfoUtils;
import com.phicomm.prototype.utils.LogUtils;
import com.phicomm.prototype.utils.NetUtils;
import com.phicomm.prototype.utils.SpfUtils;
import com.phicomm.prototype.utils.SystemUtils;
import com.umeng.analytics.MobclickAgent;
import com.wbtech.ums.JsonUtil;
import com.wbtech.ums.UmsAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by rongwei.huang on 2017/4/20.
 * <p>
 * Used for data statistics, include umeng and razor
 */

public class DataStatisticsManager {

    private static final String[] URL_STATISTICS = new String[]{UrlConfig.AccountCloudUrl.LOGIN};

    public static void init(Context context) {
        //init razor
        UmsAgent.init(context, AppConstans.ThirdParty.PHI_RAZOR_URL,
                AppConstans.ThirdParty.RAZOR_APP_KEY);
        UmsAgent.setDefaultReportPolicy(context, UmsAgent.SendPolicy.POST_NOW);
        UmsAgent.setDebugEnabled(true);
        UmsAgent.setDebugLevel(UmsAgent.LogLevel.Debug);

        //init umeng
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //close umeng exception catch.. use buggly..
        MobclickAgent.setCatchUncaughtExceptions(false);
    }

    public static void onResume(Context context) {
        //razor
        UmsAgent.onPause(context);
        // UMeng
        MobclickAgent.onResume(context);
    }

    public static void onPause(Context context) {
        //razor
        UmsAgent.onPause(context);
        //umeng
        MobclickAgent.onPause(context);
    }

    public static void onEvent(Context var0, String var1) {
        MobclickAgent.onEvent(var0, var1);
        UmsAgent.onEvent(var0, var1);
    }

    public static void onEvent(Context var0, String var1, String var2) {
        MobclickAgent.onEvent(var0, var1, var2);
        UmsAgent.onGenericEvent(var0, var1, var2);
    }

    public static void onEvent(Context context, String key, Map map) {
        MobclickAgent.onEvent(context, key, map);

        String jsonString = JsonUtil.toJsonString(map);
        UmsAgent.onEvent(context, key, "DataStatisticsManager", jsonString);
    }

    /**
     * 结构化事件接口
     *
     * @param context 当前Activity
     * @param keyPath 最大8个
     * @param value   数值参数
     * @param label   事件标签，事件的一个属性说明(一期只做采样不做计算，二期会对label进行计算)
     */
    public static void onEvent(Context context, List keyPath, int value, String label) {
        MobclickAgent.onEvent(context, keyPath, value, label);
    }

    /**
     * 计算事件接口
     *
     * @param context  context
     * @param key      key
     * @param value    parameters
     * @param duration
     */
    public static void onEventValue(Context context, String key, Map value,
                                    int duration) {
        MobclickAgent.onEventValue(context, key, value, duration);

        String jsonString = JsonUtil.toJsonString(value);
        UmsAgent.onEvent(context, key, "DataStatisticsManager", jsonString);

    }

    public static void onProfileSignIn(String id) {
        MobclickAgent.onProfileSignIn(id);
    }

    public static void onProfileSignIn(String provider, String id) {
        MobclickAgent.onProfileSignIn(provider, id);
    }

    public static void onProfileSignOff() {
        MobclickAgent.onProfileSignOff();
    }

    public static boolean isUrlStatistics(String url) {
        for (String str : URL_STATISTICS) {
            if (str.equals(url)) {
                return true;
            }
        }
        return false;
    }

    public static void saveNetSta(final String url, final NetStatistics netStatistics) {
        if (TextUtils.isEmpty(url) || netStatistics == null || !isUrlStatistics(url)) {
            return;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String content = JSONObject.toJSON(netStatistics).toString();
                    if (!TextUtils.isEmpty(content)) {
                        SpfUtils.put(MyApplication.getContext(), AppConstans.SP_NET_STATISTICS + url, content);
                    }
                } catch (Exception e) {
                    LogUtils.d(e);
                }
            }
        };

        ThreadPollManager.getInstance().executeTask(runnable);
    }

    public static NetStatistics getNetSta(String url) {
        try {
            String json = (String) SpfUtils.get(MyApplication.getContext(), AppConstans.SP_NET_STATISTICS + url, "");
            return JSONObject.parseObject(json, NetStatistics.class);
        } catch (Exception e) {
            LogUtils.d(e);
        }
        return null;
    }

    public static Map<String, String> getEventDataTemplet() {
        Map<String, String> eventDataTemplet = new HashMap<>(4);
        eventDataTemplet.put("device_brand", SystemUtils.getDeviceBrand());
        eventDataTemplet.put("network_type", NetUtils.getNetType(MyApplication.getContext()));
        eventDataTemplet.put("app_version",
                String.valueOf(AppInfoUtils.getAppVersionCode(MyApplication.getContext())));
        eventDataTemplet.put("target_sdk",
                String.valueOf(AppInfoUtils.getTargetSdkVersion(MyApplication.getContext())));

        return eventDataTemplet;
    }
}
