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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.dialog.LocationBouncedDialog;
import com.ruitukeji.zwbh.entity.BaseResult;
import com.ruitukeji.zwbh.entity.main.HomeBean.ResultBean.ListBean;
import com.ruitukeji.zwbh.entity.NearbySearchBean;
import com.ruitukeji.zwbh.entity.mine.PersonalCenterBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.main.message.MessageCenterActivity;
import com.ruitukeji.zwbh.mine.setting.aboutus.AboutUsActivity;
import com.ruitukeji.zwbh.mine.myorder.MyOrderActivity;
import com.ruitukeji.zwbh.mine.mypublishedorder.MyPublishedGoodsActivity;
import com.ruitukeji.zwbh.mine.mywallet.MyWalletActivity;
import com.ruitukeji.zwbh.mine.onlineservice.OnlineServiceActivity;
import com.ruitukeji.zwbh.mine.personaldata.PersonalDataActivity;
import com.ruitukeji.zwbh.mine.invitefriends.SharePoliteActivity;
import com.ruitukeji.zwbh.mine.setting.SettingsActivity;
import com.ruitukeji.zwbh.utils.FileNewUtil;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.amap.AMapUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.utils.Log;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, LocationSource, AMapLocationListener, MainContract.View, BGABanner.Delegate<ImageView, ListBean>, BGABanner.Adapter<ImageView, ListBean>, CloudSearch.OnCloudSearchListener {

    private long firstTime = 0;
    public static boolean isForeground = false;
    // for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    private ImageView img_message;

    private MapView mMapView;

    private AMap mAmap;

    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

    @BindView(id = R.id.toolbar)
    private Toolbar toolbar;

    @BindView(id = R.id.tv_cityDistribution, click = true)
    private TextView tv_cityDistribution;

    @BindView(id = R.id.tv_longTrunk, click = true)
    private TextView tv_longTrunk;

    @BindView(id = R.id.banner_ad)
    private BGABanner mForegroundBanner;

    private SweetAlertDialog sweetAlertDialog = null;
    private String province = null;
    private String city = null;
    private String district = null;
    private String aoiName = null;
    private String lat = null;
    private String longi = null;

    @BindView(id = R.id.drawer_layout)
    public static DrawerLayout drawer;

    private TextView tv_tag;
    private ImageView img_user;
    private TextView tv_name;
    private TextView tv_phone;
    private CloudSearch mCloudSearch;
    private CloudSearch.Query mQuery;
    private LocationBouncedDialog locationBouncedDialog;
    private Timer timer;
    private boolean isTost = true;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new MainPresenter(this);
        locationBouncedDialog = new LocationBouncedDialog(this);
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
        initHeadView();
        timer = new Timer();
        registerMessageReceiver();
        initBanner();
        mCloudSearch = new CloudSearch(this);// 初始化查询类
        mCloudSearch.setOnCloudSearchListener(this);// 设置回调函数
        boolean isUpdate = PreferenceHelper.readBoolean(MyApplication.getContext(), StringConstants.FILENAME, "isUpdate", false);
        getSuccess(String.valueOf(isUpdate), 1);
    }

    @Override
    protected void threadDataInited() {
        super.threadDataInited();
        // showLoadingDialog(getString(R.string.dataLoad));
        //  ((MainContract.Presenter) mPresenter).getHome();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        locationBouncedDialog.show();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                boolean isAvatar = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isAvatar", false);
                if (isAvatar) {
                    showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
                    ((MainContract.Presenter) mPresenter).getInfo();
                }
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.mipmap.personalcenter);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.syncState();
        img_message = (ImageView) toolbar.findViewById(R.id.img_message);
        img_message.setOnClickListener(this);
        tv_tag = (TextView) toolbar.findViewById(R.id.tv_tag);
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
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
//            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), StringConstants.PHOTOPATH);
//            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, true ? takePhotoDir : null, 1, null, false), NumericConstants.REQUEST_CODE_CHOOSE_PHOTO);
            // 设置定位监听
            mAmap.setLocationSource(this);
// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            mAmap.setMyLocationEnabled(true);
        } else {
            EasyPermissions.requestPermissions(this, "定位选择需要以下权限:\n\n1.访问设备上的gps\n\n2.读写权限", NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
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
            ViewInject.toast("您拒绝了「定位」所需要的相关权限!");
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
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
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


    public void init(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAmap = mMapView.getMap();
        choiceLocationWrapper();
        ((MainContract.Presenter) mPresenter).setupLocationStyle(mAmap);
    }

    public void initHeadView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        img_user = (ImageView) headerView.findViewById(R.id.img_user);
        tv_name = (TextView) headerView.findViewById(R.id.tv_name);
        tv_phone = (TextView) headerView.findViewById(R.id.tv_phone);
        LinearLayout personal_data = (LinearLayout) headerView.findViewById(R.id.personal_data);
        personal_data.setOnClickListener(this);
        LinearLayout my_wallet = (LinearLayout) headerView.findViewById(R.id.my_wallet);
        my_wallet.setOnClickListener(this);
        LinearLayout ll_myPublishedOrder = (LinearLayout) headerView.findViewById(R.id.ll_myPublishedOrder);
        ll_myPublishedOrder.setOnClickListener(this);
        LinearLayout my_order = (LinearLayout) headerView.findViewById(R.id.my_order);
        my_order.setOnClickListener(this);
        LinearLayout online_service = (LinearLayout) headerView.findViewById(R.id.online_service);
        online_service.setOnClickListener(this);
        LinearLayout recommend_courteous = (LinearLayout) headerView.findViewById(R.id.recommend_courteous);
        recommend_courteous.setOnClickListener(this);
        LinearLayout about_us = (LinearLayout) headerView.findViewById(R.id.about_us);
        about_us.setOnClickListener(this);
        LinearLayout settings = (LinearLayout) headerView.findViewById(R.id.settings);
        settings.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_message:
                tv_tag.setVisibility(View.GONE);
                showActivity(aty, MessageCenterActivity.class);
                break;
            case R.id.tv_cityDistribution:
                ((MainContract.Presenter) mPresenter).isLogin(5);
                break;
            case R.id.tv_longTrunk:
                ((MainContract.Presenter) mPresenter).isLogin(6);
                break;
            case R.id.personal_data:
                ((MainContract.Presenter) mPresenter).isLogin(7);
                break;
            case R.id.my_wallet:
                ((MainContract.Presenter) mPresenter).isLogin(8);
                break;
            case R.id.ll_myPublishedOrder:
                ((MainContract.Presenter) mPresenter).isLogin(12);
                break;
            case R.id.my_order:
                ((MainContract.Presenter) mPresenter).isLogin(9);
                break;
            case R.id.online_service:
                showActivity(aty, OnlineServiceActivity.class);
                drawer.closeDrawers();
                break;
            case R.id.recommend_courteous:
                ((MainContract.Presenter) mPresenter).isLogin(10);
                break;
            case R.id.about_us:
                Intent aboutUs = new Intent();
                aboutUs.setClass(aty, AboutUsActivity.class);
                aboutUs.putExtra("type", "shipper_about");
                showActivity(aty, aboutUs);
                drawer.closeDrawers();
                break;
            case R.id.settings:
                ((MainContract.Presenter) mPresenter).isLogin(11);
                break;
        }
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
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        ViewInject.toast("再按一次退出程序");
                        firstTime = secondTime;//更新firstTime
                    }
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
        boolean isGoneBanner = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "isGoneBanner", false);
        if (isGoneBanner) {
            mForegroundBanner.setVisibility(View.GONE);
        } else {
            mForegroundBanner.setVisibility(View.VISIBLE);
        }
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        isForeground = true;
        mForegroundBanner.startAutoPlay();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
        deactivate();
        isForeground = true;
        mForegroundBanner.stopAutoPlay();
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
        // mLocationTask.onDestroy();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        Intent intent = registerReceiver(mMessageReceiver, filter);
        //极光推送 定制声音、震动、闪灯等 Notification 样式。
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MainActivity.this);
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
//            HomeBean homeBean = (HomeBean) JsonUtil.getInstance().json2Obj(s, HomeBean.class);
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
//            processLogic(homeBean.getResult().getList());
//            if (timer != null) {
//                timer.schedule(new Task(), 2000);
//            }
            // ((MainContract.Presenter) mPresenter).getInfo();
            dismissLoadingDialog();
        } else if (flag == 4) {
            PersonalCenterBean userInfoBean = (PersonalCenterBean) JsonUtil.getInstance().json2Obj(s, PersonalCenterBean.class);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isAvatar", false);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "id", userInfoBean.getResult().getId());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "phone", userInfoBean.getResult().getPhone());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "sex", userInfoBean.getResult().getSex());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "avatar", userInfoBean.getResult().getAvatar());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "real_name", userInfoBean.getResult().getReal_name());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "auth_status", userInfoBean.getResult().getAuth_status());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "type", userInfoBean.getResult().getType());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "recomm_code", userInfoBean.getResult().getRecomm_code());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "bond_status", userInfoBean.getResult().getBond_status());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "bond", userInfoBean.getResult().getBond());
            if (StringUtils.isEmpty(userInfoBean.getResult().getAvatar())) {
                img_user.setImageResource(R.mipmap.headload);
            } else {
                GlideImageLoader.glideLoader(KJActivityStack.create().topActivity(), userInfoBean.getResult().getAvatar() + "?imageView2/1/w/70/h/70", img_user, 0);
            }
            if (StringUtils.isEmpty(userInfoBean.getResult().getReal_name())) {
                tv_name.setVisibility(View.GONE);
            } else {
                tv_name.setVisibility(View.VISIBLE);
                tv_name.setText(userInfoBean.getResult().getReal_name());
            }
            tv_phone.setVisibility(View.VISIBLE);
            tv_phone.setText(userInfoBean.getResult().getPhone());
            dismissLoadingDialog();
//            if (locationBouncedDialog != null && locationBouncedDialog.isShowing()) {
//                locationBouncedDialog.dismiss();
//            }
        } else if (flag == 5) {
            Intent intent = new Intent();
            intent.putExtra(getString(R.string.logisticsName), getString(R.string.cityDistribution));
            intent.putExtra("province", province);
            intent.putExtra("city", city);
            intent.putExtra("district", district);
            intent.putExtra("aoiName", aoiName);
            intent.putExtra("lat", lat);
            intent.putExtra("longi", longi);
            intent.putExtra("tran_type", 0);
            intent.setClass(this, LogisticsActivity.class);
            showActivity(aty, intent);
            dismissLoadingDialog();
        } else if (flag == 6) {
            Intent longTrunk = new Intent();
            longTrunk.putExtra(getString(R.string.logisticsName), getString(R.string.longTrunk));
            // longTrunk.putExtra(getString(R.string.location), getString(R.string.cityDistribution));
            longTrunk.putExtra("province", province);
            longTrunk.putExtra("city", city);
            longTrunk.putExtra("district", district);
            longTrunk.putExtra("aoiName", aoiName);
            longTrunk.putExtra("lat", lat);
            longTrunk.putExtra("longi", longi);
            longTrunk.putExtra("tran_type", 1);
            longTrunk.setClass(this, LogisticsActivity.class);
            showActivity(aty, longTrunk);
            dismissLoadingDialog();
        } else if (flag == 7) {
            showActivity(aty, PersonalDataActivity.class);
        } else if (flag == 8) {
            showActivity(aty, MyWalletActivity.class);
        } else if (flag == 9) {
            showActivity(aty, MyOrderActivity.class);
        } else if (flag == 10) {
            showActivity(aty, SharePoliteActivity.class);
        } else if (flag == 11) {
            showActivity(aty, SettingsActivity.class);
        } else if (flag == 12) {
            showActivity(aty, MyPublishedGoodsActivity.class);
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
            tv_tag.setVisibility(View.GONE);
        } else if (flag == 4) {
            if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
                dismissLoadingDialog();
                if (timer != null) {
                    timer.schedule(new Task(), 2000);
                }
                img_user.setImageResource(R.mipmap.headportrait);
                tv_name.setVisibility(View.GONE);
                tv_phone.setVisibility(View.VISIBLE);
                tv_phone.setText("登录/注册");
                PreferenceHelper.write(this, StringConstants.FILENAME, "isAvatar", true);
                return;
            }
            //  showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
            ((MainContract.Presenter) mPresenter).getInfo();
        } else if (flag == 5 || flag == 6 || flag == 7 || flag == 8 || flag == 9 || flag == 10 || flag == 11 || flag == 12) {
            if (msg.equals("" + NumericConstants.TOLINGIN)) {
                img_user.setImageResource(R.mipmap.headportrait);
                tv_name.setVisibility(View.GONE);
                tv_phone.setVisibility(View.VISIBLE);
                tv_phone.setText("登录/注册");
                showActivity(aty, LoginActivity.class);
            }
        }
        dismissLoadingDialog();
        //  toLigon(msg);
