package com.phicomm.prototype.net.request;

import android.text.TextUtils;
import android.util.Log;

import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.manager.CloudManager;
import com.phicomm.prototype.utils.EntryUtils;

import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * post请求：请求体为键值对
 * Created by qisheng.lv on 2017/4/12.
 */
public class PostRequest extends BaseRequest {

    private String mCommand;

    public PostRequest(String url) {
        mBuilder = new Request.Builder().url(url);
    }

    public PostRequest(String url, String command) {
        this(url);
        this.mCommand = command;
    }

    public PostRequest(String url, int cacheSecond) {
        this(url);
        mCacheSecond = cacheSecond;
    }

    @Override
    public Request generateRequest() {
        if (TextUtils.isEmpty(mCommand)) {
            return getNormalRequest();
        } else {
            return getCommandRequest();
        }
    }

    public Request getNormalRequest() {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (mParams != null && mParams.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = mParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                    formBuilder.add(entry.getKey(), entry.getValue());
                }
            }
        }
        return mBuilder.post(formBuilder.build()).build();
    }


    /**
     * 本地或远程操控路由器Request
     *
     * @return
     */
    public Request getCommandRequest() {
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<String, String>> iterator = mParams.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey()).append('=').append(entry.getValue());
            sb.append('&');
        }
        String contentSouce = sb.substring(0, sb.length() - 1);
        Log.d("okhttp", "params: " + contentSouce);
        String contentEntry = EntryUtils.aesEncrypt(contentSouce, AppConstans.AES128_SECRET_KEY);
        String data = mCommand + "?content=" + contentEntry;

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("data", data);
        formBuilder.add("devType", AppConstans.DEVTYPE_ROUTER);
        formBuilder.add("devSN", CloudManager.getInstance().getUsedRouterMac());
        return mBuilder.post(formBuilder.build()).build();
    }

}
