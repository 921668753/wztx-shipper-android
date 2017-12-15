package com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.AccountDetailsActivity;

/**
 * 未支付
 * Created by Administrator on 2017/12/15.
 */

public class UnpaidFragment extends BaseFragment {
    private AccountDetailsActivity aty;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (AccountDetailsActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_accountdetails, null);
    }
}
