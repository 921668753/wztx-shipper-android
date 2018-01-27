package com.ruitukeji.zwbh.common;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.RxVolley;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;
import com.ruitukeji.zwbh.utils.rx.RxBus;
import com.ruitukeji.zwbh.utils.rx.RxManager;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscription;
import rx.functions.Action1;


//import com.umeng.analytics.MobclickAgent;

/**
 * 公用的父Activity
 * 防止除向其他共用时增加
 * Created by ruitu on 2017/9/13.
 */

public abstract class BaseActivity extends KJActivity implements LoadingDialogView {
    public Object mPresenter = null;
    public Subscription subscription = null;
    private SweetAlertDialog mLoadingDialog = null;

    /**
     * 解决framenet重影
     *
     * @param outState
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //  super.onSaveInstanceState(outState);
    }

//    /**
//     * session的统计
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //   MobclickAgent.onResume(this);
//    }

    /**
     * 必须此处创建订阅者 Subscription subscription
     */
    @Override
    public void initData() {
        super.initData();
        subscription = RxBus.getInstance().register(MsgEvent.class).subscribe(new Action1<MsgEvent>() {
            @Override
            public void call(MsgEvent msgEvent) {
                callMsgEvent(msgEvent);
            }
        });
    }


    @Override
    public void initWidget() {
        super.initWidget();
        if (subscription != null && !subscription.isUnsubscribed()) {
            RxManager.get().add(this.getClass().getName(), subscription);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void showLoadingDialog(String title) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.lonincolors));
            mLoadingDialog.setCancelable(false);
        }
        mLoadingDialog.setTitleText(title);
        mLoadingDialog.show();
        ((View) mLoadingDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).getParent()).setVisibility(View.GONE);
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            try {
                mLoadingDialog.dismiss();
            } catch (Exception e) {
                mLoadingDialog = null;
            }
        }
        mLoadingDialog = null;
    }

    public void toLigon(String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = getString(R.string.otherError);
        }
        if (msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            skipActivity(aty, LoginActivity.class);
            return;
        }
    }

    public boolean toLigon1(String msg) {
        if (StringUtils.isEmpty(msg)) {
            ViewInject.toast(getString(R.string.otherError));
            return true;
        }
        if (msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            showActivity(aty, LoginActivity.class);
            return false;
        }
        ViewInject.toast(msg);
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        //  RxVolley.getRequestQueue().cancelAll(KJActivityStack.create().getClass().getName());
        dismissLoadingDialog();
        //    MobclickAgent.onPause(this);
    }

    public void callMsgEvent(MsgEvent msgEvent) {

    }

    /**
     * 页面销毁时取消订阅，防止内存溢出  Subscription subscription
     */
    @Override
    protected void onDestroy() {
        RxVolley.getRequestQueue().cancelAll(KJActivityStack.create().getClass().getName());
        RxManager.get().cancel(this.getClass().getName());
        super.onDestroy();
        subscription = null;
        mLoadingDialog = null;
        mPresenter = null;
    }
}
