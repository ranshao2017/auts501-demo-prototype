package com.phicomm.prototype.model.local;

import com.phicomm.prototype.constants.UrlConfig;
import com.phicomm.prototype.net.OkHttpUtil;
import com.phicomm.prototype.net.callback.BaseCallback;

/**
 * 本地管理Model，主要涉及登陆与管理账户相关
 * Created by qisheng.lv on 2017/4/17.
 */
public class AdminModel {

    private String mTag;

    public AdminModel(String tag){
        this.mTag = tag;
    }

    public void checkLogin(String action, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.LocalUrl.CHECK_LOGIN)
                .addParams("action", action)
                .doRequest(mTag,callback);
    }

    /**
     * 本地管理员登录
     *
     * @param username
     * @param password
     * @param callback
     */
    public void loginLocal(String username, String password, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.LocalUrl.LOGIN_LOCAL)
                .addParams("username", username)
                .addParams("password", password)
                .doRequest(mTag,callback);
    }

}
