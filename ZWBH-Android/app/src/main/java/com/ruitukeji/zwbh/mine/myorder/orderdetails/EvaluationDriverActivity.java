package com.ruitukeji.zwbh.mine.myorder.orderdetails;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.azhong.ratingbar.RatingBar;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.entity.mine.myorder.orderdetails.EvaluationDriverBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

/**
 * 评价司机
 * Created by Administrator on 2017/12/14.
 */

public class EvaluationDriverActivity extends BaseActivity implements EvaluationDriverContract.View {

    @BindView(id = R.id.rb_deliveryTime)
    private RatingBar rb_deliveryTime;

    @BindView(id = R.id.rb_serviceAttitude)
    private RatingBar rb_serviceAttitude;

    @BindView(id = R.id.rb_satisfactionAttitude)
    private RatingBar rb_satisfactionAttitude;

    @BindView(id = R.id.et_note)
    private EditText et_note;

    @BindView(id = R.id.tv_submit, click = true)
    private TextView tv_submit;

    private int order_id;

    private String status;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_evaluationdriver);
    }

    @Override
    public void initData() {
        super.initData();
        order_id = getIntent().getIntExtra("order_id", 0);
        status = getIntent().getStringExtra("status");
        mPresenter = new EvaluationDriverPresenter(this);
        if (!StringUtils.isEmpty(status) && status.equals("comment")) {
            tv_submit.setVisibility(View.GONE);
            ((EvaluationDriverContract.Presenter) mPresenter).getEvaluationShare(order_id);
        }
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.evaluationDriver), true, R.id.titlebar);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_submit:
                showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                ((EvaluationDriverContract.Presenter) mPresenter).postEvaluationShare(order_id, rb_deliveryTime.getStar(), rb_serviceAttitude.getStar(),
                        rb_satisfactionAttitude.getStar(), et_note.getText().toString().trim());
                break;
        }
    }

    @Override
    public void setPresenter(EvaluationDriverContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            et_note.setEnabled(false);
            et_note.setFocusable(false);
            et_note.setFocusableInTouchMode(false);
            EvaluationDriverBean evaluationShareBean = (EvaluationDriverBean) JsonUtil.getInstance().json2Obj(success, EvaluationDriverBean.class);
            rb_deliveryTime.setStar(evaluationShareBean.getResult().getLimit_ship());
            rb_deliveryTime.setClickable(false);
            rb_serviceAttitude.setStar(evaluationShareBean.getResult().getAttitude());
            rb_serviceAttitude.setClickable(false);
            rb_satisfactionAttitude.setStar(evaluationShareBean.getResult().getSatisfaction());
            rb_satisfactionAttitude.setClickable(false);
            if (StringUtils.isEmpty(evaluationShareBean.getResult().getContent())) {
                et_note.setVisibility(View.GONE);
            } else {
                et_note.setVisibility(View.VISIBLE);
                et_note.setText(evaluationShareBean.getResult().getContent());
            }
            tv_submit.setVisibility(View.GONE);
        } else if (flag == 1) {
            ViewInject.toast(getString(R.string.commentSecc));
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
        dismissLoadingDialog();
    }


    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        if (flag == 0 && !toLigon1(msg)) {
            finish();
        } else {
            toLigon1(msg);
        }
    }
}
