package com.ruitukeji.zwbh.main.cargoinformation;

import android.content.Intent;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.BuildConfig;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.main.dialog.AssignedVehicleBouncedDialogActivity;
import com.ruitukeji.zwbh.main.dialog.SubmitOrdersBouncedDialog;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_PHOTO_PREVIEW1;

/**
 * Created by Administrator on 2017/2/15.
 */

public class AddCargoInformationPresenter implements AddCargoInformationContract.Presenter {

    private AddCargoInformationContract.View mView;

    public AddCargoInformationPresenter(AddCargoInformationContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    /**
     * * 获取驾车距离
     *
     * @param origins     出发点
     * @param destination 目的地
     */
    @Override
    public void getDistance(String origins, String destination) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("key", BuildConfig.GAODE_WEBKEY);
        httpParams.put("origins", origins);
        httpParams.put("destination", destination);
        RequestClient.getDistance(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

    @Override
    public void postAddCargoInformation(SubmitOrdersBouncedDialog submitOrdersBouncedDialog, String type, String appoint_at, String premium_amount, String insured_amount, String org_address_maps,
                                        String org_city, String org_address_name,
                                        String org_address_detail, String org_send_client, String org_send_name,
                                        String org_phone, String org_telphone, String dest_address_maps, String dest_city,
                                        String dest_address_name, String dest_address_detail, String dest_receive_client,
                                        String dest_receive_name, String dest_phone, String dest_telphone, String goods_name,
                                        String volume, String weight, String car_style_type, int car_style_type_id, String car_style_length,
                                        int car_style_length_id, long effective_time, String system_price, int tran_type,
                                        String kilometres, int spot, double spot_cost, int is_card_number, int is_driver_dock, String fact_pay,
                                        int is_cargo_receipt, String cargo_man, String cargo_tel, String cargo_address, String cargo_address_detail,
                                        int cargo_is_express, int isLongTimeCar) {

        if (StringUtils.isEmpty(goods_name)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseEnter) + MyApplication.getContext().getString(R.string.descriptionGoods), 0);
            return;
        }
        if (StringUtils.isEmpty(weight)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseEnter) + MyApplication.getContext().getString(R.string.totalWeight), 0);
            return;
        }
        if (StringUtils.isEmpty(volume)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseEnter) + MyApplication.getContext().getString(R.string.totalVolume), 0);
            return;
        }
        if (StringUtils.isEmpty(car_style_length) || StringUtils.isEmpty(car_style_type)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseSelect) + MyApplication.getContext().getString(R.string.vehicleRequirements), 0);
            return;
        }
        if (!type.equals("appoint")) {
            appoint_at = String.valueOf(System.currentTimeMillis() / 1000);
        }

        if (!StringUtils.isEmpty(fact_pay) && StringUtils.toDouble(fact_pay) <= 0) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.pleaseEnterCorrectPaymentPrice), 0);
            return;
        }
        String finalAppoint_at = appoint_at;
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (is_card_number == 1) {
            map.put("type", type);
            map.put("appoint_at", finalAppoint_at);
            map.put("premium_amount", premium_amount);
            map.put("insured_amount", insured_amount);
            map.put("org_address_maps", org_address_maps);
            map.put("org_city", org_city);
            map.put("org_address_name", org_address_name);
            map.put("org_address_detail", org_address_detail);
            map.put("org_send_client", org_send_client);
            map.put("org_send_name", org_send_name);
            map.put("org_phone", org_phone);
            map.put("org_telphone", org_telphone);
            map.put("dest_address_maps", dest_address_maps);
            map.put("dest_city", dest_city);
            map.put("dest_address_name", dest_address_name);
            map.put("dest_address_detail", dest_address_detail);
            map.put("dest_receive_client", dest_receive_client);
            map.put("dest_receive_name", dest_receive_name);
            map.put("dest_phone", dest_phone);
            map.put("dest_telphone", dest_telphone);
            map.put("goods_name", goods_name);
            map.put("volume", volume);
            map.put("weight", weight);
            map.put("car_style_type", car_style_type);
            map.put("car_style_type_id", car_style_type_id);
            map.put("car_style_length", car_style_length);
            map.put("is_longtime_car", isLongTimeCar);
            map.put("car_style_length_id", car_style_length_id);
            map.put("effective_time", effective_time);
            //   map.put("is_receipt", is_receipt);
            map.put("system_price", system_price);
            map.put("tran_type", tran_type);
            map.put("kilometres", kilometres);
            map.put("spot", spot);
            map.put("spot_cost", spot_cost);
            //   map.put("card_number", pleaseLicensePlateNumber);
            map.put("is_driver_dock", is_driver_dock);
            if (!StringUtils.isEmpty(fact_pay)) {
                map.put("mind_price", fact_pay);
            }
            map.put("is_cargo_receipt", is_cargo_receipt);
            map.put("cargo_man", cargo_man);
            map.put("cargo_tel", cargo_tel);
            map.put("cargo_address", cargo_address);
            map.put("cargo_address_detail", cargo_address_detail);
            map.put("cargo_is_express", cargo_is_express);
            Intent intent = new Intent(KJActivityStack.create().topActivity(), AssignedVehicleBouncedDialogActivity.class);
            intent.putExtra("httpParams", map);
            KJActivityStack.create().topActivity().startActivityForResult(intent, REQUEST_CODE_PHOTO_PREVIEW1);
            return;
        }
        submitOrdersBouncedDialog = null;
        submitOrdersBouncedDialog = new SubmitOrdersBouncedDialog(KJActivityStack.create().topActivity()) {
            @Override
            public void confirm() {
                this.cancel();
                mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
                map.put("type", type);
                map.put("appoint_at", finalAppoint_at);
                map.put("premium_amount", premium_amount);
                map.put("insured_amount", insured_amount);
                map.put("org_address_maps", org_address_maps);
                map.put("org_city", org_city);
                map.put("org_address_name", org_address_name);
                map.put("org_address_detail", org_address_detail);
                map.put("org_send_client", org_send_client);
                map.put("org_send_name", org_send_name);
                map.put("org_phone", org_phone);
                map.put("org_telphone", org_telphone);
                map.put("dest_address_maps", dest_address_maps);
                map.put("dest_city", dest_city);
                map.put("dest_address_name", dest_address_name);
                map.put("dest_address_detail", dest_address_detail);
                map.put("dest_receive_client", dest_receive_client);
                map.put("dest_receive_name", dest_receive_name);
                map.put("dest_phone", dest_phone);
                map.put("dest_telphone", dest_telphone);
                map.put("goods_name", goods_name);
                map.put("volume", volume);
                map.put("weight", weight);
                map.put("car_style_type", car_style_type);
                map.put("car_style_type_id", car_style_type_id);
                map.put("car_style_length", car_style_length);
                map.put("car_style_length_id", car_style_length_id);
                map.put("effective_time", effective_time);
                //    map.put("is_receipt", is_receipt);
                map.put("system_price", system_price);
                map.put("tran_type", tran_type);
                map.put("kilometres", kilometres);
                map.put("spot", spot);
                map.put("spot_cost", spot_cost);
                map.put("card_number", "");
                map.put("is_driver_dock", is_driver_dock);
                if (!StringUtils.isEmpty(fact_pay)) {
                    map.put("mind_price", fact_pay);
                }
                map.put("is_cargo_receipt", is_cargo_receipt);
                map.put("cargo_man", cargo_man);
                map.put("cargo_tel", cargo_tel);
                map.put("cargo_address", cargo_address);
                map.put("cargo_address_detail", cargo_address_detail);
                map.put("cargo_is_express", cargo_is_express);
                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
                RequestClient.postLogistics(httpParams, new ResponseListener<String>() {
                    @Override
                    public void onSuccess(String response) {
                        mView.getSuccess(response, 1);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.errorMsg(msg, 0);
                    }
                });
            }
        };
        submitOrdersBouncedDialog.show();
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

}
