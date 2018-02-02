package com.ruitukeji.zwbh.main;

import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface MainContract {

    interface Presenter extends BasePresenter {
        /**
         * 首页
         */
        void getHome();

//        /**
//         * 获取附近信息
//         */
//        void getNearbySearch(double lat, double lon);

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
         * 清除颜色，并添加颜色
         */
        void chooseLogisticsType(MainActivity activity, int chageIcon, TextView tv_cityDistribution, TextView tv_cityDistribution1, TextView tv_longTrunk, TextView tv_longTrunk1);

    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
