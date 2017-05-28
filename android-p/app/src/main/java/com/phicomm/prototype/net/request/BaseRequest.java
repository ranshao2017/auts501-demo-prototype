package com.phicomm.prototype.net.request;

import android.text.TextUtils;

import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.manager.CloudManager;
import com.phicomm.prototype.net.OkHttpUtil;
import com.phicomm.prototype.net.callback.BaseCallback;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * OkHttp Request基类
 * Created by qisheng.lv on 2017/4/19.
 */
public abstract class BaseRequest {
    protected String mUrl;
    protected Map<String, String> mParams;
    protected Request.Builder mBuilder;
    protected int mCacheSecond;


    public BaseRequest addParams(String key, String value) {
        if (this.mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            mParams.put(key, value);
        }
        return this;
    }

    public BaseRequest addHeader(String name, String value) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(value) && mBuilder != null) {
            mBuilder.addHeader(name, value);
        }

        return this;
    }

    public abstract Request generateRequest();

    /**
     * 执行请求
     *
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Call doRequest(String tag, BaseCallback<T> callback) {
        if (!TextUtils.isEmpty(tag)) {
            mBuilder.tag(tag);
        }
        addHeader(AppConstans.AUTHORIZATION, CloudManager.getInstance().getToken());
        return OkHttpUtil.execute(generateRequest(), mCacheSecond, callback);
    }

}
