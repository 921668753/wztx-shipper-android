package com.ruitukeji.zwbh.mine.abnormalrecords;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/13.
 */

public class AbnormalRecordsPresenter implements AbnormalRecordsContract.Presenter {

    private AbnormalRecordsContract.View mView;

    public AbnormalRecordsPresenter(AbnormalRecordsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getAbnormalRecords(int page, int orderId) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        if (orderId != 0) {
            httpParams.put("order_id", orderId);
        }
        httpParams.put("page", page);
        httpParams.put("pageSize", 10);
        RequestClient.getAbnormalRecords(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg);
            }
        });

    }
}