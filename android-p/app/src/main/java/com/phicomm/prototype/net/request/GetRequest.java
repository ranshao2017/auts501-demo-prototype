package com.phicomm.prototype.net.request;

import android.net.Uri;
import android.util.Log;

import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.constants.UrlConfig;
import com.phicomm.prototype.utils.EntryUtils;

import java.util.Iterator;
import java.util.Map;

import okhttp3.Request;

/**
 * Get请求
 * 需要注意的是：本地管理路由器都是双向加密，因此需要对参数封装和加密后才能生成最终的Request
 * Created by qisheng.lv on 2017/4/12.
 */
public class GetRequest extends BaseRequest {

    public GetRequest(String url) {
        this.mUrl = url;
        mBuilder = new Request.Builder();
    }


    public String generateUrl(String url) {
        if (isLocalQuest(url)) {
            return getLocalUrl(url);
        } else {
            return getNormalUrl(url);
        }
    }

    private String getLocalUrl(String url) {
        StringBuffer sb = new StringBuffer();
//        Set<String> keys = mParams.keySet();
//        int count = keys.size();
//        for (String key : keys) {
//            sb.append(key).append('=').append(mParams.get(key));
//            count--;
//            if (count > 0) {
//                sb.append('&');
//            }
//        }
        Iterator<Map.Entry<String, String>> iterator = mParams.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey()).append('=').append(entry.getValue());
            sb.append('&');
        }
        String contentSouce = sb.substring(0, sb.length() - 1);
        Log.d("okhttp", "params: " + contentSouce);
        //加密:content=xxx。其中xxx为参数拼接成的串，需要通过ASE加密。
        String contentEntry = EntryUtils.aesEncrypt(contentSouce, AppConstans.AES128_SECRET_KEY);
        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
        uriBuilder.appendQueryParameter("content", contentEntry);
        return uriBuilder.build().toString();
    }

    private String getNormalUrl(String url) {
        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
        Iterator<Map.Entry<String, String>> iterator = mParams.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return uriBuilder.build().toString();
    }

    /**
     * 判断是否本地请求
     *
     * @param url
     * @return
     */
    private boolean isLocalQuest(String url) {
        return url.contains(UrlConfig.LocalUrl.URL_HOST);
    }

    @Override
    public Request generateRequest() {
        String getUrl = generateUrl(mUrl);
        mBuilder = mBuilder.url(getUrl);
        return mBuilder.get().build();
    }

}
