package com.ruitukeji.zwbh.mine.mywallet.withdrawal;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.mine.mywallet.withdrawal.dialog.SubmitBouncedDialog;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.MathUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.Map;

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
    public void postWithdrawal(String withdrawalAmount, int bankId) {
        if (StringUtils.isEmpty(withdrawalAmount)) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.notHigherWithdrawalLimit1), 0);
            return;
        }
        if (!(MathUtil.judgeTwoDecimal(withdrawalAmount))) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.notHigherWithdrawalLimit1), 0);
            return;
        }
        if (StringUtils.toDouble(withdrawalAmount) <= 0) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.notHigherWithdrawalLimit1), 0);
            return;
        }
        if (bankId < 1) {
            mView.errorMsg(MyApplication.getContext().getString(R.string.withdrawalsBank1), 0);
            return;
        }
//        SubmitBouncedDialog submitBouncedDialog = new SubmitBouncedDialog(KJActivityStack.create().topActivity()) {
//            @Override
//            public void confirm() {
//                this.cancel();
//                mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
//                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("withdrawal_amount", withdrawalAmount);
//                map.put("bank_id", bankId);
//                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
//                RequestClient.postWithdrawal(httpParams, new ResponseListener<String>() {
//                    @Override
//                    public void onSuccess(String response) {
        mView.getSuccess("", 0);
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        mView.errorMsg(msg, 0);
//                    }
//                });
//            }
//        };
//        submitBouncedDialog.show();
    }

    @Override
    public void isLogin(int flag) {
        RequestClient.isLogin(new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, flag);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, flag);
            }
        });
    }
}
