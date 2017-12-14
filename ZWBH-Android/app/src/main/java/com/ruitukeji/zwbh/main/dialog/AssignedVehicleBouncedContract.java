package com.ruitukeji.zwbh.main.dialog;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Admin on 2017/7/12.
 */

public class AssignedVehicleBouncedContract {

    interface Presenter extends BasePresenter {

        /**
         * 提交指派车辆
         */
        void getAssignedVehicle(String licensePlateNumber);
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
        void error(String msg);
    }


}
