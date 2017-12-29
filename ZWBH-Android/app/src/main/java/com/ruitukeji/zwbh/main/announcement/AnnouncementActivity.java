package com.ruitukeji.zwbh.main.announcement;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.mine.setting.aboutus.AboutUsActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.myview.WebViewLayout;

/**
 * 公告通知
 * Created by Administrator on 2017/11/27.
 */

public class AnnouncementActivity extends BaseActivity implements AnnouncementContract.View {


    /**
     * 标题
     */
    @BindView(id = R.id.tv_title)
    private TextView tv_title;

    /**
     * 内容
     */
    @BindView(id = R.id.web_viewlayout)
    private WebViewLayout webViewLayout;

    /**
     * 错误提示页
     */
    @BindView(id = R.id.ll_commonError)
    private LinearLayout ll_commonError;
    @BindView(id = R.id.tv_hintText, click = true)
    private TextView tv_hintText;


    /**
     * 关闭
     */
    @BindView(id = R.id.tv_close, click = true)
    private TextView tv_close;

    private int id = 0;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_announcement);
    }

    @Override
    public void initData() {
        super.initData();
        id = getIntent().getIntExtra("id", 0);
        mPresenter = new AnnouncementPresenter(this);
        showLoadingDialog(getString(R.string.dataLoad));
        ((AnnouncementContract.Presenter) mPresenter).getAnnouncement(id);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        webViewLayout.setTitleVisibility(false);
        ll_commonError.setVisibility(View.GONE);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_hintText:
                showLoadingDialog(getString(R.string.dataLoad));
                ((AnnouncementContract.Presenter) mPresenter).getAnnouncement(id);
                break;
            case R.id.tv_close:
                finish();
                break;
        }
    }


    @Override
    public void setPresenter(AnnouncementContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String s, int flag) {


        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        initView("", msg);
    }


    public void initView(String title, String content) {
        if (StringUtils.isEmpty(title)) {
            tv_title.setText(getString(R.string.announcement));
        } else {
            tv_title.setText(title);
        }
        dismissLoadingDialog();
        if (StringUtils.isEmpty(content)) {
            webViewLayout.setVisibility(View.GONE);
            ll_commonError.setVisibility(View.VISIBLE);
            tv_hintText.setText(content);
            return;
        }
        ll_commonError.setVisibility(View.GONE);
        webViewLayout.setVisibility(View.VISIBLE);
        String code = "<!DOCTYPE html><html lang=\"zh\"><head>\t<meta charset=\"UTF-8\"><title>" + title + "</title></head><body>" + content
                + "</body></html>";
        webViewLayout.loadDataWithBaseURL("baseurl", code, "text/html", "utf-8", null);
    }

}
