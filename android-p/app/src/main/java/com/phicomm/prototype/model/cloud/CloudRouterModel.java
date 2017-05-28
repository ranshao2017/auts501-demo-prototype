package com.phicomm.prototype.model.cloud;

import com.phicomm.prototype.constants.UrlConfig;
import com.phicomm.prototype.net.OkHttpUtil;
import com.phicomm.prototype.net.callback.BaseCallback;

/**
 * 云设备相关
 * Created by qisheng.lv on 2017/4/19.
 */

public class CloudRouterModel {

    private String mTag;
    private String mTagDevType = "devType";

    public CloudRouterModel(String tag) {
        this.mTag = tag;
    }

    /**
     * 获取账号下绑定的路由器
     *
     * @param devType
     * @param callback
     */
    public void getBindRouters(String devType, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.DeviceCloudUrl.GET_BIND_DEVICES)
                .addParams(mTagDevType, devType)
                .doRequest(mTag, callback);
    }

    /**
     * 解绑设备
     *
     * @param accountIDs
     * @param authorizationcode
     * @param devType
     * @param devSN
     * @param callback
     */
    public void unBindRouter(String accountIDs, String authorizationcode, String devType, String devSN, BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.DeviceCloudUrl.UNBIND_ROUTER)
                .addParams("accountIDs", accountIDs)
                .addParams("authorizationcode", authorizationcode)
                .addParams(mTagDevType, devType)
                .addParams("devSN", devSN)
                .doRequest(mTag, callback);
    }

    /**
     * 检查设备的绑定状态
     *
     * @param devSN
     * @param devType
     * @param callback
     */
    public void checkBindState(String devSN, String devType, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.DeviceCloudUrl.CHECK_BIND_STATE)
                .addParams("devSN", devSN)
                .addParams(mTagDevType, devType)
                .doRequest(mTag, callback);
    }

    /**
     * 绑定设备
     *
     * @param devName
     * @param devSN
     * @param devType
     * @param callback
     */
    public void bindRouter(String devName, String devSN, String devType, BaseCallback callback) {
        OkHttpUtil.bindRouter(UrlConfig.DeviceCloudUrl.BIND_ROUTER)
                .addParams("devName", devName)
                .addParams("devSN", devSN)
                .addParams(mTagDevType, devType)
                .doRequest(mTag, callback);
    }

}
