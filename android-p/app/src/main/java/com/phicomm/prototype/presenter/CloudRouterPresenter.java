package com.phicomm.prototype.presenter;

import com.phicomm.prototype.R;
import com.phicomm.prototype.bean.BindState;
import com.phicomm.prototype.bean.CloudRouterList;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.manager.CloudManager;
import com.phicomm.prototype.model.cloud.CloudRouterModel;
import com.phicomm.prototype.net.callback.BaseCallback;
import com.phicomm.prototype.net.callback.BeanCallback;
import com.phicomm.prototype.presenter.viewback.CloudRouterView;
import com.phicomm.prototype.utils.CommonUtils;

import okhttp3.Request;

/**
 * 云设备相关
 * Created by qisheng.lv on 2017/4/19.
 */
public class CloudRouterPresenter extends BasePresenter {

    private CloudRouterView mView;
    private CloudRouterModel mModel;


    public CloudRouterPresenter(String tag, CloudRouterView view) {
        this.mView = view;
        mModel = new CloudRouterModel(tag);
    }

    public void getBindRouters() {
        mModel.getBindRouters(AppConstans.DEVTYPE_ROUTER, new BeanCallback<CloudRouterList>() {

            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onGetBindRouterFailure(code, msg);
                }
            }

            @Override
            public void onSuccess(CloudRouterList cloudRouterList) {
                if (!isAlive) {
                    return;
                }

                if (cloudRouterList.data == null || cloudRouterList.data.size() == 0) {
                    mView.onGetBindRouterFailure(0, CommonUtils.getString(R.string.cloud_router_empty));
                } else {
                    mView.onGetBindRouterSuccess(cloudRouterList.data);
                }
            }

        });
    }

    /**
     * http://172.17.193.108/workspace/myWorkspace.do?projectId=2#34
     *
     * @param devSn device mac address
     */
    public void unBindRouter(final String devSn) {
        mModel.unBindRouter(CloudManager.getInstance().getUid(),
                CloudManager.getInstance().getAuthCode(),
                AppConstans.DEVTYPE_ROUTER,
                devSn,
                new BeanCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        if (!isAlive) {
                            return;
                        }
                        mView.onUnBindRouterSuccess(devSn);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        if (!isAlive) {
                            return;
                        }
                        mView.onUnBindRouterFailure(code, msg);
                    }
                });
    }

    public void checkBindState(final String devSn) {
        mModel.checkBindState(devSn, AppConstans.DEVTYPE_ROUTER, new BeanCallback<BindState>() {

            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onCheckBindFailure(code, msg);
                }
            }

            @Override
            public void onSuccess(BindState bindState) {
                if (isAlive) {
                    mView.onCheckBindSuccess(devSn, bindState.getBindState());
                }
            }
        });
    }

    public void doBindRouter(String devSn) {
        String devName = devSn.length() > 5 ? "k2_" + devSn.substring(devSn.length() - 5, devSn.length()) : devSn;
        mModel.bindRouter(devName, devSn, AppConstans.DEVTYPE_ROUTER, new BaseCallback() {
            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onBindRouterFailure(code, msg);
                }
            }

            @Override
            public void onSuccess(String result, Request request) {
                if (isAlive) {
                    mView.onBindRouterSuccess();
                }
            }
        });
    }
}
