package com.ruitukeji.zwbh.mine.addressmanagement;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 地址管理
 * Created by Administrator on 2017/12/15.
 */

public class AddressManagementActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_addressmanagement);
    }


    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.addressManagement), true, R.id.titlebar);
    }

}
