package com.ruitukeji.zwbh.mine.invoicemanagement;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.mine.invoicemanagement.fragment.ApplicationInvoiceFragment;
import com.ruitukeji.zwbh.mine.invoicemanagement.fragment.BillingRecordsFragment;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 发票管理
 * Created by Administrator on 2017/12/15.
 */

public class InvoiceManagementActivity extends BaseActivity {
    /**
     * 申请发票
     */
    @BindView(id = R.id.ll_applicationInvoice, click = true)
    private LinearLayout ll_applicationInvoice;
    @BindView(id = R.id.tv_applicationInvoice)
    private TextView tv_applicationInvoice;
    @BindView(id = R.id.tv_applicationInvoice1)
    private TextView tv_applicationInvoice1;

    /**
     * 开票记录
     */
    @BindView(id = R.id.ll_billingRecords, click = true)
    private LinearLayout ll_billingRecords;
    @BindView(id = R.id.tv_billingRecords)
    private TextView tv_billingRecords;
    @BindView(id = R.id.tv_billingRecords1)
    private TextView tv_billingRecords1;

    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;
    private int chageIcon = 0;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_invoicemanagement);
    }

    @Override
    public void initData() {
        super.initData();
        contentFragment = new ApplicationInvoiceFragment();
        contentFragment1 = new BillingRecordsFragment();
        chageIcon = getIntent().getIntExtra("chageIcon", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.invoiceManagement), true, R.id.titlebar);
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
            case R.id.ll_applicationInvoice:
                chageIcon = 0;
                cleanColors(0);
                changeFragment(contentFragment);
                break;
            case R.id.ll_billingRecords:
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
        tv_applicationInvoice.setTextColor(getResources().getColor(R.color.typecolors));
        tv_applicationInvoice1.setBackgroundResource(R.color.mainColor);
        tv_billingRecords.setTextColor(getResources().getColor(R.color.typecolors));
        tv_billingRecords1.setBackgroundResource(R.color.mainColor);
        if (chageIcon == 0) {
            tv_applicationInvoice.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_applicationInvoice1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 1) {
            tv_billingRecords.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_billingRecords1.setBackgroundResource(R.color.announcementCloseColors);
        } else {
            tv_applicationInvoice.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_applicationInvoice1.setBackgroundResource(R.color.announcementCloseColors);
        }
    }
}