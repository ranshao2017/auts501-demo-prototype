package com.phicomm.prototype.model.cloud;

import com.phicomm.prototype.constants.UrlConfig;
import com.phicomm.prototype.net.OkHttpUtil;
import com.phicomm.prototype.net.callback.BaseCallback;

/**
 * 云账号网络Model
 * Created by qisheng.lv on 2017/4/12.
 */
public class AccountModel {
    private String mTag;

    public AccountModel(String tag) {
        this.mTag = tag;
    }

    /**
     * 获取授权码
     *
     * @param client_id
     * @param client_secret
     * @param redirect_uri
     * @param response_type
     * @param scope
     * @param callback
     */
    public void authorization(String client_id, String client_secret, String redirect_uri, String response_type, String scope, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.AccountCloudUrl.GET_AUTHORIZATION)
                .addParams("client_id", client_id)
                .addParams("client_secret", client_secret)
                .addParams("redirect_uri", redirect_uri)
                .addParams("response_type", response_type)
                .addParams("scope", scope)
                .doRequest(mTag, callback);
    }

    /**
     * 云账号登录
     *
     * @param authorizationcode
     * @param mailaddress
     * @param password
     * @param phonenumber
     * @param username
     * @param callback
     */
    public void loginCloud(String authorizationcode, String mailaddress, String password, String phonenumber, String username, BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.AccountCloudUrl.LOGIN)
                .addParams("authorizationcode", authorizationcode)
                .addParams("mailaddress", mailaddress)
                .addParams("password", password)
                .addParams("phonenumber", phonenumber)
                .addParams("username", username)
                .doRequest(mTag, callback);
    }

    /**
     * 云账号退出登录
     *
     * @param callback
     */
    public void logoutCloud(BaseCallback callback) {
        OkHttpUtil.post(UrlConfig.AccountCloudUrl.LOGOUT)
                .doRequest(mTag, callback);
    }

}
