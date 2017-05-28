package com.phicomm.prototype.model;

import com.phicomm.prototype.constants.UrlConfig;
import com.phicomm.prototype.net.OkHttpUtil;
import com.phicomm.prototype.net.callback.BaseCallback;


/**
 * 业务处理model
 * Created by weiming.zeng on 2017/4/28.
 */

public class BussinessModel {
    private String mTag;

    public BussinessModel(String mTag) {
        this.mTag = mTag;
    }

//    /**
//     * 返回本地版本参数
//     * @return
//     */
//    private Map<String,String> getLocalVersionInfo() {
//        HashMap<String,String> map = new HashMap<String,String>();
//        return  map;
//    }
    /**
     * 检查更新
     */
    public void checkUpdate(String appid, String channel, int vercode, BaseCallback callback) {
        OkHttpUtil.get(UrlConfig.BussinessUrl.APP_CHECK_UPDATE)
                .addParams("appid", appid)
                .addParams("channel", channel)
                .addParams("vercode", String.valueOf(vercode))
                .doRequest(mTag,callback);
    }
}
