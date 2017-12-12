package com.ruitukeji.zwbh.main.selectaddress;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import cn.bingoogolapple.titlebar. BGATitleBar.SimpleDelegate;

/**
 * 选择地址
 * Created by Administrator on 2017/12/12.
 */

public class SelectAddressActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_selectaddress);
    }

    @Override
    public void initData() {
        super.initData();

    }


    @Override
    public void initWidget() {
        super.initWidget();
        SimpleDelegate simpleDelegate = new SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                aty.finish();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();

               // showActivity(aty, RecommendedRecordActivity.class);
            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.selectAddress), getString(R.string.cancel), R.id.titlebar, simpleDelegate);
    }














}
