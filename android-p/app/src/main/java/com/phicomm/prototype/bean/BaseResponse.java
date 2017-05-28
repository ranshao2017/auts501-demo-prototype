package com.phicomm.prototype.bean;

import java.io.Serializable;

/**
 * 网络响应基类
 * Created by qisheng.lv on 2017/4/12.
 */

public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1183820018943887365L;

    private int error;

    private String reason;

    private String message;

    private int token_status;

    //远程操控路由器时，路由器响应并透传的消息
    private String data;

    private String httpCode = "200";

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getToken_status() {
        return token_status;
    }

    public void setToken_status(int token_status) {
        this.token_status = token_status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }
}
