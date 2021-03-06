package com.ruitukeji.zwbh.main;

import android.graphics.Color;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.client.ProgressListener;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.MathUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

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
    public void chooseLogisticsType(MainActivity activity, int chageIcon, TextView tv_cityDistribution, TextView tv_cityDistribution1, TextView tv_longTrunk, TextView tv_longTrunk1) {
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
    public void setupLocationStyle(AMap aMap) {
//        mAmap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
//        mAmap.getUiSettings().setZoomControlsEnabled(false);
//        // 自定义系统定位蓝点
//        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        // 自定义定位蓝点图标
//        //   myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pin));
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
////aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
//        myLocationStyle.showMyLocation(true);
//        // 自定义精度范围的圆形边框颜色
//        //   int STROKE_COLOR = Color.argb(180, 3, 145, 255);
//        //  myLocationStyle.strokeColor(STROKE_COLOR);
//        //    //自定义精度范围的圆形边框宽度
//        //  myLocationStyle.strokeWidth(5);
//        //    // 设置圆形的填充颜色
//        //  int FILL_COLOR = Color.argb(10, 0, 0, 180);
//        //  myLocationStyle.radiusFillColor(FILL_COLOR);
//        // 将自定义的 myLocationStyle 对象添加到地图上
//        mAmap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        mAmap.setMyLocationEnabled(true);


        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_pin));// 设置小蓝点的图标
        int STROKE_COLOR = Color.argb(180, 3, 145, 255);
        myLocationStyle.strokeColor(STROKE_COLOR);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        // 设置圆形的填充颜色
        int FILL_COLOR = Color.argb(10, 0, 0, 180);
        myLocationStyle.radiusFillColor(FILL_COLOR);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }
}
