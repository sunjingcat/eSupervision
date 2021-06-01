package com.zz.supervision.utils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zz.supervision.App;

public class LocationHelper {
    private LocationCallBack callBack;
    private static LocationHelper helper;
    private LocationClient locationClient;
    private BDLocationListener locationListener = new MyBDLocationListener();
    private LocationHelper() {
        //第一步实例化定位核心类
        locationClient = new LocationClient(App.context, getLocOption());
        //第二步设置位置变化回调监听
        locationClient.registerLocationListener(locationListener);
    }
    public static LocationHelper getInstance() {
        if (helper == null) {
            helper = new LocationHelper();
        }
        return helper;
    }
    public void start() {
// 第三步开始定位
        if (locationClient != null) {

            locationClient.start();
        }
    }
    //一般会在Activity的OnDestroy方法调用
    public void stop() {
        if (locationClient != null) {
            locationClient.unRegisterLocationListener(locationListener);
            locationClient.stop();
            locationClient = null;
        }
    }
    private LocationClientOption getLocOption() {
        LocationClientOption option = new LocationClientOption();
        option.setAddrType("all");
        //设置定位坐标系
        option.setCoorType("bd09ll");
        //重新定位时间间隔
        option.setScanSpan(2*1000);
        //设置是否打开gps
        option.setOpenGps(true);
        //设置定位模式
        option.setLocationNotify(true);
        return option;

    }
    private class MyBDLocationListener implements BDLocationListener {
        @Override

        public void onReceiveLocation(BDLocation bdLocation) {
            if (callBack != null && bdLocation != null) {
                callBack.callBack(bdLocation.getLatitude(), bdLocation.getLongitude());
            }
            App.latitude=bdLocation.getLatitude();
            App.longitude=bdLocation.getLongitude();
            //多次定位必须要调用stop方法
            locationClient.stop();
        }

    }
    public interface LocationCallBack {
        void callBack( double lat, double lng);
    }
    public LocationCallBack getCallBack() {
        return callBack;

    }
    public void setCallBack(LocationCallBack callBack) {
        this.callBack = callBack;
    }

}
