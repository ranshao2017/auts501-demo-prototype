package com.phicomm.prototype.manager;

import com.phicomm.prototype.bean.WifiConfig;
import com.phicomm.prototype.constants.AppConstans;

/**
 * Created by qisheng.lv on 2017/4/20.
 */

public class RouterConfigManager {

    private static volatile RouterConfigManager mInstance;

    private RouterConfigManager() {

    }

    public static RouterConfigManager getInstance() {
        if (mInstance == null) {
            synchronized (RouterConfigManager.class) {
                if (mInstance == null) {
                    mInstance = new RouterConfigManager();
                }
            }
        }

        return mInstance;
    }

    public WifiConfig getDefaultWifiSetting() {
        WifiConfig wifiConfig = new WifiConfig();
        wifiConfig.setAction(AppConstans.ACTION_SET);
        wifiConfig.setSTATUS("ON");
        wifiConfig.setBroadcatsSSID_24G("ON");
        wifiConfig.setSTATUS_5G("ON");
        wifiConfig.setBroadcatsSSID_5G("ON");
        return wifiConfig;
    }


}
