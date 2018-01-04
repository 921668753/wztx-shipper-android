package com.ruitukeji.zwbh.main;

import android.Manifest;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.dialog.LocationBouncedDialog;
import com.ruitukeji.zwbh.entity.BaseResult;
import com.ruitukeji.zwbh.entity.main.HomeBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.DateChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.HoursChooseBean;
import com.ruitukeji.zwbh.entity.main.TimeChooseBean.ResultBean.MinutesChooseBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.main.announcement.AnnouncementActivity;
import com.ruitukeji.zwbh.main.cargoinformation.AddCargoInformationActivity;
import com.ruitukeji.zwbh.main.message.MessageCenterActivity;
import com.ruitukeji.zwbh.main.message.SystemMessageActivity;
import com.ruitukeji.zwbh.main.selectaddress.ProvenanceActivity;
import com.ruitukeji.zwbh.main.selectaddress.SelectAddressActivity;
import com.ruitukeji.zwbh.mine.PersonalCenterActivity;
import com.ruitukeji.zwbh.utils.DataUtil;
import com.ruitukeji.zwbh.utils.FileNewUtil;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.SoftKeyboardUtils;
import com.ruitukeji.zwbh.utils.amap.AMapUtil;
import com.ruitukeji.zwbh.utils.amap.SensorEventHelper;
import com.sunfusheng.marqueeview.MarqueeView;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1;


public class Main2Activity extends BaseActivity implements EasyPermissions.PermissionCallbacks, MainContract.View, OnGeocodeSearchListener, LocationSource, AMapLocationListener, OnCameraChangeListener, CloudSearch.OnCloudSearchListener {

    /**
     * 个人中心
     */
    @BindView(id = R.id.img_user, click = true)
    private ImageView img_user;

    /**
     * 消息中心
     */
    @BindView(id = R.id.img_message, click = true)
    private ImageView img_message;
    @BindView(id = R.id.tv_message)
    private TextView tv_message;


    /**
     * 同城物流
     */
    @BindView(id = R.id.ll_cityDistribution, click = true)
    private LinearLayout ll_cityDistribution;
    @BindView(id = R.id.tv_cityDistribution)
    private TextView tv_cityDistribution;
    @BindView(id = R.id.tv_cityDistribution1)
    private TextView tv_cityDistribution1;
    /**
     * 全国物流
     */
    @BindView(id = R.id.ll_longTrunk, click = true)
    private LinearLayout ll_longTrunk;
    @BindView(id = R.id.tv_longTrunk)
    private TextView tv_longTrunk;
    @BindView(id = R.id.tv_longTrunk1)
    private TextView tv_longTrunk1;
    /**
     * 通知
     */
    @BindView(id = R.id.ll_ad)
    private LinearLayout ll_ad;
    @BindView(id = R.id.marqueeView)
    private MarqueeView marqueeView;

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

    private SweetAlertDialog sweetAlertDialog = null;
    private String province = null;
    private String city = null;
    private String aoiName = null;

    private String title = null;

    private int type = 0;

    private CloudSearch mCloudSearch;
    private LocationBouncedDialog locationBouncedDialog;

    private long firstTime = 0;

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

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";


    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private boolean mFirstFix = false;
    private Marker mLocMarker;
    private SensorEventHelper mSensorHelper;
    private Circle mCircle;
    private GeocodeSearch geocoderSearch;
    private LatLng location;
    private String dateStr;
    private String hoursStr;
    private String minutesStr;
    private List<DateChooseBean> date_choose;
    private List<HoursChooseBean> hours_choose;
    private List<MinutesChooseBean> minutes_choose;
    private int day = 0;
    private int hours = 0;
    private int minutes = 0;
    private int isOff1 = 0;
    private int isOff = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        locationBouncedDialog = new LocationBouncedDialog(getIntent().getIntExtra("img", R.mipmap.startpage), this);
        locationBouncedDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    dialog = null;
                    finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
        locationBouncedDialog.show();
        mapView = (MapView) findViewById(R.id.map);
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
    public void setRootView() {
        setContentView(R.layout.activity_main1);
    }

