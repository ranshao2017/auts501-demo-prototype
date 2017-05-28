package com.phicomm.prototype.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.phicomm.prototype.MyApplication;
import com.phicomm.prototype.R;
import com.phicomm.prototype.bean.CloudLogin;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.manager.CloudManager;
import com.phicomm.prototype.presenter.AccountPresenter;
import com.phicomm.prototype.presenter.viewback.AccountView;
import com.phicomm.prototype.utils.AppManager;
import com.phicomm.prototype.utils.RegexUtils;
import com.phicomm.prototype.utils.SpfUtils;
import com.phicomm.prototype.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * 登陆云管理
 * Created by qisheng.lv on 2017/4/12.
 */
public class LoginCloudActivity extends BaseActivity {

    @BindView(R.id.et_user)
    EditText mEtUser;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;

    private AccountPresenter mPresenter;
    private String mUser;
    private String mPwd;
    public long mFirstTime;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_cloud);
    }

    @Override
    public void afterInitView() {
        hideBack();
        setPageTitle(getString(R.string.title_login_cloud));
        showTvMenu(getString(R.string.title_login_local));
        initPresenter();
        getUser();

    }

    private void getUser() {
        String user = (String) SpfUtils.get(MyApplication.getContext(), AppConstans.SP_CLOUD_USER, "");
        String pwd = (String) SpfUtils.get(MyApplication.getContext(), AppConstans.SP_CLOUD_USER_PWD, "");
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pwd)) {
            mEtUser.setText(user);
            mEtPwd.setText(pwd);
        }
    }

    @Override
    public void onTvMenuClick(TextView tv) {
        startActivity(new Intent(this, LoginLocalActivity.class));
        finish();
    }

    private void initPresenter() {
        mPresenter = new AccountPresenter(mActivityName, new AccountView() {
            //获取授权码失败，终止整个流程
            @Override
            public void onAuthorizationFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            //获取授权码成功，发起登录
            @Override
            public void onAuthorizationSuccess(String authorizationcode) {
                doLogin();
            }

            //登录失败
            @Override
            public void onLoginFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            //登录成功
            @Override
            public void onLoginSuccess(CloudLogin cloudLogin) {
                hideLoading();
//                ToastUtil.show(getString(R.string.login_cloud_success));
                gotoCloudRouters();
            }
        });
    }


    //点击登录
    @OnClick(R.id.btn_login)
    public void btn_login() {
        mUser = mEtUser.getText().toString().trim();
        mPwd = mEtPwd.getText().toString().trim();
        if (checkInput()) {
            loginPrepare();
        }
    }

    //长按登录，进入工厂界面
    @OnLongClick(R.id.btn_login)
    public boolean btn_login_long() {
        startActivity(new Intent(this, FactoryActivity.class));
//        startActivity(new Intent(this, TestActivity.class));
//        ToastUtil.show("btn_login_long");
        return true;
    }


    /**
     * 有授权码，直接登陆，否则先获取授权码。
     */
    private void loginPrepare() {
        showLoading();
        if (CloudManager.getInstance().hasAuthCode()) {
            doLogin();
        } else {
            authorization();
        }
    }

    /**
     * 获取授权码
     */
    private void authorization() {
        mPresenter.authorization();
    }

    /**
     * 发起登陆
     */
    private void doLogin() {
        mPresenter.loginCloud(mUser, mPwd);
    }

    /**
     * 页面跳转
     */
    private void gotoCloudRouters() {
        startActivity(new Intent(this, CloudRoutersActivity.class));
//        finish();
    }


    private boolean checkInput() {
        if (!RegexUtils.checkMobilePhone(mUser)) {
            ToastUtil.show(this, R.string.login_user_illegal);
            return false;
        }

        if (TextUtils.isEmpty(mPwd) || mPwd.length() < 6) {
            ToastUtil.show(this, R.string.login_pwd_illegal);
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(mActivityName);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (0.0 != mFirstTime && secondTime - mFirstTime < 2000) {
                AppManager.getAppManager().finishAllActivity();
                System.exit(0);
            } else {
                ToastUtil.show(this, R.string.app_exit_hit);
                mFirstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
