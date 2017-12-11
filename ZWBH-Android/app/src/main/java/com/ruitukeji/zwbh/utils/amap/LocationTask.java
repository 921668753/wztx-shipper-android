/**
 * Project Name:Android_Car_Example
 * File Name:LocationTask.java
 * Package Name:com.amap.api.car.example
 * Date:2015年4月3日上午9:27:45
 */

package com.ruitukeji.zwbh.utils.amap;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;

/**
 * ClassName:LocationTask <br/>
 * Function: 简单封装了定位请求，可以进行单次定位和多次定位，注意的是在app结束或定位结束时注意销毁定位 <br/>
 * Date: 2015年4月3日 上午9:27:45 <br/>
 *
 * @author yiyi.qi
 * @see
 * @since JDK 1.6
 */
public class LocationTask implements AMapLocationListener,
        OnLocationGetListener {
 LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;

    private static LocationTask mLocationTask;

    private Context mContext;

    private OnLocationGetListener mOnLocationGetlisGetListener;
    //逆地理编码
    private RegeocodeTask mRegecodeTask;
    AMapLocationClient mlocationClient = null;

    private LocationTask(Context context,LocationSource.OnLocationChangedListener locationChangedListener) {
        mListener=locationChangedListener;
        mlocationClient = new AMapLocationClient(context);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        mRegecodeTask = new RegeocodeTask(context);
        mRegecodeTask.setOnLocationGetListener(this);
        mContext = context;
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
////设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(30000);
//        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        mlocationClient.setLocationListener(this);
    }

    public void setOnLocationGetListener(
            OnLocationGetListener onGetLocationListener) {
        mOnLocationGetlisGetListener = onGetLocationListener;
    }

    public static LocationTask getInstance(Context context, LocationSource.OnLocationChangedListener locationChangedListener) {
        if (mLocationTask == null) {
            mLocationTask = new LocationTask(context,locationChangedListener);
        }
        return mLocationTask;
    }

    /**
     * 开启单次定位
     */
    public void startSingleLocate() {
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();
    }

    /**
     * 开启多次定位
     */
    public void startLocate() {
        mLocationOption.setOnceLocation(false);
//设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();
    }

    /**
     * 结束定位，可以跟多次定位配合使用
     */
    public void stopLocate() {
        mlocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    /**
     * 销毁定位资源
     */
    public void onDestroy() {
        mlocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务
    }

    /**
     * 激活定位
     */
    public void activate() {

    }
    /**
     * 停止定位
     */
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            PositionEntity entity = new PositionEntity();
            entity.latitue = amapLocation.getLatitude();
            entity.longitude = amapLocation.getLongitude();

            if (!TextUtils.isEmpty(amapLocation.getAddress())) {
                entity.address = amapLocation.getAddress();
            }
            mOnLocationGetlisGetListener.onLocationGet(entity);
            mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
        }

    }

    @Override
    public void onLocationGet(PositionEntity entity) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {

        // TODO Auto-generated method stub

    }

}
