package com.ruitukeji.zwbh.mine.myorder.logisticspositioning;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.NearbySearchBean;
import com.ruitukeji.zwbh.mine.myorder.dialog.ContactDriverBouncedDialog;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.amap.AMapUtil;
import com.ruitukeji.zwbh.utils.amap.DrivingRouteOverLay;
import com.ruitukeji.zwbh.utils.amap.SensorEventHelper;
import com.umeng.socialize.utils.Log;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PERMISSION_CALL;


/**
 * 物流定位
 * Created by Administrator on 2017/2/20.
 */

public class LogisticsPositioningActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, LocationSource, AMapLocationListener, LogisticsPositioningContract.View, CloudSearch.OnCloudSearchListener, RouteSearch.OnRouteSearchListener {

    private MapView mapView;
    private AMap aMap;

    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    private String real_name;
    private String phone;


    @BindView(id = R.id.ll_driver, click = true)
    private LinearLayout ll_driver;

    @BindView(id = R.id.tv_name)
    private TextView tv_name;

    @BindView(id = R.id.img_gps, click = true)
    private ImageView img_gps;

    @BindView(id = R.id.img_user)
    private ImageView img_user;

    @BindView(id = R.id.tv_phone)
    private TextView tv_phone;

    private SweetAlertDialog sweetAlertDialog = null;

    private String avatar;

    private String card_number;
    private CloudSearch mCloudSearch;
    private RouteSearch routeSearch;
    private String endtitle = null;
    private String org_address;
    private String org_address_maps;
    //    private String dest_address_maps;
//    private String dest_address;
    private String map_code;
    private ContactDriverBouncedDialog contactDriverBouncedDialog = null;

