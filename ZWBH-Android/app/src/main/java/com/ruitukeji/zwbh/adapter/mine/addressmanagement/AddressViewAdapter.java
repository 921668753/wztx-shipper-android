package com.ruitukeji.zwbh.adapter.mine.addressmanagement;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.OrderBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 地址适配器
 * Created by Administrator on 2017/2/13.
 */

public class AddressViewAdapter extends BGAAdapterViewAdapter<ListBean> {
    public AddressViewAdapter(Context context) {
        super(context, R.layout.item_address);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper) {
        super.setItemChildListener(helper);
        helper.setItemChildClickListener(R.id.img_setDefaultAddress);
        helper.setItemChildLongClickListener(R.id.ll_edit);
        helper.setItemChildLongClickListener(R.id.ll_delete);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {

        /**
         * 公司名字
         */
        viewHolderHelper.setText(R.id.tv_companyName, listBean.getGoods_name());

        /**
         * 电话
         */
        viewHolderHelper.setText(R.id.tv_phone, listBean.getGoods_name());

        /**
         * 地址
         */
        viewHolderHelper.setText(R.id.tv_address, listBean.getGoods_name());

        /**
         * 名字
         */
        viewHolderHelper.setText(R.id.tv_name, listBean.getGoods_name());


    }

}