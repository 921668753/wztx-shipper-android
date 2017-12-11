package com.ruitukeji.zwbh.main;


import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.utils.myview.WebViewLayout;

/**
 * 轮播图详情
 * Created by Administrator on 2017/6/6.
 */

public class BannerDetailsActivity extends BaseActivity {


    @BindView(id = R.id.web_viewlayout)
    private WebViewLayout webViewLayout;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_bannerdetails);
    }


    @Override
    public void initWidget() {
        super.initWidget();
        initView();
    }

    public void initView() {
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        if (StringUtils.isEmpty(title)) {
            webViewLayout.setTitleText(R.string.app_name);
        } else {
            webViewLayout.setTitleText(title);
        }
        webViewLayout.setTitleVisibility(true);

        webViewLayout.setWebViewCallBack(new WebViewLayout.WebViewCallBack() {
            @Override
            public void backOnclick() {
                BannerDetailsActivity.this.finish();
            }

            @Override
            public void loadFailedError() {
                webViewLayout.setTitleText(title);
            }
        });

        if (StringUtils.isEmpty(url)) {
            webViewLayout.setTitleText(title);
        } else {
            webViewLayout.loadUrl(url);
        }
    }
}
