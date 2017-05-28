package com.phicomm.prototype.net.callback;

import com.alibaba.fastjson.JSON;
import com.phicomm.prototype.constants.NetConfig;
import com.phicomm.prototype.utils.GenericUtils;
import com.phicomm.prototype.utils.LogUtils;

import okhttp3.Request;

/**
 * 网络请求回调Bean解析类
 * Created by qisheng.lv on 2017/4/12.
 */
public abstract class BeanCallback<T> extends BaseCallback<T> {

    @Override
    public void onSuccess(String result, Request request) {
        Class clasz = GenericUtils.getGenericClass(getClass());
        T obj = null;
        try {
            obj = (T) JSON.parseObject(result, clasz);
        } catch (Exception e) {
            LogUtils.d(e);
        }

        if (obj == null) {
            toUiFailure(NetConfig.ERROR_PARSE_RESULT_CODE, null, request);
        } else {
            onSuccess(obj);
        }
    }

    public abstract void onSuccess(T t);

}
