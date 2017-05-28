package com.phicomm.prototype.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.phicomm.prototype.MyApplication;
import com.phicomm.prototype.R;
import com.phicomm.prototype.bean.AppUpdate;
import com.phicomm.prototype.presenter.AccountPresenter;
import com.phicomm.prototype.presenter.BussinessPresenter;
import com.phicomm.prototype.presenter.viewback.AccountView;
import com.phicomm.prototype.presenter.viewback.BussinessView;
import com.phicomm.prototype.utils.AppInfoUtils;
import com.phicomm.prototype.utils.LogUtils;
import com.phicomm.prototype.utils.ToastUtil;

import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * 功能页面
 * Created by qisheng.lv on 2017/4/19.
 */
public class FunctionActivity extends BaseActivity {
    private AccountPresenter mPresenter;
    private BussinessPresenter mBussinessPresenter;
    private static final int FORCE_UPDATE = 1;
    private static final int NORMAL_UPDATE = 0;


    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_function);
    }

    @Override
    public void afterInitView() {
        setPageTitle(getString(R.string.title_function));
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new AccountPresenter(mActivityName, new AccountView() {
            @Override
            public void onLogoutFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onLogoutSuccess() {
                hideLoading();
                gotoLogin();
            }
        });
        mBussinessPresenter = new BussinessPresenter(mActivityName, new BussinessView() {
            @Override
            public void updateApp(AppUpdate appUpdate) {
                try {
                    LogUtils.i("ServiceInfo:" + appUpdate.getVer_code() + appUpdate.getVer_type());
                    LogUtils.i("LocalInfo:" + AppInfoUtils.getAppVersionCode(MyApplication.getContext()) + AppInfoUtils.getAppVersionName(MyApplication.getContext()));
                    //判断是否要升级
                    if (Integer.parseInt(appUpdate.getVer_code()) != AppInfoUtils.getAppVersionCode(MyApplication.getContext())) {
                        //根据ver_type升级类型判断，1是强制升级，0是普通升级
                        int type = Integer.parseInt(appUpdate.getVer_type());
                        showUpdateDialog(type);
                    } else {
                        ToastUtil.show("当前版本： " + appUpdate.getVer_name() + "为最新版本");
                    }
                } catch (Exception e) {
                    LogUtils.e(appUpdate.getVer_code() + appUpdate.getVer_type());
                }
                //显示更新提示和调用present下载更新
            }

            @Override
            public void onCheckFailue(int code, String msg) {
                ToastUtil.show("error:" + code + "," + msg);
            }
        });
    }

    private void showUpdateDialog(int type) {
        //type为1强制升级，0普通升级
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载最新版本内容
                ToastUtil.show("正在下载，请稍后...");
            }
        });
        builder.setMessage("发现新版本！请及时更新");
        if (type == FORCE_UPDATE) {
            LogUtils.d("force update");
        } else if (type == NORMAL_UPDATE) {
            builder.setNegativeButton("取消", null);
        } else {
            ToastUtil.show("服务器返回数据有误!");
            return;
        }
        builder.create().show();

    }

    @OnClick(R.id.tv_wifi)
    public void tv_wifi() {
        startActivity(new Intent(this, WifiSettingActivity.class));
    }

    @OnClick(R.id.tv_login_out)
    public void tv_OnLoginOutClick() {
        if (MyApplication.isUseCloud) {
            exitLogin();
        } else {
            gotoLogin();
        }
    }

    @OnClick(R.id.tv_check_update)
    public void tv_update() {
        mBussinessPresenter.checkUpdate();
    }

    @OnLongClick(R.id.tv_login_out)
    public boolean tv_login_out_long() {
//        NetStatistics netSta = DataStatisticsManager.getNetSta(UrlConfig.AccountCloudUrl.LOGIN);
        return false;
    }

    @OnClick(R.id.tv_test_crash)
    public void tv_test_crash() {
//        int i = 5 / 0;
        String str = null;
        str.length();
    }

    private void exitLogin() {
        showLoading();
        mPresenter.exitLogin();
    }

    private void gotoLogin() {
        startActivity(new Intent(this, LoginCloudActivity.class));
        finish();
    }

}
