package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;


import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/24.
 */

public class BillingDetailsPresenter implements BillingDetailsContract.Presenter {

    private BillingDetailsContract.View mView;

    public BillingDetailsPresenter(BillingDetailsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getBillingDetails() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("pageSize", 10);
        RequestClient.showMyRecommList(httpParams, new ResponseListener<String>() {
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
    public void isLogin(int flag) {
        RequestClient.isLogin(new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, flag);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, flag);
            }
        });
    }

}
