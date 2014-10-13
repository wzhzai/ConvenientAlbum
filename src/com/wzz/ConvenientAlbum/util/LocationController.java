package com.wzz.ConvenientAlbum.util;

import android.content.Context;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wzz.ConvenientAlbum.R;
import com.wzz.ConvenientAlbum.bean.LocationInfoBean;
import com.wzz.ConvenientAlbum.interfaces.INotifyDataSetChanged;

/**
 * Created by WANGZHENGZE on 2014/10/10.
 */
public class LocationController implements BDLocationListener {

    private LocationClient client;
    private Context context;
    private INotifyDataSetChanged notify;
    private static final int SCAN_SPAN = 5000;
    private static final int TIMEOUT = 10000;
    private static final int LOCATION_SUCCESS = 161;

    public LocationController(Context context, INotifyDataSetChanged notify) {
        this.context = context;
        this.notify = notify;
        init();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation.getLocType() == LOCATION_SUCCESS) {
            LocationInfoBean locationInfoBean = new LocationInfoBean();
            locationInfoBean.setLongitude(bdLocation.getLongitude());
            locationInfoBean.setLatitude(bdLocation.getLatitude());
            locationInfoBean.setProvince(bdLocation.getProvince());
            locationInfoBean.setCity(bdLocation.getProvince());
            locationInfoBean.setOthers(bdLocation.getDistrict() + bdLocation.getStreet() + bdLocation.getStreetNumber());
            Toast.makeText(context, context.getString(R.string.location_success_hint) + bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
            Utils.saveString(context, "recent_location", locationInfoBean.getLongitude() + "," + locationInfoBean.getLatitude() + ","
                    + locationInfoBean.getProvince() + "," + locationInfoBean.getCity() + "," + locationInfoBean.getOthers());
            notify.notifyDataSetChanged(Const.FROM_LOCATION, Const.COMMON_SUCCESS, locationInfoBean, 0, 0);
            Log.e("location", bdLocation.getAddrStr());
        } else {
            Toast.makeText(context, context.getString(R.string.location_fail_hint), Toast.LENGTH_SHORT).show();
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
