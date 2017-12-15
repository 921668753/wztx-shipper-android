package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.mine.invoicemanagement.InvoiceManagementActivity;

/**
 * 开票记录
 * Created by Administrator on 2017/12/15.
 */

public class BillingRecordsFragment extends BaseFragment {
    private InvoiceManagementActivity aty;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (InvoiceManagementActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_billingrecords, null);
    }
}
