package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;


import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/24.
 */

public class BillingRecordsPresenter implements BillingRecordsContract.Presenter {


    private BillingRecordsContract.View mView;

    public BillingRecordsPresenter(BillingRecordsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getBillingRecordsList(int page) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("page", page);
        httpParams.put("pageSize", 10);
        RequestClient.getBillingRecordsList(httpParams, new ResponseListener<String>() {
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
