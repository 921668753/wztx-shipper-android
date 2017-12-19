package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;

import android.annotation.SuppressLint;
import android.view.View;
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
 * 开票详情
 * Created by Administrator on 2017/12/15.
 */

public class BillingDetailsActivity extends BaseActivity implements BillingDetailsContract.View {

    /**
     * 详细地址
     */
    @BindView(id = R.id.tv_detailedAddress)
    private TextView tv_detailedAddress;

    /**
     * 姓名
     */
    @BindView(id = R.id.tv_name)
    private TextView tv_name;

    /**
     * 手机号
     */
    @BindView(id = R.id.tv_phone)
    private TextView tv_phone;

    /**
     * 公司名字
     */
    @BindView(id = R.id.tv_invoiceRise)
    private TextView tv_invoiceRise;

    /**
     * 纳税人识别号
     */
    @BindView(id = R.id.tv_taxpayerIdentificationNumber)
    private TextView tv_taxpayerIdentificationNumber;

    /**
     * 发票内容
     */
    @BindView(id = R.id.tv_invoiceContent)
    private TextView tv_invoiceContent;

    /**
     * 发票金额
     */
    @BindView(id = R.id.tv_invoiceAmount)
    private TextView tv_invoiceAmount;

    /**
     * 申请时间
     */
    @BindView(id = R.id.tv_applyTime)
    private TextView tv_applyTime;


    /**
     * 发票数
     */
    @BindView(id = R.id.tv_anInvoice)
    private TextView tv_anInvoice;

    /**
     * 订单数
     */
    @BindView(id = R.id.tv_anOrder)
    private TextView tv_anOrder;

    /**
     * 时间
     */
    @BindView(id = R.id.tv_startTime)
    private TextView tv_startTime;
    @BindView(id = R.id.tv_endTime)
    private TextView tv_endTime;

    /**
     * 跳转所含订单
     */
    @BindView(id = R.id.ll_anInvoice, click = true)
    private LinearLayout ll_anInvoice;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_billingdetails);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.billingDetails), true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_anInvoice:
                ((BillingDetailsContract.Presenter) mPresenter).isLogin(1);
                break;
        }
    }


    @Override
    public void setPresenter(BillingDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {

        } else if (flag == 1) {
            showActivity(aty, ContainsOrderActivity.class);
        }
        dismissLoadingDialog();
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
