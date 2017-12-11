package com.ruitukeji.zwbh.main;

import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface LogisticsContract {

    interface Presenter extends BasePresenter {

        /**
         * 发送物流信息
         */
        void getLogistics(SweetAlertDialog sweetAlertDialog,
                          String type, long appoint_at, String org_address_maps,
                          String org_city, String org_address_name, String org_address_detail,
                          String org_send_name, String org_phone, String org_telphone,
                          String dest_address_maps, String dest_city, String dest_address_name,
                          String dest_address_detail, String dest_receive_name, String dest_phone,
                          String dest_telphone, String goods_name, String weight,
                          String volume, String car_style_length, int car_style_length_id, String car_style_type,
                          int car_style_type_id, String premium_amount, String insuredAmount, int effective_time,
                          int is_receipt, String system_price, String mind_price,
                          String remark, int tran_type, String kilometres);

        /**
         * 获取驾车距离
         * http://lbs.amap.com/api/webservice/guide/api/direction#driving
         */
        void getDistance(String origins, String destination);

        /**
         * 派单给司机
         */
        void sendOrder(String order_id, String maps);

        /**
         * 获取用户信息
         */
        void getInfo(int flag);

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
