package com.ruitukeji.zwbh.mine.myorder.orderdetails;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.mine.myorder.orderdetails.OrderDetailsContract;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/16.
 */

public class OrderDetailsPresenter implements OrderDetailsContract.Presenter {

    private OrderDetailsContract.View mView;

    public OrderDetailsPresenter(OrderDetailsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getOrderDetails(int order_id) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("order_id", order_id);
        RequestClient.getOrderDetails(httpParams, new ResponseListener<String>() {
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
    public void postCollectDriver(int dr_id) {
        if (dr_id < 1) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.serverReturnsDataError), 0);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dr_id", dr_id);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postCollectDriver(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 1);
            }
        });
    }

    @Override
    public void postDelCollectDriver(int dr_id) {
        if (dr_id < 1) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.serverReturnsDataError), 0);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", dr_id);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postDeleteDriver(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 2);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 2);
            }
        });
    }


    @Override
    public void postCancelGoodsComplete(int goods_id, int type) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map map = new HashMap();
        map.put("goods_id", goods_id);
        map.put("type", type);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postConfirmCancelOrder(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 3);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 3);
            }
        });
    }
}