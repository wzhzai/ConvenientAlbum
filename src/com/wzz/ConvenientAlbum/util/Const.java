package com.wzz.ConvenientAlbum.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by WANGZHENGZE on 2014/10/9.
 */
public class Const {
    public final static int FRAGMENT_ALBUM = 0;
    public final static int CAMERA_REQUEST_CODE = 101;
    public final static String APP_NAME = "convenientAlbum";
    public final static String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public final static String CAMERA_PATH = SD_CARD + File.separator + APP_NAME + File.separator;
}
