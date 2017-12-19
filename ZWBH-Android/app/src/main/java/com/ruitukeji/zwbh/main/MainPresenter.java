package com.ruitukeji.zwbh.main;

import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.kymjs.common.Log;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.client.ProgressListener;
import com.ruitukeji.zwbh.BuildConfig;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.NearbySearchBean;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import static com.ruitukeji.zwbh.main.Main1Activity.*;


/**
 * Created by Administrator on 2017/2/20.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getHome() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getHome(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 3);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 3);
            }
        });
    }

    /**
     * 获取周边司机
     *
     * @param lat 经度
     * @param lon 纬度
     */
    @Override
    public void getNearbySearch(double lat, double lon) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        /**
         * http://yuntuapi.amap.com/datasearch/around?tableid=52b155b6e4b0bc61deeb7629&keywords=阜通东大街&
         center=116.481471,39.990471&radius=500&filter=type:写字楼&limit=10&page=1
         &key=<用户key>
         */
        httpParams.put("tableid", StringConstants.NearTableid);
        httpParams.put("center", lon + "," + lat);
        httpParams.put("radius", 10000);
        httpParams.put("limit", 100);
        httpParams.put("sortrule", "_distance:1");
        httpParams.put("page", 1);
        httpParams.put("key", BuildConfig.GAODE_WEBKEY);
        RequestClient.getNearbySearch(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                NearbySearchBean nearbySearch = (NearbySearchBean) JsonUtil.getInstance().json2Obj(response, NearbySearchBean.class);
                if (nearbySearch.getStatus() == NumericConstants.STATUS) {
                    mView.getSuccess(response, 0);
                } else {
                    Log.d("nearbySearch", nearbySearch.getStatus() + "");
                    mView.errorMsg("周边搜索出现异常,云端返回数据错误", 0);
                }
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

    @Override
    public void getInfo() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getInfo(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 4);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 4);
            }
        });
    }

    @Override
    public void registerMessageReceiver() {


    }


    /**
     * 清除颜色，并添加颜色
     */
    @Override
    public void chooseLogisticsType(Main1Activity activity, int chageIcon, TextView tv_cityDistribution, TextView tv_cityDistribution1, TextView tv_longTrunk, TextView tv_longTrunk1) {
        tv_cityDistribution.setTextColor(activity.getResources().getColor(R.color.typecolors));
        tv_cityDistribution1.setBackgroundResource(R.color.mainColor);
        tv_longTrunk.setTextColor(activity.getResources().getColor(R.color.typecolors));
        tv_longTrunk1.setBackgroundResource(R.color.mainColor);
        if (chageIcon == 0) {
            tv_cityDistribution.setTextColor(activity.getResources().getColor(R.color.announcementCloseColors));
            tv_cityDistribution1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 1) {
            tv_longTrunk.setTextColor(activity.getResources().getColor(R.color.announcementCloseColors));
            tv_longTrunk1.setBackgroundResource(R.color.announcementCloseColors);
        } else {
            tv_cityDistribution.setTextColor(activity.getResources().getColor(R.color.announcementCloseColors));
            tv_cityDistribution1.setBackgroundResource(R.color.announcementCloseColors);
        }
    }

    @Override
    public void settingType(Main1Activity activity, int type, TextView tv_realTime, TextView tv_urgent, TextView tv_makeAppointment) {
        tv_realTime.setTextColor(activity.getResources().getColor(R.color.typecolors));
        tv_realTime.setBackgroundResource(R.color.mainColor);
        tv_urgent.setTextColor(activity.getResources().getColor(R.color.typecolors));
        tv_urgent.setBackgroundResource(R.color.mainColor);
        tv_makeAppointment.setTextColor(activity.getResources().getColor(R.color.typecolors));
        tv_makeAppointment.setBackgroundResource(R.color.mainColor);
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

    /**
     * @param updateAppUrl 下载app
     */
    @Override
    public void downloadApp(String updateAppUrl) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download));
        ProgressListener progressListener = new ProgressListener() {
            @Override
            public void onProgress(long transferredBytes, long totalSize) {
                String size = MathUtil.keepZero(((double) transferredBytes) * 100 / totalSize) + "%";
                mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download) + size);
            }
        };
        RequestClient.downloadApp(updateAppUrl, progressListener, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 2);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 2);
            }
        });
    }

    @Override
    public void isLogin(int flag) {
        RequestClient.isLogin(new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, flag);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, flag);
            }
        });
    }

    @Override
    public void setupLocationStyle(AMap mAmap) {
        mAmap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAmap.getUiSettings().setZoomControlsEnabled(false);
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
        mAmap.setMyLocationStyle(myLocationStyle);
    }
}
