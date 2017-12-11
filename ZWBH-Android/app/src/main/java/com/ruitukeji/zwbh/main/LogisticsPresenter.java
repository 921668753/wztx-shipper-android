package com.ruitukeji.zwbh.main;

import android.util.Log;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.BuildConfig;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/2/17.
 */

public class LogisticsPresenter implements LogisticsContract.Presenter {

    private LogisticsContract.View mView;

    public LogisticsPresenter(LogisticsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getLogistics(SweetAlertDialog sweetAlertDialog, String type, long appoint_at, String org_address_maps, String org_city, String start_address_name, String start_address_detail, String start_name, String start_phone, String start_telephone, String dest_address_maps, String dest_city, String arr_address_name, String arr_address_detail, String arr_name, String arr_phone, String arr_telephone, String goods_name, String weight, String volume, String car_style_length, int car_style_length_id, String car_style_type,
                             int car_style_type_id, String premium_amount, String insuredAmount, int effective_time, int is_receipt, String system_price, String mind_price, String remark, int tran_type, String kilometres) {

        if (appoint_at == 0 && type.equals("appoint")) {
            mView.error(MyApplication.getContext().getString(R.string.appointmentTime2));
            return;
        } else if (appoint_at == 0 && !type.equals("appoint")) {
            appoint_at = System.currentTimeMillis() / 1000;
        }

        if (StringUtils.isEmpty(start_address_name)) {
            mView.error(MyApplication.getContext().getString(R.string.originPosition));
            return;
        }

        if (StringUtils.isEmpty(start_name)) {
            mView.error(MyApplication.getContext().getString(R.string.shipperName));
            return;
        }

        if (StringUtils.isEmpty(start_phone)) {
            mView.error(MyApplication.getContext().getString(R.string.contactInformation));
            return;
        }

        if (StringUtils.isEmpty(arr_address_name)) {
            mView.error(MyApplication.getContext().getString(R.string.originPosition1));
            return;
        }

        if (StringUtils.isEmpty(arr_name)) {
            mView.error(MyApplication.getContext().getString(R.string.shipperName1));
            return;
        }

        if (StringUtils.isEmpty(arr_phone)) {
            mView.error(MyApplication.getContext().getString(R.string.contactInformation1));
            return;
        }
        if (start_address_name.equals(arr_address_name)) {
            mView.error(MyApplication.getContext().getString(R.string.addressNotSame));
            return;
        }
        if (tran_type == 0) {
            String orgcityStr = null;
            String destcityStr = null;
            int orgcity = start_address_name.indexOf("市");
            if (orgcity != -1) {
                orgcityStr = start_address_name.substring(0, orgcity + 1);
            }
            /**
             * 终点
             */
            int destcity = arr_address_name.indexOf("市");
            if (destcity != -1) {
                destcityStr = arr_address_name.substring(0, destcity + 1);
            }
            if (!orgcityStr.equals(destcityStr)) {
                mView.error(MyApplication.getContext().getString(R.string.pleaseAdressEnter));
                return;
            }
        }
        if (StringUtils.isEmpty(goods_name)) {
            mView.error(MyApplication.getContext().getString(R.string.pleaseEnter) + MyApplication.getContext().getString(R.string.descriptionGoods));
            return;
        }

        if (StringUtils.isEmpty(weight)) {
            mView.error(MyApplication.getContext().getString(R.string.pleaseEnter) + MyApplication.getContext().getString(R.string.totalWeight));
            return;
        }

        if (StringUtils.isEmpty(volume)) {
            mView.error(MyApplication.getContext().getString(R.string.pleaseEnter) + MyApplication.getContext().getString(R.string.totalVolume));
            return;
        }

        if (StringUtils.isEmpty(car_style_length) || StringUtils.isEmpty(car_style_type)) {
            mView.error(MyApplication.getContext().getString(R.string.pleaseSelect) + MyApplication.getContext().getString(R.string.vehicleRequirements));
            return;
        }
        showSweetAlertDialog(sweetAlertDialog, type, appoint_at, org_address_maps, org_city, start_address_name, start_address_detail, start_name, start_phone, start_telephone, dest_address_maps, dest_city, arr_address_name, arr_address_detail, arr_name, arr_phone, arr_telephone, goods_name, weight, volume, car_style_length, car_style_length_id, car_style_type,
                car_style_type_id, premium_amount, insuredAmount, effective_time, is_receipt, system_price, mind_price, remark, tran_type, kilometres);
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
                mView.error(msg);
            }
        });
    }

    private void showSweetAlertDialog(SweetAlertDialog sweetAlertDialog, String type, long appoint_at, String org_address_maps, String org_city, String start_address_name, String start_address_detail, String start_name, String start_phone, String start_telephone, String dest_address_maps, String dest_city, String arr_address_name, String arr_address_detail, String arr_name, String arr_phone, String arr_telephone, String goods_name, String weight, String volume, String car_style_length, int car_style_length_id, String car_style_type,
                                      int car_style_type_id, String premium_amount, String insuredAmount, int effective_time, int is_receipt, String system_price, String mind_price, String remark, int tran_type, String kilometres) {
        if (StringUtils.isEmpty(premium_amount)) {
            premium_amount = "0";
        }
        String totalCost = "";
        if (StringUtils.isEmpty(mind_price)) {
            totalCost = MathUtil.keepTwo(StringUtils.toDouble(premium_amount) + StringUtils.toDouble(system_price));
        } else {
            totalCost = MathUtil.keepTwo(StringUtils.toDouble(premium_amount) + StringUtils.toDouble(mind_price));
        }
        sweetAlertDialog.setTitleText(KJActivityStack.create().topActivity().getString(R.string.totalCost) + totalCost + KJActivityStack.create().topActivity().getString(R.string.yuan) + "，" + KJActivityStack.create().topActivity().getString(R.string.orderSubmit1) + KJActivityStack.create().topActivity().getString(R.string.orderSubmit));
        String finalPremium_amount = premium_amount;
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("type", type);
                map.put("appoint_at", appoint_at);
                map.put("usecar_time", appoint_at);
                map.put("org_address_maps", org_address_maps);
                map.put("org_city", org_city);
                map.put("org_address_name", start_address_name);
                map.put("org_address_detail", start_address_detail);
                map.put("org_send_name", start_name);
                map.put("org_phone", start_phone);
                map.put("org_telphone", start_telephone);
                map.put("dest_address_maps", dest_address_maps);
                map.put("dest_city", dest_city);
                map.put("dest_address_name", arr_address_name);
                map.put("dest_address_detail", arr_address_detail);
                map.put("dest_receive_name", arr_name);
                map.put("dest_phone", arr_phone);
                map.put("dest_telphone", arr_telephone);
                map.put("goods_name", goods_name);
                map.put("weight", weight);
                map.put("volume", volume);
                map.put("car_style_length", car_style_length);
                map.put("car_style_type", car_style_type);
                map.put("premium_amount", finalPremium_amount);
                map.put("insured_amount", insuredAmount);
                map.put("effective_time", effective_time);
                map.put("is_receipt", is_receipt);
                map.put("system_price", system_price);
                map.put("mind_price", mind_price);
                map.put("remark", remark);
                map.put("kilometres", kilometres);
                map.put("car_style_type_id", car_style_type_id);
                map.put("car_style_length_id", car_style_length_id);
                map.put("tran_type", tran_type);
                Log.d("tag1111", "11111");
                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
                RequestClient.postLogistics(httpParams, new ResponseListener<String>() {
                    @Override
                    public void onSuccess(String response) {
                        mView.getSuccess(response, 1);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.error(msg);
                    }
                });
            }
        }).show();
    }

    /**
     * 派单给司机
     */
    @Override
    public void sendOrder(String order_id, String maps) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goods_id", order_id);
        map.put("maps", maps);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postSendOrder(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 2);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }

    @Override
    public void getInfo(int flag) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getInfo(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, flag);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });
    }

}
