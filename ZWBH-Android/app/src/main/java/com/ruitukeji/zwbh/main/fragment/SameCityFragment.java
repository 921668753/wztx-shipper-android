package com.ruitukeji.zwbh.main.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bigkoo.pickerview.OptionsPickerView;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.main.HomeBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.MinutesChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.HoursChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.DateChooseBean;
import com.ruitukeji.zwbh.main.Main3Activity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.SoftKeyboardUtils;
import com.ruitukeji.zwbh.utils.amap.AMapUtil;
import com.ruitukeji.zwbh.utils.amap.SensorEventHelper;

import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1;


/**
 * 同城专车
 * Created by Administrator on 2018/1/9.
 */
public class SameCityFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, MainFragmentContract.View, GeocodeSearch.OnGeocodeSearchListener, LocationSource, AMapLocationListener, AMap.OnCameraChangeListener, CloudSearch.OnCloudSearchListener {


    /**
     * 定位
     */
    @BindView(id = R.id.img_gps, click = true)
    private ImageView img_gps;

    /**
     * 实时
     */
    @BindView(id = R.id.tv_realTime, click = true)
    private TextView tv_realTime;
    /**
     * 加急
     */
    @BindView(id = R.id.tv_urgent, click = true)
    private TextView tv_urgent;

    /**
     * 预约
     */
    @BindView(id = R.id.tv_makeAppointment, click = true)
    private TextView tv_makeAppointment;

    /**
     * 预约时间
     */
    @BindView(id = R.id.tv_appointmentTime)
    private TextView tv_appointmentTime;
    @BindView(id = R.id.ll_appointmentTime, click = true)
    private LinearLayout ll_appointmentTime;
    @BindView(id = R.id.tv_appointmentTime1)
    private TextView tv_appointmentTime1;

    /**
     * 出发地
     */
    @BindView(id = R.id.tv_pleaseEnterDeparturePoint, click = true)
    private TextView tv_pleaseEnterDeparturePoint;


    /**
     * 目的地
     */
    @BindView(id = R.id.tv_enterDestination, click = true)
    private TextView tv_enterDestination;

    /**
     * 货物信息
     */

    @BindView(id = R.id.ll_cargoInformation)
    private LinearLayout ll_cargoInformation;

    @BindView(id = R.id.rl_cargoInformation, click = true)
    private RelativeLayout rl_cargoInformation;

    /**
     * 价格
     */
    @BindView(id = R.id.tv_price)
    private TextView tv_price;


    /**
     * 指派车辆
     */
    @BindView(id = R.id.tv_assignedVehicle, click = true)
    private TextView tv_assignedVehicle;

    private String province = null;
    private String city = null;

    private int type = 0;

    private CloudSearch mCloudSearch;

    /**
     * 时间选择器
     */
    private OptionsPickerView pvOptions;

    /**
     * 发货信息
     */
    private int isProvenance = 0;
    private String provenanceDetailedAddress;
    private String provenanceDeliveryCustomer;
    private String provenanceShipper;
    private String provenancePhone;
    private String provenanceEixedTelephone;
    private String provenanceLat;
    private String provenanceLongi;
    private String provenanceDistrict;
    private String provenancePlaceName;

    /**
     * 目的地信息
     */
    private int isDestination = 0;
    private String destinationLat;
    private String destinationLongi;
    private String destinationDistrict;
    private String destinationPlaceName;
    private String destinationDetailedAddress;
    private String destinationDeliveryCustomer;
    private String destinationShipper;
    private String destinationPhone;
    private String destinationEixedTelephone;

    /**
     * 物流类型
     */
    private String type1 = "often";
    private int tran_type = 0;


    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private boolean mFirstFix = false;
    private Marker mLocMarker;
    private SensorEventHelper mSensorHelper;
    private Circle mCircle;

    private LatLng location;
    private List<DateChooseBean> date_choose;
    private List<HoursChooseBean> hours_choose;
    private List<MinutesChooseBean> minutes_choose;
    private int day = 0;
    private int hours = 0;
    private int minutes = 0;
    private int isOff1 = 0;
    private int isOff = 0;

