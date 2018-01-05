package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.invoicemanagement.ApplicationInvoiceViewAdapter;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.invoicemanagement.ApplicationInvoiceBean;
import com.ruitukeji.zwbh.entity.mine.invoicemanagement.ApplicationInvoiceBean.ResultBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.invoicemanagement.InvoiceManagementActivity;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;
import com.ruitukeji.zwbh.utils.PickerViewUtil;
import com.ruitukeji.zwbh.utils.SoftKeyboardUtils;
import com.ruitukeji.zwbh.utils.myview.ChildLiistView;

import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;

/**
 * 申请发票
 * Created by Administrator on 2017/12/15.
 */

public class ApplicationInvoiceFragment extends BaseFragment implements ApplicationInvoiceContract.View, BGAOnItemChildClickListener {

    private InvoiceManagementActivity aty;

    /**
     * 普通发票
     */
    @BindView(id = R.id.img_commercialInvoice, click = true)
    private ImageView img_commercialInvoice;
    private int invoice_type = 0;
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
    private int up_type = 0;
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

    private PickerViewUtil pickerViewUtil;

    /**
     * 详细地址
     */
    @BindView(id = R.id.et_detailedAddress)
    private EditText et_detailedAddress;

    /**
     * 全选
     */
    @BindView(id = R.id.ll_generalElection)
    private LinearLayout ll_generalElection;
    @BindView(id = R.id.img_generalElection, click = true)
    private ImageView img_generalElection;

