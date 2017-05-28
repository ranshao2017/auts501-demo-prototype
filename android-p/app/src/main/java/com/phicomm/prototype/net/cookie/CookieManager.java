package com.phicomm.prototype.net.cookie;

import com.phicomm.prototype.MyApplication;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by weiyx on 2016/8/22.
 * Company: bmsh
 * Description:
 */
public class CookieManager implements CookieJar {
    private final CookieUtils cookieStore = new CookieUtils(MyApplication.getContext());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
