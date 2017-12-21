package com.ruitukeji.zwbh.main.dialog;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
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
        if (StringUtils.isEmpty(licensePlateNumber)) {
            mView.error(KJActivityStack.create().topActivity().getString(R.string.pleaseLicensePlateNumber));
            return;
        }
        mView.getSuccess(licensePlateNumber, 0);
    }
}
