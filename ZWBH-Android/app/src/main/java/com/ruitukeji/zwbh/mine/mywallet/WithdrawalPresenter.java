package com.ruitukeji.zwbh.mine.mywallet;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/2/17.
 */

public class WithdrawalPresenter implements WithdrawalContract.Presenter {


    private WithdrawalContract.View mView;

    public WithdrawalPresenter(WithdrawalContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postWithdrawal(String withdrawalAmount, String bankName, String paymentAccount, String accountName) {
        if (StringUtils.isEmpty(withdrawalAmount)) {
            mView.error(MyApplication.getContext().getString(R.string.notHigherWithdrawalLimit1));
            return;
        }
        if (!(MathUtil.judgeTwoDecimal(withdrawalAmount))) {
            mView.error(MyApplication.getContext().getString(R.string.notHigherWithdrawalLimit1));
            return;
        }
        if (StringUtils.isEmpty(bankName)) {
            mView.error(MyApplication.getContext().getString(R.string.presentingBank1));
            return;
        }
        if (StringUtils.isEmpty(paymentAccount)) {
            mView.error(MyApplication.getContext().getString(R.string.promptAccount1));
            return;
        }
        if (StringUtils.isEmpty(accountName)) {
            mView.error(MyApplication.getContext().getString(R.string.accountHolderName1));
            return;
        }
        mView.showAlertDialog(new WithdrawalActivity.OnDialogSelectListener() {
            @Override
            public void onDialogSelect(SweetAlertDialog sDialog) {
                sDialog.dismiss();
                sDialog = null;
                mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                mView.getSuccess("");
                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
                Map map = new HashMap();
                map.put("account", withdrawalAmount);
                map.put("type", bankName);
                map.put("type", paymentAccount);
                map.put("type", accountName);
                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
                RequestClient.postWithdrawal(httpParams, new ResponseListener<String>() {
                    @Override
                    public void onSuccess(String response) {
                        mView.getSuccess(response);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.error(msg);
                    }
                });
            }
        });
    }
}
