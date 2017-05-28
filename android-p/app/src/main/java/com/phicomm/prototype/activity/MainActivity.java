package com.phicomm.prototype.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.phicomm.prototype.R;
import com.phicomm.prototype.presenter.AccountPresenter;
import com.phicomm.prototype.presenter.viewback.AccountView;
import com.phicomm.prototype.utils.ToastUtil;

import butterknife.OnClick;

/**
 * 主页
 * Created by qisheng.lv on 2017/4/12.
 */
public class MainActivity extends BaseActivity {
    private AccountPresenter mPresenter;


    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void afterInitView() {
        setPageTitle(getString(R.string.title_main));
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new AccountPresenter(mActivityName,new AccountView() {
            @Override
            public void onLogoutFailure(int code, String msg) {
                ToastUtil.show(MainActivity.this, msg);
            }

            @Override
            public void onLogoutSuccess() {
                ToastUtil.show(MainActivity.this, R.string.logout_success);
                gotoLogin();
            }
        });
    }

    @OnClick(R.id.btn_logout)
    public void btn_logout() {
        showDialog();
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setMessage("确定要退出登录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doLogout();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void doLogout() {
        mPresenter.exitLogin();
    }

    private void gotoLogin() {
        startActivity(new Intent(this, LoginCloudActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(mActivityName);
    }

}
