package com.ruitukeji.zwbh.mine.myorder.orderfragment;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/16.
 */

public class EvaluationSharePresenter implements EvaluationShareContract.Presenter {

    private EvaluationShareContract.View mView;

    public EvaluationSharePresenter(EvaluationShareContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postEvaluationShare(int order_id, float deliveryTime, float serviceAttitude, float satisfaction, String note) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_id", order_id);
        map.put("limit_ship", (int) deliveryTime);
        map.put("attitude", (int) serviceAttitude);
        map.put("satisfaction", (int) satisfaction);
        if (StringUtils.isEmpty(note)) {
        } else {
            map.put("content", note);
        }
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());

        RequestClient.postEvaluationShare(httpParams, new ResponseListener<String>() {
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

    @Override
    public void getEvaluationShare(int order_id) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("order_id", order_id);
        RequestClient.getEvaluationShare(httpParams, new ResponseListener<String>() {
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
}