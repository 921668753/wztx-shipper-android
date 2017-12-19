package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.invoicemanagement.ApplicationInvoiceViewAdapter;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.invoicemanagement.InvoiceManagementActivity;
import com.ruitukeji.zwbh.utils.myview.ChildLiistView;

/**
 * 申请发票
 * Created by Administrator on 2017/12/15.
 */

public class ApplicationInvoiceFragment extends BaseFragment implements ApplicationInvoiceContract.View {

    private InvoiceManagementActivity aty;

    /**
     * 普通发票
     */
    @BindView(id = R.id.img_commercialInvoice, click = true)
    private ImageView img_commercialInvoice;

    /**
     * 增值税专用发票
     */
    @BindView(id = R.id.img_specialVATInvoices, click = true)
    private ImageView img_specialVATInvoices;

    /**
     * 发票金额
     */
    @BindView(id = R.id.tv_invoiceAmount)
    private TextView tv_invoiceAmount;

    /**
     * 个人
     */
    @BindView(id = R.id.img_personal, click = true)
    private ImageView img_personal;

    /**
     * 公司
     */
    @BindView(id = R.id.img_company, click = true)
    private ImageView img_company;

    /**
     * 发票抬头
     */
    @BindView(id = R.id.et_invoiceRise)
    private EditText et_invoiceRise;

    /**
     * 税号
     */
    @BindView(id = R.id.ll_einDividerWidth)
    private LinearLayout ll_einDividerWidth;
    @BindView(id = R.id.ll_ein)
    private LinearLayout ll_ein;
    @BindView(id = R.id.et_ein)
    private EditText et_ein;
    /**
     * 发票内容
     */
    @BindView(id = R.id.et_invoiceContent)
    private EditText et_invoiceContent;

    /**
     * 地址和电话
     */
    @BindView(id = R.id.ll_addressTelephoneNumberDivider)
    private LinearLayout ll_addressTelephoneNumberDivider;
    @BindView(id = R.id.ll_addressTelephoneNumber)
    private LinearLayout ll_addressTelephoneNumber;
    @BindView(id = R.id.et_addressTelephoneNumber)
    private EditText et_addressTelephoneNumber;

    /**
     * 开户行和账号
     */
    @BindView(id = R.id.ll_openingBankAccountDivider)
    private LinearLayout ll_openingBankAccountDivider;
    @BindView(id = R.id.ll_openingBankAccount)
    private LinearLayout ll_openingBankAccount;
    @BindView(id = R.id.et_openingBankAccount)
    private EditText et_openingBankAccount;

    /**
     * 收件人
     */
    @BindView(id = R.id.et_recipient)
    private EditText et_recipient;


    /**
     * 联系电话
     */
    @BindView(id = R.id.et_contactPhoneNumber)
    private EditText et_contactPhoneNumber;

    /**
     * 所在地区
     */
    @BindView(id = R.id.ll_inArea, click = true)
    private LinearLayout ll_inArea;
    @BindView(id = R.id.tv_inArea)
    private TextView tv_inArea;

    /**
     * 详细地址
     */
    @BindView(id = R.id.et_detailedAddress)
    private EditText et_detailedAddress;

    /**
     * 全选
     */
    @BindView(id = R.id.img_generalElection)
    private ImageView img_generalElection;


    /**
     * 账单列表
     */
    @BindView(id = R.id.lv_applicationInvoice)
    private ChildLiistView lv_applicationInvoice;

    private ApplicationInvoiceViewAdapter mAdapter = null;


    /**
     * 提交
     */
    @BindView(id = R.id.tv_submit, click = true)
    private TextView tv_submit;

    @Override

    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (InvoiceManagementActivity) getActivity();
        return View.inflate(aty, R.layout.fragment_applicationinvoice, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new ApplicationInvoiceViewAdapter(aty);

    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        selectHeadType("personal");
    }

    /**
     * 选择抬头类型
     */
    private void selectHeadType(String headType) {
        if (headType.equals("personal")) {
            ll_einDividerWidth.setVisibility(View.GONE);
            et_ein.setText("");
            ll_addressTelephoneNumberDivider.setVisibility(View.GONE);
            et_addressTelephoneNumber.setText("");
            ll_addressTelephoneNumber.setVisibility(View.GONE);
            ll_openingBankAccountDivider.setVisibility(View.GONE);
            et_openingBankAccount.setText("");
            ll_openingBankAccount.setVisibility(View.GONE);
        } else if (headType.equals("company")) {
            ll_einDividerWidth.setVisibility(View.VISIBLE);
            et_ein.setText("");
            ll_addressTelephoneNumberDivider.setVisibility(View.VISIBLE);
            et_addressTelephoneNumber.setText("");
            ll_addressTelephoneNumber.setVisibility(View.VISIBLE);
            ll_openingBankAccountDivider.setVisibility(View.VISIBLE);
            et_openingBankAccount.setText("");
            ll_openingBankAccount.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_commercialInvoice:


                break;
            case R.id.img_specialVATInvoices:


                break;
            case R.id.img_personal:
                selectHeadType("personal");
                break;
            case R.id.img_company:
                selectHeadType("company");
                break;
            case R.id.ll_inArea:


                break;
            case R.id.tv_submit:


                break;
        }
    }

    @Override
    public void setPresenter(ApplicationInvoiceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
        } else if (flag == 1) {
        }
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            aty.showActivity(aty, LoginActivity.class);
            return;
        }
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }
}