    private Main3Activity aty;
    private GeocodeSearch geocoderSearch = null;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (Main3Activity) getActivity();
        View view = View.inflate(aty, R.layout.fragment_intercity, null);
        init(view, bundle);
        return view;
    }

    private void init(View view, Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器，会回调onCameraChange（）和onCameraChangeFinish（）
            //设置成中文地图
            aMap.setMapLanguage(AMap.CHINESE);
            //  aMap.getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);// 设置地图logo显示在左下方
        }
        choiceLocationWrapper();
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new MainFragmentPresenter(this);
        mSensorHelper = new SensorEventHelper(getActivity());
        geocoderSearch = new GeocodeSearch(getActivity());
        mCloudSearch = new CloudSearch(getActivity());// 初始化查询类
        date_choose = ((MainFragmentContract.Presenter) mPresenter).addDateChooseBean(date_choose);
        hours_choose = ((MainFragmentContract.Presenter) mPresenter).addHoursChooseBean(hours_choose);
        minutes_choose = ((MainFragmentContract.Presenter) mPresenter).addMinutesChooseBean(minutes_choose);
        ((MainFragmentContract.Presenter) mPresenter).getHome();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        geocoderSearch.setOnGeocodeSearchListener(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
        mCloudSearch.setOnCloudSearchListener(this);// 设置回调函数
        ((MainFragmentContract.Presenter) mPresenter).settingType(aty, 0, tv_realTime, tv_urgent, tv_makeAppointment);
        tv_appointmentTime.setVisibility(View.GONE);
        ll_appointmentTime.setVisibility(View.GONE);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_appointmentTime:
                SoftKeyboardUtils.packUpKeyboard(getActivity());
                pvOptions.setSelectOptions(day, hours, minutes);
                pvOptions.show(tv_appointmentTime1);
                break;
            case R.id.img_gps:
                if (location == null) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.319687, 121.062545), 15));
                    break;
                }
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude - 0.0004, location.longitude), 15));
                break;
            case R.id.tv_realTime:
                type1 = "often";
                ((MainFragmentContract.Presenter) mPresenter).settingType(aty, 0, tv_realTime, tv_urgent, tv_makeAppointment);
                tv_appointmentTime.setVisibility(View.GONE);
                ll_appointmentTime.setVisibility(View.GONE);
                break;
            case R.id.tv_urgent:
                type1 = "urgent";
                ((MainFragmentContract.Presenter) mPresenter).settingType(aty, 1, tv_realTime, tv_urgent, tv_makeAppointment);
                tv_appointmentTime.setVisibility(View.GONE);
                ll_appointmentTime.setVisibility(View.GONE);
                break;
            case R.id.tv_makeAppointment:
                type1 = "appoint";
                ((MainFragmentContract.Presenter) mPresenter).settingType(aty, 2, tv_realTime, tv_urgent, tv_makeAppointment);
                tv_appointmentTime.setVisibility(View.VISIBLE);
                ll_appointmentTime.setVisibility(View.VISIBLE);
                tv_appointmentTime1.setText(getString(R.string.appointmentTime2));
                pvOptions = null;
                appointmentTime();
                break;
            case R.id.tv_pleaseEnterDeparturePoint:
                type = 0;
                ((MainFragmentContract.Presenter) mPresenter).startActivityForResult(this, isProvenance, isOff, type, tran_type, provenanceLat,
                        provenanceLongi, city, provenanceDistrict, provenancePlaceName, provenanceDetailedAddress, provenanceDeliveryCustomer,
                        provenanceShipper, provenancePhone, provenanceEixedTelephone, REQUEST_CODE_CHOOSE_PHOTO);
                break;
            case R.id.tv_enterDestination:
                if (StringUtils.isEmpty(provenanceDistrict) || StringUtils.isEmpty(provenancePlaceName)) {
                    ViewInject.toast(getString(R.string.pleaseEnterDeparturePoint));
                    break;
                }
                if (StringUtils.isEmpty(provenanceDeliveryCustomer) || StringUtils.isEmpty(provenanceShipper) || StringUtils.isEmpty(provenancePhone)) {
                    ViewInject.toast(getString(R.string.pleaseEnterInformationShipper));
                    break;
                }
                type = 1;
                ((MainFragmentContract.Presenter) mPresenter).startActivityForResult(this, isDestination, isOff1, type, tran_type, destinationLat,
                        destinationLongi, city, destinationDistrict, destinationPlaceName, destinationDetailedAddress, destinationDeliveryCustomer,
                        destinationShipper, destinationPhone, destinationEixedTelephone, REQUEST_CODE_PHOTO_PREVIEW);
                break;
            case R.id.rl_cargoInformation:
                ((MainFragmentContract.Presenter) mPresenter).startAddCargoInformationActivityForResult(this, tran_type, type1,
                        tv_appointmentTime1.getText().toString(), provenanceLat, provenanceLongi, provenanceDistrict, provenancePlaceName,
                        provenanceDetailedAddress, provenanceDeliveryCustomer, provenanceShipper, provenancePhone, provenanceEixedTelephone,
                        destinationLat, destinationLongi, destinationDistrict, destinationPlaceName, destinationDetailedAddress,
                        destinationDeliveryCustomer, destinationShipper, destinationPhone, destinationEixedTelephone, REQUEST_CODE_PHOTO_PREVIEW1);
                break;
        }
    }

    /**
     * 请选择预约时间
     */
    @SuppressLint("SetTextI18n")
    private void appointmentTime() {
        pvOptions = new OptionsPickerView.Builder(aty, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                day = options1;
                hours = option2;
                minutes = options3;
                ((TextView) v).setText(date_choose.get(options1).getDateStr().trim() + hours_choose.get(option2).getHoursStr() + minutes_choose.get(options3).getMinutesStr());
            }
        })
                .setCancelColor(getResources().getColor(R.color.hintcolors))
                .setSubmitColor(getResources().getColor(R.color.announcementCloseColors))
                .setSubCalSize(15)
                .setContentTextSize(14)
                .setLineSpacingMultiplier(1.6f)
                .setTextColorCenter(getResources().getColor(R.color.titletextcolors))
                .build();
        pvOptions.setNPicker(date_choose, hours_choose, minutes_choose);
        day = 0;
        hours = (new Date()).getHours();
        minutes = (new Date()).getMinutes();
        if (minutes <= 10 && minutes >= 0) {
            minutes = 1;
        } else if (minutes <= 20 && minutes > 10) {
            minutes = 2;
        } else if (minutes <= 30 && minutes > 20) {
            minutes = 3;
        } else if (minutes <= 40 && minutes > 30) {
            minutes = 4;
        } else if (minutes <= 50 && minutes > 40) {
            minutes = 5;
        } else {
            minutes = 0;
            hours = hours + 1;
            if (hours > 23) {
                hours = 0;
                day = 1;
            }
        }
        pvOptions.setSelectOptions(day, hours, minutes);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
        readDefaultAddress();
    }

    /**
     * 读取默认发货地址
     */
    private void readDefaultAddress() {
        boolean isDefaultAddress = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isDefaultAddress", false);
        if (!isDefaultAddress) {
            return;
        }
        PreferenceHelper.write(aty, StringConstants.FILENAME, "isDefaultAddress", false);
        provenanceLat = PreferenceHelper.readString(aty, StringConstants.FILENAME, "provenanceLat", "");
        isOff = 1;
        provenanceLongi = PreferenceHelper.readString(aty, StringConstants.FILENAME, "provenanceLongi", "");
        provenanceDistrict = PreferenceHelper.readString(aty, StringConstants.FILENAME, "provenanceDistrict", "");
        provenancePlaceName = PreferenceHelper.readString(aty, StringConstants.FILENAME, "provenancePlaceName", "");
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(StringUtils.toDouble(provenanceLat) - 0.0004, StringUtils.toDouble(provenanceLongi)), 15));
        tv_pleaseEnterDeparturePoint.setText(provenancePlaceName);
        provenanceDetailedAddress = PreferenceHelper.readString(aty, StringConstants.FILENAME, "provenanceDetailedAddress", "");
        provenanceDeliveryCustomer = PreferenceHelper.readString(aty, StringConstants.FILENAME, "provenanceDeliveryCustomer", "");
        provenanceShipper = PreferenceHelper.readString(aty, StringConstants.FILENAME, "provenanceShipper", "");
        provenancePhone = PreferenceHelper.readString(aty, StringConstants.FILENAME, "provenancePhone", "");
        provenanceEixedTelephone = PreferenceHelper.readString(aty, StringConstants.FILENAME, "provenanceEixedTelephone", "");
        int orgprovince = provenancePlaceName.indexOf("省");
        int orgcity = provenancePlaceName.indexOf("市");
        if (orgprovince == -1 && orgcity != -1) {
            city = provenancePlaceName.substring(0, orgcity + 1);
        } else if (orgprovince != -1 && orgcity != -1) {
            province = provenancePlaceName.substring(0, orgprovince + 1);
            city = provenancePlaceName.substring(orgprovince + 1, orgcity + 1);
        }
