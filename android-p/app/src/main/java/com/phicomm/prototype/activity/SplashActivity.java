package com.phicomm.prototype.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;

import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.utils.AppInfoUtils;
import com.phicomm.prototype.utils.SpfUtils;
import com.phicomm.prototype.utils.ToastUtil;

public class SplashActivity extends BaseActivity {
    Handler handler = new Handler();
    Runnable mR = new Runnable() {
        @Override
        public void run() {
            Intent intent = null;
            if ((boolean)SpfUtils.get(SplashActivity.this, AppConstans.FIRST_OPEN, true)) {
                intent = new Intent(SplashActivity.this, GuideActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginCloudActivity.class);
            }
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void initLayout(Bundle savedInstanceState) {

    }

    @Override
    public void afterInitView() {
        if (AppInfoUtils.getAppVersionCode(this) > 22) {
            ToastUtil.show("当前SDK版本为" + Build.VERSION.SDK_INT);
        }
        handler.postDelayed(mR, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mR != null) {
            handler.removeCallbacks(mR);
        }
    }
}
