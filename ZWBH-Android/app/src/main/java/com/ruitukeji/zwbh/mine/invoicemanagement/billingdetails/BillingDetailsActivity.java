package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 开票详情
 * Created by Administrator on 2017/12/15.
 */

public class BillingDetailsActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_billingdetails);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.billingDetails), true, R.id.titlebar);
    }
}
