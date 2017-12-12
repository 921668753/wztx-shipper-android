package com.ruitukeji.zwbh.main.cargoinformation;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 填写货物签收单
 * Created by Administrator on 2017/12/12.
 */

public class FillCargoReceiptFormActivity extends BaseActivity {


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_fillcargoreceiptform);
    }


    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.fillCargoReceipt), true, R.id.titlebar);
    }
}
