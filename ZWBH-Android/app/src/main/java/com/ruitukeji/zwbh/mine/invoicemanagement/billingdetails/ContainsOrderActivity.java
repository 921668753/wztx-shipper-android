package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 所含订单
 * Created by Administrator on 2017/12/16.
 */

public class ContainsOrderActivity extends BaseActivity {
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_containsorder);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.containsOrder), true, R.id.titlebar);
    }
}