    @Override
    public void initData() {
        super.initData();

        mPresenter = new MainPresenter(this);
        mSensorHelper = new SensorEventHelper(this);
        geocoderSearch = new GeocodeSearch(this);
        mCloudSearch = new CloudSearch(this);// 初始化查询类
        ((MainContract.Presenter) mPresenter).getHome();
    }

    @Override
    public void initWidget() {
        super.initWidget();

        registerMessageReceiver();
        geocoderSearch.setOnGeocodeSearchListener(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
        mCloudSearch.setOnCloudSearchListener(this);// 设置回调函数
        ((MainContract.Presenter) mPresenter).settingType(this, 0, tv_realTime, tv_urgent, tv_makeAppointment);
        tv_appointmentTime.setVisibility(View.GONE);
        ll_appointmentTime.setVisibility(View.GONE);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_user:
                showActivity(aty, PersonalCenterActivity.class);
                break;
            case R.id.img_message:
                showActivity(aty, SystemMessageActivity.class);
                tv_message.setVisibility(View.GONE);
                break;
            case R.id.ll_appointmentTime:
                SoftKeyboardUtils.packUpKeyboard(this);
                pvOptions.setSelectOptions(day, hours, minutes);
                pvOptions.show(tv_appointmentTime1);
                break;
            case R.id.ll_cityDistribution:
                tran_type = 0;
                ((MainContract.Presenter) mPresenter).chooseLogisticsType(this, 0, tv_cityDistribution, tv_cityDistribution1, tv_longTrunk, tv_longTrunk1);
                break;
            case R.id.ll_longTrunk:
                tran_type = 1;
                ((MainContract.Presenter) mPresenter).chooseLogisticsType(this, 1, tv_cityDistribution, tv_cityDistribution1, tv_longTrunk, tv_longTrunk1);
                break;
            case R.id.img_gps:
                if (location == null) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.319688, 121.062545), 18));
                    break;
                }
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude - 0.0005, location.longitude), 18));
                break;
            case R.id.tv_realTime:
                type1 = "often";
                ((MainContract.Presenter) mPresenter).settingType(this, 0, tv_realTime, tv_urgent, tv_makeAppointment);
                tv_appointmentTime.setVisibility(View.GONE);
                ll_appointmentTime.setVisibility(View.GONE);
                break;
            case R.id.tv_urgent:
                type1 = "urgent";
                ((MainContract.Presenter) mPresenter).settingType(this, 1, tv_realTime, tv_urgent, tv_makeAppointment);
                tv_appointmentTime.setVisibility(View.GONE);
                ll_appointmentTime.setVisibility(View.GONE);
                break;
            case R.id.tv_makeAppointment:
                type1 = "appoint";
                ((MainContract.Presenter) mPresenter).settingType(this, 2, tv_realTime, tv_urgent, tv_makeAppointment);
                tv_appointmentTime.setVisibility(View.VISIBLE);
                ll_appointmentTime.setVisibility(View.VISIBLE);
                tv_appointmentTime1.setText(getString(R.string.appointmentTime2));
                pvOptions = null;
                appointmentTime();
                break;
            case R.id.tv_pleaseEnterDeparturePoint:
                type = 0;
                Intent provenanceIntent = new Intent(this, SelectAddressActivity.class);
                if (isProvenance == 0 || StringUtils.isEmpty(provenanceLat) || StringUtils.isEmpty(provenanceDistrict) || StringUtils.isEmpty(provenancePlaceName)) {
                    provenanceIntent.setClass(this, SelectAddressActivity.class);
                } else {
                    provenanceIntent.setClass(this, ProvenanceActivity.class);
                    provenanceIntent.putExtra("isProvenance", isProvenance);
                    provenanceIntent.putExtra("isOff1", isOff);
                }
                provenanceIntent.putExtra("type", type);
                provenanceIntent.putExtra("title", getString(R.string.provenance));
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
                startActivityForResult(provenanceIntent, REQUEST_CODE_CHOOSE_PHOTO);
                break;
            case R.id.tv_enterDestination:
                type = 1;
                Intent destinationIntent = new Intent();
                if (isDestination == 0 || StringUtils.isEmpty(destinationLat) || StringUtils.isEmpty(destinationDistrict) || StringUtils.isEmpty(destinationPlaceName)) {
                    destinationIntent.setClass(this, SelectAddressActivity.class);
                } else {
                    destinationIntent.setClass(this, ProvenanceActivity.class);
                    destinationIntent.putExtra("isProvenance", isDestination);
                    destinationIntent.putExtra("isOff1", isOff1);
                }
                destinationIntent.putExtra("lat", destinationLat);
                destinationIntent.putExtra("longi", destinationLongi);
                destinationIntent.putExtra("district", destinationDistrict);
                destinationIntent.putExtra("placeName", destinationPlaceName);
                destinationIntent.putExtra("tran_type", tran_type);
                destinationIntent.putExtra("cityName", city);
                destinationIntent.putExtra("type", type);
                destinationIntent.putExtra("title", getString(R.string.destination));
                destinationIntent.putExtra("detailedAddress", destinationDetailedAddress);
                destinationIntent.putExtra("deliveryCustomer", destinationDeliveryCustomer);
                destinationIntent.putExtra("shipper", destinationShipper);
                destinationIntent.putExtra("phone", destinationPhone);
                destinationIntent.putExtra("eixedTelephone", destinationEixedTelephone);
                startActivityForResult(destinationIntent, REQUEST_CODE_PHOTO_PREVIEW);
                break;
            case R.id.rl_cargoInformation:
                if (StringUtils.isEmpty(provenanceDistrict) || StringUtils.isEmpty(provenancePlaceName)) {
                    ViewInject.toast(getString(R.string.pleaseEnterDeparturePoint));
                    break;
                }

                if (StringUtils.isEmpty(provenanceDetailedAddress) || StringUtils.isEmpty(provenanceDeliveryCustomer) || StringUtils.isEmpty(provenanceShipper) || StringUtils.isEmpty(provenancePhone)) {
                    ViewInject.toast(getString(R.string.pleaseEnterInformationShipper));
                    break;
                }

                if (StringUtils.isEmpty(destinationDistrict)) {
                    ViewInject.toast(getString(R.string.enterDestination));
                    break;
                }

                if (StringUtils.isEmpty(destinationDetailedAddress) || StringUtils.isEmpty(destinationDeliveryCustomer) || StringUtils.isEmpty(destinationShipper) || StringUtils.isEmpty(destinationPhone)) {
                    ViewInject.toast(getString(R.string.pleaseEnterConsigneeInformation));
                    break;
                }
                if (type1.equals("appoint") && tv_appointmentTime1.getText().toString().equals(getString(R.string.appointmentTime2))) {
                    ViewInject.toast(getString(R.string.appointmentTime2));
                    return;
                }
                if (type1.equals("appoint") && DataUtil.getStringToDate(tv_appointmentTime1.getText().toString(), getString(R.string.timeStr)) < System.currentTimeMillis()) {
                    ViewInject.toast(getString(R.string.greateThanCurrentTime));
                    return;
                }
                Intent cargoInformationIntent = new Intent(this, AddCargoInformationActivity.class);
                cargoInformationIntent.putExtra("tran_type", tran_type);
                cargoInformationIntent.putExtra("type", type1);
                if (type1.equals("appoint")) {
                    cargoInformationIntent.putExtra("appoint_at", DataUtil.getStringToDate(tv_appointmentTime1.getText().toString(), getString(R.string.timeStr)) / 1000 + "");
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
                startActivityForResult(cargoInformationIntent, REQUEST_CODE_PHOTO_PREVIEW1);
                break;
        }
    }

    /**
     * 请选择预约时间
     */
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

        dateStr = DataUtil.formatData(System.currentTimeMillis() / 1000, getString(R.string.dateStr));
        date_choose = new ArrayList<DateChooseBean>();
        hours_choose = new ArrayList<HoursChooseBean>();
        minutes_choose = new ArrayList<MinutesChooseBean>();
        DateChooseBean dateChooseBean = new DateChooseBean();
        dateChooseBean.setDateStr(" " + dateStr);
        DateChooseBean dateChooseBean1 = new DateChooseBean();
        dateChooseBean1.setDateStr(" " + DataUtil.formatData(System.currentTimeMillis() / 1000 + 24 * 60 * 60, getString(R.string.dateStr)));
        DateChooseBean dateChooseBean2 = new DateChooseBean();
        dateChooseBean2.setDateStr(" " + DataUtil.formatData(System.currentTimeMillis() / 1000 + 2 * 24 * 60 * 60, getString(R.string.dateStr)));
        DateChooseBean dateChooseBean3 = new DateChooseBean();
        dateChooseBean3.setDateStr(" " + DataUtil.formatData(System.currentTimeMillis() / 1000 + 3 * 24 * 60 * 60, getString(R.string.dateStr)));
        DateChooseBean dateChooseBean4 = new DateChooseBean();
        dateChooseBean4.setDateStr(" " + DataUtil.formatData(System.currentTimeMillis() / 1000 + 4 * 24 * 60 * 60, getString(R.string.dateStr)));
        DateChooseBean dateChooseBean5 = new DateChooseBean();
        dateChooseBean5.setDateStr(" " + DataUtil.formatData(System.currentTimeMillis() / 1000 + 5 * 24 * 60 * 60, getString(R.string.dateStr)));
        DateChooseBean dateChooseBean6 = new DateChooseBean();
        dateChooseBean6.setDateStr(" " + DataUtil.formatData(System.currentTimeMillis() / 1000 + 6 * 24 * 60 * 60, getString(R.string.dateStr)));
        date_choose.add(dateChooseBean);
        date_choose.add(dateChooseBean1);
        date_choose.add(dateChooseBean2);
        date_choose.add(dateChooseBean3);
        date_choose.add(dateChooseBean4);
        date_choose.add(dateChooseBean5);
        date_choose.add(dateChooseBean6);
        for (int i = 0; i < 24; i++) {
            HoursChooseBean hoursChooseBean = new HoursChooseBean();
            if (i < 10) {
                hoursChooseBean.setHoursStr("0" + i + getString(R.string.dian));
            } else {
                hoursChooseBean.setHoursStr(i + getString(R.string.dian));
            }
            hours_choose.add(hoursChooseBean);
        }

        MinutesChooseBean minutesChooseBean = new MinutesChooseBean();
        minutesChooseBean.setMinutesStr("00" + getString(R.string.minute));
        MinutesChooseBean minutesChooseBean1 = new MinutesChooseBean();
        minutesChooseBean1.setMinutesStr("10" + getString(R.string.minute));
        MinutesChooseBean minutesChooseBean2 = new MinutesChooseBean();
        minutesChooseBean2.setMinutesStr("20" + getString(R.string.minute));
        MinutesChooseBean minutesChooseBean3 = new MinutesChooseBean();
        minutesChooseBean3.setMinutesStr("30" + getString(R.string.minute));
        MinutesChooseBean minutesChooseBean4 = new MinutesChooseBean();
        minutesChooseBean4.setMinutesStr("40" + getString(R.string.minute));
        MinutesChooseBean minutesChooseBean5 = new MinutesChooseBean();
        minutesChooseBean5.setMinutesStr("50" + getString(R.string.minute));
        minutes_choose.add(minutesChooseBean);
        minutes_choose.add(minutesChooseBean1);
        minutes_choose.add(minutesChooseBean2);
        minutes_choose.add(minutesChooseBean3);
        minutes_choose.add(minutesChooseBean4);
        minutes_choose.add(minutesChooseBean5);
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
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        unregisterReceiver(mMessageReceiver);
    }

    /**
     * 退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    //如果两次按键时间间隔大于2秒，则不退出
                    ViewInject.toast(getString(R.string.exitProcedureAgain));
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    //  int i = 1 / 0;
                    //   KjBitmapUtil.getInstance().getKjBitmap().cleanCache();
                    MobclickAgent.onProfileSignOff();//关闭账号统计     退出登录也加
                    JPushInterface.stopCrashHandler(getApplication());//JPush关闭CrashLog上报
                    MobclickAgent.onKillProcess(aty);
                    KJActivityStack.create().appExit(aty);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            tv_pleaseEnterDeparturePoint.setText(provenancePlaceName);
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
            tv_enterDestination.setText(destinationPlaceName);
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW1 && resultCode == RESULT_OK) {
            /**
             * 目的地信息
             */
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
    }


    /**
     * 对正在移动地图事件回调
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        android.util.Log.d("tag", cameraPosition.toString());
    }

    /**
     * 对移动地图结束事件回调
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        android.util.Log.d("tag", "1");
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
        if (EasyPermissions.hasPermissions(this, perms)) {
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
                    addCircle(location, amapLocation.getAccuracy());//添加定位精度圆
                    addMarker(location);//添加定位图标
                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                } else {
                    mCircle.setCenter(location);
                    mCircle.setRadius(amapLocation.getAccuracy());
                    mLocMarker.setPosition(location);
                }
                PreferenceHelper.write(aty, StringConstants.FILENAME, "locationCity", amapLocation.getCity());
                deactivate();
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(amapLocation.getLatitude() - 0.0005, amapLocation.getLongitude()), 18));
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
            mlocationClient = new AMapLocationClient(this);
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

    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }

    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_map_mylocation);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS && regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
            province = regeocodeResult.getRegeocodeAddress().getProvince();
            city = regeocodeResult.getRegeocodeAddress().getCity();
            isProvenance = 1;
            provenanceLat = String.valueOf(regeocodeResult.getRegeocodeQuery().getPoint().getLatitude());
            provenanceLongi = String.valueOf(regeocodeResult.getRegeocodeQuery().getPoint().getLongitude());
            provenanceDistrict = province + city + regeocodeResult.getRegeocodeAddress().getDistrict();
            provenancePlaceName = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            tv_pleaseEnterDeparturePoint.setText(provenancePlaceName);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "currentLocationProvince", province);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "currentLocationCity", city);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "currentLocationArea", regeocodeResult.getRegeocodeAddress().getDistrict());
            android.util.Log.d("tag", regeocodeResult.getRegeocodeAddress().getFormatAddress());
            android.util.Log.d("tag", regeocodeResult.getRegeocodeAddress().getProvince());
            android.util.Log.d("tag", regeocodeResult.getRegeocodeAddress().getCity());
            android.util.Log.d("tag", regeocodeResult.getRegeocodeAddress().getDistrict());
        } else {
            isProvenance = 0;
            provenanceLat = "";
            provenanceLongi = "";
            city = "";
            provenanceDistrict = "";
            provenancePlaceName = "";
            dismissDialog();
            ViewInject.toast(getString(R.string.no_result));
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 1) {
            if (!(success.equals("true"))) {
                return;
            }
            String updateAppUrl = PreferenceHelper.readString(this, StringConstants.FILENAME, "updateAppUrl", null);
            if (StringUtils.isEmpty(updateAppUrl)) {
                return;
            }
            sweetAlertDialog = new SweetAlertDialog(aty, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setTitleText(getString(R.string.updateVersion))
                    .setCancelText(getString(R.string.cancel1))
                    .setConfirmText(getString(R.string.update))
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            ((MainContract.Presenter) mPresenter).downloadApp(updateAppUrl);
                        }
                    }).show();
            dismissLoadingDialog();
        } else if (flag == 2) {
            BaseResult baseResult = (BaseResult) JsonUtil.getInstance().json2Obj(success, BaseResult.class);
            if ((String) baseResult.getResult() == null) {
                return;
            }
            File path = new File((String) baseResult.getResult());
            FileNewUtil.installApkFile(this, path.getAbsolutePath());
            dismissLoadingDialog();
        } else if (flag == 3) {
            HomeBean homeBean = (HomeBean) JsonUtil.getInstance().json2Obj(success, HomeBean.class);
            if (homeBean.getResult().getUnreadMsg() == null || homeBean.getResult().getUnreadMsg().getMsgX() == 0) {
                tv_message.setVisibility(View.GONE);
            } else {
                tv_message.setVisibility(View.VISIBLE);
                String accessToken = PreferenceHelper.readString(this, StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    tv_message.setVisibility(View.GONE);
                }
                tv_message.setText(String.valueOf(homeBean.getResult().getUnreadMsg().getMsgX()));
            }
            processLogic(homeBean.getResult().getList());
            dismissLoadingDialog();
        }
    }

    /**
     * 广告轮播图
     */
    List<String> tips = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private void processLogic(List<HomeBean.ResultBean.ListBean> list) {
        if (list == null || list.size() == 0) {
            ll_ad.setVisibility(View.GONE);
            return;
        }
        tips.clear();
        for (int i = 0; i < list.size(); i++) {
            tips.add(list.get(i).getAd_content());
        }
        marqueeView.startWithList(tips);
// 在代码里设置自己的动画
        //   marqueeView.startWithList(tips, R.anim.anim_bottom_in, R.anim.anim_top_out);
        ll_ad.setVisibility(View.VISIBLE);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Intent intent = new Intent(aty, AnnouncementActivity.class);
                intent.putExtra("id", list.get(position).getId());
                showActivity(aty, intent);
            }
        });
    }

    @Override
    public void errorMsg(String msg, int flag) {
        if (flag == 0) {
            ViewInject.toast(msg);
        } else if (flag == 1) {
        } else if (flag == 2) {
        } else if (flag == 3) {
            ((MainContract.Presenter) mPresenter).getHome();
        } else if (flag == 5 || flag == 6 || flag == 7 || flag == 8 || flag == 9 || flag == 10 || flag == 11 || flag == 12) {
            if (msg.equals("" + NumericConstants.TOLINGIN)) {
                showActivity(aty, LoginActivity.class);
            }
        }
        dismissLoadingDialog();
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        Intent intent = registerReceiver(mMessageReceiver, filter);
        //极光推送 定制声音、震动、闪灯等 Notification 样式。
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
//        builder.statusBarDrawable = R.mipmap.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);
    }

    @Override
    public void onCloudSearched(CloudResult cloudResult, int rCode) {
        Log.d("tag", cloudResult.getClouds().toString());
        if (rCode == AMapException.CODE_AMAP_SUCCESS && cloudResult != null && cloudResult.getClouds() != null && !cloudResult.getClouds().isEmpty()) {
            AMapUtil.addEmulateData1(aMap, cloudResult.getClouds());
        } else {
            ViewInject.toast(getString(R.string.no_result));
        }
        dismissDialog();
    }

    /**
     * 关闭弹框
     */
    public void dismissDialog() {
        //实现页面跳转
        if (locationBouncedDialog != null && locationBouncedDialog.isShowing()) {
            locationBouncedDialog.dismiss();
        }
    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {

    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!StringUtils.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                //  setCostomMsg(showMsg.toString());
            }
        }
    }

}