package com.ruitukeji.zwbh.mine.invoicemanagement;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 发票管理
 * Created by Administrator on 2017/12/15.
 */

public class InvoiceManagementActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_invoicemanagement);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.invoiceManagement), true, R.id.titlebar);
    }
}
