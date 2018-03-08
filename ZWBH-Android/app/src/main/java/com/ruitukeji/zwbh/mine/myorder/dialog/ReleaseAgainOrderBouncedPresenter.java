package com.ruitukeji.zwbh.mine.myorder.dialog;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 再次发布弹框
 * Created by Administrator on 2017/2/21.
 */

public class ReleaseAgainOrderBouncedPresenter implements ReleaseAgainOrderBouncedContract.Presenter {

    private ReleaseAgainOrderBouncedContract.View mView;

    public ReleaseAgainOrderBouncedPresenter(ReleaseAgainOrderBouncedContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postReleaseAgain(int orderId) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_id", orderId);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postReleaseAgain(httpParams, new ResponseListener<String>() {
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
