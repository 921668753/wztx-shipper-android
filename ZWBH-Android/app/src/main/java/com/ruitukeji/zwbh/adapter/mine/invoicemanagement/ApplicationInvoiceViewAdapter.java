package com.ruitukeji.zwbh.adapter.mine.invoicemanagement;

import android.content.Context;
import android.view.View;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.mine.invoicemanagement.ApplicationInvoiceBean.ResultBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 申请发票列表适配器
 * Created by Administrator on 2017/12/22.
 */

public class ApplicationInvoiceViewAdapter extends BGAAdapterViewAdapter<ResultBean> {

    public ApplicationInvoiceViewAdapter(Context context) {
        super(context, R.layout.item_applicationinvoice);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper) {
        super.setItemChildListener(helper);
        helper.setItemChildClickListener(R.id.img_applicationInvoice);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, ResultBean resultBean) {
        /**
         * 选择按钮
         */
        helper.setVisibility(R.id.img_applicationInvoice, View.VISIBLE);
        if (resultBean.getIsSelected() == 1) {
            helper.setImageResource(R.id.img_applicationInvoice, R.mipmap.ic_checkbox_select);
        } else if (resultBean.getIsSelected() == 0) {
            helper.setImageResource(R.id.img_applicationInvoice, R.mipmap.ic_checkbox_unselect);
        }

        /**
         * 订单号
         */
        helper.setText(R.id.tv_orderNumber, mContext.getString(R.string.orderNumber1) + resultBean.getOrder_code());

        /**
         * 状态
         */
        if (StringUtils.isEmpty(resultBean.getType())) {
            helper.setImageDrawable(R.id.img_label, null);
        } else if (resultBean.getType().equals("often")) {
            helper.setImageResource(R.id.img_label, R.mipmap.label_shishi);
        } else if (resultBean.getType().equals("urgent")) {
            helper.setImageResource(R.id.img_label, R.mipmap.label_jiaji);
        } else if (resultBean.getType().equals("appoint")) {
            helper.setImageResource(R.id.img_label, R.mipmap.label_yuyue);
        }

        /**
         * 时间
         */
        helper.setText(R.id.tv_time, resultBean.getPay_time());


        /**
         * 起始地
         */
        helper.setText(R.id.tv_startAddress, resultBean.getOrg_address_name());

        /**
         * 目的地
         */
        helper.setText(R.id.tv_endAddress, resultBean.getDest_address_name());

        /**
         * 价格
         */
        helper.setText(R.id.tv_money, resultBean.getClear_price());
    }
}
