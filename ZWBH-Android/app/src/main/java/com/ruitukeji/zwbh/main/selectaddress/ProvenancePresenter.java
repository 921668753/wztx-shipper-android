package com.ruitukeji.zwbh.main.selectaddress;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/13.
 */

public class ProvenancePresenter implements ProvenanceContract.Presenter {

    private ProvenanceContract.View mView;

    public ProvenancePresenter(ProvenanceContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void postAddress(String longi, String lat, String provincialLevel, String address, String detailedAddress, String deliveryCustomer, String shipper, String phone, String eixedTelephone, int isOff, int type) {
        if (isOff == 0) {
            mView.getSuccess("", 0);
            return;
        }
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("address_maps", longi + "," + lat);
        httpParams.put("city", provincialLevel);
        httpParams.put("address_name", address);
        httpParams.put("address_detail", detailedAddress);
        httpParams.put("client", deliveryCustomer);
        httpParams.put("client_name", shipper);
        httpParams.put("phone", phone);
        httpParams.put("telphone", eixedTelephone);
        httpParams.put("type", type);
        RequestClient.postAddress(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }
}
