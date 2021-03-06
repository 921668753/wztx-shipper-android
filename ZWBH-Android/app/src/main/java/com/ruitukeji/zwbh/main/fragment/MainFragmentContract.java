package com.ruitukeji.zwbh.main.fragment;

import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.services.core.LatLonPoint;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.MinutesChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.HoursChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.DateChooseBean;
import com.ruitukeji.zwbh.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface MainFragmentContract {

    interface Presenter extends BasePresenter {
        /**
         * 首页
         */
        void getHome();

        /**
         * 请选择预约时间
         */
        List<DateChooseBean> addDateChooseBean(List<DateChooseBean> date_choose);

        List<ArrayList<HoursChooseBean>> addHoursChooseBean(List<ArrayList<HoursChooseBean>> hours_choose);

        List<ArrayList<ArrayList<MinutesChooseBean>>> addMinutesChooseBean(List<ArrayList<ArrayList<MinutesChooseBean>>> minutes_choose);

        /**
         * 地图添加定位点
         */
        Circle addCircle(LatLng latlng, double radius, AMap aMap, Circle mCircle);

        Marker addMarker(LatLng latlng, double radius, AMap aMap, Marker mLocMarker);

        /**
         * 设置状态：实时，加急，预约
         */
        void settingType(MainActivity activity, int type, TextView tv_realTime, TextView tv_urgent, TextView tv_makeAppointment);


        /**
         * 获取附近信息
         */
        void getNearbySearch(LatLonPoint latLonPoint);

        /**
         * 同城始發地/目的地
         */
        void startActivityForResult(BaseFragment baseFragment, int isProvenance, int isOff, int type, int tran_type, String provenanceLat, String provenanceLongi,
                                    String city, String provenanceDistrict, String provenancePlaceName, String provenanceDetailedAddress, String provenanceDeliveryCustomer,
                                    String provenanceShipper, String provenancePhone, String provenanceEixedTelephone, int resultCode, String startCity);


        /**
         * 跳傳貨物詳情
         */
        void startAddCargoInformationActivityForResult(BaseFragment baseFragment, int tran_type, String type, String appointmentTime, String provenanceLat, String provenanceLongi, String provenanceDistrict,
                                                       String provenancePlaceName, String provenanceDetailedAddress, String provenanceDeliveryCustomer, String provenanceShipper,
                                                       String provenancePhone, String provenanceEixedTelephone, String destinationLat, String destinationLongi,
                                                       String destinationDistrict, String destinationPlaceName, String destinationDetailedAddress,
                                                       String destinationDeliveryCustomer, String destinationShipper, String destinationPhone, String destinationEixedTelephone, int resultCode);

    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
