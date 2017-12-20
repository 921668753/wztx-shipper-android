package com.ruitukeji.zwbh.main.cargoinformation;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/15.
 */

public class AddCargoInformationPresenter implements AddCargoInformationContract.Presenter {


    private AddCargoInformationContract.View mView;

    public AddCargoInformationPresenter(AddCargoInformationContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getUnRead() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getUnRead(httpParams, new ResponseListener<String>() {
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
