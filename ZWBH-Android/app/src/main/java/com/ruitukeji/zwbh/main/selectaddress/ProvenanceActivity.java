package com.ruitukeji.zwbh.main.selectaddress;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 始发地/目的地
 * destination
 * Created by Administrator on 2017/12/12.
 */

public class ProvenanceActivity extends BaseActivity {


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
