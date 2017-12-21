package com.ruitukeji.zwbh.main;

import android.Manifest;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.bigkoo.pickerview.OptionsPickerView;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.dialog.LocationBouncedDialog;
import com.ruitukeji.zwbh.entity.BaseResult;
import com.ruitukeji.zwbh.entity.HomeBean;
import com.ruitukeji.zwbh.entity.HomeBean.ResultBean.ListBean;
import com.ruitukeji.zwbh.entity.NearbySearchBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.main.announcement.AnnouncementActivity;
import com.ruitukeji.zwbh.main.cargoinformation.AddCargoInformationActivity;
import com.ruitukeji.zwbh.main.dialog.AssignedVehicleBouncedDialog;
import com.ruitukeji.zwbh.main.message.MessageCenterActivity;
import com.ruitukeji.zwbh.main.selectaddress.SelectAddressActivity;
import com.ruitukeji.zwbh.mine.PersonalCenterActivity;
import com.ruitukeji.zwbh.mine.myorder.MyOrderActivity;
import com.ruitukeji.zwbh.mine.mypublishedorder.MyPublishedGoodsActivity;
import com.ruitukeji.zwbh.mine.mywallet.MyWalletActivity;
import com.ruitukeji.zwbh.mine.personaldata.PersonalDataActivity;
import com.ruitukeji.zwbh.mine.invitefriends.SharePoliteActivity;
import com.ruitukeji.zwbh.mine.setting.SettingsActivity;
import com.ruitukeji.zwbh.utils.FileNewUtil;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.amap.AMapUtil;
import com.sunfusheng.marqueeview.MarqueeView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.utils.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1;

public class Main1Activity extends BaseActivity implements EasyPermissions.PermissionCallbacks, LocationSource, AMapLocationListener, MainContract.View, CloudSearch.OnCloudSearchListener {

    private long firstTime = 0;
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";


    private MapView mMapView;

    private AMap mAmap;

    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

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
     * 提交订单
     */
    @BindView(id = R.id.ll_submitOrders)
    private LinearLayout ll_submitOrders;
    @BindView(id = R.id.tv_submitOrders, click = true)
    private TextView tv_submitOrders;

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
    private CloudSearch.Query mQuery;
    private LocationBouncedDialog locationBouncedDialog;
    private Timer timer;
    private boolean isTost = true;

    /**
     * 时间选择器
     */
    private OptionsPickerView pvOptions;

    /**
     * 发货信息
     */
    private String detailedAddress;
    private String deliveryCustomer;
    private String shipper;
    private String phone;
    private String eixedTelephone;
    private String lat;
    private String longi;
    private String district;
    private String placeName;
    /**
     * 目的地信息
     */
    private String lat1;
    private String longi1;
    private String district1;
    private String placeName1;
    private String detailedAddress1;
    private String deliveryCustomer1;
    private String shipper1;
    private String phone1;
    private String eixedTelephone1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main1);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new MainPresenter(this);
