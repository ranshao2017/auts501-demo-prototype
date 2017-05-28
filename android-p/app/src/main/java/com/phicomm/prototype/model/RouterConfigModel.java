package com.phicomm.prototype.model;

import com.phicomm.prototype.bean.WifiConfig;
import com.phicomm.prototype.constants.UrlConfig;
import com.phicomm.prototype.net.OkHttpUtil;
import com.phicomm.prototype.net.callback.BaseCallback;

/**
 * 路由器配置model
 * Created by qisheng.lv on 2017/4/20.
 */
public class RouterConfigModel {

    private String mTag;

    public RouterConfigModel(String tag) {
        this.mTag = tag;
    }

    /**
     * 本地连接-获取路由器信息
     *
     * @param action
     * @param callback
     */
    public void getLocalRouterInfo(String action, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.LocalUrl.GET_ROUER_INFO)
                .addParams("action", action)
                .doRequest(mTag, callback);
    }

    /**
     * 获取路由器信息
     *
     * @param action
     * @param callback
     */
    public void getRouterInfo(String action, BaseCallback callback) {
        OkHttpUtil.command(UrlConfig.LocalUrl.GET_ROUER_INFO)
                .addParams("action", action)
                .doRequest(mTag, callback);
    }

    /**
     * Wifi设置
     *
     * @param wifiConfig
     * @param callback
     */
    public void setWifi(WifiConfig wifiConfig, BaseCallback callback) {
        OkHttpUtil.command(UrlConfig.LocalUrl.WIFI_SETTING)
                .addParams("action", wifiConfig.getAction())
                .addParams("STATUS", wifiConfig.getSTATUS())
                .addParams("BroadcatsSSID_24G", wifiConfig.getBroadcatsSSID_24G())
                .addParams("txpower", wifiConfig.getTxpower())
                .addParams("SSID", wifiConfig.getSSID())
                .addParams("PASSWORD", wifiConfig.getPASSWORD())
                .addParams("STATUS_5G", wifiConfig.getSTATUS_5G())
                .addParams("BroadcatsSSID_5G", wifiConfig.getBroadcatsSSID_5G())
                .addParams("TXPOWER_5G", wifiConfig.getTXPOWER_5G())
                .addParams("SSID_5G", wifiConfig.getSSID_5G())
                .addParams("PASSWORD_5G", wifiConfig.getPASSWORD_5G())
                .addParams("SmartConn", wifiConfig.getSmartConn())
                .doRequest(mTag, callback);
    }


}
