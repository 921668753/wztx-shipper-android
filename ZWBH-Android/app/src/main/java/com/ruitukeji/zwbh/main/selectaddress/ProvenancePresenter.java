package com.ruitukeji.zwbh.main.selectaddress;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.AccountValidatorUtil;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

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
        if (phone.length() != 11 || !AccountValidatorUtil.isMobile(phone)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.inputPhone), 0);
            return;
        }
        if (deliveryCustomer.length() > 16 || deliveryCustomer.length() <= 0) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.deliveryCustomer1), 0);
            return;
        }
        if (isOff == 0) {
            mView.getSuccess("", 0);
            return;
        }
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("address_maps", longi + "," + lat);
        map.put("city", provincialLevel);
        map.put("address_name", address);
        map.put("address_detail", detailedAddress);
        map.put("client", deliveryCustomer);
        map.put("client_name", shipper);
        map.put("phone", phone);
        map.put("telphone", eixedTelephone);
        map.put("type", type);
        map.put("is_default", 0);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
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
