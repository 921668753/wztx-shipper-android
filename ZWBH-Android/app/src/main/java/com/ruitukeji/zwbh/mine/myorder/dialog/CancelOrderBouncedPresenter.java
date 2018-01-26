package com.ruitukeji.zwbh.mine.myorder.dialog;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 取消订单弹框
 * Created by Administrator on 2017/2/21.
 */

public class CancelOrderBouncedPresenter implements CancelOrderBouncedContract.Presenter {

    private CancelOrderBouncedContract.View mView;

    public CancelOrderBouncedPresenter(CancelOrderBouncedContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postCancelOrder(int orderId) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goods_id", orderId);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postCancelOrder(httpParams, new ResponseListener<String>() {
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
}
