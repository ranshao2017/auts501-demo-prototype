package com.phicomm.prototype.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.phicomm.prototype.R;
import com.phicomm.prototype.adapter.CloudRouterAdapter;
import com.phicomm.prototype.bean.CloudRouterList;
import com.phicomm.prototype.bean.RouterInfo;
import com.phicomm.prototype.manager.CloudManager;
import com.phicomm.prototype.presenter.CloudRouterPresenter;
import com.phicomm.prototype.presenter.RouterConfigPresenter;
import com.phicomm.prototype.presenter.viewback.CloudRouterView;
import com.phicomm.prototype.presenter.viewback.RouterConfigView;
import com.phicomm.prototype.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 云账号下已绑定的设备列表页面
 * Created by qisheng.lv on 2017/4/19.
 */
public class CloudRoutersActivity extends BaseActivity implements CloudRouterAdapter.ItemClickListener {

    @BindView(R.id.rv_routers)
    RecyclerView mRvRouters;

    private android.os.Handler mHandler;

    Runnable mR = new Runnable() {
        @Override
        public void run() {
            getBindRouters();
        }
    };

    private CloudRouterAdapter mAdapter;
    private List<CloudRouterList.CloudRouter> mList = new ArrayList<>();
    private CloudRouterPresenter mCloudPresenter;
    private RouterConfigPresenter mRouterPresenter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cloud_routers);
    }

    @Override
    public void afterInitView() {
        setPageTitle(getString(R.string.title_cloud_routers));
        showTvMenu(getString(R.string.add_bind_rouer));
        mHandler = new android.os.Handler();
        initRv();
        initPresenter();
        getBindRouters();
    }

    @Override
    public void onTvMenuClick(TextView view) {
        getRouterMac();
    }

    private void initRv() {
        mRvRouters.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
//        mRvRouters.addItemDecoration(new RecyclerViewDi(this, LinearLayoutManager.VERTICAL));
        if (mAdapter == null) {
            mAdapter = new CloudRouterAdapter(this, mList);
        }
        mRvRouters.setAdapter(mAdapter);
        mAdapter.setItemListener(this);
    }

    private void initPresenter() {
        mCloudPresenter = new CloudRouterPresenter(mActivityName, new CloudRouterView() {
            @Override
            public void onGetBindRouterFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onGetBindRouterSuccess(List<CloudRouterList.CloudRouter> list) {
                hideLoading();
                mList = list;
                mAdapter.refreshData(mList);
            }

            @Override
            public void onUnBindRouterFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onUnBindRouterSuccess(String devSn) {
                hideLoading();
                mAdapter.removeRouter(devSn);
            }

            @Override
            public void onCheckBindFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onCheckBindSuccess(String devSn, String state) {
                if (state.equals("1")) {
                    hideLoading();
                    ToastUtil.show(getString(R.string.do_bind_failure_already));
                } else {
                    doBindRouter(devSn);
                }
            }

            @Override
            public void onBindRouterFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(msg);
            }

            @Override
            public void onBindRouterSuccess() {
                hideLoading();
                ToastUtil.show(getString(R.string.do_bind_success));
                mHandler.postDelayed(mR, 1800);
            }
        });

        mRouterPresenter = new RouterConfigPresenter(mActivityName, new RouterConfigView() {
            @Override
            public void onGetRouterInfoFailure(int code, String msg) {
                hideLoading();
                ToastUtil.show(getString(R.string.login_admin_failure_notfx));
            }

            @Override
            public void onGetRouterInfoSuccess(RouterInfo.RetSysInfo info) {
                if (info == null || TextUtils.isEmpty(info.getMAC())) {
                    ToastUtil.show(getString(R.string.login_admin_failure_notfx));
                } else {
                    checkRouterBind(info.getMAC());
                }
            }

        });
    }


    public void getBindRouters() {
        showLoading();
        mCloudPresenter.getBindRouters();
    }

    @Override
    public void onItemClick(int position, CloudRouterList.CloudRouter cloudRouter) {
        CloudManager.getInstance().setUsedRouter(cloudRouter);
        startActivity(new Intent(this, FunctionActivity.class));
    }

    @Override
    public void onUnBindRouterClick(CloudRouterList.CloudRouter cloudRouter) {
        unBindRouterInner(cloudRouter.getDevMac());
    }

    private void unBindRouterInner(String devMac) {
        showLoading(R.string.loading);
        mCloudPresenter.unBindRouter(devMac);
    }


    private void getRouterMac() {
        showLoading();
        mRouterPresenter.getLocalRouterInfo();
    }

    private void checkRouterBind(String mac) {
        mCloudPresenter.checkBindState(mac);
    }

    private void doBindRouter(String mac) {
        mCloudPresenter.doBindRouter(mac);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCloudPresenter.onDestroy(mActivityName);
        if (mR != null) {
            mHandler.removeCallbacks(mR);
        }
    }

}
