package com.ruitukeji.zwbh.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.main.Main2Activity;

/**
 * 城际专车
 * Created by Administrator on 2018/1/9.
 */

public class IntercityFragment extends BaseFragment {

    private Main2Activity aty;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (Main2Activity) getActivity();
        return View.inflate(aty, R.layout.fragment_intercity, null);
    }











}
