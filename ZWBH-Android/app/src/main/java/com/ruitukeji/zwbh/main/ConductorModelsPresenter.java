package com.ruitukeji.zwbh.main;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/21.
 */

public class ConductorModelsPresenter implements ConductorModelsContract.Presenter {


    private ConductorModelsContract.View mView;

    public ConductorModelsPresenter(ConductorModelsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getConductorModels() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getConductorModels(httpParams, new ResponseListener<String>() {
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
