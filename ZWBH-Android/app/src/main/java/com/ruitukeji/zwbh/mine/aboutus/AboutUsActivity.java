package com.ruitukeji.zwbh.mine.aboutus;

import android.util.Log;
import android.webkit.WebView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.entity.AboutUsBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.myview.WebViewLayout;

/**
 * 关于我们
 * Created by Administrator on 2017/2/10.
 */

public class AboutUsActivity extends BaseActivity implements AboutUsContract.View {

//    @BindView(id = R.id.tv_version)
//    private TextView tv_version;


    @BindView(id = R.id.web_viewlayout)
    private WebViewLayout webViewLayout;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_aboutus);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new AboutUsPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        //  tv_version.setText("v" + SystemTool.getAppVersionName(this));
        //  initView();
        String type = getIntent().getStringExtra("type");
        Log.d("type", type);
        showLoadingDialog(getString(R.string.dataLoad));
        ((AboutUsContract.Presenter) mPresenter).getArticle(type);
    }

    public void initView(String title, String content) {
        webViewLayout.setTitleVisibility(true);
        webViewLayout.setWebViewCallBack(new WebViewLayout.WebViewCallBack() {
            @Override
            public void backOnclick() {
                AboutUsActivity.this.finish();
            }

            @Override
            public void loadFailedError() {
                webViewLayout.setTitleText(title);
            }
        });
        if (StringUtils.isEmpty(title)) {
            ActivityTitleUtils.initToolbar(aty, getString(R.string.app_name), true, R.id.titlebar);
        } else {
            ActivityTitleUtils.initToolbar(aty, title, true, R.id.titlebar);
        }
        if (!StringUtils.isEmpty(content)) {
            String code = "<!DOCTYPE html><html lang=\"zh\"><head>\t<meta charset=\"UTF-8\"><title>" + title + "</title></head><body>" + content
                    + "</body></html>";
            webViewLayout.loadDataWithBaseURL("baseurl", code, "text/html", "utf-8", null);
        }
    }

    @Override
    public void setPresenter(AboutUsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String s) {
        AboutUsBean aboutUsBean = (AboutUsBean) JsonUtil.json2Obj(s, AboutUsBean.class);
        initView(aboutUsBean.getResult().getTitle(), aboutUsBean.getResult().getContent());
        dismissLoadingDialog();
    }

    @Override
    public void error(String msg) {
        initView("", msg);
        dismissLoadingDialog();
    }
}
