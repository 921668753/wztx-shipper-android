package com.ruitukeji.zwbh.mine.mywallet.mybankcard;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 添加银行卡
 * Created by Administrator on 2017/11/30.
 */

public class AddBankCardActivity extends BaseActivity implements AddBankCardContract.View {

    /**
     * 持卡人
     */
    @BindView(id = R.id.et_cardholder)
    private EditText et_cardholder;

    /**
     * 银行卡号
     */
    @BindView(id = R.id.et_bankCardNumber)
    private EditText et_bankCardNumber;

    /**
     * 提现银行
     */
    @BindView(id = R.id.ll_withdrawalsBank, click = true)
    private LinearLayout ll_withdrawalsBank;
    @BindView(id = R.id.tv_withdrawalsBank)
    private TextView tv_withdrawalsBank;

    /**
     * 开户行
     */
    @BindView(id = R.id.et_openingBank)
    private EditText et_openingBank;

    /**
     * 手机号
     */
    @BindView(id = R.id.et_phone)
    private EditText et_phone;

    /**
     * 倒计时内部类
     */
    private TimeCount time;

    /**
     * t验证码类型 reg=注册 restpwd=找回密码 login=登陆 bind=绑定手机号.
     */
    private String type = "reg";

    /**
     * 验证码
     */
    @BindView(id = R.id.et_verificationCode)
    private EditText et_verificationCode;
    @BindView(id = R.id.tv_verificationCode, click = true)
    private TextView tv_verificationCode;

    /**
     * 确认添加
     */
    @BindView(id = R.id.tv_prepaidImmediately, click = true)
    private TextView tv_prepaidImmediately;
    private OptionsPickerView pvOptions;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_addbankcard);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new AddBankCardPresenter(this);
        time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
        selectBankName();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.addBankCard), true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_withdrawalsBank:

                break;
            case R.id.tv_verificationCode:
                showLoadingDialog(getString(R.string.sendingLoad));
                ((AddBankCardContract.Presenter) mPresenter).postCode(et_phone.getText().toString(), type);
                break;
            case R.id.tv_prepaidImmediately:
                ((AddBankCardContract.Presenter) mPresenter).postAddBankCard(et_cardholder.getText().toString().trim(), et_bankCardNumber.getText().toString().trim(),
                        tv_withdrawalsBank.getText().toString().trim(), et_openingBank.getText().toString().trim(), et_phone.getText().toString().trim(),
                        et_verificationCode.getText().toString().trim());
                break;
        }
    }


    /**
     * 选择银行名称
     */
    @SuppressWarnings("unchecked")
    private void selectBankName() {
        pvOptions = new OptionsPickerView.Builder(aty, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                //  car_type_id = carInfoBean.getResult().get(options1).getId();
                //  ((TextView) v).setText(restaurant_chooseList.get(options1).getDescription());
            }
        }).build();
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tv_verificationCode.setText(getString(R.string.revalidation));
            tv_verificationCode.setClickable(true);
            tv_verificationCode.setTextColor(getResources().getColor(R.color.announcementCloseColors));
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tv_verificationCode.setClickable(false);
            tv_verificationCode.setText(millisUntilFinished / 1000 + getString(R.string.toResend));
            tv_verificationCode.setTextColor(getResources().getColor(R.color.hintcolors));
        }
    }


    @Override
    public void setPresenter(AddBankCardContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            //    tv_registe.setEnabled(true);
            //    CodeBean bean = (CodeBean) JsonUtil.getInstance().json2Obj(s, CodeBean.class);
            ViewInject.toast(getString(R.string.testget));
            time.start();
        } else if (flag == 1) {

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
