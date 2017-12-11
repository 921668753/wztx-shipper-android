package com.ruitukeji.zwbh.mine.mypublishedorder;

import android.view.View;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment.QuoteGoodsFragment;
import com.ruitukeji.zwbh.mine.mypublishedorder.publishedorderfragment.TheBidGoodsFragment;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;

import static com.ruitukeji.zwbh.main.MainActivity.drawer;

/**
 * 我发布的订单
 * Created by Administrator on 2017/2/10.
 */

public class MyPublishedGoodsActivity extends BaseActivity {

    //    @BindView(id = R.id.tv_toQuoteOrder, click = true)
//    private TextView tv_toQuoteOrder;
//    @BindView(id = R.id.tv_toQuoteOrder1)
//    private TextView tv_toQuoteOrder1;
    @BindView(id = R.id.tv_quoteOrder, click = true)
    private TextView tv_quoteOrder;
    @BindView(id = R.id.tv_quoteOrder1)
    private TextView tv_quoteOrder1;

    //    @BindView(id = R.id.tv_canceledOrder, click = true)
//    private TextView tv_canceledOrder;
//    @BindView(id = R.id.tv_canceledOrder1)
//    private TextView tv_canceledOrder1;
    @BindView(id = R.id.tv_theBidOrder, click = true)
    private TextView tv_theBidOrder;
    @BindView(id = R.id.tv_theBidOrder1)
    private TextView tv_theBidOrder1;

    //    private BaseFragment contentFragment;
    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;
//    private BaseFragment contentFragment3;

    /**
     * 用来表示移动的Fragment
     */
    private int chageIcon;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_mypublishedorder);
    }

    @Override
    public void initData() {
        super.initData();
//        contentFragment = new ToQuoteOrderFragment();
        contentFragment = new QuoteGoodsFragment();
        contentFragment1 = new TheBidGoodsFragment();
//        contentFragment3 = new TheBidGoodsFragment();
        chageIcon = getIntent().getIntExtra("chageIcon", 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        try {
            drawer.closeDrawers();
        } catch (Exception e) {
        }
        ActivityTitleUtils.initToolbar(aty, getString(R.string.myPublishedOrder), true, R.id.titlebar);
//        if (chageIcon == 0) {
//            chageIcon = 0;
//            cleanColors(0);
//            changeFragment(contentFragment);
//        } else
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
        //  transaction.commit();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_toQuoteOrder:
//                chageIcon = 0;
//                cleanColors(0);
//                changeFragment(contentFragment);
                break;
            case R.id.tv_quoteOrder:
                chageIcon = 0;
                cleanColors(0);
                changeFragment(contentFragment);
                break;
//            case R.id.tv_canceledOrder:
//                chageIcon = 2;
//                cleanColors(2);
//                changeFragment(contentFragment2);
//                break;
            case R.id.tv_theBidOrder:
                chageIcon = 1;
                cleanColors(1);
                changeFragment(contentFragment1);
//                chageIcon = 3;
//                cleanColors(3);
//                changeFragment(contentFragment3);
                break;
            default:
                break;
        }
    }

    /**
     * 清除颜色，并添加颜色
     */
    public void cleanColors(int chageIcon) {
//        tv_toQuoteOrder.setTextColor(getResources().getColor(R.color.typecolors));
//        tv_toQuoteOrder1.setBackgroundResource(R.color.mainColor);
        tv_quoteOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_quoteOrder1.setBackgroundResource(R.color.mainColor);
//        tv_canceledOrder.setTextColor(getResources().getColor(R.color.typecolors));
//        tv_canceledOrder1.setBackgroundResource(R.color.mainColor);
        tv_theBidOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_theBidOrder1.setBackgroundResource(R.color.mainColor);
//        if (chageIcon == 0) {
//            tv_toQuoteOrder.setTextColor(getResources().getColor(R.color.lonincolors));
//            tv_toQuoteOrder1.setBackgroundResource(R.color.lonincolors);
//        } else
        if (chageIcon == 0) {
            tv_quoteOrder.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_quoteOrder1.setBackgroundResource(R.color.lonincolors);
        }
//        else if (chageIcon == 2) {
//            tv_canceledOrder.setTextColor(getResources().getColor(R.color.lonincolors));
//            tv_canceledOrder1.setBackgroundResource(R.color.lonincolors);
//        }
        else if (chageIcon == 1) {
            tv_theBidOrder.setTextColor(getResources().getColor(R.color.lonincolors));
            tv_theBidOrder1.setBackgroundResource(R.color.lonincolors);
        }
    }
}
