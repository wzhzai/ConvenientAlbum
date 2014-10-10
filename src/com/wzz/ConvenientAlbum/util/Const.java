package com.wzz.ConvenientAlbum.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by WANGZHENGZE on 2014/10/9.
 */
public class Const {
    public static final int FRAGMENT_ALBUM = 0;
    public static final int CAMERA_REQUEST_CODE = 101;
    public static final String APP_NAME = "convenientAlbum";
    public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String CAMERA_PATH = SD_CARD + File.separator + APP_NAME + File.separator;

    public static final int COMMON_SUCCESS = 1;
    public static final int COMMON_FAIL = -1;
    public static final int COMMON_NO_CHANGE = 0;
}
