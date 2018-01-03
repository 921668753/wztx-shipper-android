package com.ruitukeji.zwbh.adapter.mine.drivermanagement;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.mine.drivermanagement.DriverManagementBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 常用司机适配器
 * Created by Administrator on 2017/2/13.
 */

public class DriverManagementViewAdapter extends BGAAdapterViewAdapter<ListBean> {

    public DriverManagementViewAdapter(Context context) {
        super(context, R.layout.item_driver);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper) {
        super.setItemChildListener(helper);
        helper.setItemChildClickListener(R.id.ll_joinBlacklist);
        helper.setItemChildClickListener(R.id.ll_delete);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {

        /**
         * 姓名
         */
        viewHolderHelper.setText(R.id.tv_name, listBean.getDr_name() + "");

        /**
         * 手机号
         */
        viewHolderHelper.setText(R.id.tv_phone, listBean.getDr_tel());

        /**
         * 车牌号
         */
        viewHolderHelper.setText(R.id.tv_licensePlateNumber, listBean.getCard_code());

        /**
         * 车长车型
         */
        viewHolderHelper.setText(R.id.tv_conductorModels, listBean.getCar_length() + mContext.getString(R.string.m) + "/" + listBean.getCar_type());

        if (listBean.getIs_black() == 0) {
            viewHolderHelper.setImageResource(R.id.img_join, R.mipmap.icon_join);
            viewHolderHelper.setText(R.id.tv_joinBlacklist, mContext.getString(R.string.joinBlacklist));
        } else {
            viewHolderHelper.setImageResource(R.id.img_join, R.mipmap.icon_remove);
            viewHolderHelper.setText(R.id.tv_joinBlacklist, mContext.getString(R.string.removeBlacklist));
        }

    }

}