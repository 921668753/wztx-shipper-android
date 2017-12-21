package com.ruitukeji.zwbh.main.cargoinformation;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface FillCargoReceiptFormContract {

    interface Presenter extends BasePresenter {
        /**
         * 填写货物签收单
         */
        void postFillCargoReceiptForm(String contactPerson, String contactInformation, String inArea, String detailedAddressInformation, int expressDelivery);
    }

    interface View extends BaseNewView<Presenter, String> {
    }

}
