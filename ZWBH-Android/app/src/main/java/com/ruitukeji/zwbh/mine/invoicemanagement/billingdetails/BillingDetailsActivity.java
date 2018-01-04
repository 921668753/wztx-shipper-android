package com.ruitukeji.zwbh.mine.invoicemanagement.billingdetails;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.entity.mine.invoicemanagement.BillingDetailsBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

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

    @BindView(id = R.id.ll_taxpayerIdentificationNumber)
    private LinearLayout ll_taxpayerIdentificationNumber;
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
    private int id = 0;
    private String g_id = "";


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_billingdetails);
    }

    @Override
    public void initData() {
        super.initData();
        id = getIntent().getIntExtra("id", 0);
        mPresenter = new BillingDetailsPresenter(this);
        ((BillingDetailsContract.Presenter) mPresenter).getBillingDetails(id);
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
            BillingDetailsBean billingDetailsBean = (BillingDetailsBean) JsonUtil.json2Obj(success, BillingDetailsBean.class);
            tv_detailedAddress.setText(billingDetailsBean.getResult().getAddress() + billingDetailsBean.getResult().getAddress_detail());
            tv_name.setText(billingDetailsBean.getResult().getRecipient_man());
            tv_phone.setText(billingDetailsBean.getResult().getRecipient_tel());
            tv_invoiceRise.setText(billingDetailsBean.getResult().getInvoice_up());
            if (StringUtils.isEmpty(billingDetailsBean.getResult().getEin_num())) {
                ll_taxpayerIdentificationNumber.setVisibility(View.GONE);
            } else {
                tv_taxpayerIdentificationNumber.setText(billingDetailsBean.getResult().getEin_num());
            }
            tv_invoiceContent.setText(billingDetailsBean.getResult().getContent());
            tv_invoiceAmount.setText(billingDetailsBean.getResult().getMoney());
            tv_applyTime.setText(billingDetailsBean.getResult().getCreate_time());
            tv_anInvoice.setText(billingDetailsBean.getResult().getZ_num());
            tv_anOrder.setText(billingDetailsBean.getResult().getGood_num());
            tv_startTime.setText(billingDetailsBean.getResult().getStart_time());
            tv_endTime.setText(billingDetailsBean.getResult().getEnd_time());
            g_id = billingDetailsBean.getResult().getG_id();
        } else if (flag == 1) {
            Intent intent = new Intent(aty, ContainsOrderActivity.class);
            intent.putExtra("g_id", g_id);
            showActivity(aty, intent);
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
