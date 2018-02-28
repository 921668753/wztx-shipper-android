package com.ruitukeji.zwbh.mine.myorder.orderdetails;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/19.
 */

public class CheckReceiptPresenter implements CheckReceiptContract.Presenter {

    private CheckReceiptContract.View mView;

    public CheckReceiptPresenter(CheckReceiptContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getReceiptInformation(int g_id) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("order_id", g_id);
//        RequestClient.getCarGoInfo(httpParams, new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response, 0);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.errorMsg(msg, 0);
//            }
//        });
    }
}
