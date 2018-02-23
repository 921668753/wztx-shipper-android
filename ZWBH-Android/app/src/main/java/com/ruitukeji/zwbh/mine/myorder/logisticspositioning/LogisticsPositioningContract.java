package com.ruitukeji.zwbh.mine.myorder.logisticspositioning;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/21.
 */

public interface LogisticsPositioningContract {


    interface Presenter extends BasePresenter {
        /**
         * 获取订单轨迹信息
         */
        void getTrajectory(String id);
        /**
         * 地图添加定位点
         */
        Circle addCircle(LatLng latlng, double radius, AMap aMap, Circle mCircle);

        Marker addMarker(LatLng latlng, double radius, AMap aMap, Marker mLocMarker);
    }

    interface View extends BaseView<Presenter> {
        /**
         * http请求正确
         *
         * @param s
         */
        void getSuccess(String s);

        /**
         * http请求错误
         */
        void error(String msg);

    }

}
