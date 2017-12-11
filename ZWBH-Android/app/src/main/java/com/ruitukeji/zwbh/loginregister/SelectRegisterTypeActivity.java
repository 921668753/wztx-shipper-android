package com.ruitukeji.zwbh.loginregister;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;


/**
 * 选择注册类型
 * Created by Administrator on 2017/2/17.
 */

public class SelectRegisterTypeActivity extends BaseActivity {

    @BindView(id = R.id.ll_personalUser, click = true)
    private LinearLayout ll_personalUser;
    @BindView(id = R.id.ll_enterpriseUser, click = true)
    private LinearLayout ll_enterpriseUser;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_selectregistertype);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.selectRegisterTypeTitle), true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_personalUser:
                Intent intent = new Intent();
                intent.putExtra(getString(R.string.selectRegisterType), getString(R.string.personalUser));
                intent.setClass(this, NewUserRegisterActivity.class);
                showActivity(aty, intent);
                break;
            case R.id.ll_enterpriseUser:
                Intent intent1 = new Intent();
                intent1.putExtra(getString(R.string.selectRegisterType), getString(R.string.enterpriseUser));
                intent1.setClass(this, NewUserRegisterActivity.class);
                showActivity(aty, intent1);
                break;
        }
    }
}
