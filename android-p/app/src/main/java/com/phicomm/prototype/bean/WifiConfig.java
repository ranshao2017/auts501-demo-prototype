package com.phicomm.prototype.bean;

import java.io.Serializable;

/**
 * Wifi配置bean
 * Created by qisheng.lv on 2017/4/20.
 */
public class WifiConfig implements Serializable{
    private static final long serialVersionUID = 8699189479989815415L;

    private String action;
    private String STATUS;
    private String BroadcatsSSID_24G;
    private String txpower;
    private String SSID;
    private String PASSWORD;
    private String STATUS_5G;
    private String BroadcatsSSID_5G;
    private String TXPOWER_5G;
    private String SSID_5G;
    private String PASSWORD_5G;
    private String SmartConn;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getBroadcatsSSID_24G() {
        return BroadcatsSSID_24G;
    }

    public void setBroadcatsSSID_24G(String broadcatsSSID_24G) {
        BroadcatsSSID_24G = broadcatsSSID_24G;
    }

    public String getTxpower() {
        return txpower;
    }

    public void setTxpower(String txpower) {
        this.txpower = txpower;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getSTATUS_5G() {
        return STATUS_5G;
    }

    public void setSTATUS_5G(String STATUS_5G) {
        this.STATUS_5G = STATUS_5G;
    }

    public String getBroadcatsSSID_5G() {
        return BroadcatsSSID_5G;
    }

    public void setBroadcatsSSID_5G(String broadcatsSSID_5G) {
        BroadcatsSSID_5G = broadcatsSSID_5G;
    }

    public String getTXPOWER_5G() {
        return TXPOWER_5G;
    }

    public void setTXPOWER_5G(String TXPOWER_5G) {
        this.TXPOWER_5G = TXPOWER_5G;
    }

    public String getSSID_5G() {
        return SSID_5G;
    }

    public void setSSID_5G(String SSID_5G) {
        this.SSID_5G = SSID_5G;
    }

    public String getPASSWORD_5G() {
        return PASSWORD_5G;
    }

    public void setPASSWORD_5G(String PASSWORD_5G) {
        this.PASSWORD_5G = PASSWORD_5G;
    }

    public String getSmartConn() {
        return SmartConn;
    }

    public void setSmartConn(String smartConn) {
        SmartConn = smartConn;
    }
}
