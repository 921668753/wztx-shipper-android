package com.ruitukeji.zwbh.main.dialog;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;

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
