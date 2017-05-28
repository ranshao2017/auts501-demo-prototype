package com.phicomm.prototype.net.request;

import android.net.Uri;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by qisheng.lv on 2017/4/12.
 */
public class BindRouterRequest extends BaseRequest {

    public BindRouterRequest(String url) {
        this.mUrl = url;
        mBuilder = new Request.Builder();
    }

    @Override
    public BindRouterRequest addParams(String key, String value) {
        if (this.mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, value);
        return this;
    }

    public String generateUrl(String url) {
        return getUrlWithParams(url);
    }


    private String getUrlWithParams(String url) {
        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
        Set<String> keys = mParams.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            uriBuilder.appendQueryParameter(key, mParams.get(key));
        }
        return uriBuilder.build().toString();
    }


    @Override
    public Request generateRequest() {
        String paramsUrl = generateUrl(mUrl);
        mBuilder = mBuilder.url(paramsUrl);
        FormBody.Builder formBuilder = new FormBody.Builder();
        return mBuilder.put(formBuilder.build()).build();
    }

}
