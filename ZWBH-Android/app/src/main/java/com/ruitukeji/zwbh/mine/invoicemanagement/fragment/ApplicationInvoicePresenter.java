package com.ruitukeji.zwbh.mine.invoicemanagement.fragment;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.entity.mine.invoicemanagement.ApplicationInvoiceBean.ResultBean;
import com.ruitukeji.zwbh.mine.invoicemanagement.fragment.dialog.ConfirmSubmitBouncedDialog;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ruitu on 2016/9/24.
 */

public class ApplicationInvoicePresenter implements ApplicationInvoiceContract.Presenter {

    private ApplicationInvoiceContract.View mView;

    public ApplicationInvoicePresenter(ApplicationInvoiceContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getApplicationInvoiceList() {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getApplicationInvoiceList(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

    @Override
    public void postApplicationInvoice(int invoice_type, String money, int up_type, String invoice_up, String content, String recipient_man, String recipient_tel,
                                       String address, String address_detail, String ein_num, String address_phone, String bank_account, List<ResultBean> list) {

        if (StringUtils.isEmpty(invoice_up)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseFillOut) + KJActivityStack.create().topActivity().getString(R.string.invoiceRise), 1);
            return;
        }
        if (StringUtils.isEmpty(invoice_up)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseFillOut) + KJActivityStack.create().topActivity().getString(R.string.invoiceContent), 1);
            return;
        }
        if (up_type == 1) {
            if (StringUtils.isEmpty(ein_num)) {
                mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.fillTaxpayerIdentificationNumber), 1);
                return;
            }
            if (StringUtils.isEmpty(address_phone)) {
                mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.fillAddressTelephoneNumber), 1);
                return;
            }
            if (StringUtils.isEmpty(bank_account)) {
                mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.fillOpeningBankAccount), 1);
                return;
            }
        }
        if (StringUtils.isEmpty(recipient_man)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseFillOut) + KJActivityStack.create().topActivity().getString(R.string.recipient), 1);
            return;
        }
        if (StringUtils.isEmpty(recipient_tel)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseFillOut) + KJActivityStack.create().topActivity().getString(R.string.contactPhoneNumber), 1);
            return;
        }
        if (StringUtils.isEmpty(address)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseSelect) + KJActivityStack.create().topActivity().getString(R.string.inArea), 1);
            return;
        }
        if (StringUtils.isEmpty(address_detail)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseFillOut) + KJActivityStack.create().topActivity().getString(R.string.detailedAddress), 1);
            return;
        }
        String idsStr = getIdList(list);
        if (StringUtils.isEmpty(idsStr)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.notSelectedOrder), 1);
            return;
        }

        ConfirmSubmitBouncedDialog confirmSubmitBouncedDialog = new ConfirmSubmitBouncedDialog(KJActivityStack.create().topActivity());
        confirmSubmitBouncedDialog.setConfirmSubmitDialogCallBack(new ConfirmSubmitBouncedDialog.ConfirmSubmitDialogCallBack() {
            @Override
            public void confirm() {
                confirmSubmitBouncedDialog.cancel();
                mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.submissionLoad));
                HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("g_id", idsStr);
                map.put("invoice_type", invoice_type);
                map.put("money", money);
                map.put("up_type", up_type);

                map.put("invoice_up", invoice_up);
                map.put("content", content);
                map.put("recipient_man", recipient_man);

                map.put("recipient_tel", recipient_tel);
                map.put("address", address);
                map.put("address_detail", address_detail);

                map.put("ein_num", ein_num);
                map.put("address_phone", address_phone);
                map.put("bank_account", bank_account);

                httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
                RequestClient.postApplicationInvoice(httpParams, new ResponseListener<String>() {
                    @Override
                    public void onSuccess(String response) {
                        mView.getSuccess(response, 1);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.errorMsg(msg, 1);
                    }
                });
            }
        });
        confirmSubmitBouncedDialog.show();
    }


    /**
     * 获取标记的id
     */
    private String getIdList(List<ResultBean> masageList) {
        String msgIdStr = "";
        for (int i = 0; i < masageList.size(); i++) {
            if (masageList.get(i).getIsSelected() == 1) {
                msgIdStr = msgIdStr + "," + masageList.get(i).getId();
            }
        }
        if (StringUtils.isEmpty(msgIdStr)) {
            return "";
        }
        msgIdStr = msgIdStr.substring(1);
        return msgIdStr;
    }


}
