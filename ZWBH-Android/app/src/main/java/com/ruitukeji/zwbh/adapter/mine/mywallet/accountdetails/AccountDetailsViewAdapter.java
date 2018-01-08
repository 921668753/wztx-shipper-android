package com.ruitukeji.zwbh.adapter.mine.mywallet.accountdetails;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.main.cargoinformation.selectvehicle.ConductorModelsBean.ResultBean.LengthBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 账户详情列表适配器
 * Created by Administrator on 2017/12/22.
 */

public class AccountDetailsViewAdapter extends BGAAdapterViewAdapter<LengthBean> {

    public AccountDetailsViewAdapter(Context context) {
        super(context, R.layout.item_accountdetails);
    }


    @Override
    public void fillData(BGAViewHolderHelper helper, int position, LengthBean lengthBean) {


        /**
         *订单号
         */
        helper.setText(R.id.tv_orderNumber, lengthBean.getName());

        /**
         * 时间
         */
        helper.setText(R.id.tv_time, lengthBean.getName());


        /**
         * 订单金额
         */
        helper.setText(R.id.tv_money, lengthBean.getName());

    }
}
