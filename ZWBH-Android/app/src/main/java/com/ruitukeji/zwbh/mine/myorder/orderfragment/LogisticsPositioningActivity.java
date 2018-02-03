package com.ruitukeji.zwbh.mine.myorder.orderfragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
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
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.NearbySearchBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.amap.AMapUtil;
import com.ruitukeji.zwbh.utils.amap.DrivingRouteOverLay;
import com.umeng.socialize.utils.Log;

import java.util.List;

import cn.bingoogolapple.imageview.BGAImageView;
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

    @BindView(id = R.id.img_user)
    private BGAImageView img_user;

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
    private String dest_address_maps;
    private String dest_address;
    private String map_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        // ((LogisticsPositioningContract.Presenter) mPresenter).getTrajectory();
    }

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_logisticspositioning);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new LogisticsPositioningPresenter(this);
        mCloudSearch = new CloudSearch(this);// 初始化查询类
        routeSearch = new RouteSearch(this);
        initDialog();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.checkPositioning), true, R.id.titlebar);
        real_name = getIntent().getStringExtra("real_name");
        card_number = getIntent().getStringExtra("card_number");
        avatar = getIntent().getStringExtra("avatar");
        GlideImageLoader.glideLoader(this, avatar, img_user, 0);
        tv_name.setText(real_name + " • " + card_number);
        phone = getIntent().getStringExtra("phone");
        tv_phone.setText(phone);
        org_address = getIntent().getStringExtra("org_address");
        org_address_maps = getIntent().getStringExtra("org_address_maps");
        dest_address_maps = getIntent().getStringExtra("dest_address_maps");
        dest_address = getIntent().getStringExtra("dest_address");
        map_code = getIntent().getStringExtra("map_code");
        mCloudSearch.setOnCloudSearchListener(this);// 设置回调函数
        // mId 数据_id 信息
        mCloudSearch.searchCloudDetailAsyn(StringConstants.NearTableid, map_code);
        routeSearch.setRouteSearchListener(this);
        showLoadingDialog(getString(R.string.dataLoad));
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_driver:
                choiceCallWrapper(getString(R.string.phoneCall), phone);
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
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
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
        sweetAlertDialog = null;
        mapView.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mlocationClient.stopLocation();
                //   ((MainContract.Presenter) mPresenter).getNearbySearch(amapLocation.getLatitude(), amapLocation.getLongitude());
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                //26.336164经度52.030018
                //  setUpMap(new LatLng(52.030018, 26.336164), new LatLng(34.7568711, 113.663221));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

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
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            //获取一次定位结果：
//该方法默认为false。
            mLocationOption.setOnceLocation(true);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
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

    private void init(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        setupLocationStyle();
        choiceLocationWrapper();
    }

    private void setupLocationStyle() {
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        int STROKE_COLOR = Color.argb(180, 3, 145, 255);
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        int FILL_COLOR = Color.argb(10, 0, 0, 180);
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }


    @Override
    public void getSuccess(String s) {
        NearbySearchBean nearbySearch = (NearbySearchBean) JsonUtil.getInstance().json2Obj(s, NearbySearchBean.class);
        if (nearbySearch.getStatus() == NumericConstants.STATUS && nearbySearch.getDatas() != null && nearbySearch.getDatas().size() > 0) {
            AMapUtil.drawLineMap(aMap, nearbySearch.getDatas());
            dismissLoadingDialog();
        } else {
            dismissLoadingDialog();
            ViewInject.toast(getString(R.string.surroundingSearchEmpty));
        }
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
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
//            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), StringConstants.PHOTOPATH);
//            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, true ? takePhotoDir : null, 1, null, false), NumericConstants.REQUEST_CODE_CHOOSE_PHOTO);
            // 设置定位监听
            aMap.setLocationSource(this);
// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.locationPermissions), NumericConstants.REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CALL)
    private void choiceCallWrapper(String title, String phone) {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            showDialog(title, phone);
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


    public void showDialog(String title, String phone) {
        if (sweetAlertDialog == null) {
            initDialog();
        }
        sweetAlertDialog.setTitleText(title)
                .setContentText(phone)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        sDialog = null;
                        Uri uri = Uri.parse("tel:" + phone);
                        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                        //     系统打电话界面：
                        startActivity(intent);
                    }
                }).show();
    }


    public void initDialog() {
        sweetAlertDialog = null;
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setCancelText(getString(R.string.cancel1))
                .setConfirmText(getString(R.string.confirm))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
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
            final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, cloudItemDetail.getLatLonPoint());
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
                    final DrivePath drivePath = driveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
                            this, aMap, drivePath,
                            driveRouteResult.getStartPos(),
                            driveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(false);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
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
