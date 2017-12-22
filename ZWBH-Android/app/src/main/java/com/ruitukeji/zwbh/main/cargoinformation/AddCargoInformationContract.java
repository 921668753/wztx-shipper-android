package com.ruitukeji.zwbh.main.cargoinformation;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.main.dialog.SubmitOrdersBouncedDialog;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface AddCargoInformationContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取驾车距离
         * http://lbs.amap.com/api/webservice/guide/api/direction#driving
         */
        void getDistance(String origins, String destination);

        /**
         * 检验添加货物信息参数是否正确
         */
        void postAddCargoInformation(SubmitOrdersBouncedDialog submitOrdersBouncedDialog, String type, String appoint_at, String premium_amount, String insured_amount,
                                     String org_address_maps, String org_city, String org_address_name, String org_address_detail, String org_send_client, String org_send_name,
                                     String org_phone, String org_telphone, String dest_address_maps, String dest_city, String dest_address_name, String dest_address_detail,
                                     String dest_receive_client, String dest_receive_name, String dest_phone, String dest_telphone, String goods_name, String volume, String weight,
                                     String car_style_type, int car_style_type_id, String car_style_length, int car_style_length_id, long effective_time, int is_receipt,
                                     String system_price, int tran_type, String kilometres, int spot, double spot_cost, String card_number, int is_driver_dock, String fact_pay,
                                     int is_cargo_receipt, String cargo_man, String cargo_tel, String cargo_address, String cargo_address_detail, int cargo_is_express);

    }

    interface View extends BaseNewView<Presenter, String> {
    }

}
