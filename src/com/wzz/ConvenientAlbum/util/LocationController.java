package com.wzz.ConvenientAlbum.util;

import android.content.Context;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by WANGZHENGZE on 2014/10/10.
 */
public class LocationController implements BDLocationListener {

    private LocationClient client;
    private Context context;
    private static final int SCAN_SPAN = 5000;
    private static final int TIMEOUT = 10000;
    private static final int SUCCESS = 161;

    public LocationController(Context context) {
        this.context = context;
        init();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation.getLocType() == SUCCESS) {
            Log.e("location", bdLocation.getAddrStr());
        } else {
            Log.e("location", "fail:" + bdLocation.getLocType());
        }
        client.stop();

    }

    private void init() {
        client = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setCoorType("gcj02");
        option.setScanSpan(SCAN_SPAN);
        option.setIsNeedAddress(true);
        option.setTimeOut(TIMEOUT);
        client.setLocOption(option);
        client.registerLocationListener(this);
    }

    public void start() {
        client.start();
    }

    public void stop() {
        client.stop();
    }

}
