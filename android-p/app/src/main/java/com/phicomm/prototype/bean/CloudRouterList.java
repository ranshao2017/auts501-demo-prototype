package com.phicomm.prototype.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 云账号下绑定的设备列表
 * Created by qisheng.lv on 2017/4/19.
 */
public class CloudRouterList implements Serializable{

    private static final long serialVersionUID = -261230500722969509L;

    public List<CloudRouter> data;

    public class CloudRouter implements Serializable{
        private static final long serialVersionUID = 5637709266940536179L;

        private String devMac;
        private String devModel;
        private String devName;
        private String devRemoteIP;
        private String devRemotePort;
        private String netType;
        /**
         * 是否在线，1：在线；0：不在线
         */
        private String online;

        public String getDevMac() {
            return devMac;
        }

        public void setDevMac(String devMac) {
            this.devMac = devMac;
        }

        public String getDevModel() {
            return devModel;
        }

        public void setDevModel(String devModel) {
            this.devModel = devModel;
        }

        public String getDevName() {
            return devName;
        }

        public void setDevName(String devName) {
            this.devName = devName;
        }

        public String getDevRemoteIP() {
            return devRemoteIP;
        }

        public void setDevRemoteIP(String devRemoteIP) {
            this.devRemoteIP = devRemoteIP;
        }

        public String getDevRemotePort() {
            return devRemotePort;
        }

        public void setDevRemotePort(String devRemotePort) {
            this.devRemotePort = devRemotePort;
        }

        public String getNetType() {
            return netType;
        }

        public void setNetType(String netType) {
            this.netType = netType;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        @Override
        public String toString() {
            return "CloudRouterList{" +
                    "devMac='" + devMac + '\'' +
                    ", devModel='" + devModel + '\'' +
                    ", devName='" + devName + '\'' +
                    ", devRemoteIP='" + devRemoteIP + '\'' +
                    ", devRemotePort='" + devRemotePort + '\'' +
                    ", netType='" + netType + '\'' +
                    ", online='" + online + '\'' +
                    '}';
        }
    }

}
