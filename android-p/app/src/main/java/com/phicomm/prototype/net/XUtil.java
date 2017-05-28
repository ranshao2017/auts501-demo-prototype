package com.phicomm.prototype.net;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * XUtils网络工具类
 * Created by qisheng.lv on 2017/4/24.
 */
public class XUtil {

    /**
     * 文件下载
     *
     * @param url
     * @param path
     * @param callback
     */
    public static void download(String url, String path, Callback.ProgressCallback callback) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(path);
        x.http().get(requestParams, callback);
    }


}