//        ViewInject.toast(msg);
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
            //    showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
            ((MainContract.Presenter) mPresenter).getHome();
            //  timer.schedule(new Task(), 1000); // 定时器延时执行任务方法
        } else {
            ((MainContract.Presenter) mPresenter).getHome();
            //   timer.schedule(new Task(), 1000); // 定时器延时执行任务方法
            //  showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
            dismissLoadingDialog();
            ViewInject.toast(getString(R.string.no_result));
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

    /**
     * 广告轮播图
     */
    @SuppressWarnings("unchecked")
    private void processLogic(List<ListBean> list) {
        if (list == null || list.size() == 0) {
            // error(getString(R.string.serverReturnsDataNull), 0);
            return;
        }
        mForegroundBanner.setBackground(null);
        mForegroundBanner.setData(list, null);
    }

    //  监听广告 item 的单击事件
    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, ListBean model, int position) {
       // GlideImageLoader.glideOrdinaryLoader(this, model.getSrc(), itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, ListBean model, int position) {
//        if (StringUtils.isEmpty(model.getUrl())) {
//            return;
//        }
//        Intent bannerDetails = new Intent(this, BannerDetailsActivity.class);
//        bannerDetails.putExtra("url", model.getUrl());
//        showActivity(aty, bannerDetails);
    }

    /**
     * 初始化轮播图
     */
    public void initBanner() {
        mForegroundBanner.setAutoPlayAble(true);
        mForegroundBanner.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mForegroundBanner.setAllowUserScrollable(true);
        mForegroundBanner.setAutoPlayInterval(3000);
        // 初始化方式1：配置数据源的方式1：通过传入数据模型并结合 Adapter 的方式配置数据源。这种方式主要用于加载网络图片，以及实现少于3页时的无限轮播
        mForegroundBanner.setAdapter(this);
        mForegroundBanner.setDelegate(this);
    }


    class Task extends TimerTask {
        @Override
        public void run() {
            //实现页面跳转
            if (locationBouncedDialog != null && locationBouncedDialog.isShowing()) {
                locationBouncedDialog.dismiss();
            }
            timer.cancel();
            timer = null;
        }
    }
}
