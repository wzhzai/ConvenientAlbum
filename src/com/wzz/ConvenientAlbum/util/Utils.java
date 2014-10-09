package com.wzz.ConvenientAlbum.util;

import android.os.Environment;

/**
 * Created by WANGZHENGZE on 2014/10/9.
 */
public class Utils {
    /**
     * 判断sd卡是否存在
     * @return 存在返回true
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }
}
