package com.ruitukeji.zwbh.main.fragment;

import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.main.Main2Activity;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface MainContract {

    interface Presenter extends BasePresenter {
        /**
         * 首页
         */
        void getHome();

        /**
         * 获取附近信息
         */
        void getNearbySearch(double lat, double lon);

        /**
         * 获取用户信息
         */
        void getInfo();

        /**
         * 注册广播
         */
        void registerMessageReceiver();

        /**
         * 下载app
         */
        void downloadApp(String updateAppUrl);

        /**
         * 是否登录
         */
        void isLogin(int flag);

        /**
         * 地图设置
         */
        void setupLocationStyle(AMap mAmap);

        /**
         * 选择物流类型
         */
        void chooseLogisticsType(Main2Activity activity, int chageIcon, TextView tv_cityDistribution, TextView tv_cityDistribution1, TextView tv_longTrunk, TextView tv_longTrunk1);

        /**
         * 设置状态：实时，加急，预约
         */
        void settingType(Main2Activity activity, int type, TextView tv_realTime, TextView tv_urgent, TextView tv_makeAppointment);
    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
