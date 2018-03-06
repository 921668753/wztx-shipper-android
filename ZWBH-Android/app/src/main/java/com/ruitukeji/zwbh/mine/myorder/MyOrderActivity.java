package com.ruitukeji.zwbh.mine.myorder;

import android.content.Intent;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.kymjs.common.Log;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.mine.myorder.dialog.CancelOrderNoticBouncedDialog;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.AllFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.CompletedFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.PendingDeliveryFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.PendingOrderFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.PendingPaymentFragment;
import com.ruitukeji.zwbh.mine.myorder.orderfragment.TransportationFragment;
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
     * 待支付
     */
    @BindView(id = R.id.tv_pendingPayment, click = true)
    private TextView tv_pendingPayment;
    @BindView(id = R.id.tv_pendingPayment1)
    private TextView tv_pendingPayment1;

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
    private BaseFragment contentFragment5;

    /**
     * 用来表示移动的Fragment
     */
    private int chageIcon;
    public CancelOrderNoticBouncedDialog cancelOrderNoticBouncedDialog = null;
    private int isAll = 0;
    private int isPendingDelivery = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_myorder);
    }

    @Override
    public void initData() {
        super.initData();
        cancelOrderNoticBouncedDialog = new CancelOrderNoticBouncedDialog(this, 0, "");
        contentFragment = new AllFragment();
        contentFragment1 = new PendingOrderFragment();
        contentFragment2 = new PendingDeliveryFragment();
        contentFragment3 = new TransportationFragment();
        contentFragment4 = new PendingPaymentFragment();
        contentFragment5 = new CompletedFragment();
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
        } else if (chageIcon == 5) {
            chageIcon = 5;
            cleanColors(5);
            changeFragment(contentFragment4);
        } else {
            chageIcon = 0;
            cleanColors(0);
            changeFragment(contentFragment);
        }
    }

    /**
     * Activity的启动模式变为singleTask
     * 调用onNewIntent(Intent intent)方法。
     * Fragment调用的时候，一定要在onResume方法中。
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int newChageIcon = intent.getIntExtra("newChageIcon", 6);
        Log.d("newChageIcon", newChageIcon + "");
        if (newChageIcon == 0) {
            isAll = 1;
            setSimulateClick(tv_all, 160, 100);
        } else if (newChageIcon == 2) {
            isPendingDelivery = 1;
            setSimulateClick(tv_pendingDelivery, 160, 100);
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
                if (isAll == 1) {
                    isAll = 0;
                    ((AllFragment) contentFragment).showCancelOrderNoticBouncedDialog();
                }
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
                if (isPendingDelivery == 1) {
                    isPendingDelivery = 0;
                    ((PendingDeliveryFragment) contentFragment2).showCancelOrderNoticBouncedDialog();
                }
                break;
            case R.id.tv_transportation:
                chageIcon = 3;
                cleanColors(3);
                changeFragment(contentFragment3);
                break;
            case R.id.tv_pendingPayment:
                chageIcon = 4;
                cleanColors(4);
                changeFragment(contentFragment4);
                break;
            case R.id.tv_completed:
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
        tv_all.setTextColor(getResources().getColor(R.color.typecolors));
        tv_all1.setBackgroundResource(R.color.mainColor);
        tv_pendingOrder.setTextColor(getResources().getColor(R.color.typecolors));
        tv_pendingOrder1.setBackgroundResource(R.color.mainColor);
        tv_pendingDelivery.setTextColor(getResources().getColor(R.color.typecolors));
        tv_pendingDelivery1.setBackgroundResource(R.color.mainColor);
        tv_transportation.setTextColor(getResources().getColor(R.color.typecolors));
        tv_transportation1.setBackgroundResource(R.color.mainColor);
        tv_pendingPayment.setTextColor(getResources().getColor(R.color.typecolors));
        tv_pendingPayment1.setBackgroundResource(R.color.mainColor);
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
            tv_pendingPayment.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_pendingPayment1.setBackgroundResource(R.color.announcementCloseColors);
        } else if (chageIcon == 5) {
            tv_completed.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_completed1.setBackgroundResource(R.color.announcementCloseColors);
        } else {
            tv_all.setTextColor(getResources().getColor(R.color.announcementCloseColors));
            tv_all1.setBackgroundResource(R.color.announcementCloseColors);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cancelOrderNoticBouncedDialog != null) {
            cancelOrderNoticBouncedDialog.cancel();
        }
        cancelOrderNoticBouncedDialog = null;
    }

    /**
     * 模拟点击
     *
     * @param view
     * @param x
     * @param y
     */
    public void setSimulateClick(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

}
