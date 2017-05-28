package com.phicomm.prototype.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.phicomm.prototype.R;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.zxing.CaptureActivity;
import com.phicomm.prototype.zxing.Intents;
import com.tencent.bugly.crashreport.CrashReport;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * H5联调页面
 * Created by qisheng.lv on 2017/4/26.
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView mWebView;

//    private String mUrl = "http://geek.csdn.net/";
    private String mUrl = "http://info.3g.qq.com/";

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_view);
    }

    @Override
    public void afterInitView() {
        mTvTitle.setTextSize(10);
        setPageTitle(mUrl);
        showIvMenu(R.drawable.ico_zxing);
        initWebView();
        loadWebViewData(mUrl);
        //防止键盘弹起后遮挡输入框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @OnClick(R.id.tv_title)
    public void tv_title() {
        mTvTitle.setText(mUrl);
    }

    @Override
    public void onIvMenuClick(ImageView view) {
        gotoCapture();
    }

    private void gotoCapture() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, AppConstans.REQUEST_CODE_ZXING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstans.REQUEST_CODE_ZXING && resultCode == RESULT_OK && data != null) {
            String url = data.getStringExtra(Intents.Scan.RESULT);
            if (!TextUtils.isEmpty(url)) {
                mUrl = url;
                loadH5();
            }
        }
    }

    private void loadH5() {
        setPageTitle(mUrl);
        showLoading();
        loadWebViewData(mUrl);
    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLightTouchEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setTextZoom(100);

        mWebView.requestFocus();

        mWebView.setWebChromeClient(new MyWebChromeClient());

//        mWebView.addJavascriptInterface(jsInterFace, "DataAnalysis");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                hideLoading();
            }

            @Override
            public void onReceivedError(WebView view, final WebResourceRequest request, WebResourceError error) {
                hideLoading();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (WebViewActivity.this == null || WebViewActivity.this.isFinishing()) {
                    return true;
                }
                loadWebViewData(url, true);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (WebViewActivity.this == null || WebViewActivity.this.isFinishing()) {
                    return;
                }
                super.onPageStarted(view, url, favicon);
//                LogUtils.d(WebViewActivity.class.getSimpleName() + "onPageStarted url = " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoading();
                if (WebViewActivity.this == null || WebViewActivity.this.isFinishing()) {
                    return;
                }
                super.onPageFinished(view, url);
                mWebView.setNetworkAvailable(true);
//                LogUtils.d("onPageFinished url = " + url);
                try {
                    setPageTitle(view.getTitle());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    protected void loadWebViewData(String url) {
        loadWebViewData(url, false);
    }

    protected void loadWebViewData(String url, boolean overUrlLoad) {
        if (TextUtils.isEmpty(url)) {
            url = getLoadUrl();
        }

        if (!overUrlLoad) {
            mWebView.loadUrl(url);
        }
    }

    protected String getLoadUrl() {
        return mUrl;
    }

    private void closeWebView() {
        try {
            if (mWebView != null) {
                mWebView.removeAllViews();
                //在5.1上如果不加上这句话就会出现内存泄露。这是5.1的bug
                // mComponentCallbacks导致的内存泄漏
                ((ViewGroup) mWebView.getParent()).removeView(mWebView);
                mWebView.setTag(null);
                mWebView.clearHistory();
                mWebView.stopLoading();
                mWebView.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGoback() {
        if (mWebView != null && mWebView.canGoBack()) {
            WebBackForwardList historys = mWebView.copyBackForwardList();
            if (historys != null && historys.getSize() > 1) {
                mWebView.goBack();
            } else {
                super.onGoback();
            }
        } else {
            super.onGoback();
        }
    }

    @Override
    protected void onDestroy() {
        closeWebView();
        super.onDestroy();
    }

    private class MyWebChromeClient extends WebChromeClient {
        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        }

        // For Android  > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (WebViewActivity.this == null || WebViewActivity.this.isFinishing()) {
                return;
            }
            setPageTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            CrashReport.setJavascriptMonitor(view, true);

            if (WebViewActivity.this == null || WebViewActivity.this.isFinishing()) {
                return;
            }


            if (newProgress == 100) {
                hideLoading();
            }
//            newProgress = newProgress < 5 ? 5 : newProgress;
//            setCurrProgress(newProgress);
        }

        @Override
        public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
            quotaUpdater.updateQuota(quota * 2);
        }

        @Override
        public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
            quotaUpdater.updateQuota(quota * 2);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

    }


}
