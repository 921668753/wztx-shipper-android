package com.ruitukeji.zwbh.mine.mywallet.withdrawal;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.mywallet.paymentpasswordmanagement.setpaymentpassword.SetPaymentPasswordActivity;
import com.ruitukeji.zwbh.mine.mywallet.withdrawal.dialog.PayPasswordBouncedDialogActivity;
import com.ruitukeji.zwbh.mine.setting.aboutus.AboutUsActivity;
import com.ruitukeji.zwbh.mine.mywallet.mybankcard.MyBankCardActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;
import com.ruitukeji.zwbh.utils.rx.RxBus;

import cn.bingoogolapple.titlebar.BGATitleBar;

import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_CHOOSE_PHOTO;
import static com.ruitukeji.zwbh.constant.NumericConstants.REQUEST_CODE_SELECT;


/**
 * 提现
 * Created by Administrator on 2017/2/17.
 */

public class WithdrawalActivity extends BaseActivity implements WithdrawalContract.View {

    /**
     * 提险金额
     */
    @BindView(id = R.id.et_withdrawalAmount1)
    private EditText et_withdrawalAmount1;

    @BindView(id = R.id.tv_money)
    private TextView tv_money;

    /**
     * 全部提现
     */
    @BindView(id = R.id.tv_allWithdrawal, click = true)
    private TextView tv_allWithdrawal;

    /**
     * 中国银行（尾号3215）
     */
    @BindView(id = R.id.tv_withdrawalBank)
    private TextView tv_withdrawalBank;

    /**
     * 选择银行卡
     */
    @BindView(id = R.id.tv_modification, click = true)
    private TextView tv_modification;

    /**
     * 协议
     */
    @BindView(id = R.id.tv_driverWithdrawAgreement, click = true)
    private TextView tv_driverWithdrawAgreement;


    /**
     * 确定
     */
    @BindView(id = R.id.tv_confirmSubmit, click = true)
    private TextView tv_confirmSubmit;

    private String bankCardName = "";
    private String bankCardNun = "";
    private int bankCardId = 0;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_withdrawal);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new WithdrawalPresenter(this);
        bankCardName = getIntent().getStringExtra("bankCardName");
        bankCardNun = getIntent().getStringExtra("bankCardNun");
        bankCardId = getIntent().getIntExtra("bankCardId", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        BGATitleBar.SimpleDelegate simpleDelegate = new BGATitleBar.SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                aty.finish();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();
                showActivity(aty, WithdrawalRecordActivity.class);
            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.withdrawal), getString(R.string.withdrawalRecord), R.id.titlebar, simpleDelegate);
        String withdrawalAmount = PreferenceHelper.readString(this, StringConstants.FILENAME, "withdrawalAmount");
        tv_money.setText(withdrawalAmount);
        if (StringUtils.isEmpty(bankCardName) || StringUtils.isEmpty(bankCardNun)) {
            tv_withdrawalBank.setText(getString(R.string.noCard));
            tv_modification.setText(getString(R.string.addCard));
            return;
        }
        tv_withdrawalBank.setText(bankCardName + "  (" + getString(R.string.tail) + bankCardNun + ")");
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_allWithdrawal:
                et_withdrawalAmount1.setText(tv_money.getText().toString());
                et_withdrawalAmount1.setSelection(et_withdrawalAmount1.getText().toString().trim().length());
                et_withdrawalAmount1.requestFocus();
                break;
            case R.id.tv_modification:
                ((WithdrawalContract.Presenter) mPresenter).isLogin(1);
                break;
            case R.id.tv_driverWithdrawAgreement:
                Intent intentDriver = new Intent(aty, AboutUsActivity.class);
                intentDriver.putExtra("type", "driver_withdrawa_description");
                showActivity(aty, intentDriver);
                break;
            case R.id.tv_confirmSubmit:
                int is_pay_password = PreferenceHelper.readInt(aty, StringConstants.FILENAME, "is_pay_password", 0);
                if (is_pay_password == 0) {
                    ViewInject.toast(getString(R.string.notPaymentPassword));
                    Intent intent = new Intent(aty, SetPaymentPasswordActivity.class);
                    startActivity(intent);
                    return;
                }
                ((WithdrawalContract.Presenter) mPresenter).postWithdrawal(et_withdrawalAmount1.getText().toString().trim(), bankCardId);
                break;
        }
    }


    @Override
    public void setPresenter(WithdrawalContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        dismissLoadingDialog();
        if (flag == 0) {
            Intent intent = new Intent(aty, PayPasswordBouncedDialogActivity.class);
            intent.putExtra("withdrawalAmount", et_withdrawalAmount1.getText().toString().trim());
            intent.putExtra("bankId", bankCardId);
            startActivityForResult(intent, REQUEST_CODE_SELECT);
        } else if (flag == 1) {
            Intent intent = new Intent(aty, MyBankCardActivity.class);
            intent.putExtra("type", 1);
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
        }
    }

    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            showActivity(aty, LoginActivity.class);
            return;
        }
        ViewInject.toast(msg);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT && resultCode == RESULT_OK) {
            ViewInject.toast(getString(R.string.confirmSubmit2));
            RxBus.getInstance().post(new MsgEvent<String>("RxBusWithdrawalEvent"));
            finish();
        } else if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            bankCardName = data.getStringExtra("bankCardName");
            bankCardNun = data.getStringExtra("bankCardNun");
            bankCardId = data.getIntExtra("bankCardId", 0);
            tv_withdrawalBank.setText(bankCardName + "  (" + getString(R.string.tail) + bankCardNun + ")");
        }
    }

}
