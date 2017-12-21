package com.ruitukeji.zwbh.main.cargoinformation.selectvehicle;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/21.
 */

public class SelectVehiclePresenter implements SelectVehicleContract.Presenter {


    private SelectVehicleContract.View mView;

    public SelectVehiclePresenter(SelectVehicleContract.View view) {
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
