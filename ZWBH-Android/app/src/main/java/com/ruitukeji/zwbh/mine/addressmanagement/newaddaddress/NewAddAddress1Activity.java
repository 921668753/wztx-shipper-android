package com.ruitukeji.zwbh.mine.addressmanagement.newaddaddress;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 新增地址 /修改地址  changeAddress
 * Created by Administrator on 2017/12/12.
 */

public class NewAddAddress1Activity extends BaseActivity {


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_provenance);
    }


    @Override
    public void initWidget() {
        super.initWidget();
        String title = getIntent().getStringExtra("title");
        ActivityTitleUtils.initToolbar(aty, title, true, R.id.titlebar);
    }
}
