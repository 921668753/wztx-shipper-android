package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by ruitu on 2016/9/24.
 */

public class ContainsOrderPresenter implements ContainsOrderContract.Presenter {

    private ContainsOrderContract.View mView;

    public ContainsOrderPresenter(ContainsOrderContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getContainsOrder(String id) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("g_id", id);
        RequestClient.getContainsOrder(httpParams, new ResponseListener<String>() {
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
