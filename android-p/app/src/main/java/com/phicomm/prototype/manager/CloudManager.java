package com.phicomm.prototype.manager;

import android.text.TextUtils;

import com.phicomm.prototype.MyApplication;
import com.phicomm.prototype.bean.CloudRouterList;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.utils.SpfUtils;

import static com.phicomm.prototype.utils.SpfUtils.put;

/**
 * 云账号管理类
 * Created by qisheng.lv on 2017/4/12.
 */

public class CloudManager {

    private static volatile CloudManager mInstance;

    private CloudManager() {

    }

    public static CloudManager getInstance() {
        if (mInstance == null) {
            synchronized (CloudManager.class) {
                if (mInstance == null) {
                    mInstance = new CloudManager();
                }
            }
        }
        return mInstance;
    }

    public void saveAuthCode(String code) {
        if (!TextUtils.isEmpty(code)) {
            put(MyApplication.getContext(), AppConstans.SP_AUTHORIZATIONCODE, code);
        }
    }

    public String getAuthCode() {
        return (String) SpfUtils.get(MyApplication.getContext(), AppConstans.SP_AUTHORIZATIONCODE, "");
    }

    public boolean hasAuthCode() {
        String code = getAuthCode();
        return !TextUtils.isEmpty(code);
    }

    public void saveToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            put(MyApplication.getContext(), AppConstans.SP_ACCESS_TOKEN, token);
        }
    }

    public String getToken() {
        return (String) SpfUtils.get(MyApplication.getContext(), AppConstans.SP_ACCESS_TOKEN, "");
    }

    public boolean isUseCloud() {
        return MyApplication.isUseCloud;
    }

    public void setUseCloud(boolean isCloud) {
        MyApplication.isUseCloud = isCloud;
    }

    public void setUsedRouter(CloudRouterList.CloudRouter cloudRouter) {
        String mac = cloudRouter.getDevMac();
        if (!TextUtils.isEmpty(mac)) {
            put(MyApplication.getContext(), AppConstans.SP_USED_ROUTER_MAC, mac);
        }
    }

    public String getUsedRouterMac() {
        return (String) SpfUtils.get(MyApplication.getContext(), AppConstans.SP_USED_ROUTER_MAC, "");
    }

    public void saveUid(String code) {
        if (!TextUtils.isEmpty(code)) {
            put(MyApplication.getContext(), AppConstans.SP_USER_UID, code);
            DataStatisticsManager.onProfileSignIn(code);
        }
    }

    public String getUid() {
        return (String) SpfUtils.get(MyApplication.getContext(), AppConstans.SP_USER_UID, "");
    }

}
