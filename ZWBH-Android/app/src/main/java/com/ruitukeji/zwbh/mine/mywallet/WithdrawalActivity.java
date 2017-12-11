package com.ruitukeji.zwbh.mine.mywallet;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 余额提现
 * Created by Administrator on 2017/2/17.
 */

public class WithdrawalActivity extends BaseActivity implements WithdrawalContract.View {

    @BindView(id = R.id.et_withdrawalAmount1)
    private EditText et_withdrawalAmount1;

    @BindView(id = R.id.et_bankName)
    private EditText et_bankName;

    @BindView(id = R.id.et_paymentAccount)
    private EditText et_paymentAccount;

    @BindView(id = R.id.et_openAccountName)
    private EditText et_openAccountName;

    @BindView(id = R.id.tv_confirmSubmit, click = true)
    private TextView tv_confirmSubmit;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_withdrawal);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new WithdrawalPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.withdrawal), true, R.id.titlebar);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_confirmSubmit:
                ((WithdrawalContract.Presenter) mPresenter).postWithdrawal(et_withdrawalAmount1.getText().toString(), et_bankName.getText().toString(), et_paymentAccount.getText().toString(), et_openAccountName.getText().toString());
                break;
        }
    }

    @Override
    public void showAlertDialog(OnDialogSelectListener onDialogSelectListener) {

//        SweetAlertDialog.confirmBackgroundResource = R.drawable.shape_login;
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        //   sweetAlertDialog.findViewById(cn.pedant.SweetAlert.R.id.confirm_button).setBackgroundResource(R.drawable.shape_login);
        sweetAlertDialog.setTitleText(getString(R.string.confirmSubmit1))
                .setCancelText(getString(R.string.cancel1))
                .setConfirmText(getString(R.string.confirm))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.dismiss();
//                        sDialog = null;
                        onDialogSelectListener.onDialogSelect(sDialog);
                    }
                }).show();

    }

    public interface OnDialogSelectListener {
        void onDialogSelect(SweetAlertDialog sDialog);
    }


    @Override
    public void getSuccess(String s) {
        dismissLoadingDialog();
//        SweetAlertDialog.confirmBackgroundResource = R.drawable.shape_login;
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        //   sweetAlertDialog.findViewById(cn.pedant.SweetAlert.R.id.confirm_button).setBackgroundResource(R.drawable.shape_login);
        sweetAlertDialog.setTitleText(getString(R.string.confirmSubmit2))
                .setConfirmText(getString(R.string.confirm))
                .showCancelButton(false)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        sDialog = null;
                        aty.finish();
                    }
                }).show();

    }

    @Override
    public void error(String msg) {
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            showActivity(aty, LoginActivity.class);
            return;
        }
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    @Override
    public void setPresenter(WithdrawalContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
