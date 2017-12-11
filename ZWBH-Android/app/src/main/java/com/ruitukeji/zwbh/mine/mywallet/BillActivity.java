package com.ruitukeji.zwbh.mine.mywallet;

import android.view.View;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.mine.mywallet.billfragment.NotPaymentBillFragment;
import com.ruitukeji.zwbh.mine.mywallet.billfragment.PaymentBillFragment;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 账单
 * Created by Administrator on 2017/3/9.
 */

public class BillActivity extends BaseActivity {

    @BindView(id = R.id.tv_notPaymentBill, click = true)
    private TextView tv_notPaymentBill;
    @BindView(id = R.id.tv_notPaymentBill1)
    private TextView tv_notPaymentBill1;
    @BindView(id = R.id.tv_paymentBill, click = true)
    private TextView tv_paymentBill;
    @BindView(id = R.id.tv_paymentBill1)
    private TextView tv_paymentBill1;

    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;

    /**
     * 用来表示移动的Fragment
     */
    private int chageIcon;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_bill);
    }

    @Override
    public void initData() {
        super.initData();
        contentFragment = new NotPaymentBillFragment();
        contentFragment1 = new PaymentBillFragment();
        chageIcon = getIntent().getIntExtra("chageIcon", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.bill), true, R.id.titlebar);
        if (chageIcon == 0) {
            chageIcon = 0;
            cleanColors(0);
            changeFragment(contentFragment);
        } else if (chageIcon == 1) {
            chageIcon = 1;
            cleanColors(1);
            changeFragment(contentFragment1);
        }
    }

    public void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.main_content, targetFragment);
        //  transaction.commit();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_notPaymentBill:
                chageIcon = 0;
                cleanColors(0);
                changeFragment(contentFragment);
                break;
            case R.id.tv_paymentBill:
                chageIcon = 1;
                cleanColors(1);
                changeFragment(contentFragment1);
                break;

            default:
                break;
        }
    }

    /**
     * 清除颜色，并添加颜色
     */
    public void cleanColors(int chageIcon) {
        tv_notPaymentBill.setTextColor(getResources().getColor(R.color.typecolors));
        tv_notPaymentBill1.setBackgroundResource(R.color.mainColor);
        tv_paymentBill.setTextColor(getResources().getColor(R.color.typecolors));
        tv_paymentBill1.setBackgroundResource(R.color.mainColor);
        if (chageIcon == 0) {
            tv_notPaymentBill.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_notPaymentBill1.setBackgroundResource(R.color.lonincolors);
        } else if (chageIcon == 1) {
            tv_paymentBill.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_paymentBill1.setBackgroundResource(R.color.lonincolors);
        }
    }
}
