package com.ruitukeji.zwbh.mine.addressmanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.mine.addressmanagement.AddressManagementActivity;

/**
 * Created by Administrator on 2017/12/15.
 */

public class ShippingDddressFragment extends BaseFragment {

    private AddressManagementActivity aty;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (AddressManagementActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_address, null);
    }
}