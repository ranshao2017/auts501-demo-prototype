package com.phicomm.prototype.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Intent工具类
 * Created by qisheng.lv on 2017/4/12.
 */
public class IntentUtils {

    /**
     * 启动相册选择图片
     *
     * @return
     */
    public static Intent getCategoryIntent() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("image/*");
        return intent;
    }

    /**
     * 打开相机获取照片
     *
     * @return
     */
    public static Intent getCameraIntent(File saveFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(saveFile);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * 调用系统图库查看图片
     *
     * @param context
     * @param file
     */
    public static void playPicture(Context context, File file) {
        Intent picIntent = new Intent("android.intent.action.VIEW");
        picIntent.addCategory("android.intent.category.DEFAULT");
        picIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri picUri = Uri.fromFile(file);
        picIntent.setDataAndType(picUri, "image/*");
        context.startActivity(picIntent);
    }

    public static void callPhone(Context context, String phone) {
        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

}
