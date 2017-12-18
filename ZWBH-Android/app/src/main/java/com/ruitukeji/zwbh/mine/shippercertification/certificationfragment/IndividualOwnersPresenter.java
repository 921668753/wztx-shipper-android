package com.ruitukeji.zwbh.mine.shippercertification.certificationfragment;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/12/11.
 */

public class IndividualOwnersPresenter implements IndividualOwnersContract.Presenter {

    private IndividualOwnersContract.View mView;

    public IndividualOwnersPresenter(IndividualOwnersContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postIndividualOwners() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getInfo(httpParams, new ResponseListener<String>() {
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
    public void getIndividualOwners() {
        RequestClient.isLogin(new ResponseListener<String>() {
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

}
