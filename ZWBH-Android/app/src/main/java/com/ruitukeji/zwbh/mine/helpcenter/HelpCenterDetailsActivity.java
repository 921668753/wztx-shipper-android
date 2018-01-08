package com.ruitukeji.zwbh.mine.helpcenter;

import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.entity.mine.setting.AboutUsBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.myview.WebViewLayout;

/**
 * 帮助中心详情
 * Created by Administrator on 2017/12/12.
 */

public class HelpCenterDetailsActivity extends BaseActivity implements HelpCenterDetailsContract.View {

    @BindView(id = R.id.tv_title)
    private TextView tv_title;

    @BindView(id = R.id.web_viewlayout)
    private WebViewLayout webViewLayout;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_helpcenterdetails);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.helpCenter), true, R.id.titlebar);
    }

    public void initView(String title, String content) {
        webViewLayout.setTitleVisibility(false);

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
    public void setPresenter(HelpCenterDetailsContract.Presenter presenter) {
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
