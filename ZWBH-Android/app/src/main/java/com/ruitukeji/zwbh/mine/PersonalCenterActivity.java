package com.ruitukeji.zwbh.mine;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 个人中心------我的
 * Created by Administrator on 2017/12/13.
 */

public class PersonalCenterActivity extends BaseActivity {


    @BindView(id = R.id.titlebar)
    private BGATitleBar titlebar;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_personalcenter);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.app_name), true, R.id.titlebar);
        titlebar.getTitleCtv().setTextColor(getResources().getColor(R.color.white));
        titlebar.setBackgroundResource(R.color.announcementCloseColors);
    }
}
