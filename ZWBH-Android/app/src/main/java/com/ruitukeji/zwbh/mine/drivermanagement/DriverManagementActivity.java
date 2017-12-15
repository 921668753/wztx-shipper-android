package com.ruitukeji.zwbh.mine.drivermanagement;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 司机管理
 * Created by Administrator on 2017/12/15.
 */

public class DriverManagementActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_drivermanagement);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.driverManagement), true, R.id.titlebar);
    }
}
