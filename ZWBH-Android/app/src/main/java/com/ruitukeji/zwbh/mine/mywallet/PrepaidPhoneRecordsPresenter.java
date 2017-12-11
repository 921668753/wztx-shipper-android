package com.ruitukeji.zwbh.mine.mywallet;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/13.
 */
public class PrepaidPhoneRecordsPresenter implements PrepaidPhoneRecordsContract.Presenter {

    private PrepaidPhoneRecordsContract.View mView;

    public PrepaidPhoneRecordsPresenter(PrepaidPhoneRecordsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getPrepaidPhoneRecords(int page) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("page", page);
        RequestClient.getPrepaidPhoneRecords(httpParams, new ResponseListener<String>() {
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
