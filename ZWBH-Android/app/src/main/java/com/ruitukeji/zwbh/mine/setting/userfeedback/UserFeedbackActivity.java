package com.ruitukeji.zwbh.mine.setting.userfeedback;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 用户反馈
 * Created by Administrator on 2017/12/15.
 */

public class UserFeedbackActivity extends BaseActivity {
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_userfeedback);
    }


    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.userFeedback), true, R.id.titlebar);
    }
}
