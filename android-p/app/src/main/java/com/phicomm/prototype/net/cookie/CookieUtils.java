package com.phicomm.prototype.net.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.phicomm.prototype.MyApplication;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.utils.LogUtils;
import com.phicomm.prototype.utils.SpfUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;


/**
 * Created by weiyx on 2016/8/22.
 * Company: bmsh
 * Description:
 */
public class CookieUtils {
    public static void saveCookie(List<Cookie> cookies) {
        SpfUtils.put(MyApplication.getContext(), AppConstans.SP_COOKIE_TOKEN, getFromHttp(cookies));
    }

    public static String getCookie() {
        String cookie = (String) SpfUtils.get(MyApplication.getContext(), AppConstans.SP_COOKIE_TOKEN, "");
        return cookie;
    }

    public static void saveToken(String token) {
        SpfUtils.put(MyApplication.getContext(), AppConstans.SP_COOKIE_TOKEN, token);
    }

    public static String getToken() {
        String token = (String) SpfUtils.get(MyApplication.getContext(), AppConstans.SP_COOKIE_TOKEN, "");
        return token;
    }

    public static String getFromHttp(List<Cookie> cookies) {
        StringBuilder sb = new StringBuilder();

        if (cookies != null) {
            String name;
            String value;
            for (Cookie cookie : cookies) {
                if (cookie == null) {
                    continue;
                }
                name = cookie.name();
                value = cookie.value();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
                    continue;
                }
                if (name.equals(AppConstans.SP_COOKIE_TOKEN)) {
                    saveToken(value);
                }
                if (sb.length() > 0) {
                    sb.append('&');
                }
                sb.append(name).append('=').append(value);
            }
        }
        return sb.toString();
    }


    //    private static final String LOG_TAG = "PersistentCookieStore";
    private static final String COOKIE_PREFS = "Cookies_Prefs";

    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    public CookieUtils(Context context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new HashMap<>();

        //将持久化的cookies缓存到内存中 即map cookies
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieNames) {
                String encodedCookie = cookiePrefs.getString(name, null);
                if (encodedCookie != null) {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        if (!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }
        }
    }

    protected String getCookieToken(Cookie cookie) {
        if (cookie != null) {
            String name = cookie.name();
            String value = cookie.value();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
                LogUtils.d("value is empty");
            } else {
                if (name.equals(AppConstans.SP_COOKIE_TOKEN)) {
                    saveToken(value);
                }
            }

        }


        return cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);

        //将cookies缓存到内存中 如果缓存过期 就重置此cookie
        if (!cookie.persistent()) {
            if (!cookies.containsKey(url.host())) {
                cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>());
            }
            cookies.get(url.host()).put(name, cookie);
        } else {
            if (cookies.containsKey(url.host())) {
                cookies.get(url.host()).remove(name);
            } else {
                cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>());
            }
            cookies.get(url.host()).put(name, cookie);
        }

        //讲cookies持久化到本地
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        String host = url.host();
        ConcurrentHashMap<String, Cookie> cookieMap = cookies.get(host);
        if (cookieMap == null) {
            cookieMap = new ConcurrentHashMap<String, Cookie>();
            cookies.put(host, cookieMap);
        }

        prefsWriter.putString(host, TextUtils.join(",", cookieMap.keySet()));
        prefsWriter.putString(name, encodeCookie(new SerializableOkHttpCookies(cookie)));
        prefsWriter.apply();
    }

    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(url.host())) {
            ret.addAll(cookies.get(url.host()).values());
        }
        return ret;
    }

    public boolean removeAll() {
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.apply();
        cookies.clear();
        return true;
    }

    public boolean remove(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);

        if (cookies.containsKey(url.host()) && cookies.get(url.host()).containsKey(name)) {
            cookies.get(url.host()).remove(name);

            SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            if (cookiePrefs.contains(name)) {
                prefsWriter.remove(name);
            }
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            prefsWriter.apply();

            return true;
        } else {
            return false;
        }
    }

    public List<Cookie> getCookies() {
        ArrayList<Cookie> ret = new ArrayList<>();
        for (String key : cookies.keySet()) {
            ret.addAll(cookies.get(key).values());
        }

        return ret;
    }

    /**
     * cookies 序列化成 string
     *
     * @param cookie 要序列化的cookie
     * @return 序列化之后的string
     */
    protected String encodeCookie(SerializableOkHttpCookies cookie) {
        if (cookie == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
//            LogUtils.d("encodeCookie: " + e.getMessage());
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    protected Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableOkHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (IOException e) {
            LogUtils.d(e);
        } catch (ClassNotFoundException e) {
            LogUtils.d(e);
        }

        return cookie;
    }

    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }


    public static class SerializableOkHttpCookies implements Serializable {

        private static final long serialVersionUID = -8385457048476094893L;

        private final transient Cookie cookies;
        private transient Cookie clientCookies;

        public SerializableOkHttpCookies(Cookie cookies) {
            this.cookies = cookies;
        }

        public Cookie getCookies() {
            Cookie bestCookies = cookies;
            if (clientCookies != null) {
                bestCookies = clientCookies;
            }
            return bestCookies;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(cookies.name());
            out.writeObject(cookies.value());
            out.writeLong(cookies.expiresAt());
            out.writeObject(cookies.domain());
            out.writeObject(cookies.path());
            out.writeBoolean(cookies.secure());
            out.writeBoolean(cookies.httpOnly());
            out.writeBoolean(cookies.hostOnly());
            out.writeBoolean(cookies.persistent());
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            String name = (String) in.readObject();
            String value = (String) in.readObject();
            long expiresAt = in.readLong();
            String domain = (String) in.readObject();
            String path = (String) in.readObject();
            boolean secure = in.readBoolean();
            boolean httpOnly = in.readBoolean();
            boolean hostOnly = in.readBoolean();
//            boolean persistent = in.readBoolean();
            Cookie.Builder builder = new Cookie.Builder();
            builder = builder.name(name);
            builder = builder.value(value);
            builder = builder.expiresAt(expiresAt);
            builder = hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain);
            builder = builder.path(path);
            builder = secure ? builder.secure() : builder;
            builder = httpOnly ? builder.httpOnly() : builder;
            clientCookies = builder.build();
        }
    }
}
