package com.phicomm.prototype.zxing;

import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;
import com.phicomm.prototype.zxing.camera.CameraManager;

/**
 * Created by weiyx on 2016/9/8.
 * Company: bmsh
 * Description:
 */
public interface ZxingInterface {
    void drawViewfinder();

    void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor);

    ViewfinderView getViewfinderView();

    Handler getHandler();

    CameraManager getCameraManager();
}
