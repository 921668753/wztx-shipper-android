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
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.entity.EvaluationShareBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 评价司机
 * Created by Administrator on 2017/12/14.
 */

public class EvaluationDriverActivity extends BaseActivity implements EvaluationDriverContract.View {

    @BindView(id = R.id.rb_deliveryTime)
    private RatingBar rb_deliveryTime;

    @BindView(id = R.id.rb_serviceAttitude)
    private RatingBar rb_serviceAttitude;

    @BindView(id = R.id.tv_submit, click = true)
    private TextView tv_submit;

    private int order_id;

    private String status;

    private boolean isRefresh = false;

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
//        BGATitleBar.SimpleDelegate simpleDelegate = new BGATitleBar.SimpleDelegate() {
//            @Override
//            public void onClickLeftCtv() {
//                super.onClickLeftCtv();
//                if (isRefresh) {
//                    Intent intent = new Intent();
//                    setResult(RESULT_OK, intent);
//                }
//                finish();
//            }
//
//            @Override
//            public void onClickRightCtv() {
//                super.onClickRightCtv();
//
//            }
//        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.evaluationDriver), true, R.id.titlebar);
     //   ActivityTitleUtils.initToolbar(aty, getString(R.string.evaluationDriver), R.id.titlebar);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_submit:
                showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                ((EvaluationDriverContract.Presenter) mPresenter).postEvaluationShare(order_id, rb_deliveryTime.getStar(), rb_serviceAttitude.getStar());
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
            EvaluationShareBean evaluationShareBean = (EvaluationShareBean) JsonUtil.getInstance().json2Obj(success, EvaluationShareBean.class);
            rb_deliveryTime.setStar(evaluationShareBean.getResult().getLimit_ship());
            rb_deliveryTime.setClickable(false);
            rb_serviceAttitude.setStar(evaluationShareBean.getResult().getAttitude());
            rb_serviceAttitude.setClickable(false);
            tv_submit.setVisibility(View.GONE);
        } else if (flag == 1) {
            ViewInject.toast(getString(R.string.commentSecc));
            KJActivityStack.create().finishActivity(OrderDetailsActivity.class);
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
