package com.phicomm.prototype.presenter;

import com.phicomm.prototype.R;
import com.phicomm.prototype.bean.RouterInfo;
import com.phicomm.prototype.bean.WifiConfig;
import com.phicomm.prototype.bean.WifiResult;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.constants.NetConfig;
import com.phicomm.prototype.manager.RouterConfigManager;
import com.phicomm.prototype.model.RouterConfigModel;
import com.phicomm.prototype.net.callback.BeanCallback;
import com.phicomm.prototype.presenter.viewback.RouterConfigView;
import com.phicomm.prototype.utils.CommonUtils;

/**
 * 路由器配置Presenter
 * Created by qisheng.lv on 2017/4/20.
 */
public class RouterConfigPresenter extends BasePresenter {

    private RouterConfigView mView;
    private RouterConfigModel mModel;

    public RouterConfigPresenter(String tag, RouterConfigView routerConfigView) {
        this.mView = routerConfigView;
        mModel = new RouterConfigModel(tag);
    }

    /**
     * 本地连接-获取路由器配置信息
     */
    public void getLocalRouterInfo() {
        mModel.getLocalRouterInfo(AppConstans.ACTION_GET, new BeanCallback<RouterInfo>() {
            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onGetRouterInfoFailure(code, msg);
                }
            }

            @Override
            public void onSuccess(RouterInfo routerInfo) {
                if (isAlive) {
                    RouterInfo.RetSysInfo info = routerInfo.retSysInfo;
                    if (info == null) {
                        mView.onGetRouterInfoFailure(0, NetConfig.ERROR_PARSE_RESULT);
                    } else {
                        mView.onGetRouterInfoSuccess(info);
                    }
                }
            }
        });
    }

    /**
     * 获取路由器配置信息
     */
    public void getRouterInfo() {
        mModel.getRouterInfo(AppConstans.ACTION_GET, new BeanCallback<RouterInfo>() {
            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onGetRouterInfoFailure(code, msg);
                }
            }

            @Override
            public void onSuccess(RouterInfo routerInfo) {
                if (isAlive) {
                    RouterInfo.RetSysInfo info = routerInfo.retSysInfo;
                    if (info == null) {
                        mView.onGetRouterInfoFailure(0, NetConfig.ERROR_PARSE_RESULT);
                    } else {
                        mView.onGetRouterInfoSuccess(info);
                    }
                }
            }
        });
    }

    /**
     * 设置WiFi
     *
     * @param ssid
     * @param pwd
     */
    public void setWifi(String ssid, String pwd) {
        final WifiConfig wifi = RouterConfigManager.getInstance().getDefaultWifiSetting();
        wifi.setSSID(ssid);
        wifi.setPASSWORD(pwd);
        wifi.setSSID_5G(ssid + "_5g");
        wifi.setPASSWORD_5G(pwd);
        mModel.setWifi(wifi, new BeanCallback<WifiResult>() {

            @Override
            public void onFailure(int code, String msg) {
                if (isAlive) {
                    mView.onSetWifiFailure(code, msg);
                }
            }

            @Override
            public void onSuccess(WifiResult wifiResult) {
                if (isAlive) {
                    if (wifiResult.retWlansetresult != null && wifiResult.retWlansetresult.getALREADYLOGIN() == 1 && wifiResult.retWlansetresult.getWlansetresult() == 1) {
                        mView.onSetWifiSuccess(wifi);
                    } else {
                        mView.onSetWifiFailure(0, CommonUtils.getString(R.string.wifi_set_failure));
                    }
                }
            }
        });
    }

}
