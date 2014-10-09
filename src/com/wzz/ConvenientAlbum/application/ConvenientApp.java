package com.wzz.ConvenientAlbum.application;

import android.app.Application;

/**
 * Created by WANGZHENGZE on 2014/10/9.
 */
public class ConvenientApp extends Application {

    private static ConvenientApp app = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
    }

    private void setInstance(ConvenientApp c) {
        app = c;
    }

    public static ConvenientApp getApplication() {
        return app;
    }
}
