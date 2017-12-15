package com.ruitukeji.zwbh.mine.drivermanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.mine.drivermanagement.DriverManagementActivity;

/**
 * 黑名单
 * Created by Administrator on 2017/12/15.
 */

public class BlacklistFragment extends BaseFragment {
    private DriverManagementActivity aty;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (DriverManagementActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_driver, null);
    }
}