//        destinationLat = PreferenceHelper.readString(this, StringConstants.FILENAME, "destinationLat", "");
//        destinationLongi = PreferenceHelper.readString(this, StringConstants.FILENAME, "destinationLongi", "");
//        destinationDistrict = PreferenceHelper.readString(this, StringConstants.FILENAME, "destinationDistrict", "");
//        destinationPlaceName = PreferenceHelper.readString(this, StringConstants.FILENAME, "destinationPlaceName", "");
//        destinationDetailedAddress = PreferenceHelper.readString(this, StringConstants.FILENAME, "destinationDetailedAddress", "");
//        destinationDeliveryCustomer = PreferenceHelper.readString(this, StringConstants.FILENAME, "destinationDeliveryCustomer", "");
//        destinationShipper = PreferenceHelper.readString(this, StringConstants.FILENAME, "destinationShipper", "");
//        destinationPhone = PreferenceHelper.readString(this, StringConstants.FILENAME, "destinationPhone", "");
//        destinationEixedTelephone = PreferenceHelper.readString(this, StringConstants.FILENAME, "destinationEixedTelephone", "");
//        if (!StringUtils.isEmpty(destinationLat)) {
//            isOff1 = 1;
//        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            aMap.setOnCameraChangeListener(null);
        } else {
            aMap.setOnCameraChangeListener(this);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
        mapView.onPause();
        deactivate();
        mFirstFix = false;
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        date_choose.clear();
        date_choose = null;
        hours_choose.clear();
        hours_choose = null;
        minutes_choose.clear();
        minutes_choose = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            /**
             * 选择始发地页面返回
             */
            isProvenance = 1;
            provenanceLat = data.getStringExtra("lat");
            provenanceLongi = data.getStringExtra("longi");
            provenanceDistrict = data.getStringExtra("district");
            provenancePlaceName = data.getStringExtra("placeName");
            provenanceDetailedAddress = data.getStringExtra("detailedAddress");
            provenanceDeliveryCustomer = data.getStringExtra("deliveryCustomer");
            provenanceShipper = data.getStringExtra("shipper");
            provenancePhone = data.getStringExtra("phone");
            isOff = data.getIntExtra("isOff1", 0);
            provenanceEixedTelephone = data.getStringExtra("eixedTelephone");
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(StringUtils.toDouble(provenanceLat) - 0.0004, StringUtils.toDouble(provenanceLongi)), 15));
            tv_pleaseEnterDeparturePoint.setText(provenancePlaceName);
            if (tran_type == 0) {
                int orgprovince = provenancePlaceName.indexOf("省");
                int orgcity = provenancePlaceName.indexOf("市");
                if (orgprovince == -1 && orgcity != -1) {
                    city = provenancePlaceName.substring(0, orgcity + 1);
                } else if (orgprovince != -1 && orgcity != -1) {
                    province = provenancePlaceName.substring(0, orgprovince + 1);
                    city = provenancePlaceName.substring(orgprovince + 1, orgcity + 1);
                }
            }
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW && resultCode == RESULT_OK) {
            /**
             * 选择目的地页面返回
             */
            isDestination = 1;
            destinationLat = data.getStringExtra("lat");
            destinationLongi = data.getStringExtra("longi");
            destinationDistrict = data.getStringExtra("district");
            destinationPlaceName = data.getStringExtra("placeName");
            destinationDetailedAddress = data.getStringExtra("detailedAddress");
            destinationDeliveryCustomer = data.getStringExtra("deliveryCustomer");
            destinationShipper = data.getStringExtra("shipper");
            destinationPhone = data.getStringExtra("phone");
            isOff1 = data.getIntExtra("isOff1", 0);
            destinationEixedTelephone = data.getStringExtra("eixedTelephone");
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(StringUtils.toDouble(destinationLat) - 0.0004, StringUtils.toDouble(destinationLongi)), 15));
            tv_enterDestination.setText(destinationPlaceName);
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW1 && resultCode == RESULT_OK) {
            /**
             * 目的地信息
             */
            cleanDestination();
        }
    }

    /**
     * 清除目的地信息
     */
    private void cleanDestination() {
        destinationLat = "";
        destinationLongi = "";
        destinationDistrict = "";
        destinationPlaceName = "";
        destinationDetailedAddress = "";
        destinationDeliveryCustomer = "";
        destinationShipper = "";
        destinationPhone = "";
        destinationEixedTelephone = "";
        isOff1 = 0;
        tv_enterDestination.setText(destinationPlaceName);
    }


    /**
     * 对正在移动地图事件回调
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.d("tag", cameraPosition.toString());
    }

    /**
     * 对移动地图结束事件回调
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        Log.d("tag", "1");
//// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(AMapUtil.convertToLatLonPoint(cameraPosition.target), 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
        // 设置中心点及检索范围
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(AMapUtil.convertToLatLonPoint(cameraPosition.target), 10000);
        //设置查询条件 mTableID是将数据存储到数据管理台后获得。
        try {
            CloudSearch.Query mQuery = new CloudSearch.Query(StringConstants.NearTableid, "", bound);
            mCloudSearch.searchCloudAsyn(mQuery);// 异步搜索
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }

    @AfterPermissionGranted(NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choiceLocationWrapper() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.CHANGE_WIFI_STATE};
        if (EasyPermissions.hasPermissions(aty, perms)) {
            // 设置定位监听
            aMap.setLocationSource(this);
            aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.locationPermissions), NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            ViewInject.toast(getString(R.string.locationPermissions1));
        } else if (requestCode == NumericConstants.READ_AND_WRITE_CODE) {
            ViewInject.toast(getString(R.string.sdPermission));
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                Log.d("tag", location.latitude + "," + location.longitude);
                if (!mFirstFix) {
                    mFirstFix = true;
                    ((MainFragmentContract.Presenter) mPresenter).addCircleMarker(location, amapLocation.getAccuracy(), aMap, mCircle, mLocMarker);
//                    addCircle(location, amapLocation.getAccuracy());//添加定位精度圆
//                    addMarker(location);//添加定位图标
                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                } else {
                    mCircle.setCenter(location);
                    mCircle.setRadius(amapLocation.getAccuracy());
                    mLocMarker.setPosition(location);
                }
                PreferenceHelper.write(aty, StringConstants.FILENAME, "locationCity", amapLocation.getCity());
                deactivate();
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(amapLocation.getLatitude() - 0.0004, amapLocation.getLongitude()), 15));
            } else {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "locationCity", getString(R.string.locateFailure));
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(aty);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            mLocationOption.setOnceLocationLatest(true);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS && regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
            isProvenance = 1;
            if (!StringUtils.isEmpty(provenanceDeliveryCustomer) && !StringUtils.isEmpty(provenanceShipper) && !StringUtils.isEmpty(provenancePhone)) {
                return;
            }
            province = regeocodeResult.getRegeocodeAddress().getProvince();
            city = regeocodeResult.getRegeocodeAddress().getCity();
            provenanceLat = String.valueOf(regeocodeResult.getRegeocodeQuery().getPoint().getLatitude());
            provenanceLongi = String.valueOf(regeocodeResult.getRegeocodeQuery().getPoint().getLongitude());
            provenanceDistrict = province + city + regeocodeResult.getRegeocodeAddress().getDistrict();
            provenancePlaceName = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            PreferenceHelper.write(aty, StringConstants.FILENAME, "currentLocationProvince", province);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "currentLocationCity", city);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "currentLocationArea", regeocodeResult.getRegeocodeAddress().getDistrict());
            Log.d("tag", regeocodeResult.getRegeocodeAddress().getFormatAddress());
            Log.d("tag", province);
            Log.d("tag", city);
            Log.d("tag", regeocodeResult.getRegeocodeAddress().getDistrict());
            tv_pleaseEnterDeparturePoint.setText(provenancePlaceName);
        } else {
            isProvenance = 0;
            provenanceLat = "";
            provenanceLongi = "";
            city = "";
            provenanceDistrict = "";
            provenancePlaceName = "";
            aty.dismissDialog();
            // ViewInject.toast(getString(R.string.no_result));
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void setPresenter(MainFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            HomeBean homeBean = (HomeBean) JsonUtil.getInstance().json2Obj(success, HomeBean.class);
            aty.processLogic(homeBean.getResult().getList());
            if (homeBean.getResult().getUnreadMsg() == null || homeBean.getResult().getUnreadMsg().getMsgX() == 0) {
                aty.tv_message.setVisibility(View.GONE);
            } else {
                aty.tv_message.setVisibility(View.VISIBLE);
                String accessToken = PreferenceHelper.readString(aty, StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    aty.tv_message.setVisibility(View.GONE);
                }
                aty.tv_message.setText(String.valueOf(homeBean.getResult().getUnreadMsg().getMsgX()));
            }
            HomeBean.ResultBean.StartAddressBean start_address = homeBean.getResult().getStart_address();
            if (start_address != null && start_address.getCity() != null) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isDefaultAddress", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isDefaultAddress1", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "provenanceLat", start_address.getAddress_maps().split(",")[1]);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "provenanceLongi", start_address.getAddress_maps().split(",")[0]);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "provenanceDistrict", start_address.getCity());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "provenancePlaceName", start_address.getAddress_name());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "provenanceDetailedAddress", start_address.getAddress_detail());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "provenanceDeliveryCustomer", start_address.getClient());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "provenanceShipper", start_address.getClient_name());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "provenancePhone", start_address.getPhone());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "provenanceEixedTelephone", start_address.getTelphone());
            }
            HomeBean.ResultBean.EndAddressBean end_address = homeBean.getResult().getEnd_address();
            if (end_address != null && end_address.getCity() != null) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "destinationLat", end_address.getAddress_maps().split(",")[1]);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "destinationLongi", end_address.getAddress_maps().split(",")[0]);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "destinationDistrict", end_address.getCity());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "destinationPlaceName", end_address.getAddress_name());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "destinationDetailedAddress", end_address.getAddress_detail());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "destinationDeliveryCustomer", end_address.getClient());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "destinationShipper", end_address.getClient_name());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "destinationPhone", end_address.getPhone());
                PreferenceHelper.write(aty, StringConstants.FILENAME, "destinationEixedTelephone", end_address.getTelphone());
            }
            readDefaultAddress();
        }
        dismissLoadingDialog();
    }


    @Override
    public void errorMsg(String msg, int flag) {
        if (flag == 0) {
            ViewInject.toast(msg);
        }
        dismissLoadingDialog();

    }


    @Override
    public void onCloudSearched(CloudResult cloudResult, int rCode) {
        Log.d("tag", cloudResult.getClouds().toString());
        if (rCode == AMapException.CODE_AMAP_SUCCESS && cloudResult != null && cloudResult.getClouds() != null && !cloudResult.getClouds().isEmpty()) {
            AMapUtil.addEmulateData1(aMap, cloudResult.getClouds());
        } else {
            //     ViewInject.toast(getString(R.string.no_result));
        }
        aty.dismissDialog();
    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {

    }


}