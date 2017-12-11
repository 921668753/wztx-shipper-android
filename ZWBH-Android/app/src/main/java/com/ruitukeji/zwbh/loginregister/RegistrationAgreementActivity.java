package com.ruitukeji.zwbh.loginregister;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;


/**
 * 注册协议
 * Created by ruitu ck on 2016/9/14.
 */

public class RegistrationAgreementActivity extends BaseActivity {

    //    @BindView(id = R.id.webViewLayout)
//    private WebViewLayout webViewLayout;
//
//    @BindView(id = R.id.icon_back, click = true)
//    private ImageView icon_back;
//
//
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_registrationagreement);
    }

    //
//    /**
//     * 初始化数据
//     */
    @Override
    public void initData() {
        super.initData();
    }

    //    /**
//     * 渲染view
//     */
    @Override
    public void initWidget() {
        super.initWidget();
        String title = getIntent().getStringExtra("title");
        ActivityTitleUtils.initToolbar(aty, title, true, R.id.titlebar);
        //initView();
    }
//
//    public void initView() {
//        //   webViewLayout.setTitleText(R.string.app_name);
//        webViewLayout.setTitleVisibility(false);
//        webViewLayout.setWebViewCallBack(new WebViewLayout.WebViewCallBack() {
//            @Override
//            public void backOnclick() {
//                RegistrationAgreementActivity.this.finish();
//            }
//        });
//        webViewLayout.loadUrl("http://weixin.zenmechi.cc/register/agreement");
//    }
//
//    @Override
//    public void widgetClick(View v) {
//        super.widgetClick(v);
//        switch (v.getId()) {
//            case R.id.icon_back:
//                finish();
//                break;
//        }
//    }
}
