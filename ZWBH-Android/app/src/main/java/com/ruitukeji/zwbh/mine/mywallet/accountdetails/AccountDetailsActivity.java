package com.ruitukeji.zwbh.mine.mywallet.accountdetails;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 账户明细
 * Created by Administrator on 2017/12/15.
 */

public class AccountDetailsActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_accountdetails);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.accountDetails), true, R.id.titlebar);
    }
}
