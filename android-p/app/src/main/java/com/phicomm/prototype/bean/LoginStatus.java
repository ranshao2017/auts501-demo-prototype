package com.phicomm.prototype.bean;

import java.io.Serializable;

/**
 * Created by qisheng.lv on 2017/4/21.
 */

public class LoginStatus extends BaseResponse implements Serializable{

    private static final long serialVersionUID = 6903706100246197210L;

    public RetLoginstatus retLoginstatus;

    public class RetLoginstatus implements Serializable{
        private static final long serialVersionUID = -1254673836521967091L;

        private int LOGINSTATUS;
        private int ALREADYLOGIN;
        private String MAC;
        private String MODEL;
        private String USERNAME;
        private String PASSWORD;

        public int getLOGINSTATUS() {
            return LOGINSTATUS;
        }

        public void setLOGINSTATUS(int LOGINSTATUS) {
            this.LOGINSTATUS = LOGINSTATUS;
        }

        public int getALREADYLOGIN() {
            return ALREADYLOGIN;
        }

        public void setALREADYLOGIN(int ALREADYLOGIN) {
            this.ALREADYLOGIN = ALREADYLOGIN;
        }

        public String getMAC() {
            return MAC;
        }

        public void setMAC(String MAC) {
            this.MAC = MAC;
        }

        public String getMODEL() {
            return MODEL;
        }

        public void setMODEL(String MODEL) {
            this.MODEL = MODEL;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }

        public String getPASSWORD() {
            return PASSWORD;
        }

        public void setPASSWORD(String PASSWORD) {
            this.PASSWORD = PASSWORD;
        }
    }

}
