package com.wzz.ConvenientAlbum.application;

import android.app.Application;
import com.wzz.ConvenientAlbum.sqlite.DBManage;

/**
 * Created by WANGZHENGZE on 2014/10/9.
 */
public class ConvenientApp extends Application {

    private static ConvenientApp app = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        init();
    }

    private void init() {
        DBManage.getInstance(getApplicationContext()).open();
    }

    private void setInstance(ConvenientApp c) {
        app = c;
    }

    public static ConvenientApp getApplication() {
        return app;
    }
}
