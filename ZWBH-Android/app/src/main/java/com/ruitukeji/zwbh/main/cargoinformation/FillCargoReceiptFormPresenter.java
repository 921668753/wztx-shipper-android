package com.ruitukeji.zwbh.main.cargoinformation;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;

/**
 * Created by Administrator on 2017/2/15.
 */

public class FillCargoReceiptFormPresenter implements FillCargoReceiptFormContract.Presenter {


    private FillCargoReceiptFormContract.View mView;

    public FillCargoReceiptFormPresenter(FillCargoReceiptFormContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void postFillCargoReceiptForm(String contactPerson, String contactInformation, String inArea, String detailedAddressInformation, int expressDelivery) {
        if (StringUtils.isEmpty(contactPerson)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseFillOut) + KJActivityStack.create().topActivity().getString(R.string.contactPerson), 0);
            return;
        }
        if (StringUtils.isEmpty(contactInformation)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseFillOut) + KJActivityStack.create().topActivity().getString(R.string.contactInformation2), 0);
            return;
        }
        if (StringUtils.isEmpty(inArea)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseSelect) + KJActivityStack.create().topActivity().getString(R.string.inArea), 0);
            return;
        }
        if (StringUtils.isEmpty(detailedAddressInformation)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseDetailedAddressInformation) , 0);
            return;
        }
        mView.getSuccess("", 0);
    }
}
