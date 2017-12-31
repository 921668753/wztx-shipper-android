package com.ruitukeji.zwbh.adapter.mine.addressmanagement;

import android.content.Context;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.mine.addressmanagement.AddressBean.ResultBean.ListBean;

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
        helper.setItemChildClickListener(R.id.ll_edit);
        helper.setItemChildClickListener(R.id.ll_delete);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {

        /**
         * 公司名字
         */
        viewHolderHelper.setText(R.id.tv_companyName, listBean.getClient());

        /**
         * 电话
         */
        viewHolderHelper.setText(R.id.tv_phone, listBean.getPhone());

        /**
         * 地址
         */
        if (StringUtils.isEmpty(listBean.getAddress_detail())) {
            viewHolderHelper.setText(R.id.tv_address, listBean.getAddress_name());
        } else {
            viewHolderHelper.setText(R.id.tv_address, listBean.getAddress_name() + listBean.getAddress_detail());
        }


        /**
         * 名字
         */
        viewHolderHelper.setText(R.id.tv_name, listBean.getClient_name());


    }

}