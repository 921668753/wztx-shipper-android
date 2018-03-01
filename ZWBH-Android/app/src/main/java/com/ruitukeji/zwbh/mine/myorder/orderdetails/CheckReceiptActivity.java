package com.ruitukeji.zwbh.mine.myorder.orderdetails;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.entity.mine.myorder.orderdetails.CheckReceiptBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;

/**
 * 查看签收单
 * Created by Administrator on 2018/2/28.
 */

public class CheckReceiptActivity extends BaseActivity implements CheckReceiptContract.View {

    @BindView(id = R.id.tv_billReceipt)
    private TextView tv_billReceipt;

    @BindView(id = R.id.tv_mailingAddress)
    private TextView tv_mailingAddress;

    @BindView(id = R.id.tv_recipient)
    private TextView tv_recipient;

    @BindView(id = R.id.tv_contactInformation)
    private TextView tv_contactInformation;

    @BindView(id = R.id.ll_expressNumber)
    private LinearLayout ll_expressNumber;

    @BindView(id = R.id.tv_expressNumber)
    private TextView tv_expressNumber;

    @BindView(id = R.id.ll_expressCompany)
    private LinearLayout ll_expressCompany;

    @BindView(id = R.id.tv_expressCompany)
    private TextView tv_expressCompany;

    private int order_id = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_checkreceipt);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new CheckReceiptPresenter(this);
        order_id = getIntent().getIntExtra("order_id", 0);
        showLoadingDialog(getString(R.string.dataLoad));
        ((CheckReceiptContract.Presenter) mPresenter).getReceiptInformation(order_id);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.checkReceipt), true, R.id.titlebar);
    }

    @Override
    public void setPresenter(CheckReceiptContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        dismissLoadingDialog();
        CheckReceiptBean checkReceiptBean = (CheckReceiptBean) JsonUtil.getInstance().json2Obj(success, CheckReceiptBean.class);
        CheckReceiptBean.ResultBean resultBean = checkReceiptBean.getResult();
        if (resultBean.getCargo_is_express() == 0) {
            tv_billReceipt.setText(getString(R.string.billReceipt1));
        } else {
            tv_billReceipt.setText(getString(R.string.billReceipt));
        }
        String cargo_address = resultBean.getCargo_address();
        String cargo_address_detail = resultBean.getCargo_address_detail();
        tv_mailingAddress.setText(cargo_address + cargo_address_detail);
        tv_recipient.setText(resultBean.getCargo_man());
        tv_contactInformation.setText(resultBean.getCargo_tel());
        if (StringUtils.isEmpty(resultBean.getNum())) {
            ll_expressNumber.setVisibility(View.GONE);
        } else {
            ll_expressNumber.setVisibility(View.VISIBLE);
            tv_expressNumber.setText(resultBean.getNum());
        }
        if (StringUtils.isEmpty(resultBean.getCompany())) {
            ll_expressCompany.setVisibility(View.GONE);
        } else {
            tv_expressCompany.setVisibility(View.VISIBLE);
            tv_expressCompany.setText(resultBean.getCompany());
        }
    }

    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        if (!toLigon1(msg)) {
            finish();
        }
    }

}
