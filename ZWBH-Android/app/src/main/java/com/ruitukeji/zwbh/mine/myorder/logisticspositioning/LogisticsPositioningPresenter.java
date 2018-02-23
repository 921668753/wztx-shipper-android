package com.ruitukeji.zwbh.mine.myorder.logisticspositioning;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.kymjs.common.Log;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.BuildConfig;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.NearbySearchBean;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/21.
 */

public class LogisticsPositioningPresenter implements LogisticsPositioningContract.Presenter {


    private LogisticsPositioningContract.View mView;

    public LogisticsPositioningPresenter(LogisticsPositioningContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    /**
     * 获取订单实时信息经纬度
     */
    @Override
    public void getTrajectory(String id) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        /**
         * http://yuntuapi.amap.com/datamanage/data/list?tableid=52b155b6e4b0bc61deeb7629&filter
         *=_name:颐和园+type:公园&limit=10&page=1&key= <用户key>
         *
         */
        httpParams.put("tableid", StringConstants.NearTableid);
        //当前时间截   System.currentTimeMillis()
        httpParams.put("filter", "_name=" + id + "createtime:[11111111,222222222]");
//        httpParams.put("sortrule", 0);
//        httpParams.put("limit", 100);
//        httpParams.put("page", 1);
        httpParams.put("key", BuildConfig.GAODE_WEBKEY);

        RequestClient.getTrajectory(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                NearbySearchBean nearbySearch = (NearbySearchBean) JsonUtil.getInstance().json2Obj(response, NearbySearchBean.class);
                if (nearbySearch.getStatus() == NumericConstants.STATUS) {
                    mView.getSuccess(response);
                } else {
                    Log.d("nearbySearch", nearbySearch.getStatus() + "");
                    mView.error("周边搜索出现异常,云端返回数据错误");
                }
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }

    @Override
    public Circle addCircle(LatLng latlng, double radius, AMap aMap, Circle mCircle) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(Color.argb(10, 0, 0, 180));
        options.strokeColor(Color.argb(180, 3, 145, 255));
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
        return mCircle;
    }

    @Override
    public Marker addMarker(LatLng latlng, double radius, AMap aMap, Marker mLocMarker) {
        if (mLocMarker != null) {
            return null;
        }
        Bitmap bMap = BitmapFactory.decodeResource(KJActivityStack.create().topActivity().getResources(), R.mipmap.ic_map_mylocation);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        MarkerOptions options1 = new MarkerOptions();
        options1.icon(des);
        options1.anchor(0.5f, 0.5f);
        options1.position(latlng);
        mLocMarker = aMap.addMarker(options1);
        return mLocMarker;
    }
}
