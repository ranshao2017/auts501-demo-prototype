package com.phicomm.prototype.utils;

import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.phicomm.prototype.BuildConfig;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 基于Xlog封装的日志工具类
 * Created by weiming.zeng on 2017/4/19.
 */

public class LogUtils {
    private static final String TAG = "szsoho"; //默认TAG
    private static final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String logPath = SDCARD + "/max_Zeng/log";

    public static int getMethodCcount() {
        return METHOD_CCOUNT;
    }

    public static void setMethodCcount(int methodCcount) {
        METHOD_CCOUNT = methodCcount;
    }

    public static void setJsonIndent(int sonIndent) {
        JSON_INDENT = sonIndent;
    }

    private static int METHOD_CCOUNT = 8;   //打印方法栈的方法数

    private static final int LEVEL_VERBOSE = 0;
    private static final int LEVEL_DEBUG = 1;
    private static final int LEVEL_INFO = 2;
    private static final int LEVEL_WARNING = 3;
    private static final int LEVEL_ERROR = 4;
    private static final int LEVEL_FATAL = 5;
//    private static final int LEVEL_NONE = 6;

    private static int JSON_INDENT = 2;    //the number of spaces to indent for each level of nesting.

    private LogUtils() {
        throw new UnsupportedOperationException("can not instantiate LogUtils...");
    }

