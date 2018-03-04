package com.ruitukeji.zwbh.adapter.main.selectaddress.dialog;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.main.selectaddress.dialog.AddressBean.ResultBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 始发地/目的地---添加路线省弹框
 * Created by Administrator on 2018/1/23.
 */

public class AddressProvinceViewAdapter extends BGAAdapterViewAdapter<ResultBean> {

    public AddressProvinceViewAdapter(Context context) {
        super(context, R.layout.item_addressbounced);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ResultBean listBean) {
        if (listBean.getName().equals(mContext.getString(R.string.all1))) {
            removeItem(position);
        } else {
            /**
             * 城市名称
             */
            viewHolderHelper.setText(R.id.tv_adress, listBean.getName());
        }
    }

}