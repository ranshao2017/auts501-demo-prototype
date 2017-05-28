package com.phicomm.prototype.presenter;

import com.phicomm.prototype.BuildConfig;
import com.phicomm.prototype.bean.CloudLogin;
import com.phicomm.prototype.presenter.viewback.AccountView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by qisheng.lv on 2017/5/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AccountPresenterTest extends BaseUnitTest {

    AccountPresenter presenter;

    @Before
    public void setUp() {
        super.setUp();

        presenter = new AccountPresenter("okhttp", new AccountView() {
            @Override
            public void onAuthorizationFailure(int code, String msg) {
                syso("onAuthorizationFailure: " + code + msg);
            }

            @Override
            public void onAuthorizationSuccess(String authorizationcode) {
                syso("onAuthorizationSuccess: " + authorizationcode);
            }

            @Override
            public void onLoginFailure(int code, String msg) {
                syso("onLoginFailure: " + code + msg);
            }

            @Override
            public void onLoginSuccess(CloudLogin cloudLogin) {
                syso("onLoginSuccess: " + cloudLogin.toString());
            }
        });
    }


    @Test
    public void authorization() throws Exception {
        presenter.authorization();
    }

    @Test
    public void loginCloud() throws Exception {
        presenter.loginCloud("17620351278", "ddfa223");
    }

    @Test
    public void exitLogin() throws Exception {

    }

}