    public static void init() {
        try {
            if (!checkCpu()) {
                return;
            }
            System.loadLibrary("stlport_shared");
            System.loadLibrary("marsxlog");
            //init xlog
            if (BuildConfig.isDebug) {
                Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "max_Zeng");
                Xlog.setConsoleLogOpen(true);
            } else {
                Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, "", logPath, "max_Zeng");
                Xlog.setConsoleLogOpen(false);
            }
            Log.setLogImp(new Xlog());
        } catch (Exception e) {
            e.printStackTrace();
        }
        d("xLog ok");
    }


    private static boolean checkCpu() {
        String[] abis = new String[]{};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        //这里目前只支持armeabi架构
        for (String abi : abis) {
            android.util.Log.d("myso", abi);
            if (abi.equals("armeabi")) {
                return true;
            }
        }
        return false;
    }

    public static String getLogLevel() {
        String level;
        switch (Log.getLogLevel()) {
            case 0:
                level = "LEVEL_VERBOSE";
                break;
            case 1:
                level = "LEVEL_DEBUG";
                break;
            case 2:
                level = "LEVEL_INFO";
                break;
            case 3:
                level = "LEVEL_WARNING";
                break;
            case 4:
                level = "LEVEL_ERROR";
                break;
            case 5:
                level = "LEVEL_FATAL";
                break;
            case 6:
                level = "LEVEL_NONE";
                break;
            default:
                level = "";
        }
        return level;
    }

    /**
     * 生成格式化的日志
     *
     * @param obj 需要格式化的内容
     * @return 格式化后的日志内容
     */
    private static String createMessage(Object obj) {
        String message;
        if (obj.getClass().isArray()) {
            message = Arrays.deepToString((Object[]) obj);
        } else {
            message = obj.toString();
        }
        StringBuilder content = new StringBuilder("Thread:" + Thread.currentThread().getName() + "---");
        content.append(message);
        return content.toString();
    }

    public static void v(Object message) {
        v(TAG, message, false);
    }

    public static void v(Object message, boolean isPrStack) {
        v(TAG, message, isPrStack);
    }

    public static void v(String tag, Object message, boolean isPrStack) {
        log(LEVEL_VERBOSE, tag, message, isPrStack, null);
    }

    public static void v(String tag, Object message, boolean isPrStack, final Object... args) {
        log(LEVEL_VERBOSE, tag, message, isPrStack, null, args);
    }

    public static void d(Object message) {
        d(message, false);
    }

    public static void d(Object message, boolean isPrStack) {
        d(TAG, message, isPrStack);
    }

    public static void d(String tag, Object message, boolean isPrStack) {
        log(LEVEL_DEBUG, tag, message, isPrStack, null);
    }

    public static void d(String tag, Object message, boolean isPrStack, final Object... args) {
        log(LEVEL_DEBUG, tag, message, isPrStack, null, args);
    }

    public static void i(Object message) {
        i(message, false);
    }

    public static void i(Object message, boolean isPrStack) {
        i(TAG, message, isPrStack);
    }

    public static void i(String tag, Object message, boolean isPrStack) {
        log(LEVEL_INFO, tag, message, isPrStack, null);
    }

    public static void i(String tag, Object message, boolean isPrStack, final Object... args) {
        log(LEVEL_INFO, tag, message, isPrStack, null, args);
    }

    public static void w(Object message) {
        w(message, false);
    }

    public static void w(Object message, boolean isPrStack) {
        w(TAG, message, isPrStack);
    }

    public static void w(String tag, Object message, boolean isPrStack) {
        log(LEVEL_WARNING, tag, message, isPrStack, null);
    }

    public static void w(String tag, Object message, boolean isPrStack, final Object... args) {
        log(LEVEL_WARNING, tag, message, isPrStack, null, args);
    }

    public static void e(Object message) {
        e(message, false);
    }

    public static void e(Object message, boolean isPrStack) {
        e(TAG, message, isPrStack);
    }

    public static void e(String tag, Object message, boolean isPrStack) {
        log(LEVEL_ERROR, tag, message, isPrStack, null);
    }

    public static void e(String tag, final Object message, boolean isPrStack, final Object... args) {
        log(LEVEL_ERROR, tag, message, isPrStack, null, args);
    }

    /**
     * 打印json日志
     *
     * @param json
     */
    public static void json(String json, boolean isPrStack) {
        if (TextUtils.isEmpty(json)) {
            log(LEVEL_INFO, TAG, "Empty/Null json content", isPrStack, null);
        }
        try {
            if (json.charAt(0) == '{') {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                log(LEVEL_INFO, TAG, message, isPrStack, null);
                return;
            }
            if (json.charAt(0) == '[') {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                log(LEVEL_INFO, TAG, message, isPrStack, null);
                return;
            }
            log(LEVEL_ERROR, TAG, "Invalid json", isPrStack, null);
        } catch (JSONException e) {
            log(LEVEL_ERROR, TAG, "error Json", isPrStack, e.getCause());
        }
    }

    /**
     * 获取堆栈信息
     *
     * @param tr
     * @return
     */
    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        // This is to reduce the amount of log spew that apps do in the non-error
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 获取当前thread的堆栈信息
     *
     * @param message
     * @return
     */
    private static String getThreadStack(String message) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StringBuilder builder = new StringBuilder(message);
        if (elements != null) {
            for (int i = 0; i < METHOD_CCOUNT; i++) {
                builder.append(System.getProperty("line.separator"))
                        .append(elements[i].getFileName())
                        .append(File.separator)
                        .append(elements[i].getClassName())
                        .append(File.separator)
                        .append(elements[i].getMethodName())
                        .append(File.separator)
                        .append(elements[i].getLineNumber());
            }
        }
        return builder.toString();
    }

    public static void log(int priority, String tag, Object message, boolean isPrStack, @Nullable Throwable throwable, Object... args) {
        if (Log.getLogLevel() == Log.LEVEL_NONE) {
            return;
        }
        if (null == message || message.toString().length() == 0) {
            Log.e(TAG, "Empty/null log content");
            return;
        }
        String contents = createMessage(message);
        try {
            if (isPrStack) {
                contents = getThreadStack(contents);
            }
            if (throwable != null) {
                contents += ":" + getStackTraceString(throwable);
            }
        } catch (Exception e) {
            Log.e("error", "error message:" + message.toString());
        }
        switch (priority) {
            case LEVEL_DEBUG:
                Log.d(tag, contents, args);
                break;
            case LEVEL_INFO:
                Log.i(tag, contents, args);
                break;
            case LEVEL_WARNING:
                Log.w(tag, contents, args);
                break;
            case LEVEL_ERROR:
                Log.e(tag, contents, args);
                break;
            case LEVEL_FATAL:
                Log.f(tag, contents, args);
                break;
            default:
                Log.i(tag, contents, args);
                break;
        }
    }

    /**
     * 反注册，需要在程序关闭时调用
     */
    public static void unLoad() {
        Log.appenderClose();
    }
}
