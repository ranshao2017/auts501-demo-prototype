package com.phicomm.prototype.bean;

import java.io.Serializable;

/**
 * 授权码
 * Created by qisheng.lv on 2017/4/12.
 */

public class Authorization extends BaseResponse implements Serializable {
    private static final long serialVersionUID = 6070612291843375587L;

    private String authorizationcode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAuthorizationcode() {
        return authorizationcode;
    }

    public void setAuthorizationcode(String authorizationcode) {
        this.authorizationcode = authorizationcode;
    }

}
