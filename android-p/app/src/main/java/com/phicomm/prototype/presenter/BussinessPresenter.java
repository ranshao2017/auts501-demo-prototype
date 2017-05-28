package com.phicomm.prototype.presenter;

import com.phicomm.prototype.MyApplication;
import com.phicomm.prototype.bean.AppUpdate;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.model.BussinessModel;
import com.phicomm.prototype.net.callback.BeanCallback;
import com.phicomm.prototype.presenter.viewback.BussinessView;
import com.phicomm.prototype.utils.AppInfoUtils;

/**
 * 业务处理presenter
 * Created by weiming.zeng on 2017/4/28.
 */

public class BussinessPresenter {
    private BussinessModel mModel;
    private BussinessView mView;

    public BussinessPresenter(String tag, BussinessView mView) {
        this.mModel = new BussinessModel(tag);
        this.mView = mView;
    }

    public void versionUpdate() {
    }

    public void checkUpdate() {
        String appid = AppConstans.APPID;
        String channel = "YYB";
        int vercode = AppInfoUtils.getAppVersionCode(MyApplication.getContext());
        mModel.checkUpdate(appid, channel, vercode, new BeanCallback<AppUpdate>() {
            @Override
            public void onSuccess(AppUpdate appUpdate) {
                mView.updateApp(appUpdate);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.onCheckFailue(code,msg);
            }
        });
    }
}
