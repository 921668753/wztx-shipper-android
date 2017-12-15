package com.ruitukeji.zwbh.mine.helpcenter;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/13.
 */

public class HelpCenterDetailsPresenter implements HelpCenterDetailsContract.Presenter {

    private HelpCenterDetailsContract.View mView;

    public HelpCenterDetailsPresenter(HelpCenterDetailsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getHelpCenterDetails(int id) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("id", id);
        RequestClient.getHelpCenterDetails(httpParams, new ResponseListener<String>() {
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