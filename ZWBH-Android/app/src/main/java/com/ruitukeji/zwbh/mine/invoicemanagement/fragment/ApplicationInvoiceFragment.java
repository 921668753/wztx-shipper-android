package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.mine.invoicemanagement.InvoiceManagementActivity;

/**
 * 申请发票
 * Created by Administrator on 2017/12/15.
 */

public class ApplicationInvoiceFragment extends BaseFragment {
    private InvoiceManagementActivity aty;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (InvoiceManagementActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_applicationinvoice, null);
    }
}
