package com.ruitukeji.zwbh.adapter.mine.mywallet.accountdetails;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.main.cargoinformation.selectvehicle.ConductorModelsBean.ResultBean.LengthBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 账户详情----分类列表适配器
 * Created by Administrator on 2017/12/22.
 */

public class ClassificationViewAdapter extends BGAAdapterViewAdapter<LengthBean> {

    public ClassificationViewAdapter(Context context) {
        super(context, R.layout.item_classification);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, LengthBean lengthBean) {
        helper.setText(R.id.tv_classification, lengthBean.getName());
    }
}
