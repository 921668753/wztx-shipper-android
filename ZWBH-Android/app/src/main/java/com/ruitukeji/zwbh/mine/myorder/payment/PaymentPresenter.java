package com.ruitukeji.zwbh.mine.myorder.payment;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.nanchen.compresshelper.FileUtil;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.mine.myorder.payment.PaymentContract;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.picturerelated.BitmapCoreUtil;
import com.ruitukeji.zwbh.utils.DataCleanManager;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/6.
 */

public class PaymentPresenter implements PaymentContract.Presenter {

    private PaymentContract.View mView;

    public PaymentPresenter(PaymentContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void postScorePay(int orderId) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_id", orderId);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postScorePay(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg,0);
            }
        });
    }


    @Override
    public void getWxPay(int orderId, String total_amount) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("order_id", orderId);
        httpParams.put("total_amount", total_amount);
        RequestClient.getWxPay(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg,0);
            }
        });
    }

    @Override
    public void getAlipay(int orderId, String total_amount) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("order_id", orderId);
        httpParams.put("total_amount", total_amount);
        RequestClient.getAlipay(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 2);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg,0);
            }
        });
    }


    @Override
    public void getMyWallet() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getPay(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 3);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg, 3);
            }
        });
    }




}
