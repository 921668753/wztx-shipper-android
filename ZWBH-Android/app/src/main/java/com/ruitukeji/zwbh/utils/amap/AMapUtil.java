/**
 * Project Name:Android_Car_Example
 * File Name:AMapUtil.java
 * Package Name:com.amap.api.car.example
 * Date:2015年4月7日下午3:43:05
 */

package com.ruitukeji.zwbh.utils.amap;


import android.graphics.Color;
import android.util.Log;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.core.LatLonPoint;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.NearbySearchBean.DatasBean;

import java.util.ArrayList;
import java.util.List;


/**
 * ClassName:AMapUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2015年4月7日 下午3:43:05 <br/>
 *
 * @author yiyi.qi
 * @see
 * @since JDK 1.6
 */
public class AMapUtil {

    /**
     * 添加模拟测试的车的点
     */
    public static void addEmulateData(AMap amap, List<DatasBean> listInfo) {
        Log.d("tag", "addEmulateData");
        if (listInfo.size() != 0) {
            ArrayList<Marker> markers = new ArrayList<Marker>();
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_car);
            for (int i = 0; i < listInfo.size() - 1; i++) {
                DatasBean nearbyInfo = listInfo.get(i);
                MarkerOptions markerOptions = new MarkerOptions();
                //   markerOptions.setFlat(true);
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.draggable(false);//设置Marker可拖动
                markerOptions.icon(bitmapDescriptor);
                String[] latLng = nearbyInfo.get_location().split(",");
//                Log.d("tag","latLng[0]="+latLng[0]+",latLng[1]="+latLng[1]);
                markerOptions.position(new LatLng(Double.parseDouble(latLng[1]), Double.parseDouble(latLng[0])));
                Marker marker = amap.addMarker(markerOptions);
                marker.setTitle(nearbyInfo.getCar_length() + "/" + nearbyInfo.getCar_type() + "   " + nearbyInfo.getCar_number());
                markers.add(marker);
            }
        }
    }

    /**
     * 添加模拟测试的车的点
     */
    public static void addEmulateData1(AMap amap, List<CloudItem> listInfo) {
        Log.d("tag", "addEmulateData");
        if (listInfo.size() != 0) {
            ArrayList<Marker> markers = new ArrayList<Marker>();
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_car);
            for (int i = 0; i < listInfo.size() - 1; i++) {
                CloudItem cloudItem = listInfo.get(i);
                MarkerOptions markerOptions = new MarkerOptions();
                //   markerOptions.setFlat(true);
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.draggable(false);//设置Marker可拖动
                markerOptions.icon(bitmapDescriptor);
                //     String[] latLng = cloudItem.get_location().split(",");
//                Log.d("tag","latLng[0]="+latLng[0]+",latLng[1]="+latLng[1]);
                markerOptions.position(convertToLatLng(cloudItem.getLatLonPoint()));
                Marker marker = amap.addMarker(markerOptions);
                marker.setTitle(cloudItem.getCustomfield().get("car_length") + "/" + cloudItem.getCustomfield().get("car_type"));
                markers.add(marker);
            }
        }
    }

    /**
     * 绘制绘制线
     */
    public static void drawLineMap(AMap aMap, List<DatasBean> listInfo) {
        List<LatLng> latLngList = new ArrayList<LatLng>();
        if (listInfo.size() != 0) {
            for (int i = 0; i < listInfo.size() - 1; i++) {
                DatasBean nearbyInfo = listInfo.get(i);
                String[] latLngs = nearbyInfo.get_location().split(",");
                LatLng latLng = new LatLng(Double.parseDouble(latLngs[0]), Double.parseDouble(latLngs[1]));
                if (i == 0) {
                    customMarker(aMap, latLng, true);
                } else if (i == listInfo.size() - 1) {
                    customMarker(aMap, latLng, true);
                }
                latLngList.add(latLng);
            }
        }
        //  aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
        //   aMap.addMarker(new MarkerOptions().position(new LatLng(39.955192, 116.140092)).title("北京").snippet("DefaultMarker"));
        aMap.addPolyline(new PolylineOptions().addAll(latLngList).width(10).color(Color.GREEN));
    }

    /**
     * 绘制自定义Marker
     */
    public static void customMarker(AMap aMap, LatLng latLng, boolean bool) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("西安市");
        //    .snippet("西安市：34.341568, 108.940174");
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_car));
        aMap.addMarker(markerOption);
        Marker marker = aMap.addMarker(markerOption);
        if (bool) {
            marker.showInfoWindow();
        }
//    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.icon_car)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        //  marker.setFlat(true);//设置marker平贴地图效果
    }


    /**
     * 绘制自定义Marker
     */
    public static Marker customMarker(AMap aMap, LatLng latLng, String title) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.anchor(0.5f, 0.5f);
        markerOption.draggable(false);//设置Marker可拖动
        int orgcity = title.indexOf("市");
        markerOption.title(title.substring(0, orgcity + 1)).snippet(title.substring(orgcity + 1));
        //    .snippet("西安市：34.341568, 108.940174");
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_car));
        aMap.addMarker(markerOption);
        Marker marker = aMap.addMarker(markerOption);
        // 定义 Marker 点击事件监听
        //   marker.showInfoWindow();
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.isInfoWindowShown()) {
                    markerOption.visible(false);
                    marker.hideInfoWindow();
                } else {
                    markerOption.visible(true);
                    marker.showInfoWindow();
                }
                return false;
            }
        };
// 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
//    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.icon_car)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        //  marker.setFlat(true);//设置marker平贴地图效果
        return marker;
    }


    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

}
  
