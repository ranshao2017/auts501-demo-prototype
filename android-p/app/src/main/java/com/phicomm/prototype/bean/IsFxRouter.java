package com.phicomm.prototype.bean;

import java.io.Serializable;

/**
 * 当前连接的是否是斐讯路由器
 * Created by qisheng.lv on 2017/4/17.
 */
public class IsFxRouter extends BaseResponse implements Serializable{

    private static final long serialVersionUID = 6101327594922210439L;

    private int LOGINSTATUS;

    private String MODEL;

    public int getLOGINSTATUS() {
        return LOGINSTATUS;
    }

    public void setLOGINSTATUS(int LOGINSTATUS) {
        this.LOGINSTATUS = LOGINSTATUS;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

}
