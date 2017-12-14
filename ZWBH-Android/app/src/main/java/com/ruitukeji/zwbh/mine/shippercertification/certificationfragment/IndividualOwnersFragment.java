package com.ruitukeji.zwbh.mine.shippercertification.certificationfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.mine.shippercertification.ShipperCertificationActivity;

/**
 * 个人货主
 * Created by Administrator on 2017/12/14.
 */

public class IndividualOwnersFragment extends BaseFragment {

    private ShipperCertificationActivity aty;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (ShipperCertificationActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_individualowners, null);
    }
}
