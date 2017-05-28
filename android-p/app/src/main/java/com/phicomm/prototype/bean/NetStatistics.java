package com.phicomm.prototype.bean;

/**
 * 网络请求数据统计
 * Created by qisheng.lv on 2017/4/25.
 */
public class NetStatistics {
    public String url;

    public String net_type;

    public long took_time;

    public String error_msg;

    public int response_code;

    public int error_code;

    @Override
    public String toString() {
        return "NetStatistics{" +
                "url='" + url + '\'' +
                ", net_type='" + net_type + '\'' +
                ", took_time=" + took_time +
                ", error_msg='" + error_msg + '\'' +
                ", response_code=" + response_code +
                ", error_code=" + error_code +
                '}';
    }
}
