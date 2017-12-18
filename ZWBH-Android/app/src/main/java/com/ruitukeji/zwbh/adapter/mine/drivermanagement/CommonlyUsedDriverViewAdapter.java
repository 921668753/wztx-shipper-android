package com.ruitukeji.zwbh.adapter.mine.drivermanagement;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.RecommendedRecordBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 常用司机适配器
 * Created by Administrator on 2017/2/13.
 */

public class CommonlyUsedDriverViewAdapter extends BGAAdapterViewAdapter<ListBean> {

    public CommonlyUsedDriverViewAdapter(Context context) {
        super(context, R.layout.item_driver);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper) {
        super.setItemChildListener(helper);
        helper.setItemChildClickListener(R.id.ll_joinBlacklist);
        helper.setItemChildLongClickListener(R.id.ll_delete);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {

        /**
         * 姓名
         */
        viewHolderHelper.setText(R.id.tv_name, listBean.getBonus() + "");

        /**
         * 手机号
         */
        viewHolderHelper.setText(R.id.tv_phone, listBean.getBonus() + "");

        /**
         * 车牌号
         */
        viewHolderHelper.setText(R.id.tv_licensePlateNumber, listBean.getBonus() + "");

        /**
         * 车长车型
         */
        viewHolderHelper.setText(R.id.tv_conductorModels, listBean.getBonus() + "");

    }

}