package com.ruitukeji.zwbh.mine.myorder;

import android.view.View;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.AllOrderFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.CompletedFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.DeliveryFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.QuoteOrderFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.SendGoodsFragment;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

/**
 * 我的订单
 * Created by Administrator on 2017/2/10.
 */

public class MyOrderActivity extends BaseActivity {
    /**
     * 全部订单
     */
    @BindView(id = R.id.tv_all, click = true)
    private TextView tv_all;
    @BindView(id = R.id.tv_all1)
    private TextView tv_all1;

    /**
     * 待接单
     */
    @BindView(id = R.id.tv_pendingOrder, click = true)
    private TextView tv_pendingOrder;
    @BindView(id = R.id.tv_pendingOrder1)
    private TextView tv_pendingOrder1;


    /**
     * 待发货
     */
    @BindView(id = R.id.tv_pendingDelivery, click = true)
    private TextView tv_pendingDelivery;
    @BindView(id = R.id.tv_pendingDelivery1)
    private TextView tv_pendingDelivery1;

    /**
     * 运输中
     */
    @BindView(id = R.id.tv_transportation, click = true)
    private TextView tv_transportation;
    @BindView(id = R.id.tv_transportation1)
    private TextView tv_transportation1;

    /**
     * 已完成
     */
    @BindView(id = R.id.tv_completed, click = true)
    private TextView tv_completed;
    @BindView(id = R.id.tv_completed1)
    private TextView tv_completed1;


    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;
    private BaseFragment contentFragment2;
    private BaseFragment contentFragment3;
    private BaseFragment contentFragment4;

    /**
     * 用来表示移动的Fragment
     */
    private int chageIcon;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_myorder);
    }

    @Override
    public void initData() {
        super.initData();
        contentFragment = new AllOrderFragment();
        contentFragment1 = new QuoteOrderFragment();
        contentFragment2 = new SendGoodsFragment();
        contentFragment3 = new DeliveryFragment();
        contentFragment4 = new CompletedFragment();
        chageIcon = getIntent().getIntExtra("chageIcon", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.my_order), true, R.id.titlebar);
        if (chageIcon == 0) {
            chageIcon = 0;
            cleanColors(0);
            changeFragment(contentFragment);
        } else if (chageIcon == 1) {
            chageIcon = 1;
            cleanColors(1);
            changeFragment(contentFragment1);
        } else if (chageIcon == 2) {
            chageIcon = 2;
            cleanColors(2);
            changeFragment(contentFragment2);
        } else if (chageIcon == 3) {
            chageIcon = 3;
            cleanColors(3);
            changeFragment(contentFragment3);
        } else if (chageIcon == 4) {
            chageIcon = 4;
            cleanColors(4);
            changeFragment(contentFragment4);
        } else {
            chageIcon = 0;
            cleanColors(0);
            changeFragment(contentFragment);
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
            case R.id.tv_all:
                chageIcon = 0;
                cleanColors(0);
                changeFragment(contentFragment);
                break;
            case R.id.tv_pendingOrder:
                chageIcon = 1;
                cleanColors(1);
                changeFragment(contentFragment1);
                break;

            case R.id.tv_pendingDelivery:
                chageIcon = 2;
                cleanColors(2);
                changeFragment(contentFragment2);
                break;
            case R.id.tv_transportation:
                chageIcon = 3;
                cleanColors(3);
                changeFragment(contentFragment3);
                break;
            case R.id.tv_completed:
                chageIcon = 4;
                cleanColors(4);
                changeFragment(contentFragment4);
                break;
            default:
                break;
        }
    }

    /**
     * 清除颜色，并添加颜色
     */
    public void cleanColors(int chageIcon) {
        tv_all.setTextColor(getResources().getColor(R.color.typecolors));
        tv_all1.setBackgroundResource(R.color.mainColor);
        tv_pendingOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_pendingOrder1.setBackgroundResource(R.color.mainColor);
        tv_pendingDelivery.setTextColor(getResources().getColor(R.color.typecolors));
        tv_pendingDelivery1.setBackgroundResource(R.color.mainColor);
        tv_transportation.setTextColor(getResources().getColor(R.color.typecolors));
        tv_transportation1.setBackgroundResource(R.color.mainColor);
        tv_completed.setTextColor(getResources().getColor(R.color.typecolors));
        tv_completed1.setBackgroundResource(R.color.mainColor);
        if (chageIcon == 0) {
            tv_all.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_all1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 1) {
            tv_pendingOrder.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_pendingOrder1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 2) {
            tv_pendingDelivery.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_pendingDelivery1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 3) {
            tv_transportation.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_transportation1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 4) {
            tv_completed.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_completed1.setBackgroundResource(R.color.announcementCloseColors);
        } else {
            tv_all.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_all1.setBackgroundResource(R.color.announcementCloseColors);
        }
    }
}
