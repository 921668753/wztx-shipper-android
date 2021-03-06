package com.ruitukeji.zwbh.adapter.mine.invoicemanagement;

import android.content.Context;
import android.view.View;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.mine.invoicemanagement.BillingRecordsBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 开票记录列表适配器
 * Created by Administrator on 2017/12/22.
 */

public class BillingRecordsViewAdapter extends BGAAdapterViewAdapter<ListBean> {

    public BillingRecordsViewAdapter(Context context) {
        super(context, R.layout.item_billingrecords);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper) {
        super.setItemChildListener(helper);
        //   helper.setItemChildClickListener(R.id.img_applicationInvoice);
    }


    @Override
    public void fillData(BGAViewHolderHelper helper, int position, ListBean lengthBean) {

        /**
         *发票类型
         */
        helper.setText(R.id.tv_invoiceType, lengthBean.getInvoice_type());

        /**
         * 发票状态
         */
        helper.setText(R.id.tv_invoiceState, lengthBean.getIs_invoice_status());


        /**
         * 发票金额
         */
        helper.setText(R.id.tv_invoiceAmount, lengthBean.getMoney());

        /**
         * 开票时间
         */
        helper.setText(R.id.tv_invoiceTime, lengthBean.getCreate());

    }
}
