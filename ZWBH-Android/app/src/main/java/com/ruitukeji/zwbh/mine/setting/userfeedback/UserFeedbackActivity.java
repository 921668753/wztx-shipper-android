package com.ruitukeji.zwbh.mine.setting.userfeedback;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 用户反馈
 * Created by Administrator on 2017/12/15.
 */

public class UserFeedbackActivity extends BaseActivity implements UserFeedbackContract.View {


    /**
     * 填写建议
     */
    @BindView(id = R.id.et_ridiculeGiveSomeAdvice)
    private EditText et_ridiculeGiveSomeAdvice;

    /**
     * 手机号
     */
    @BindView(id = R.id.et_phone)
    private EditText et_phone;


    /**
     * 确定
     */
    @BindView(id = R.id.tv_determine, click = true)
    private TextView tv_determine;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_userfeedback);
    }


    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.userFeedback), true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_determine:
                showLoadingDialog(getString(R.string.submissionLoad));
                ((UserFeedbackContract.Presenter) mPresenter).postUserFeedback();
                break;
        }
    }

    @Override
    public void setPresenter(UserFeedbackContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        dismissLoadingDialog();
        ViewInject.toast(getString(R.string.submittedSuccessfully));
        finish();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            showActivity(aty, LoginActivity.class);
            return;
        }
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }
}
