package com.phicomm.prototype.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.phicomm.prototype.R;
import com.phicomm.prototype.bean.LoginStatus;
import com.phicomm.prototype.presenter.AdminPresenter;
import com.phicomm.prototype.presenter.viewback.AdminView;
import com.phicomm.prototype.utils.AppManager;
import com.phicomm.prototype.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登陆本地管理
 * Created by qisheng.lv on 2017/4/17.
 */
public class LoginLocalActivity extends BaseActivity {

    @BindView(R.id.et_admin)
    EditText mEtAdmin;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;

    private AdminPresenter mPresenter;
    private String mAdmin;
    private String mPwd;
    public long mFirstTime;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_local);
    }

    @Override
    public void afterInitView() {
        hideBack();
        setPageTitle(getString(R.string.title_login_local));
        showTvMenu(getString(R.string.title_login_cloud));
        initPresenter();
        checkLogin();
    }

    /**
     * 检查登陆状态，在此主要为了获取管理员账号
     */
    private void checkLogin() {
        mPresenter.checkLogin();
    }


    @Override
    public void onTvMenuClick(TextView view) {
        startActivity(new Intent(this, LoginCloudActivity.class));
        finish();
    }

    private void initPresenter() {
        mPresenter = new AdminPresenter(mActivityName, new AdminView() {
            @Override
            public void onCheckLoginFailure(int code, String msg) {
            }

            @Override
            public void onCheckLoginSuccess(LoginStatus.RetLoginstatus retLoginstatus) {
                String adminName = retLoginstatus.getUSERNAME();
                if (!TextUtils.isEmpty(adminName)) {
                    mEtAdmin.setText(adminName);
                    mEtPwd.requestFocus();
                }
            }

            @Override
            public void onLoginLocalFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onLoginLocalSuccess() {
                hideLoading();
                gotoFunction();
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void btn_login() {
        mAdmin = mEtAdmin.getText().toString().trim();
        mPwd = mEtPwd.getText().toString().trim();
        if (checkInput()) {
            doLogin();
        }
    }

    private void doLogin() {
        showLoading();
        mPresenter.loginLocal(mAdmin, mPwd);
//        LogUtils.d(DataStatisticsManager.getNetSta(UrlConfig.AccountCloudUrl.LOGIN).toString());
    }

    private void gotoFunction() {
//        finish();
        startActivity(new Intent(this, FunctionActivity.class));
    }


    private boolean checkInput() {
        if (TextUtils.isEmpty(mAdmin) || mAdmin.length() < 4) {
            ToastUtil.show(this, R.string.login_admin_illegal);
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
