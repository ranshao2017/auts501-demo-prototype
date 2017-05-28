package com.phicomm.prototype.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;

import com.phicomm.prototype.R;
import com.phicomm.prototype.bean.RouterInfo;
import com.phicomm.prototype.bean.WifiConfig;
import com.phicomm.prototype.presenter.RouterConfigPresenter;
import com.phicomm.prototype.presenter.viewback.RouterConfigView;
import com.phicomm.prototype.utils.RegexUtils;
import com.phicomm.prototype.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * WiFi设置页面
 * Created by qisheng.lv on 2017/4/19.
 */
public class WifiSettingActivity extends BaseActivity {

    @BindView(R.id.et_ssid)
    EditText mEtSsid;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;

    private String mSsid;
    private String mPwd;
    private RouterConfigPresenter mPresenter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wifi_setting);
    }

    @Override
    public void afterInitView() {
        setPageTitle(getString(R.string.wifi_setting));
        initPresenter();
        getRouterInfo();
    }

    private void initPresenter() {
        mPresenter = new RouterConfigPresenter(mActivityName,new RouterConfigView() {
            @Override
            public void onGetRouterInfoFailure(int code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onGetRouterInfoSuccess(RouterInfo.RetSysInfo info) {
                showSsid(info);
            }

            @Override
            public void onSetWifiFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onSetWifiSuccess(WifiConfig wifiConfig) {
                hideLoading();
                ToastUtil.show(getString(R.string.setting_success));
            }


        });
    }

    private void showSsid(RouterInfo.RetSysInfo info) {
        if (info != null && !TextUtils.isEmpty(info.getSSID())) {
            mEtSsid.setText(info.getSSID());
            mEtPwd.requestFocus();
        }
    }

    public void getRouterInfo() {
        mPresenter.getRouterInfo();
    }

    @OnClick(R.id.btn_setting)
    public void btn_setting() {
        mSsid = mEtSsid.getText().toString().trim();
        mPwd = mEtPwd.getText().toString().trim();
        if (checkInput()) {
            showDialog();
        }
    }

    private void setWifi() {
        showLoading();
        mPresenter.setWifi(mSsid, mPwd);
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setMessage("此操作会导致Wifi短暂断开，是否继续？")
                .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setWifi();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(mSsid) || RegexUtils.hadCn(mSsid)) {
            ToastUtil.show(this, getString(R.string.wifi_ssid_illegal));
            return false;
        }

        if (TextUtils.isEmpty(mPwd) || mPwd.length() < 8) {
            ToastUtil.show(this, getString(R.string.wifi_pwd_illegal));
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(mActivityName);
    }
}
