package com.phicomm.prototype.presenter;

import com.phicomm.prototype.MyApplication;

import org.junit.Before;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by rongwei.huang on 2017/5/17.
 */

public class BaseUnitTest{
    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        MyApplication.isJunitTest = true;
    }

    public void syso(String msg) {
        System.out.print(msg);
    }

}
