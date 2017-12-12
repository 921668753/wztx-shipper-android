package com.ruitukeji.zwbh.main.cargoinformation;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 添加货物信息
 * Created by Administrator on 2017/12/12.
 */

public class AddCargoInformationActivity extends BaseActivity {



    @Override
    public void setRootView() {
        setContentView(R.layout.activity_addcargoinformation);
    }


    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.addCargoInformation), true, R.id.titlebar);
    }
}
