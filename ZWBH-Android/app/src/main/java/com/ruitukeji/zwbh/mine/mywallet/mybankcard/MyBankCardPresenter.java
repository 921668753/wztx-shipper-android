package com.ruitukeji.zwbh.mine.mywallet.mybankcard;

/**
 * Created by Administrator on 2017/2/17.
 */

public class MyBankCardPresenter implements MyBankCardContract.Presenter {


    private MyBankCardContract.View mView;

    public MyBankCardPresenter(MyBankCardContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }









//    @Override
//    public void postWithdrawal(SweetAlertDialog sweetAlertDialog, String withdrawalAmount, String bankName, String paymentAccount, String accountName) {
//        if (StringUtils.isEmpty(withdrawalAmount)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.notHigherWithdrawalLimit1), 0);
//            return;
//        }
//        if (!(MathUtil.judgeTwoDecimal(withdrawalAmount))) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.notHigherWithdrawalLimit1), 0);
//            return;
//        }
//        if (StringUtils.toDouble(withdrawalAmount) <= 0) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.notHigherWithdrawalLimit1), 0);
//            return;
//        }
//        if (StringUtils.isEmpty(bankName)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.presentingBank1), 0);
//            return;
//        }
//        if (StringUtils.isEmpty(paymentAccount)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.promptAccount1), 0);
//            return;
//        }
//        if (StringUtils.isEmpty(accountName)) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.accountHolderName1), 0);
//            return;
//        }
//        String all = "^[A-Za-z\\u4e00-\\u9fa5]{2,10}";//{2,10}表示字符的长度是2-10
//        Pattern pattern = Pattern.compile(all);
//        boolean tf = Pattern.matches(all, accountName);
//        if (!tf) {
//            mView.errorMsg(MyApplication.getContext().getString(R.string.hintName1), 0);
//            return;
//        }
//        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                sweetAlertDialog.dismiss();
//                mView.showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
//                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("withdrawal_amount", withdrawalAmount);
//                map.put("bank_name", bankName);
//                map.put("payment_account", paymentAccount);
//                map.put("account_name", accountName);
//                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
//                RequestClient.postWithdrawal(httpParams, new ResponseListener<String>() {
//                    @Override
//                    public void onSuccess(String response) {
//                        mView.getSuccess(response, 0);
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        mView.errorMsg(msg, 0);
//                    }
//                });
//            }
//        }).show();
//    }

//    @Override
//    public void isLogin(int flag) {
//        RequestClient.isLogin(new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response, flag);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.errorMsg(msg, flag);
//            }
//        });
//    }

    @Override
    public void getMyBankCard() {

    }
}
