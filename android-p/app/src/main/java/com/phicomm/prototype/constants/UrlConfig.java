package com.phicomm.prototype.constants;

/**
 * Url配置
 * Created by qisheng.lv on 2017/4/12.
 */
public interface UrlConfig {

    interface AccountCloudUrl {
        String URL_HOST = "https://account.phicomm.com";
        String GET_AUTHORIZATION = URL_HOST + "/v1/authorization"; //获取授权码
        String LOGIN = URL_HOST + "/v1/login"; //登陆云
        String LOGOUT = URL_HOST + "/v1/logout"; //退出登陆
    }

    interface DeviceCloudUrl {
        String URL_HOST = "https://phiclouds.phicomm.com";
        String GET_BIND_DEVICES = URL_HOST + "/routerappservicev1/routerapp/device";
        String COMMAND_DEVICE = URL_HOST + "/routerappservicev1/routerapp/device/command";
        String UNBIND_ROUTER = URL_HOST + "/routerappservicev1/routerapp/device/unbindaccount";
        String CHECK_BIND_STATE = URL_HOST + "/routerappservicev1/routerapp/device/bindstate";
        String BIND_ROUTER = URL_HOST + "/routerappservicev1/routerapp/device";
    }

    interface LocalUrl {
        String URL_ROUTER_HOST = "http://192.168.2.1";
        String URL_HOST = URL_ROUTER_HOST;
        String CHECK_LOGIN = URL_HOST + "/alreadylogin.asp";
        String LOGIN_LOCAL = URL_HOST + "/login.asp";
        String GET_ROUER_INFO = URL_HOST + "/parameterlist.asp";
        String WIFI_SETTING = URL_HOST + "/wirelesssetup.asp";
    }

    interface BussinessUrl {
        String URL_HOST = "http://app.phiwifi.phicomm.com:80";
        String APP_CHECK_UPDATE = URL_HOST + "/Service/App/checkupdate";
    }


}
