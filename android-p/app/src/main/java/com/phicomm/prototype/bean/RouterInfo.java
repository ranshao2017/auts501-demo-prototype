package com.phicomm.prototype.bean;

import java.io.Serializable;

/**
 * 路由器信息
 * Created by qisheng.lv on 2017/4/20.
 */

public class RouterInfo implements Serializable {
    private static final long serialVersionUID = -1146303446672782002L;

    public RetSysInfo retSysInfo;

    public class RetSysInfo implements Serializable {

        private static final long serialVersionUID = 4554540926889642034L;

        private int ALREADYLOGIN;
        private String MODEL;
        private String MAC;
        private String HWVER;
        private String SWVER;
        private int UPTIME;
        private String WANIP;
        private String LANIP;
        private String SSID;
        private String SSID2;
        private int UPRATE;
        private int DOWNRATE;
        private int CPULOAD;
        private int RAMLOAD;
        private String SSID_5G;
        private String SSID2_5G;
        private int WireDevNum;
        private int WirelessDevNum;
        private int NetworkOnline;
        private String InternalModel;
        private String operationMode;
        private String PRODUCT_ID;
        private String HW_ID;

        public int getALREADYLOGIN() {
            return ALREADYLOGIN;
        }

        public void setALREADYLOGIN(int ALREADYLOGIN) {
            this.ALREADYLOGIN = ALREADYLOGIN;
        }

        public String getMODEL() {
            return MODEL;
        }

        public void setMODEL(String MODEL) {
            this.MODEL = MODEL;
        }

        public String getMAC() {
            return MAC;
        }

        public void setMAC(String MAC) {
            this.MAC = MAC;
        }

        public String getHWVER() {
            return HWVER;
        }

        public void setHWVER(String HWVER) {
            this.HWVER = HWVER;
        }

        public String getSWVER() {
            return SWVER;
        }

        public void setSWVER(String SWVER) {
            this.SWVER = SWVER;
        }

        public int getUPTIME() {
            return UPTIME;
        }

        public void setUPTIME(int UPTIME) {
            this.UPTIME = UPTIME;
        }

        public String getWANIP() {
            return WANIP;
        }

        public void setWANIP(String WANIP) {
            this.WANIP = WANIP;
        }

        public String getLANIP() {
            return LANIP;
        }

        public void setLANIP(String LANIP) {
            this.LANIP = LANIP;
        }

        public String getSSID() {
            return SSID;
        }

        public void setSSID(String SSID) {
            this.SSID = SSID;
        }

        public String getSSID2() {
            return SSID2;
        }

        public void setSSID2(String SSID2) {
            this.SSID2 = SSID2;
        }

        public int getUPRATE() {
            return UPRATE;
        }

        public void setUPRATE(int UPRATE) {
            this.UPRATE = UPRATE;
        }

        public int getDOWNRATE() {
            return DOWNRATE;
        }

        public void setDOWNRATE(int DOWNRATE) {
            this.DOWNRATE = DOWNRATE;
        }

        public int getCPULOAD() {
            return CPULOAD;
        }

        public void setCPULOAD(int CPULOAD) {
            this.CPULOAD = CPULOAD;
        }

        public int getRAMLOAD() {
            return RAMLOAD;
        }

        public void setRAMLOAD(int RAMLOAD) {
            this.RAMLOAD = RAMLOAD;
        }

        public String getSSID_5G() {
            return SSID_5G;
        }

        public void setSSID_5G(String SSID_5G) {
            this.SSID_5G = SSID_5G;
        }

        public String getSSID2_5G() {
            return SSID2_5G;
        }

        public void setSSID2_5G(String SSID2_5G) {
            this.SSID2_5G = SSID2_5G;
        }

        public int getWireDevNum() {
            return WireDevNum;
        }

        public void setWireDevNum(int wireDevNum) {
            WireDevNum = wireDevNum;
        }

        public int getWirelessDevNum() {
            return WirelessDevNum;
        }

        public void setWirelessDevNum(int wirelessDevNum) {
            WirelessDevNum = wirelessDevNum;
        }

        public int getNetworkOnline() {
            return NetworkOnline;
        }

        public void setNetworkOnline(int networkOnline) {
            NetworkOnline = networkOnline;
        }

        public String getInternalModel() {
            return InternalModel;
        }

        public void setInternalModel(String internalModel) {
            InternalModel = internalModel;
        }

        public String getOperationMode() {
            return operationMode;
        }

        public void setOperationMode(String operationMode) {
            this.operationMode = operationMode;
        }

        public String getPRODUCT_ID() {
            return PRODUCT_ID;
        }

        public void setPRODUCT_ID(String PRODUCT_ID) {
            this.PRODUCT_ID = PRODUCT_ID;
        }

        public String getHW_ID() {
            return HW_ID;
        }

        public void setHW_ID(String HW_ID) {
            this.HW_ID = HW_ID;
        }
    }

}
