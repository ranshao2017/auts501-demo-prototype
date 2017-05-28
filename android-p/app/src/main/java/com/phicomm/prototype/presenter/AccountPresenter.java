package com.phicomm.prototype.presenter;

import android.text.TextUtils;

import com.phicomm.prototype.MyApplication;
import com.phicomm.prototype.R;
import com.phicomm.prototype.bean.Authorization;
import com.phicomm.prototype.bean.CloudLogin;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.manager.CloudManager;
import com.phicomm.prototype.manager.DataStatisticsManager;
import com.phicomm.prototype.model.cloud.AccountModel;
import com.phicomm.prototype.net.callback.BaseCallback;
import com.phicomm.prototype.net.callback.BeanCallback;
import com.phicomm.prototype.presenter.viewback.AccountView;
import com.phicomm.prototype.utils.CommonUtils;
import com.phicomm.prototype.utils.EntryUtils;
import com.phicomm.prototype.utils.SpfUtils;
import com.phicomm.prototype.utils.SystemUtils;

import java.util.Map;

import okhttp3.Request;

/**
 * 云账号Presenter
 * Created by qisheng.lv on 2017/4/12.
 */
public class AccountPresenter extends BasePresenter {
    private static final String CLIENT_ID = "7";
    private static final String CLIENT_SECRET = "feixun*123";
    private static final String RESPONSE_TYPE = "code";
    private static final String SCOPE = "write";

    private AccountView mView;
    private AccountModel mModel;

    public AccountPresenter(String tag, AccountView accountView) {
        this.mView = accountView;
        mModel = new AccountModel(tag);
    }

    /**
     * 获取授权码
     */
    public void authorization() {
        mModel.authorization(CLIENT_ID, CLIENT_SECRET, null, RESPONSE_TYPE, SCOPE, new BeanCallback<Authorization>() {
            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onAuthorizationFailure(code, msg);
                }
            }

            @Override
            public void onSuccess(Authorization authorization) {
                if (isAlive) {
                    if (TextUtils.isEmpty(authorization.getAuthorizationcode())) {
                        mView.onAuthorizationFailure(0, CommonUtils.getString(R.string.login_auth_fail));
                    } else {
                        CloudManager.getInstance().saveAuthCode(authorization.getAuthorizationcode());
                        mView.onAuthorizationSuccess(authorization.getAuthorizationcode());
                    }
                }
            }
        });

    }

    /**
     * 登陆云账号
     *
     * @param phonenumber
     * @param password
     */
    public void loginCloud(final String phonenumber, final String password) {
        addOnLoginDataStatistics();

        String authCode = CloudManager.getInstance().getAuthCode();
//        String authCode = "feixun.SH_7";
        String md5Pwd = EntryUtils.getMd5(password);
        mModel.loginCloud(authCode, null, md5Pwd, phonenumber, null, new BeanCallback<CloudLogin>() {

            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onLoginFailure(code, msg);
                }
            }


            @Override
            public void onSuccess(CloudLogin cloudLogin) {
                SpfUtils.put(MyApplication.getContext(), AppConstans.SP_CLOUD_USER, phonenumber);
                SpfUtils.put(MyApplication.getContext(), AppConstans.SP_CLOUD_USER_PWD, password);
                if (isAlive) {
                    if (TextUtils.isEmpty(cloudLogin.getAccess_token()) || TextUtils.isEmpty(cloudLogin.getUid())) {
                        mView.onLoginFailure(0, CommonUtils.getString(R.string.login_token_fail));
                    } else {
                        if (!TextUtils.isEmpty(cloudLogin.getUid())) {
                            CloudManager.getInstance().saveUid(cloudLogin.getUid());
                        }
                        CloudManager.getInstance().setUseCloud(true);
                        CloudManager.getInstance().saveToken(cloudLogin.getAccess_token());
                        mView.onLoginSuccess(cloudLogin);
                    }
                }
            }

        });

    }

    private void addOnLoginDataStatistics() {
        Map<String, String> map = DataStatisticsManager.getEventDataTemplet();

        map.put("device_firm", SystemUtils.getDeviceBrand());
        map.put("device_model", SystemUtils.getSystemModel());
        DataStatisticsManager.onEvent(MyApplication.getContext(),
                AppConstans.ThirdParty.KEY_ON_LOGIN_CLICK, map);
    }

    public void exitLogin() {
        String token = CloudManager.getInstance().getToken();
        if (TextUtils.isEmpty(token) && isAlive) {
            mView.onLogoutSuccess();
            return;
        }

        mModel.logoutCloud(new BaseCallback() {
            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onLogoutFailure(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                CloudManager.getInstance().setUseCloud(false);
                DataStatisticsManager.onProfileSignOff();
                if (isAlive) {
                    mView.onLogoutSuccess();
                }
            }
        });

    }


}
