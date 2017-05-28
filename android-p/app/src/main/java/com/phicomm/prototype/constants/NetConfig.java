package com.phicomm.prototype.constants;

/**
 * 网络请求相关配置
 * Created by qisheng.lv on 2017/4/12.
 */
public interface NetConfig {
    int HTTP_CONNECT_TIME_OUT = 10 * 1000;
    int HTTP_READ_TIME_OUT = 30 * 1000;
    int HTTP_WRITE_TIME_OUT = 30 * 1000;
    int HTTP_CACHE_SIZE = 1024 * 1024 * 10;//缓存空间大小，这里设为10M

    //自定义的错误
    String ERROR_UNKNOW = "请求失败";
    int ERROR_UNKNOW_CODE = 0x100;

    String ERROR_NET_UNAVALIABLE = "网络不可用";
    int ERROR_NET_UNAVALIABLE_CODE = 0x101;

    String ERROR_TIMEOUT = "网络超时";
    int ERROR_TIMEOUT_CODE = 0x102;

    String ERROR_SERVER_NO_RESPONSE = "服务器无响应";
    int ERROR_SERVER_NO_RESPONSE_CODE = 0x103;

    String ERROR_PARSE_RESULT = "解析出错";
    int ERROR_PARSE_RESULT_CODE = 0x104;


    String ERROR_TOKEN_REFRESH = "token过期，请重新登录";
    int ERROR_TOKEN_REFRESH_CODE = 0X105;


    //服务器返回的错误信息
    String ERROR_TOKEN_INVALID = "token失效";
    int ERROR_TOKEN_INVALID_CODE = 5;

    String ERROR_USER_NOET_EXIST = "用户不存在";
    int ERROR_USER_NOET_EXIST_CODE = 7;

    String ERROR_CLOUD_PASSWORD = "密码错误";
    int ERROR_CLOUD_PASSWORD_CODE = 8;

    String ERROR_AUTHCODE = "授权码错误";
    int ERROR_AUTHCODE_CODE = 11;

    String ERROR_PARAMS = "参数错误";
    int ERROR_PARAMS_CODE = 12;

    String ERROR_SERVER = "服务器异常";
    int ERROR_SERVER_CODE = 50;



}
