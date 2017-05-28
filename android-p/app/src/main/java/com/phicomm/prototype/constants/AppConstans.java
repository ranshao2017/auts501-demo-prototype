package com.phicomm.prototype.constants;

/**
 * 常量
 * Created by qisheng.lv on 2017/4/12.
 */

public interface AppConstans {
    String APPID = "2015100011";

    /**
     * aes128加密的秘钥
     */
    String AES128_SECRET_KEY = "FX_001279_005609";

    String ACTION_GET = "get";
    String ACTION_SET = "set";
    String DEVTYPE_ROUTER = "1";

    /**
     * header头部
     */
    String AUTHORIZATION = "Authorization";

    String SP_COOKIE_TOKEN = "SP_COOKIE_TOKEN";

    String SP_AUTHORIZATIONCODE = "SP_AUTHORIZATIONCODE";

    String SP_ACCESS_TOKEN = "SP_ACCESS_TOKEN";

    String SP_USED_ROUTER_MAC = "SP_USED_ROUTER_MAC";

    String SP_CLOUD_USER = "SP_CLOUD_USER";

    String SP_CLOUD_USER_PWD = "SP_CLOUD_USER_PWD";

    String SP_USER_UID = "SP_USER_UID";

    String SP_NET_STATISTICS = "NET_STATISTICS_";


    int REQUEST_CODE_ZXING = 0x101;

    //是否是第一次打开
    String FIRST_OPEN = "first_open";

    /**
     * 第三方相关
     */
    interface ThirdParty {

        /**
         * Data statistics razor
         */
        String RAZOR_SERVER_URL = "https://phiapplog.phicomm.com/index.php?/";
        String PHI_RAZOR_URL = RAZOR_SERVER_URL + "phiwifi";
        String RAZOR_APP_KEY = "d555c9d71a4667b59a0e9780e9bf52f7";

        String KEY_ON_LOGIN_CLICK = "on_login_click";
//        String KEY_ON_REGIST_CLICK = "on_btn_regist_click";
    }
}
