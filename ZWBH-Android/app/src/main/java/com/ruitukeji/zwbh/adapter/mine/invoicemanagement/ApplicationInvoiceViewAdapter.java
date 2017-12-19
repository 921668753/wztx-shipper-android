package com.ruitukeji.zwbh.adapter.mine.invoicemanagement;

import android.content.Context;
import android.view.View;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.ConductorModelsBean.ResultBean.LengthBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 申请发票列表适配器
 * Created by Administrator on 2017/12/22.
 */

public class ApplicationInvoiceViewAdapter extends BGAAdapterViewAdapter<LengthBean> {

    public ApplicationInvoiceViewAdapter(Context context) {
        super(context, R.layout.item_applicationinvoice);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper) {
        super.setItemChildListener(helper);
        helper.setItemChildClickListener(R.id.img_applicationInvoice);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, LengthBean lengthBean) {
        /**
         * 选择按钮
         */
        helper.setVisibility(R.id.img_applicationInvoice, View.VISIBLE);

        /**
         * 订单号
         */
        helper.setText(R.id.tv_orderNumber, mContext.getString(R.string.orderNumber1) + lengthBean.getName());

        /**
         * 时间
         */
        helper.setText(R.id.tv_time, lengthBean.getName());


        /**
         * 起始地
         */
        helper.setText(R.id.tv_startAddress, lengthBean.getName());

        /**
         * 目的地
         */
        helper.setText(R.id.tv_endAddress, lengthBean.getName());

        /**
         * 价格
         */
        helper.setText(R.id.tv_money, lengthBean.getName());
    }
}
