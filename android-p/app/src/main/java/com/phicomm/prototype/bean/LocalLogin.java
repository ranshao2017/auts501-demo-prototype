package com.phicomm.prototype.bean;

import java.io.Serializable;

/**
 * 本地登陆结果
 * Created by qisheng.lv on 2017/4/17.
 */

public class LocalLogin extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 5024448757427495054L;

    public RetLoginresult retLoginresult;

    public class RetLoginresult implements Serializable {

        private static final long serialVersionUID = 7136377797172988002L;

        private int LOGINSTATUS;

        private String RESULT;

        public int getLOGINSTATUS() {
            return LOGINSTATUS;
        }

        public void setLOGINSTATUS(int LOGINSTATUS) {
            this.LOGINSTATUS = LOGINSTATUS;
        }

        public String getRESULT() {
            return RESULT;
        }

        public void setRESULT(String RESULT) {
            this.RESULT = RESULT;
        }

    }


}
