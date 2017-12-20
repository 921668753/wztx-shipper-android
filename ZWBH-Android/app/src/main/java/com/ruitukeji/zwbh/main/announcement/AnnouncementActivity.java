package com.ruitukeji.zwbh.main.announcement;

import android.view.View;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;

/**
 * 公告通知
 * Created by Administrator on 2017/11/27.
 */

public class AnnouncementActivity extends BaseActivity {

    /**
     * 关闭
     */
    @BindView(id = R.id.tv_close, click = true)
    private TextView tv_close;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_announcement);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();


    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_close:
                finish();
                break;
        }
    }
}
