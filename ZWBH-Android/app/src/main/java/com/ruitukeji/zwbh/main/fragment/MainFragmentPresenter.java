package com.ruitukeji.zwbh.main.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.MinutesChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.HoursChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.DateChooseBean;
import com.ruitukeji.zwbh.main.Main3Activity;
import com.ruitukeji.zwbh.main.cargoinformation.AddCargoInformationActivity;
import com.ruitukeji.zwbh.main.selectaddress.ProvenanceActivity;
import com.ruitukeji.zwbh.main.selectaddress.SelectAddressActivity;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.DataUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MainFragmentPresenter implements MainFragmentContract.Presenter {

    private MainFragmentContract.View mView;

    public MainFragmentPresenter(MainFragmentContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getHome() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getHome(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

    @Override
    public List<DateChooseBean> addDateChooseBean(List<DateChooseBean> date_choose) {
        long date = System.currentTimeMillis() / 1000;
        String dateStr = KJActivityStack.create().topActivity().getString(R.string.dateStr);
        date_choose = new ArrayList<DateChooseBean>();
        DateChooseBean dateChooseBean = new DateChooseBean();
        dateChooseBean.setDateStr(" " + DataUtil.formatData(date, dateStr));
        DateChooseBean dateChooseBean1 = new DateChooseBean();
        dateChooseBean1.setDateStr(" " + DataUtil.formatData(date + 24 * 60 * 60, dateStr));
        DateChooseBean dateChooseBean2 = new DateChooseBean();
        dateChooseBean2.setDateStr(" " + DataUtil.formatData(date + 2 * 24 * 60 * 60, dateStr));
        DateChooseBean dateChooseBean3 = new DateChooseBean();
        dateChooseBean3.setDateStr(" " + DataUtil.formatData(date + 3 * 24 * 60 * 60, dateStr));
        DateChooseBean dateChooseBean4 = new DateChooseBean();
        dateChooseBean4.setDateStr(" " + DataUtil.formatData(date + 4 * 24 * 60 * 60, dateStr));
        DateChooseBean dateChooseBean5 = new DateChooseBean();
        dateChooseBean5.setDateStr(" " + DataUtil.formatData(date + 5 * 24 * 60 * 60, dateStr));
        DateChooseBean dateChooseBean6 = new DateChooseBean();
        dateChooseBean6.setDateStr(" " + DataUtil.formatData(date + 6 * 24 * 60 * 60, dateStr));
        date_choose.add(dateChooseBean);
        date_choose.add(dateChooseBean1);
        date_choose.add(dateChooseBean2);
        date_choose.add(dateChooseBean3);
        date_choose.add(dateChooseBean4);
        date_choose.add(dateChooseBean5);
        date_choose.add(dateChooseBean6);
        return date_choose;
    }

    @Override
    public List<HoursChooseBean> addHoursChooseBean(List<HoursChooseBean> hours_choose) {
        hours_choose = new ArrayList<HoursChooseBean>();
        for (int i = 0; i < 24; i++) {
            HoursChooseBean hoursChooseBean = new HoursChooseBean();
            if (i < 10) {
                hoursChooseBean.setHoursStr("0" + i + KJActivityStack.create().topActivity().getString(R.string.dian));
            } else {
                hoursChooseBean.setHoursStr(i + KJActivityStack.create().topActivity().getString(R.string.dian));
            }
            hours_choose.add(hoursChooseBean);
        }
        return hours_choose;
    }

    @Override
    public List<MinutesChooseBean> addMinutesChooseBean(List<MinutesChooseBean> minutes_choose) {
        minutes_choose = new ArrayList<MinutesChooseBean>();
        String minute = KJActivityStack.create().topActivity().getString(R.string.minute);
        MinutesChooseBean minutesChooseBean = new MinutesChooseBean();
        minutesChooseBean.setMinutesStr("00" + minute);
        MinutesChooseBean minutesChooseBean1 = new MinutesChooseBean();
        minutesChooseBean1.setMinutesStr("10" + minute);
        MinutesChooseBean minutesChooseBean2 = new MinutesChooseBean();
        minutesChooseBean2.setMinutesStr("20" + minute);
        MinutesChooseBean minutesChooseBean3 = new MinutesChooseBean();
        minutesChooseBean3.setMinutesStr("30" + minute);
        MinutesChooseBean minutesChooseBean4 = new MinutesChooseBean();
        minutesChooseBean4.setMinutesStr("40" + minute);
        MinutesChooseBean minutesChooseBean5 = new MinutesChooseBean();
        minutesChooseBean5.setMinutesStr("50" + minute);
        minutes_choose.add(minutesChooseBean);
        minutes_choose.add(minutesChooseBean1);
        minutes_choose.add(minutesChooseBean2);
        minutes_choose.add(minutesChooseBean3);
        minutes_choose.add(minutesChooseBean4);
        minutes_choose.add(minutesChooseBean5);
        return minutes_choose;
    }


    @Override
    public void settingType(Main3Activity activity, int type, TextView tv_realTime, TextView tv_urgent, TextView tv_makeAppointment) {
        tv_realTime.setTextColor(activity.getResources().getColor(R.color.typecolors));
        tv_realTime.setBackgroundResource(R.color.mainColor);
        tv_realTime.setBackgroundResource(R.drawable.shape_main_type2);
        tv_urgent.setTextColor(activity.getResources().getColor(R.color.typecolors));
        tv_urgent.setBackgroundResource(R.color.mainColor);
        tv_makeAppointment.setTextColor(activity.getResources().getColor(R.color.typecolors));
        tv_makeAppointment.setBackgroundResource(R.color.mainColor);
        tv_makeAppointment.setBackgroundResource(R.drawable.shape_main_type2);
        if (type == 0) {
            tv_realTime.setTextColor(activity.getResources().getColor(R.color.announcementCloseColors));
            tv_realTime.setBackgroundResource(R.drawable.shape_main_type1);
        } else if (type == 1) {
            tv_urgent.setTextColor(activity.getResources().getColor(R.color.announcementCloseColors));
            tv_urgent.setBackgroundResource(R.drawable.shape_main_type1);
        } else if (type == 2) {
            tv_makeAppointment.setTextColor(activity.getResources().getColor(R.color.announcementCloseColors));
            tv_makeAppointment.setBackgroundResource(R.drawable.shape_main_type1);
        } else {
            tv_realTime.setTextColor(activity.getResources().getColor(R.color.announcementCloseColors));
            tv_realTime.setBackgroundResource(R.drawable.shape_main_type1);
        }
    }

    @Override
    public void startActivityForResult(BaseFragment baseFragment, int isProvenance, int isOff, int type, int tran_type, String provenanceLat, String provenanceLongi,
                                       String city, String provenanceDistrict, String provenancePlaceName, String provenanceDetailedAddress, String provenanceDeliveryCustomer,
                                       String provenanceShipper, String provenancePhone, String provenanceEixedTelephone, int resultCode) {
        Intent provenanceIntent = new Intent();
        if (isProvenance == 0 || StringUtils.isEmpty(provenanceLat) || StringUtils.isEmpty(provenanceDistrict) || StringUtils.isEmpty(provenancePlaceName)) {
            provenanceIntent.setClass(KJActivityStack.create().topActivity(), SelectAddressActivity.class);
        } else {
            provenanceIntent.setClass(KJActivityStack.create().topActivity(), ProvenanceActivity.class);
            provenanceIntent.putExtra("isProvenance", isProvenance);
            provenanceIntent.putExtra("isOff1", isOff);
        }
        provenanceIntent.putExtra("type", type);
        if (type == 0) {
            provenanceIntent.putExtra("title", KJActivityStack.create().topActivity().getString(R.string.provenance));
        } else {
            provenanceIntent.putExtra("title", KJActivityStack.create().topActivity().getString(R.string.destination));
        }
        provenanceIntent.putExtra("tran_type", tran_type);
        provenanceIntent.putExtra("lat", provenanceLat);
        provenanceIntent.putExtra("longi", provenanceLongi);
        provenanceIntent.putExtra("cityName", city);
        provenanceIntent.putExtra("district", provenanceDistrict);
        provenanceIntent.putExtra("placeName", provenancePlaceName);
        provenanceIntent.putExtra("detailedAddress", provenanceDetailedAddress);
        provenanceIntent.putExtra("deliveryCustomer", provenanceDeliveryCustomer);
        provenanceIntent.putExtra("shipper", provenanceShipper);
        provenanceIntent.putExtra("phone", provenancePhone);
        provenanceIntent.putExtra("eixedTelephone", provenanceEixedTelephone);
        baseFragment.startActivityForResult(provenanceIntent, resultCode);
    }

    @Override
    public void startAddCargoInformationActivityForResult(BaseFragment baseFragment, int tran_type, String type1, String appointmentTime, String provenanceLat, String provenanceLongi,
                                                          String provenanceDistrict, String provenancePlaceName, String provenanceDetailedAddress,
                                                          String provenanceDeliveryCustomer, String provenanceShipper, String provenancePhone,
                                                          String provenanceEixedTelephone, String destinationLat, String destinationLongi, String destinationDistrict,
                                                          String destinationPlaceName, String destinationDetailedAddress, String destinationDeliveryCustomer,
                                                          String destinationShipper, String destinationPhone, String destinationEixedTelephone) {

        if (StringUtils.isEmpty(provenanceDistrict) || StringUtils.isEmpty(provenancePlaceName)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseEnterDeparturePoint), 0);
            return;
        }
        if (StringUtils.isEmpty(provenanceDeliveryCustomer) || StringUtils.isEmpty(provenanceShipper) || StringUtils.isEmpty(provenancePhone)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseEnterInformationShipper), 0);
            return;
        }
        if (StringUtils.isEmpty(destinationDistrict) || StringUtils.isEmpty(destinationPlaceName)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.enterDestination), 0);
            return;
        }
        if (StringUtils.isEmpty(destinationDeliveryCustomer) || StringUtils.isEmpty(destinationShipper) || StringUtils.isEmpty(destinationPhone)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseEnterConsigneeInformation), 0);
            return;
        }
        int orgprovince = provenancePlaceName.indexOf("省");
        int orgcity = provenancePlaceName.indexOf("市");
        String provenanceCity = null;
        if (orgprovince == -1 && orgcity != -1) {
            provenanceCity = provenancePlaceName.substring(0, orgcity + 1);
        } else if (orgprovince != -1 && orgcity != -1) {
            provenanceCity = provenancePlaceName.substring(orgprovince + 1, orgcity + 1);
        }
        int orgprovince1 = destinationPlaceName.indexOf("省");
        int orgcity1 = destinationPlaceName.indexOf("市");
        String destinationCity = null;
        if (orgprovince1 == -1 && orgcity1 != -1) {
            destinationCity = provenancePlaceName.substring(0, orgcity1 + 1);
        } else if (orgprovince1 != -1 && orgcity != -1) {
            destinationCity = destinationPlaceName.substring(orgprovince1 + 1, orgcity1 + 1);
        }
        if (!provenanceCity.equals(destinationCity) && tran_type == 0) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.enterAddressSameCity1), 0);
            return;
        }
        if (type1.equals("appoint") && appointmentTime.equals(KJActivityStack.create().topActivity().getString(R.string.appointmentTime2))) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.appointmentTime2), 0);
            return;
        }
        if (type1.equals("appoint") && DataUtil.getStringToDate(appointmentTime, KJActivityStack.create().topActivity().getString(R.string.timeStr)) < System.currentTimeMillis()) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.greateThanCurrentTime), 0);
            return;
        }
        Intent cargoInformationIntent = new Intent(KJActivityStack.create().topActivity(), AddCargoInformationActivity.class);
        cargoInformationIntent.putExtra("tran_type", tran_type);
        cargoInformationIntent.putExtra("type", type1);
        if (type1.equals("appoint")) {
            cargoInformationIntent.putExtra("appoint_at", DataUtil.getStringToDate(appointmentTime, KJActivityStack.create().topActivity().getString(R.string.timeStr)) / 1000 + "");
        }
        cargoInformationIntent.putExtra("provenanceLat", provenanceLat);
        cargoInformationIntent.putExtra("provenanceLongi", provenanceLongi);
        cargoInformationIntent.putExtra("provenanceDistrict", provenanceDistrict);
        cargoInformationIntent.putExtra("provenancePlaceName", provenancePlaceName);
        cargoInformationIntent.putExtra("provenanceDetailedAddress", provenanceDetailedAddress);
        cargoInformationIntent.putExtra("provenanceDeliveryCustomer", provenanceDeliveryCustomer);
        cargoInformationIntent.putExtra("provenanceShipper", provenanceShipper);
        cargoInformationIntent.putExtra("provenancePhone", provenancePhone);
        cargoInformationIntent.putExtra("provenanceEixedTelephone", provenanceEixedTelephone);
        cargoInformationIntent.putExtra("destinationLat", destinationLat);
        cargoInformationIntent.putExtra("destinationLongi", destinationLongi);
        cargoInformationIntent.putExtra("destinationDistrict", destinationDistrict);
        cargoInformationIntent.putExtra("destinationPlaceName", destinationPlaceName);
        cargoInformationIntent.putExtra("destinationDetailedAddress", destinationDetailedAddress);
        cargoInformationIntent.putExtra("destinationDeliveryCustomer", destinationDeliveryCustomer);
        cargoInformationIntent.putExtra("destinationShipper", destinationShipper);
        cargoInformationIntent.putExtra("destinationPhone", destinationPhone);
        cargoInformationIntent.putExtra("destinationEixedTelephone", destinationEixedTelephone);
        baseFragment.startActivityForResult(cargoInformationIntent, REQUEST_CODE_PHOTO_PREVIEW1);
    }


    @Override
    public void addCircleMarker(LatLng latlng, double radius, AMap aMap, Circle mCircle, Marker mLocMarker) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(Color.argb(10, 0, 0, 180));
        options.strokeColor(Color.argb(180, 3, 145, 255));
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
        if (mLocMarker != null) {
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(KJActivityStack.create().topActivity().getResources(), R.mipmap.ic_map_mylocation);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        MarkerOptions options1 = new MarkerOptions();
        options1.icon(des);
        options1.anchor(0.5f, 0.5f);
        options1.position(latlng);
        mLocMarker = aMap.addMarker(options1);
    }
}
