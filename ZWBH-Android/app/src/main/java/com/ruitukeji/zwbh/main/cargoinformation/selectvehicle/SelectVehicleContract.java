package com.ruitukeji.zwbh.main.cargoinformation.selectvehicle;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/21.
 */

public interface SelectVehicleContract {


    interface Presenter extends BasePresenter {
        /**
         * 获取车长车型信息
         */
        void getConductorModels();
    }

    interface View extends BaseView<Presenter> {
        /**
         * http请求正确
         *
         * @param s
         */
        void getSuccess(String s);

        /**
         * http请求错误
         */
        void error(String msg);
    }

}
