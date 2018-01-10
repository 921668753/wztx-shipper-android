package com.ruitukeji.zwbh.main.fragment;

import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.bigkoo.pickerview.OptionsPickerView;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.MinutesChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.HoursChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.DateChooseBean;
import com.ruitukeji.zwbh.main.Main3Activity;

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

        List<HoursChooseBean> addHoursChooseBean(List<HoursChooseBean> hours_choose);

        List<MinutesChooseBean> addMinutesChooseBean(List<MinutesChooseBean> minutes_choose);

        /**
         * 地图添加定位点
         */
        void addCircleMarker(LatLng latlng, double radius, AMap aMap, Circle mCircle, Marker mLocMarker);


        /**
         * 设置状态：实时，加急，预约
         */
        void settingType(Main3Activity activity, int type, TextView tv_realTime, TextView tv_urgent, TextView tv_makeAppointment);


        /**
         * 始發地/目的地
         */
        void startActivityForResult(BaseFragment baseFragment, int isProvenance, int isOff, int type, int tran_type, String provenanceLat, String provenanceLongi,
                                    String city, String provenanceDistrict, String provenancePlaceName, String provenanceDetailedAddress, String provenanceDeliveryCustomer,
                                    String provenanceShipper, String provenancePhone, String provenanceEixedTelephone, int resultCode);

        /**
         * 跳傳貨物詳情
         */
        void startAddCargoInformationActivityForResult(BaseFragment baseFragment, int tran_type, String type, String appointmentTime,String provenanceLat, String provenanceLongi, String provenanceDistrict,
                                                       String provenancePlaceName, String provenanceDetailedAddress, String provenanceDeliveryCustomer, String provenanceShipper,
                                                       String provenancePhone, String provenanceEixedTelephone, String destinationLat, String destinationLongi,
                                                       String destinationDistrict, String destinationPlaceName, String destinationDetailedAddress,
                                                       String destinationDeliveryCustomer, String destinationShipper, String destinationPhone, String destinationEixedTelephone);

    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
