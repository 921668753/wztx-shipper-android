package com.ruitukeji.zwbh.mine.mywallet.accountdetails;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment.PaidFragment;
import com.ruitukeji.zwbh.mine.mywallet.accountdetails.fragment.UnpaidFragment;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 账户明细
 * Created by Administrator on 2017/12/15.
 */

public class AccountDetailsActivity extends BaseActivity {


    /**
     * 已支付
     */
    @BindView(id = R.id.ll_paid, click = true)
    private LinearLayout ll_paid;
    @BindView(id = R.id.tv_paid)
    private TextView tv_paid;
    @BindView(id = R.id.tv_paid1)
    private TextView tv_paid1;

    /**
     * 未支付
     */
    @BindView(id = R.id.ll_unpaid, click = true)
    private LinearLayout ll_unpaid;
    @BindView(id = R.id.tv_unpaid)
    private TextView tv_unpaid;
    @BindView(id = R.id.tv_unpaid1)
    private TextView tv_unpaid1;

    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;
    private int chageIcon = 0;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_accountdetails);
    }

    @Override
    public void initData() {
        super.initData();
        contentFragment = new PaidFragment();
        contentFragment1 = new UnpaidFragment();
        chageIcon = getIntent().getIntExtra("chageIcon", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.accountDetails), true, R.id.titlebar);
        if (chageIcon == 0) {
            chageIcon = 0;
            cleanColors(0);
            changeFragment(contentFragment);
        } else if (chageIcon == 1) {
            chageIcon = 1;
            cleanColors(1);
            changeFragment(contentFragment1);
        } else {
            chageIcon = 0;
            cleanColors(0);
            changeFragment(contentFragment);
        }
    }


    public void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.main_content, targetFragment);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_paid:
                chageIcon = 0;
                cleanColors(0);
                changeFragment(contentFragment);
                break;
            case R.id.ll_unpaid:
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
        tv_paid.setTextColor(getResources().getColor(R.color.typecolors));
        tv_paid1.setBackgroundResource(R.color.mainColor);
        tv_unpaid.setTextColor(getResources().getColor(R.color.typecolors));
        tv_unpaid1.setBackgroundResource(R.color.mainColor);
        if (chageIcon == 0) {
            tv_paid.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_paid1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 1) {
            tv_unpaid.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_unpaid1.setBackgroundResource(R.color.announcementCloseColors);
        } else {
            tv_paid.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_paid1.setBackgroundResource(R.color.announcementCloseColors);
        }
    }
}