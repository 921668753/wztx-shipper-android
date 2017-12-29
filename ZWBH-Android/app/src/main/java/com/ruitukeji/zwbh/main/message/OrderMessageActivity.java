package com.ruitukeji.zwbh.main.message;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.MessageCenterViewAdapter;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 订单消息
 * Created by Administrator on 2017/12/12.
 */

public class OrderMessageActivity extends BaseActivity {

    @BindView(id = R.id.titlebar)
    private BGATitleBar titlebar;

    /**
     * 系统消息
     */
    @BindView(id = R.id.ll_systemMessage, click = true)
    private TextView ll_systemMessage;
    @BindView(id = R.id.tv_systemMessage)
    private TextView tv_systemMessage;
    @BindView(id = R.id.tv_systemMessage1)
    private TextView tv_systemMessage1;

    /**
     * 订单消息
     */
    @BindView(id = R.id.ll_orderMessage, click = true)
    private TextView ll_orderMessage;
    @BindView(id = R.id.tv_orderMessage)
    private TextView tv_orderMessage;
    @BindView(id = R.id.tv_orderMessage1)
    private TextView tv_orderMessage1;

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;


    @BindView(id = R.id.lv_messagecenter)
    private ListView lv_messagecenter;

    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError)
    private LinearLayout ll_commonError;
    @BindView(id = R.id.tv_hintText, click = true)
    private TextView tv_hintText;

    private MessageCenterViewAdapter messageCenterViewAdapter;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_ordermessage);
    }


    @Override
    public void initWidget() {
        super.initWidget();
        BGATitleBar.SimpleDelegate simpleDelegate = new BGATitleBar.SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                KJActivityStack.create().finishActivity(SystemMessageActivity.class);
                aty.finish();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();
                titlebar.setRightText(getString(R.string.complete));
//                Intent intent = new Intent(aty, AboutUsActivity.class);
//                intent.putExtra("type", "type");
//                showActivity(aty, intent);
            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.message), getString(R.string.edit), R.id.titlebar, simpleDelegate);
    }

    @Override
    public void initData() {
        super.initData();
        tv_systemMessage.setTextColor(getResources().getColor(R.color.typecolors));
        tv_systemMessage1.setBackgroundResource(R.color.white);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_systemMessage:
                showActivity(aty, SystemMessageActivity.class);
                overridePendingTransition(0, 0);
                break;
        }
    }


}
