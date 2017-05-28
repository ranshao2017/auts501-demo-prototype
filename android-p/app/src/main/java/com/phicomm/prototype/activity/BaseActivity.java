package com.phicomm.prototype.activity;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phicomm.prototype.MyApplication;
import com.phicomm.prototype.R;
import com.phicomm.prototype.listener.OnTimeoutListener;
import com.phicomm.prototype.manager.DataStatisticsManager;
import com.phicomm.prototype.utils.AppManager;
import com.phicomm.prototype.utils.LogUtils;
import com.phicomm.prototype.views.LoadingDialog;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.lang.String;

/**
 * Activity基类
 * Created by qisheng.lv on 2017/4/12.
 */
public abstract class BaseActivity extends AppCompatActivity implements OnTimeoutListener {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_menu)
    TextView mTvMenu;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.iv_back)
    ImageView mIvBack;

    private LoadingDialog mLoadingDialog;
    private Unbinder mUnbinder;
    protected String mActivityName;

    public abstract void initLayout(Bundle savedInstanceState);

    public abstract void afterInitView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityName = this.getClass().getSimpleName();
        AppManager.getAppManager().addActivity(this);
        PushAgent.getInstance(MyApplication.getContext()).onAppStart();

        // 设置只能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initLayout(savedInstanceState);

        try {
            mUnbinder = ButterKnife.bind(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        afterInitView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataStatisticsManager.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataStatisticsManager.onResume(this);
    }

    @OnClick(R.id.iv_back)
    public void iv_back() {
        onGoback();
    }

    public void onGoback() {
        finish();
    }

    public void hideBack() {
        try {
            mIvBack.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    public void setPageTitle(String title) {
        try {
            mTvTitle.setText(title);
        } catch (Exception e) {
            LogUtils.d(e);
        }
    }

    public void showTvMenu(String menu) {
        try {
            mTvMenu.setText(menu);
        } catch (Exception e) {
            LogUtils.d(e);
        }
    }


    @OnClick(R.id.tv_menu)
    public void tv_menu(TextView view) {
        onTvMenuClick(view);
    }

    public void onTvMenuClick(TextView view) {

    }

    public void showIvMenu(int ico) {
        try {
            mIvMenu.setVisibility(View.VISIBLE);
            mIvMenu.setImageResource(ico);
        } catch (Exception e) {
            LogUtils.d(e);
        }
    }

    @OnClick(R.id.iv_menu)
    public void iv_menu(ImageView view) {
        onIvMenuClick(view);
    }

    public void onIvMenuClick(ImageView view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

    }

    /**
     * showLoading dialog with no text message.
     */
    protected void showLoading() {
        showLoading(-1);
    }

    /**
     * Show showLoading dialog with given tipid which used for showLoading message.
     *
     * @param tipid showLoading dialog message
     */
    protected void showLoading(int tipid) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.setMessage(tipid);
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onTimeout(String result) {

    }

    /**
     * 权限授予成功回调
     */
    public void onGranted(int requestCode) {

    }


    /**
     * 权限申请
     *
     * @param permissions 需要申请的权限
     * @param requestCode 请求code，默认为1
     */
    public void requestRuntimePermission(String[] permissions, int requestCode) {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            //如果未授权的不为空则申请权限。该请求方法是个异步的
            for (String permission : permissionList) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    LogUtils.i("提示用户打开权限,shouldShowRequestPermissionRationale");
                }
                ActivityCompat.requestPermissions(this,
                        permissionList.toArray(new String[permissionList.size()]), requestCode);
            }
        } else {
            onGranted(requestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            List<String> deniedPermissions = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permissions[i]);
                }
            }
            if (deniedPermissions.isEmpty()) {
                onGranted(requestCode);
            } else {
                onDenied(deniedPermissions);
            }
        }
    }

    /**
     * 权限授予失败回调
     *
     * @param deniedPermission
     */
    public void onDenied(List<String> deniedPermission) {

    }

    /**
     * 弹出提示框，提示用户授予权限
     */
    public void showPermissionMessage() {
    }
}
