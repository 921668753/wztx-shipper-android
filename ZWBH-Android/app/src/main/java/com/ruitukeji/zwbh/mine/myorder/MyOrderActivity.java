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
import com.ruitukeji.zwbh.mine.myorder.orderfragment.EvaluationShareFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.QuoteOrderFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.SendGoodsFragment;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import static com.ruitukeji.zwbh.main.MainActivity.drawer;

/**
 * 我的订单
 * Created by Administrator on 2017/2/10.
 */

public class MyOrderActivity extends BaseActivity {

    @BindView(id = R.id.tv_allOrder, click = true)
    private TextView tv_allOrder;
    @BindView(id = R.id.tv_allOrder1)
    private TextView tv_allOrder1;
    @BindView(id = R.id.tv_quoteOrder, click = true)
    private TextView tv_quoteOrder;
    @BindView(id = R.id.tv_quoteOrder1)
    private TextView tv_quoteOrder1;
    @BindView(id = R.id.tv_sendGoodsOrder, click = true)
    private TextView tv_sendGoodsOrder;
    @BindView(id = R.id.tv_sendGoodsOrder1)
    private TextView tv_sendGoodsOrder1;
    @BindView(id = R.id.tv_deliveryOrder, click = true)
    private TextView tv_deliveryOrder;
    @BindView(id = R.id.tv_deliveryOrder1)
    private TextView tv_deliveryOrder1;
    @BindView(id = R.id.tv_completedOrder, click = true)
    private TextView tv_completedOrder;
    @BindView(id = R.id.tv_completedOrder1)
    private TextView tv_completedOrder1;
    @BindView(id = R.id.tv_evaluationShareOrder, click = true)
    private TextView tv_evaluationShareOrder;
    @BindView(id = R.id.tv_evaluationShareOrder1)
    private TextView tv_evaluationShareOrder1;

    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;
    private BaseFragment contentFragment2;
    private BaseFragment contentFragment3;
    private BaseFragment contentFragment4;
    private BaseFragment contentFragment5;

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
        contentFragment5 = new EvaluationShareFragment();
        chageIcon = getIntent().getIntExtra("chageIcon", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        try {
            drawer.closeDrawers();
        } catch (Exception e) {
        }
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
        } else if (chageIcon == 5) {
            chageIcon = 5;
            cleanColors(5);
            changeFragment(contentFragment5);
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
            case R.id.tv_allOrder:
                chageIcon = 0;
                cleanColors(0);
                changeFragment(contentFragment);
                break;
            case R.id.tv_quoteOrder:
                chageIcon = 1;
                cleanColors(1);
                changeFragment(contentFragment1);
                break;
            case R.id.tv_sendGoodsOrder:
                chageIcon = 2;
                cleanColors(2);
                changeFragment(contentFragment2);
                break;
            case R.id.tv_deliveryOrder:
                chageIcon = 3;
                cleanColors(3);
                changeFragment(contentFragment3);
                break;
            case R.id.tv_completedOrder:
                chageIcon = 4;
                cleanColors(4);
                changeFragment(contentFragment4);
                break;
            case R.id.tv_evaluationShareOrder:
                chageIcon = 5;
                cleanColors(5);
                changeFragment(contentFragment5);
                break;
            default:
                break;
        }
    }

    /**
     * 清除颜色，并添加颜色
     */
    public void cleanColors(int chageIcon) {
        tv_allOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_allOrder1.setBackgroundResource(R.color.mainColor);
        tv_quoteOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_quoteOrder1.setBackgroundResource(R.color.mainColor);
        tv_sendGoodsOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_sendGoodsOrder1.setBackgroundResource(R.color.mainColor);
        tv_deliveryOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_deliveryOrder1.setBackgroundResource(R.color.mainColor);
        tv_completedOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_completedOrder1.setBackgroundResource(R.color.mainColor);
        tv_evaluationShareOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_evaluationShareOrder1.setBackgroundResource(R.color.mainColor);
        if (chageIcon == 0) {
            tv_allOrder.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_allOrder1.setBackgroundResource(R.color.lonincolors);
        } else if (chageIcon == 1) {
            tv_quoteOrder.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_quoteOrder1.setBackgroundResource(R.color.lonincolors);
        } else if (chageIcon == 2) {
            tv_sendGoodsOrder.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_sendGoodsOrder1.setBackgroundResource(R.color.lonincolors);
        } else if (chageIcon == 3) {
            tv_deliveryOrder.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_deliveryOrder1.setBackgroundResource(R.color.lonincolors);
        } else if (chageIcon == 4) {
            tv_completedOrder.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_completedOrder1.setBackgroundResource(R.color.lonincolors);
        } else if (chageIcon == 5) {
            tv_evaluationShareOrder.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_evaluationShareOrder1.setBackgroundResource(R.color.lonincolors);
        }
    }
}