//        locationBouncedDialog = new LocationBouncedDialog(this);
//        locationBouncedDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss();
//                    dialog = null;
//                    finish();
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
        //    timer = new Timer();
        registerMessageReceiver();
        mCloudSearch = new CloudSearch(this);// 初始化查询类
        mCloudSearch.setOnCloudSearchListener(this);// 设置回调函数
        boolean isUpdate = PreferenceHelper.readBoolean(this, StringConstants.FILENAME, "isUpdate", false);
        getSuccess(String.valueOf(isUpdate), 1);
    }

    @Override
    public void initWidget() {
        super.initWidget();
//        img_message.setOnClickListener(this);
//        img_user.setOnClickListener(this);
//        ll_cityDistribution.setOnClickListener(this);
//        ll_longTrunk.setOnClickListener(this);
        //   locationBouncedDialog.show();
        ((MainContract.Presenter) mPresenter).settingType(this, 0, tv_realTime, tv_urgent, tv_makeAppointment);
        tv_appointmentTime.setVisibility(View.GONE);
        ll_appointmentTime.setVisibility(View.GONE);
        ll_cargoInformation.setVisibility(View.GONE);
        ll_submitOrders.setVisibility(View.GONE);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_user:
                showActivity(aty, PersonalCenterActivity.class);
                break;
            case R.id.img_message:
                //   tv_tag.setVisibility(View.GONE);
                showActivity(aty, MessageCenterActivity.class);
                break;
            case R.id.ll_appointmentTime:

                break;
            case R.id.ll_cityDistribution:
                ((MainContract.Presenter) mPresenter).chooseLogisticsType(this, 0, tv_cityDistribution, tv_cityDistribution1, tv_longTrunk, tv_longTrunk1);
                break;
            case R.id.ll_longTrunk:
                ((MainContract.Presenter) mPresenter).chooseLogisticsType(this, 1, tv_cityDistribution, tv_cityDistribution1, tv_longTrunk, tv_longTrunk1);
                break;
            case R.id.tv_realTime:
                ((MainContract.Presenter) mPresenter).settingType(this, 0, tv_realTime, tv_urgent, tv_makeAppointment);
                tv_appointmentTime.setVisibility(View.GONE);
                ll_appointmentTime.setVisibility(View.GONE);
                break;
            case R.id.tv_urgent:
                ((MainContract.Presenter) mPresenter).settingType(this, 1, tv_realTime, tv_urgent, tv_makeAppointment);
                tv_appointmentTime.setVisibility(View.GONE);
                ll_appointmentTime.setVisibility(View.GONE);
                break;
            case R.id.tv_makeAppointment:
                ((MainContract.Presenter) mPresenter).settingType(this, 2, tv_realTime, tv_urgent, tv_makeAppointment);
                tv_appointmentTime.setVisibility(View.VISIBLE);
                ll_appointmentTime.setVisibility(View.VISIBLE);
                tv_appointmentTime1.setText(getString(R.string.appointmentTime2));
                break;
            case R.id.tv_pleaseEnterDeparturePoint:
                Intent provenanceIntent = new Intent(this, SelectAddressActivity.class);
                provenanceIntent.putExtra("lat", lat);
                provenanceIntent.putExtra("longi", longi);
                provenanceIntent.putExtra("district", district);
                provenanceIntent.putExtra("placeName", placeName);
                provenanceIntent.putExtra("type", type);
                provenanceIntent.putExtra("title", getString(R.string.provenance));
                provenanceIntent.putExtra("detailedAddress", detailedAddress);
                provenanceIntent.putExtra("deliveryCustomer", deliveryCustomer);
                provenanceIntent.putExtra("shipper", shipper);
                provenanceIntent.putExtra("phone", phone);
                provenanceIntent.putExtra("eixedTelephone", eixedTelephone);
                startActivityForResult(provenanceIntent, REQUEST_CODE_CHOOSE_PHOTO);
                break;
            case R.id.tv_enterDestination:
                Intent destinationIntent = new Intent(this, SelectAddressActivity.class);
                destinationIntent.putExtra("lat", lat);
                destinationIntent.putExtra("longi", longi);
                destinationIntent.putExtra("district", district);
                destinationIntent.putExtra("placeName", placeName);
                destinationIntent.putExtra("type", type);
                destinationIntent.putExtra("title", getString(R.string.destination));
                destinationIntent.putExtra("detailedAddress", detailedAddress);
                destinationIntent.putExtra("deliveryCustomer", deliveryCustomer);
                destinationIntent.putExtra("shipper", shipper);
                destinationIntent.putExtra("phone", phone);
                destinationIntent.putExtra("eixedTelephone", eixedTelephone);
                startActivityForResult(destinationIntent, REQUEST_CODE_PHOTO_PREVIEW);
                break;
            case R.id.rl_cargoInformation:
                Intent cargoInformationIntent = new Intent(this, AddCargoInformationActivity.class);
                cargoInformationIntent.putExtra("lat", lat);
                cargoInformationIntent.putExtra("longi", longi);
                cargoInformationIntent.putExtra("district", district);
                cargoInformationIntent.putExtra("plac eName", placeName);
                cargoInformationIntent.putExtra("type", type);
                cargoInformationIntent.putExtra("title", getString(R.string.destination));
                cargoInformationIntent.putExtra("detailedAddress", detailedAddress);
                cargoInformationIntent.putExtra("deliveryCustomer", deliveryCustomer);
                cargoInformationIntent.putExtra("shipper", shipper);
                cargoInformationIntent.putExtra("phone", phone);
                cargoInformationIntent.putExtra("eixedTelephone", eixedTelephone);
                startActivityForResult(cargoInformationIntent, REQUEST_CODE_PHOTO_PREVIEW1);
                break;
            case R.id.tv_submitOrders:


                break;
            case R.id.tv_assignedVehicle:
                AssignedVehicleBouncedDialog assignedVehicleBouncedDialog = new AssignedVehicleBouncedDialog(this) {
                    @Override
                    public void confirm(String pleaseLicensePlateNumber) {

                    }
                };
                assignedVehicleBouncedDialog.show();
                break;
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(true);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            //关闭缓存机制
//            mLocationOption.setLocationCacheEnable(false);
            //给定位客户端对象设置定位参数
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();//启动定位
        }
    }

    @AfterPermissionGranted(NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choiceLocationWrapper() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.CHANGE_WIFI_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 设置定位监听
            mAmap.setLocationSource(this);
// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            mAmap.setMyLocationEnabled(true);
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
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                isTost = true;
                mlocationClient.stopLocation();
                province = amapLocation.getProvince();
                city = amapLocation.getCity();
                district = amapLocation.getDistrict();
                Log.d("AmapErr", amapLocation.getProvider());
                Log.d("AmapErr", amapLocation.getAddress());
                aoiName = amapLocation.getAoiName();
                lat = String.valueOf(amapLocation.getLatitude());
                longi = String.valueOf(amapLocation.getLongitude());
                Log.d("AmapErr", amapLocation.getPoiName());
                Log.d("AmapErr", amapLocation.getStreet());
                //              showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
                //             ((MainContract.Presenter) mPresenter).getNearbySearch(amapLocation.getLatitude(), amapLocation.getLongitude());
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                // 设置中心点及检索范围
                CloudSearch.SearchBound bound = new CloudSearch.SearchBound(new LatLonPoint(
                        amapLocation.getLatitude(), amapLocation.getLongitude()), 10000);
                //设置查询条件 mTableID是将数据存储到数据管理台后获得。
                try {
                    mQuery = new CloudSearch.Query(StringConstants.NearTableid, "", bound);
                    mCloudSearch.searchCloudAsyn(mQuery);// 异步搜索
                } catch (AMapException e) {
                    e.printStackTrace();
                    ((MainContract.Presenter) mPresenter).getHome();
                }
            } else {
                mlocationClient.startLocation();
                if (amapLocation.getErrorCode() == 12) {
                    if (isTost) {
                        ViewInject.toast("请打开GPS定位");
                        isTost = false;
                    }
                    //  return;
                }
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }


    @AfterPermissionGranted(NumericConstants.READ_AND_WRITE_CODE)
    public void readAndWriteTask(String updateAppUrl) {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(aty, perms)) {
            // Have permission, do the thing!
            ((MainContract.Presenter) mPresenter).downloadApp(updateAppUrl);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.readAndWrite), NumericConstants.READ_AND_WRITE_CODE, perms);
        }
    }


    private void init(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAmap = mMapView.getMap();
        choiceLocationWrapper();
        ((MainContract.Presenter) mPresenter).setupLocationStyle(mAmap);
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
    protected void onResume() {
        super.onResume();
//        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        marqueeView.startFlipping();
        isForeground = true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        marqueeView.stopFlipping();
        mMapView.onPause();
        deactivate();
        isForeground = true;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isForeground = false;
        unregisterReceiver(mMessageReceiver);
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        timer.cancel();
        timer = null;
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        Intent intent = registerReceiver(mMessageReceiver, filter);
        //极光推送 定制声音、震动、闪灯等 Notification 样式。
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(Main1Activity.this);
//        builder.statusBarDrawable = R.mipmap.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);
    }


    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            NearbySearchBean nearbySearch = (NearbySearchBean) JsonUtil.getInstance().json2Obj(s, NearbySearchBean.class);
            if (nearbySearch.getStatus() == NumericConstants.STATUS && nearbySearch.getDatas() != null && nearbySearch.getDatas().size() > 0) {
                AMapUtil.addEmulateData(mAmap, nearbySearch.getDatas());
                dismissLoadingDialog();
            } else {
                errorMsg("周边搜索为空", 0);
            }
        } else if (flag == 1) {
            if (!(s.equals("true"))) {
                return;
            }
            String updateAppUrl = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "updateAppUrl", null);
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
                            readAndWriteTask(updateAppUrl);
                        }
                    }).show();
            dismissLoadingDialog();
        } else if (flag == 2) {
            BaseResult baseResult = (BaseResult) JsonUtil.getInstance().json2Obj(s, BaseResult.class);
            if ((String) baseResult.getResult() == null) {
                return;
            }
            File path = new File((String) baseResult.getResult());
            FileNewUtil.installApkFile(this, path.getAbsolutePath());
            dismissLoadingDialog();
        } else if (flag == 3) {
            HomeBean homeBean = (HomeBean) JsonUtil.getInstance().json2Obj(s, HomeBean.class);
//            if (homeBean.getResult().getUnreadMsg() == null || homeBean.getResult().getUnreadMsg().getMsgX() == 0) {
//                tv_tag.setVisibility(View.GONE);
//            } else {
//                tv_tag.setVisibility(View.VISIBLE);
//                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
//                if (StringUtils.isEmpty(accessToken)) {
//                    tv_tag.setVisibility(View.GONE);
//                }
//                // tv_tag.setText(String.valueOf(homeBean.getResult().getUnreadMsg().getMsgX()));
//            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    processLogic(homeBean.getResult().getList());
                    if (timer != null) {
                        timer.schedule(new Task(), 2000);
                    }
                    dismissLoadingDialog();
                }
            });
        }
    }

    @Override
    public void errorMsg(String msg, int flag) {
        if (flag == 0) {
            ViewInject.toast(msg);
        } else if (flag == 1) {
        } else if (flag == 2) {
        } else if (flag == 3) {
            ((MainContract.Presenter) mPresenter).getHome();
            //   tv_tag.setVisibility(View.GONE);
        } else if (flag == 4) {
            if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
                dismissLoadingDialog();
                if (timer != null) {
                    timer.schedule(new Task(), 2000);
                }
                PreferenceHelper.write(this, StringConstants.FILENAME, "isAvatar", true);
                return;
            }
            //  showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
            ((MainContract.Presenter) mPresenter).getInfo();
        } else if (flag == 5 || flag == 6 || flag == 7 || flag == 8 || flag == 9 || flag == 10 || flag == 11 || flag == 12) {
            if (msg.equals("" + NumericConstants.TOLINGIN)) {
                showActivity(aty, LoginActivity.class);
            }
        }
        dismissLoadingDialog();
        //  toLigon(msg);