    private int isAllSelected = 0;

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
        mPresenter = new ApplicationInvoicePresenter(this);
        initPickerView();
    }

    /**
     * 选项选择器
     */
    private void initPickerView() {
        if (pickerViewUtil == null) {
            pickerViewUtil = new PickerViewUtil(aty, 0) {
                @Override
                public void getAddress(String address) {
                    tv_inArea.setText(address);
                }
            };
        }
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        ((ApplicationInvoiceContract.Presenter) mPresenter).getApplicationInvoiceList();
        selectHeadType(up_type);
        lv_applicationInvoice.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    /**
     * 选择抬头类型
     */
    private void selectHeadType(int headType) {
        if (headType == 0) {
            ll_einDividerWidth.setVisibility(View.GONE);
            ll_ein.setVisibility(View.GONE);
            ll_addressTelephoneNumberDivider.setVisibility(View.GONE);
            ll_addressTelephoneNumber.setVisibility(View.GONE);
            ll_openingBankAccountDivider.setVisibility(View.GONE);
            ll_openingBankAccount.setVisibility(View.GONE);
        } else if (headType == 1) {
            ll_einDividerWidth.setVisibility(View.VISIBLE);
            ll_ein.setVisibility(View.VISIBLE);
            ll_addressTelephoneNumberDivider.setVisibility(View.VISIBLE);
            ll_addressTelephoneNumber.setVisibility(View.VISIBLE);
            ll_openingBankAccountDivider.setVisibility(View.VISIBLE);
            ll_openingBankAccount.setVisibility(View.VISIBLE);
        }
        et_ein.setText("");
        et_addressTelephoneNumber.setText("");
        et_openingBankAccount.setText("");
    }


    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_commercialInvoice:
                invoice_type = 0;
                img_commercialInvoice.setImageResource(R.mipmap.ic_checkbox_select);
                img_specialVATInvoices.setImageResource(R.mipmap.ic_checkbox_unselect);
                break;
            case R.id.img_specialVATInvoices:
                invoice_type = 1;
                img_commercialInvoice.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_specialVATInvoices.setImageResource(R.mipmap.ic_checkbox_select);
                break;
            case R.id.img_personal:
                up_type = 0;
                img_personal.setImageResource(R.mipmap.ic_checkbox_select);
                img_company.setImageResource(R.mipmap.ic_checkbox_unselect);
                selectHeadType(up_type);
                break;
            case R.id.img_company:
                up_type = 1;
                img_personal.setImageResource(R.mipmap.ic_checkbox_unselect);
                img_company.setImageResource(R.mipmap.ic_checkbox_select);
                selectHeadType(up_type);
                break;
            case R.id.ll_inArea:
                SoftKeyboardUtils.packUpKeyboard(aty);
                //点击弹出选项选择器
                pickerViewUtil.onoptionsSelectListener();
                break;
            case R.id.img_generalElection:
                if (isAllSelected == 0) {
                    isAllSelected = 1;
                    img_generalElection.setImageResource(R.mipmap.ic_checkbox_select);
                } else if (isAllSelected == 1) {
                    isAllSelected = 0;
                    img_generalElection.setImageResource(R.mipmap.ic_checkbox_unselect);
                }
                selectOption(mAdapter.getData(), isAllSelected);
                break;
            case R.id.tv_submit:
                ((ApplicationInvoiceContract.Presenter) mPresenter).postApplicationInvoice(invoice_type, tv_invoiceAmount.getText().toString(), up_type, et_invoiceRise.getText().toString().trim(),
                        et_invoiceContent.getText().toString().trim(), et_recipient.getText().toString().trim(), et_contactPhoneNumber.getText().toString().trim(), tv_inArea.getText().toString().trim(),
                        et_detailedAddress.getText().toString().trim(), et_ein.getText().toString().trim(), et_addressTelephoneNumber.getText().toString().trim(), et_openingBankAccount.getText().toString().trim(), mAdapter.getData());
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
            dismissLoadingDialog();
            ApplicationInvoiceBean applicationInvoiceBean = (ApplicationInvoiceBean) JsonUtil.json2Obj(success, ApplicationInvoiceBean.class);
            if (applicationInvoiceBean.getResult() == null || applicationInvoiceBean.getResult().size() == 0) {
                ll_generalElection.setVisibility(View.GONE);
                return;
            }
            ll_generalElection.setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.addNewData(applicationInvoiceBean.getResult());
            selectOption(applicationInvoiceBean.getResult(), 0);
        } else if (flag == 1) {
            selectHeadType(up_type);
            et_invoiceRise.setText("");
            et_invoiceContent.setText("");
            et_addressTelephoneNumber.setText("");
            et_openingBankAccount.setText("");
            et_recipient.setText("");
            et_contactPhoneNumber.setText("");
            tv_inArea.setText("");
            et_detailedAddress.setText("");
            PreferenceHelper.write(aty, StringConstants.FILENAME, "RefreshBillingRecordsFragment", true);
            ((ApplicationInvoiceContract.Presenter) mPresenter).getApplicationInvoiceList();
            Intent intent = new Intent(aty, InvoiceManagementActivity.class);
            intent.putExtra("newChageIcon", 1);
            aty.showActivity(aty, intent);
        }
    }

    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            aty.showActivity(aty, LoginActivity.class);
            return;
        }
        if (msg.equals(getString(R.string.requestedDataEmpty))) {
            ll_generalElection.setVisibility(View.GONE);
            mAdapter.clear();
            return;
        }
        ViewInject.toast(msg);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.img_applicationInvoice) {
            ImageView img_applicationInvoice = (ImageView) childView.findViewById(R.id.img_applicationInvoice);
            if (mAdapter.getItem(position).getIsSelected() == 0) {
                img_applicationInvoice.setImageResource(R.mipmap.ic_checkbox_select);
                mAdapter.getItem(position).setIsSelected(1);
                calculatePrice(mAdapter.getData());
                return;
            }
            img_applicationInvoice.setImageResource(R.mipmap.ic_checkbox_unselect);
            mAdapter.getItem(position).setIsSelected(0);
            calculatePrice(mAdapter.getData());
        }
    }

    /**
     * 计算价格
     */
    public void calculatePrice(List<ResultBean> list) {
        double price = 0;
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSelected() == 1) {
                price = price + StringUtils.toDouble(list.get(i).getClear_price());
                j++;
            } else {
                list.get(i).setIsSelected(0);
            }
        }
        if (list.size() == j) {
            img_generalElection.setImageResource(R.mipmap.ic_checkbox_select);
        } else {
            img_generalElection.setImageResource(R.mipmap.ic_checkbox_unselect);
        }
        mAdapter.notifyDataSetChanged();
        tv_invoiceAmount.setText(MathUtil.keepTwo(price));
    }

    /**
     * 全选 取消
     */
    public void selectOption(List<ResultBean> list, int isSelected) {
        double price = 0;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIsSelected(isSelected);
            if (list.get(i).getIsSelected() == 1) {
                price = price + StringUtils.toDouble(list.get(i).getClear_price());
            }
        }
        mAdapter.notifyDataSetChanged();
        tv_invoiceAmount.setText(MathUtil.keepTwo(price));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pickerViewUtil != null && pickerViewUtil.isShowing()) {
            pickerViewUtil.onDismiss();
        }
        pickerViewUtil = null;
    }

}
