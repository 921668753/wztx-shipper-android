package com.ruitukeji.zwbh.main.selectaddress.dialog;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/21.
 */

public class AddressBouncedPresenter implements AddressBouncedContract.Presenter {

    private AddressBouncedContract.View mView;

    public AddressBouncedPresenter(AddressBouncedContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getAddress(int id, int type) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        if (id != 0) {
            httpParams.put("id", id);
        }
        RequestClient.getAddressBounced(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, type);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, type);
            }
        });
    }

}
