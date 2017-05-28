package com.phicomm.prototype.presenter;

import com.phicomm.prototype.net.OkHttpUtil;

/**
 * Presenter基类
 * Created by qisheng.lv on 2017/4/12.
 */
public class BasePresenter {
    /**
     * 此变量用于判断对应的页面是否还存活，如果不存活，则不需要再将结果回调给页面
     */
    protected boolean isAlive = true;

    public void onDestroy(String tag) {
        isAlive = false;
        OkHttpUtil.cancelRequest(tag);
    }


}
