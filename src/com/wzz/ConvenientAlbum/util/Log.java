package com.wzz.ConvenientAlbum.util;

/**
 * Created by WANGZHENGZE on 2014/10/9.
 *
 * 管理log
 */
public class Log {

    private static boolean isLog = true;

    public static void e(String tag, String message) {
        if (isLog)
            android.util.Log.e(tag, message);
    }

    public static void d(String tag, String message) {
        if (isLog)
            android.util.Log.d(tag, message);
    }

    public static void v(String tag, String message) {
        if (isLog)
            android.util.Log.v(tag, message);
    }

    public static void i(String tag, String message) {
        if (isLog)
            android.util.Log.i(tag, message);
    }

    public static void w(String tag, String message) {
        if (isLog)
            android.util.Log.w(tag, message);
    }
}