//        ViewInject.toast(msg);
    }

    /**
     * 请选择预约时间
     */
    private void appointmentTime() {
        pvOptions = new OptionsPickerView.Builder(aty, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                //  car_type_id = carInfoBean.getResult().get(options1).getId();
                //  ((TextView) v).setText(trip_chooseList.get(options1).getDescription());
            }
        })
                .setCancelColor(getResources().getColor(R.color.hintcolors))
                .setSubmitColor(getResources().getColor(R.color.announcementCloseColors))
                .setSubCalSize(15)
                .setContentTextSize(17)
                .setTextColorCenter(getResources().getColor(R.color.titletextcolors))
                .build();
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCloudSearched(CloudResult cloudResult, int rCode) {
        Log.d("tag", cloudResult.getClouds().toString());
        if (rCode == AMapException.CODE_AMAP_SUCCESS && cloudResult != null && cloudResult.getClouds() != null && !cloudResult.getClouds().isEmpty()) {
            AMapUtil.addEmulateData1(mAmap, cloudResult.getClouds());
            dismissLoadingDialog();
        } else {
            dismissLoadingDialog();
            ViewInject.toast(getString(R.string.no_result));
        }
        startHome();
    }

    private void startHome() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ((MainContract.Presenter) mPresenter).getHome();
            }
        }).start();
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

    /**
     * 广告轮播图
     */
    List<String> tips = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private void processLogic(List<ListBean> list) {
//        if (list == null || list.size() == 0) {
//            ll_ad.setVisibility(View.GONE);
//            return;
//        }
        tips.add("2. 欢迎大家关注我哦！");
        tips.add("3. GitHub帐号：sfsheng0322");
        tips.add("4. 新浪微博：孙福生微博");
        tips.add("5. 个人博客：sunfusheng.com");
        tips.add("6. 微信公众号：孙福生");
        marqueeView.startWithList(tips);
// 在代码里设置自己的动画
        //   marqueeView.startWithList(tips, R.anim.anim_bottom_in, R.anim.anim_top_out);
        ll_ad.setVisibility(View.VISIBLE);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Intent intent = new Intent(aty, AnnouncementActivity.class);
                showActivity(aty, intent);
            }
        });
    }


    class Task extends TimerTask {
        @Override
        public void run() {
            //实现页面跳转
            if (locationBouncedDialog != null && locationBouncedDialog.isShowing()) {
                locationBouncedDialog.dismiss();
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            /**
             * 选择始发地页面返回
             */
            lat = getIntent().getStringExtra("lat");
            longi = getIntent().getStringExtra("longi");
            district = getIntent().getStringExtra("district");
            placeName = getIntent().getStringExtra("placeName");
            type = getIntent().getIntExtra("type", 0);

            detailedAddress = getIntent().getStringExtra("detailedAddress");
            deliveryCustomer = getIntent().getStringExtra("deliveryCustomer");
            shipper = getIntent().getStringExtra("shipper");
            phone = getIntent().getStringExtra("phone");
            eixedTelephone = getIntent().getStringExtra("eixedTelephone");
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW && resultCode == RESULT_OK) {
            /**
             * 选择目的地页面返回
             */
            lat1 = getIntent().getStringExtra("lat");
            longi1 = getIntent().getStringExtra("longi");
            district1 = getIntent().getStringExtra("district");
            placeName1 = getIntent().getStringExtra("placeName");
            type = getIntent().getIntExtra("type", 0);

            detailedAddress1 = getIntent().getStringExtra("detailedAddress");
            deliveryCustomer1 = getIntent().getStringExtra("deliveryCustomer");
            shipper1 = getIntent().getStringExtra("shipper");
            phone1 = getIntent().getStringExtra("phone");
            eixedTelephone1 = getIntent().getStringExtra("eixedTelephone");
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW1 && resultCode == RESULT_OK) {
            //货物信息页面返回









        }
    }
}
