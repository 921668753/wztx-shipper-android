package com.ruitukeji.zwbh.main.dialog;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;

/**
 * Created by Admin on 2017/7/12.
 */

public class AssignedVehicleBouncedPresenter implements AssignedVehicleBouncedContract.Presenter {

    private AssignedVehicleBouncedContract.View mView;

    public AssignedVehicleBouncedPresenter(AssignedVehicleBouncedContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getAssignedVehicle(HashMap hashMap, String licensePlateNumber) {
        if (StringUtils.isEmpty(licensePlateNumber)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseLicensePlateNumber), 0);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        hashMap.put("card_number", licensePlateNumber);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(hashMap).toString());
        RequestClient.postLogistics(httpParams, new ResponseListener<String>() {
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
