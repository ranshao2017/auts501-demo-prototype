package com.phicomm.prototype.bean;

import java.io.Serializable;

/**
 * Created by qisheng.lv on 2017/4/20.
 */

public class WifiResult extends BaseResponse implements Serializable{

    private static final long serialVersionUID = -7346410199498056739L;

    public RetWlanSetResult retWlansetresult;

    public class RetWlanSetResult implements Serializable {

        private int ALREADYLOGIN;
        private int Wlansetresult;
        private int ErrorCode;

        public int getALREADYLOGIN() {
            return ALREADYLOGIN;
        }

        public void setALREADYLOGIN(int ALREADYLOGIN) {
            this.ALREADYLOGIN = ALREADYLOGIN;
        }

        public int getWlansetresult() {
            return Wlansetresult;
        }

        public void setWlansetresult(int wlansetresult) {
            Wlansetresult = wlansetresult;
        }

        public int getErrorCode() {
            return ErrorCode;
        }

        public void setErrorCode(int errorCode) {
            ErrorCode = errorCode;
        }

        @Override
        public String toString() {
            return "WifiResult{" +
                    "ALREADYLOGIN=" + ALREADYLOGIN +
                    ", Wlansetresult=" + Wlansetresult +
                    ", ErrorCode=" + ErrorCode +
                    '}';
        }

    }


}
