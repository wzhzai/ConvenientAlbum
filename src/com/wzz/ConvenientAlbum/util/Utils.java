package com.wzz.ConvenientAlbum.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.Toast;
import com.wzz.ConvenientAlbum.R;

/**
 * Created by WANGZHENGZE on 2014/10/9.
 */
public class Utils {
    /**
     * 判断sd卡是否存在
     * @return 存在返回true
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 无SD卡的toast
     * @param context context
     */
    public static void toastSDCardError(Context context) {
        Toast.makeText(context, R.string.sd_card_error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 得到SharedPreferencesEditor
     * @param context context
     * @return editor
     */
    private static SharedPreferences.Editor getAppSharedPreferencesEditor(Context context) {
        return context.getSharedPreferences(Const.APP_NAME, 0).edit();
    }

    /**
     * 得到SharedPreferences
     * @param context context
     * @return SharedPreferences
     */
    private static SharedPreferences getAppSharedPreferences(Context context) {
        return context.getSharedPreferences(Const.APP_NAME, 0);
    }

    /**
     * SharedPreferences存String
     * @param context context
     * @param columnName columnName
     * @param value value
     */
    public static void saveString(Context context, String columnName, String value) {
        try {
            SharedPreferences.Editor editor = getAppSharedPreferencesEditor(context);
            editor.putString(columnName, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SharedPreferences存int
     * @param context context
     * @param columnName columnName
     * @param value value
     */
    public static void saveInteger(Context context, String columnName, int value) {
        try {
            SharedPreferences.Editor editor = getAppSharedPreferencesEditor(context);
            editor.putInt(columnName, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SharedPreferences已存在的int进行更新
     * @param context context
     * @param columnName columnName
     * @param value value
     */
    public static void addInteger(Context context, String columnName, int value) {
        int t = getInteger(context, columnName, 0);
        t = t + value;
        saveInteger(context, columnName, t);
    }

    /**
     * SharedPreferences存long
     * @param context context
     * @param columnName columnName
     * @param value value
     */
    public static void saveLong(Context context, String columnName, long value) {
        try {
            SharedPreferences.Editor editor = getAppSharedPreferencesEditor(context);
            editor.putLong(columnName, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除SharedPreferences
     * @param context context
     * @param columnName columnName
     */
    public static void removeString(Context context, String columnName) {
        try {
            SharedPreferences.Editor editor = getAppSharedPreferencesEditor(context);
            editor.remove(columnName);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取SharedPreferences的String
     * @param context context
     * @param columnName columnName
     * @param defValue defValue
     * @return string
     */
    public static String getString(Context context, String columnName, String defValue) {
        try {
            SharedPreferences sharedPreferences = getAppSharedPreferences(context);
            return sharedPreferences.getString(columnName, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 读取SharedPreferences的int
     * @param context context
     * @param columnName columnName
     * @param defValue defValue
     * @return int
     */
    public static int getInteger(Context context, String columnName, int defValue) {
        try {
            SharedPreferences sharedPreferences = getAppSharedPreferences(context);
            return sharedPreferences.getInt(columnName, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 读取SharedPreferences的long
     * @param context context
     * @param columnName columnName
     * @param defValue defValue
     * @return long
     */
    public static Long getLong(Context context, String columnName, long defValue) {
        try {
            SharedPreferences sharedPreferences = getAppSharedPreferences(context);
            return sharedPreferences.getLong(columnName, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

}
