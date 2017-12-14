package com.ruitukeji.zwbh.main.dialog;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;

import java.util.HashMap;
import java.util.Map;

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
    public void getAssignedVehicle(String licensePlateNumber) {
        mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("licensePlateNumber", licensePlateNumber);
//        map.put("dr_price", dr_price);
//        map.put("is_receive", is_place_order);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
//        RequestClient.getQuoteAdd(httpParams, new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response, 1);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.error(msg);
//            }
//        });
    }
}
