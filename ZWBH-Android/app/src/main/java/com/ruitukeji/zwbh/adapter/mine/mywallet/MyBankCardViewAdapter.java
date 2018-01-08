package com.ruitukeji.zwbh.adapter.mine.mywallet;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.main.cargoinformation.selectvehicle.ConductorModelsBean.ResultBean.LengthBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 我的银行卡列表适配器
 * Created by Administrator on 2017/12/22.
 */

public class MyBankCardViewAdapter extends BGAAdapterViewAdapter<LengthBean> {

    public MyBankCardViewAdapter(Context context) {
        super(context, R.layout.item_bankcard);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, LengthBean lengthBean) {
        helper.setText(R.id.tv_bankCardName, lengthBean.getName());
        helper.setText(R.id.tv_tail, lengthBean.getName());
    }
}
