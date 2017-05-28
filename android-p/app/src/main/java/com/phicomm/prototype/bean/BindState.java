package com.phicomm.prototype.bean;

import java.io.Serializable;

/**
 * 检查设备绑定状态
 * Created by qisheng.lv on 2017/4/27.
 */
public class BindState implements Serializable {

    private static final long serialVersionUID = 5313394667826995777L;

    private String bindState;

    public String getBindState() {
        return bindState;
    }

    public void setBindState(String bindState) {
        this.bindState = bindState;
    }

}
