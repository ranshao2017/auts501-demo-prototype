package com.phicomm.prototype.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.phicomm.prototype.R;
import com.phicomm.prototype.listener.OnTimeoutListener;


/**
 * 加载对话框
 *
 * @author Administrator
 */
public class LoadingDialog extends Dialog {

    private ImageView mIvProgress;
    private AnimationDrawable mProgressAnimationDrawable;
    private int mResId;
    private TextView mTvMessage;
    private long mDialogTimeout = 20 * 1000;//20s

    private Handler mHandler;
    private TimeoutRunnable mRunnable;
    private OnTimeoutListener mOnTimeoutListener;

    public LoadingDialog(Context context) {
        this(context, R.string.loading);
    }

    public LoadingDialog(Context context, int resId) {
        super(context, R.style.ZLDialog);
        this.mResId = resId;
        createDailog();

        if (context instanceof OnTimeoutListener) {
            setOnTimeoutListener((OnTimeoutListener) context);
        }
    }

    private void createDailog() {
        setContentView(R.layout.dialog_loading);
        initView();
    }

    private void initView() {
        mTvMessage = (TextView) findViewById(R.id.tv_message);
        setMessage(mResId);

        mIvProgress = (ImageView) findViewById(R.id.iv_progress);
    }

    public void setMessage(int resId) {
        if (resId > 0) {
            mTvMessage.setText(resId);
            mTvMessage.setVisibility(View.VISIBLE);
        } else {
            mTvMessage.setVisibility(View.GONE);
        }
    }

    public void setMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            mTvMessage.setVisibility(View.GONE);
        } else {
            mTvMessage.setText(message);
            mTvMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();

        if (mProgressAnimationDrawable != null) {
            mProgressAnimationDrawable.stop();
        }

        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    /**
     * 外部方法，显示对话框
     */
    @Override
    public void show() {
        if (mHandler == null) {
            mHandler = new Handler();
        }

        if (mRunnable == null) {
            mRunnable = new TimeoutRunnable();
        }

        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, mDialogTimeout);

        showInner();
    }

    /**
     * 外部方法，显示对话框
     */
    private void showInner() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        super.show();

        mProgressAnimationDrawable = (AnimationDrawable) mIvProgress.getDrawable();
        mProgressAnimationDrawable.start();
    }

    /**
     * 超时任务
     */
    public class TimeoutRunnable implements Runnable {

        @Override
        public void run() {
            dismiss();

            if (mOnTimeoutListener != null) {
                mOnTimeoutListener.onTimeout(null);
            }
        }

    }

    /**
     * 设置超时监听
     *
     * @param onTimeoutListener
     */
    public void setOnTimeoutListener(OnTimeoutListener onTimeoutListener) {
        this.mOnTimeoutListener = onTimeoutListener;
    }
}