    private boolean mFirstFix = false;
    private Marker mLocMarker;
    private SensorEventHelper mSensorHelper;
    private Circle mCircle;
    private LatLng location = null;
    private DrivingRouteOverLay drivingRouteOverlay = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        // ((LogisticsPositioningContract.Presenter) mPresenter).getTrajectory();
    }

    private void init(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置成中文地图
            aMap.setMapLanguage(AMap.CHINESE);
        }
        //  setupLocationStyle();
        choiceLocationWrapper();
    }

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_logisticspositioning);
    }

    @Override
    public void initData() {
        super.initData();
        PreferenceHelper.write(aty, StringConstants.FILENAME, "logisticsPositioningNum", 0);
        mPresenter = new LogisticsPositioningPresenter(this);
        mCloudSearch = new CloudSearch(this);// 初始化查询类
        routeSearch = new RouteSearch(this);
        real_name = getIntent().getStringExtra("real_name");
        card_number = getIntent().getStringExtra("card_number");
        avatar = getIntent().getStringExtra("avatar");
        phone = getIntent().getStringExtra("phone");
        org_address = getIntent().getStringExtra("org_address");
        org_address_maps = getIntent().getStringExtra("org_address_maps");
//        dest_address_maps = getIntent().getStringExtra("dest_address_maps");
//        dest_address = getIntent().getStringExtra("dest_address");
        map_code = getIntent().getStringExtra("map_code");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.checkPositioning), true, R.id.titlebar);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
        GlideImageLoader.glideLoader(this, avatar, img_user, 0);
        tv_name.setText(real_name + " • " + card_number);
        if (StringUtils.isEmpty(phone)) {
            tv_phone.setText("");
        } else {
            tv_phone.setText(phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4));
        }
        mCloudSearch.setOnCloudSearchListener(this);// 设置回调函数
        mCloudSearch.searchCloudDetailAsyn(StringConstants.NearTableid, map_code);
        routeSearch.setRouteSearchListener(this);
        showLoadingDialog(getString(R.string.dataLoad));
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_gps:
                if (location == null) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.319687, 121.062545), aMap.getCameraPosition().zoom));
                    break;
                }
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude, location.longitude), aMap.getCameraPosition().zoom));
                break;
            case R.id.ll_driver:
                choiceCallWrapper(phone);
                break;
        }
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
        mapView.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
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
        deactivate();
        if (contactDriverBouncedDialog != null) {
            contactDriverBouncedDialog.cancel();
        }
        contactDriverBouncedDialog = null;
        mapView.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                if (!mFirstFix) {
                    mFirstFix = true;
                    mCircle = ((LogisticsPositioningContract.Presenter) mPresenter).addCircle(location, amapLocation.getAccuracy(), aMap, mCircle);
                    mLocMarker = ((LogisticsPositioningContract.Presenter) mPresenter).addMarker(location, amapLocation.getAccuracy(), aMap, mLocMarker);
//                    addCircle(location, amapLocation.getAccuracy());//添加定位精度圆
//                    addMarker(location);//添加定位图标
                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                } else {
                    mCircle.setCenter(location);
                    mCircle.setRadius(amapLocation.getAccuracy());
                    mLocMarker.setPosition(location);
                }
                PreferenceHelper.write(aty, StringConstants.FILENAME, "locationCity", amapLocation.getCity());
                //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                // mId 数据_id 信息
                Log.d("tag", "1111111");
                mCloudSearch.searchCloudDetailAsyn(StringConstants.NearTableid, map_code);
            } else {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "locationCity", getString(R.string.locateFailure));
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                android.util.Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(aty);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
//            mLocationOption.setOnceLocationLatest(true);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(60000);
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(30000);
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

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
    public void getSuccess(String s) {
//        NearbySearchBean nearbySearch = (NearbySearchBean) JsonUtil.getInstance().json2Obj(s, NearbySearchBean.class);
//        if (nearbySearch.getStatus() == NumericConstants.STATUS && nearbySearch.getDatas() != null && nearbySearch.getDatas().size() > 0) {
//            AMapUtil.drawLineMap(aMap, nearbySearch.getDatas());
//            dismissLoadingDialog();
//        } else {
//            dismissLoadingDialog();
//            ViewInject.toast(getString(R.string.surroundingSearchEmpty));
//        }
    }

    @Override
    public void error(String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    @Override
    public void setPresenter(LogisticsPositioningContract.Presenter presenter) {
        mPresenter = presenter;
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

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CALL)
    private void choiceCallWrapper(String phone) {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(aty, perms)) {
            if (contactDriverBouncedDialog != null && !contactDriverBouncedDialog.isShowing()) {
                contactDriverBouncedDialog.show();
                contactDriverBouncedDialog.setPhone(phone);
                return;
            }
            if (contactDriverBouncedDialog == null) {
                contactDriverBouncedDialog = new ContactDriverBouncedDialog(this, phone);
            }
            contactDriverBouncedDialog.show();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.phoneCallPermissions), REQUEST_CODE_PERMISSION_CALL, perms);
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
        if (requestCode == REQUEST_CODE_PERMISSION_CALL) {
            ViewInject.toast(getString(R.string.phoneCallPermissions1));
        } else if (requestCode == NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            ViewInject.toast(getString(R.string.locationPermissions1));
        }
    }


    @Override
    public void onCloudSearched(CloudResult cloudResult, int i) {

    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int rCode) {
        //数据展现
        Log.d("tag", cloudItemDetail.getSnippet() + cloudItemDetail.getLatLonPoint().toString());
        if (rCode == AMapException.CODE_AMAP_SUCCESS && cloudItemDetail != null) {
            endtitle = cloudItemDetail.getSnippet();
            //    AMapUtil.customMarker(aMap, new LatLng(cloudItemDetail.getLatLonPoint().getLatitude(), cloudItemDetail.getLatLonPoint().getLongitude()), cloudItemDetail.getSnippet());
//        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, drivingMode, null, null, "");
//        routeSearch.calculateDriveRouteAsyn(query);
            String[] latLng = org_address_maps.split(",");
            LatLonPoint mStartPoint = new LatLonPoint(Double.parseDouble(latLng[1]), Double.parseDouble(latLng[0]));//起点，116.335891,39.942295
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, cloudItemDetail.getLatLonPoint());
            // 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            routeSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else {
            dismissLoadingDialog();
            ViewInject.toast(getString(R.string.no_result));
            //  ToastUtil.showerror(CloudActivity.this, rCode);
        }


    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        //解析result获取算路结果
        // aMap.clear();// 清理地图上的所有覆盖物
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    DrivePath drivePath = driveRouteResult.getPaths().get(0);
                    if (drivingRouteOverlay == null) {
                        drivingRouteOverlay = new DrivingRouteOverLay(this, aMap);
                    }
                    Log.d("tag", "222222");
                    drivingRouteOverlay.setDrivePath(drivePath);
                    drivingRouteOverlay.setStartLatLonPoint(driveRouteResult.getStartPos());
                    drivingRouteOverlay.setEndLatLonPoint(driveRouteResult.getTargetPos());
                    drivingRouteOverlay.setThroughPointList(null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(false);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    int logisticsPositioningNum = PreferenceHelper.readInt(aty, StringConstants.FILENAME, "logisticsPositioningNum", 0);
                    if (logisticsPositioningNum == 0) {
                        drivingRouteOverlay.zoomToSpan();
                        PreferenceHelper.write(aty, StringConstants.FILENAME, "logisticsPositioningNum", logisticsPositioningNum + 1);
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AMapUtil.convertToLatLng(driveRouteResult.getStartPos()), aMap.getCameraPosition().zoom - 1));
                    }
                    drivingRouteOverlay.addStartAndEndMarker(org_address, endtitle);
                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
                    ViewInject.toast(getString(R.string.no_result));
                }

            } else {
                ViewInject.toast(getString(R.string.no_result));
            }
        } else {
            ViewInject.toast(getString(R.string.no_result));
        }
        dismissLoadingDialog();
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
