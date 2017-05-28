package com.phicomm.prototype.net.callback;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.phicomm.prototype.bean.BaseResponse;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.constants.NetConfig;
import com.phicomm.prototype.constants.UrlConfig;
import com.phicomm.prototype.net.Err2MsgUtils;
import com.phicomm.prototype.net.OkHttpUtil;
import com.phicomm.prototype.utils.Base64Utils;
import com.phicomm.prototype.utils.LogUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.phicomm.prototype.utils.EntryUtils.aesDecrypt;


/**
 * 网络请求callback基类
 * Created by qisheng.lv on 2017/4/12.
 */
public abstract class BaseCallback<T> implements okhttp3.Callback {

    /**
     * 最终UI线程回调：请求失败
     *
     * @param code
     * @param msg
     */
    public abstract void onFailure(int code, String msg);

    /**
     * OkHttp失败回调
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, IOException e) {
        if (e instanceof SocketTimeoutException) {
            toUiFailure(NetConfig.ERROR_TIMEOUT_CODE, null, call.request());
        } else {
            toUiFailure(NetConfig.ERROR_UNKNOW_CODE, null, call.request());
        }
    }

    /**
     * 最终UI线程回调：请求成功
     *
     * @param result
     */
    public abstract void onSuccess(String result, Request request);


    /**
     * OkHttp响应回调
     * @param call
     * @param response
     * @throws IOException
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response == null) {
            toUiFailure(NetConfig.ERROR_SERVER_NO_RESPONSE_CODE, null, call.request());
            return;
        }
        if (response.isSuccessful()) {
            onSucessResponse(response);
        } else {
            toUiFailure(NetConfig.ERROR_UNKNOW_CODE, null, call.request());
        }
    }

    /**
     * 成功收到响应，在此对数据进行预处理，此处仍然是子线程
     *
     * @param response
     */
    public void onSucessResponse(Response response) {
        ResponseBody body = response.body();
        if (body == null) {
            toUiFailure(NetConfig.ERROR_SERVER_NO_RESPONSE_CODE, null, response.request());
        } else {
            String bodyStr = null;
            try {
                bodyStr = body.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(bodyStr)) {
                toUiFailure(NetConfig.ERROR_SERVER_NO_RESPONSE_CODE, null, response.request());
                return;
            }
            String url = response.request().url().toString();
            //本地连接的请求都是双向加密的，需要先解密，再做解析数据
            if (url.contains(UrlConfig.LocalUrl.URL_HOST)) {
                bodyStr = aesDecrypt(bodyStr, AppConstans.AES128_SECRET_KEY);
            }

//            LogUtils.d("BaseCallback response bodystr: " + bodyStr);


            BaseResponse baseObj = null;
            try {
                baseObj = JSON.parseObject(bodyStr, BaseResponse.class);
            } catch (Exception e) {
                LogUtils.d(e);
            }
            if (baseObj == null) {
                toUiFailure(NetConfig.ERROR_PARSE_RESULT_CODE, null, response.request());
                return;
            }

            int errorCode = baseObj.getError();
            int tokenStatus = baseObj.getToken_status();
            String message = baseObj.getMessage();
            String data = baseObj.getData();
            String httpCode = baseObj.getHttpCode();
            if (errorCode == 0 && tokenStatus == 0 && httpCode.equals("200")) {
//                toUiSuccess(bodyStr);
                toUiSuccess(getData(bodyStr, data), response.request());
            } else if (tokenStatus > 0) {
                //token需要刷新，目前做法是直接回调错误，让用户重新登录。后面做成调用接口以刷新token
                toUiFailure(NetConfig.ERROR_TOKEN_REFRESH_CODE, message, response.request());
            } else {
                toUiFailure(errorCode, message, response.request());
            }
        }
    }

    /**
     * //如果data不为空，说明是远程操控路由器的透传消息，需要解密
     *
     * @param bodyStr
     * @param data
     * @return
     */
    private String getData(String bodyStr, String data) {
        if (TextUtils.isEmpty(data) || data.contains("[") || data.contains("{")) {
            return bodyStr;
        }

        try {

            String routerData = Base64Utils.decodeStr(data);
            String decodeData = aesDecrypt(routerData, AppConstans.AES128_SECRET_KEY);
//            LogUtils.d("BaseCallback response routerData: " + data);
//            LogUtils.d("BaseCallback response decodeData: " + decodeData);
            return decodeData;
        } catch (Exception e) {
            return bodyStr;
        }
    }


    /**
     * 从子线程转到UI线程
     *
     * @param code
     * @param message
     */
    public void toUiFailure(final int code, final String message, final Request request) {
        OkHttpUtil.getInstance().postRunable(new Runnable() {
            @Override
            public void run() {
                try {
                    String errorMsg = TextUtils.isEmpty(message) ? Err2MsgUtils.getErrMsg(code) : message;
                    onFailure(code, errorMsg);
                } catch (Exception e) {
                    LogUtils.d(e);
                }
            }
        });
    }


    /**
     * 从子线程转到UI线程
     *
     * @param result
     */
    public void toUiSuccess(final String result, final Request request) {
        OkHttpUtil.getInstance().postRunable(new Runnable() {
            @Override
            public void run() {
                onSuccess(result, request);
            }
        });
    }



}
