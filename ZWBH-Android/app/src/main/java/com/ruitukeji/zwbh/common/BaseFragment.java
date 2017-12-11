package com.ruitukeji.zwbh.common;

import android.content.Intent;
import android.view.View;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.RxVolley;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 公用的父Fragment
 * 防止除向其他共用时增加
 * Created by ruitu on 2016/9/14.
 */

public abstract class BaseFragment extends KJFragment implements LoadingDialogView {

    public Object mPresenter;
    private SweetAlertDialog mLoadingDialog;

    @SuppressWarnings("deprecation")
    @Override
    public void showLoadingDialog(String title) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.lonincolors));
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setTitleText(title);
        }
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


    @Override
    public void onPause() {
        super.onPause();
        dismissLoadingDialog();
        RxVolley.getRequestQueue().cancelAll(KJActivityStack.create().getClass().getName());
        //    MobclickAgent.onPause(this);
    }

    public void toLigon(String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = getString(R.string.otherError);
        }
        if (msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            return;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoadingDialog = null;
        mPresenter = null;
    }
}
