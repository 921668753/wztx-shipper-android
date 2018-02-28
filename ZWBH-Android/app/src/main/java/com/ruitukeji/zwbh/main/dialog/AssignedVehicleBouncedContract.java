package com.ruitukeji.zwbh.main.dialog;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

import java.util.HashMap;

/**
 * Created by Admin on 2017/7/12.
 */

public class AssignedVehicleBouncedContract {

    interface Presenter extends BasePresenter {

        /**
         * 提交指派车辆
         */
        void getAssignedVehicle(HashMap httpParams, String licensePlateNumber);
    }

    interface View extends BaseNewView<Presenter, String> {
//        /**
//         * http请求正确
//         *
//         * @param s
//         */
//        void getSuccess(String s, int flag);
//
//        /**
//         * http请求错误
//         */
//        void error(String msg);
    }


}
