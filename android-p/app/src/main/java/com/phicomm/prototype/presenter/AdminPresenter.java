package com.phicomm.prototype.presenter;

import android.text.TextUtils;

import com.phicomm.prototype.R;
import com.phicomm.prototype.bean.LocalLogin;
import com.phicomm.prototype.bean.LoginStatus;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.constants.NetConfig;
import com.phicomm.prototype.manager.CloudManager;
import com.phicomm.prototype.model.local.AdminModel;
import com.phicomm.prototype.net.callback.BeanCallback;
import com.phicomm.prototype.presenter.viewback.AdminView;
import com.phicomm.prototype.utils.Base64Utils;
import com.phicomm.prototype.utils.CommonUtils;

/**
 * 本地管理Presenter，主要涉及本地登陆与管理账户相关
 * Created by qisheng.lv on 2017/4/17.
 */
public class AdminPresenter extends BasePresenter {

    private AdminView mView;
    private AdminModel mModel;

    public AdminPresenter(String tag,AdminView adminView) {
        this.mView = adminView;
        mModel = new AdminModel(tag);
    }

    public void checkLogin() {
        mModel.checkLogin(AppConstans.ACTION_GET, new BeanCallback<LoginStatus>() {

            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onCheckLoginFailure(code, msg);
                }
            }

            @Override
            public void onSuccess(LoginStatus loginStatus) {
                if (isAlive && loginStatus.retLoginstatus != null) {
                    mView.onCheckLoginSuccess(loginStatus.retLoginstatus);
                }
            }
        });
    }

    /**
     * 本地连接，管理员登陆
     *
     * @param username
     * @param password 密码需要通过Base64加密
     */
    public void loginLocal(String username, String password) {
        String entryPwd = Base64Utils.encode(password.getBytes());
        mModel.loginLocal(username, entryPwd, new BeanCallback<LocalLogin>() {

            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    if (code == NetConfig.ERROR_TIMEOUT_CODE) {
                        mView.onLoginLocalFailure(code, CommonUtils.getString(R.string.login_admin_failure_notfx));
                    } else {
                        mView.onLoginLocalFailure(code, msg);
                    }
                }
            }

            @Override
            public void onSuccess(LocalLogin localLogin) {
                if (isAlive) {
                    int state = localLogin.retLoginresult.getLOGINSTATUS();
                    String result = localLogin.retLoginresult.getRESULT();

                    if (state == 0 && !TextUtils.isEmpty(result) && result.equalsIgnoreCase("OK")) {
                        CloudManager.getInstance().setUseCloud(false);
                        mView.onLoginLocalSuccess();
                    } else if (!TextUtils.isEmpty(result) && result.equalsIgnoreCase("Unauthorized")) {
                        mView.onLoginLocalFailure(0, CommonUtils.getString(R.string.login_admin_failure_pwd));
                    } else {
                        mView.onLoginLocalFailure(0, CommonUtils.getString(R.string.login_admin_failure));
                    }
                }
            }

        });
    }

}
