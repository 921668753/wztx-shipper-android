package com.ruitukeji.zwbh.mine.recommendcourteous;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/13.
 */

public class RecommendedRecordPresenter implements RecommendedRecordContract.Presenter {

    private RecommendedRecordContract.View mView;

    public RecommendedRecordPresenter(RecommendedRecordContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getRecommendedRecord(int page) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("page", page);
        httpParams.put("pageSize", 10);
        RequestClient.showMyRecommList(httpParams, new ResponseListener<String>() {
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