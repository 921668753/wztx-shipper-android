package com.ruitukeji.zwbh.main;

import com.amap.api.maps2d.AMap;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

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

    }

    interface View extends BaseView<Presenter> {
        /**
         * http请求正确
         *
         * @param s
         */
        void getSuccess(String s, int flag);

        /**
         * http请求错误
         */
        void error(String msg, int flag);
    }

}
