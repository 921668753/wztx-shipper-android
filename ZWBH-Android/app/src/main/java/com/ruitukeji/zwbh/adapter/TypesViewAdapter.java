package com.ruitukeji.zwbh.adapter;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.main.cargoinformation.selectvehicle.ConductorModelsBean.ResultBean.TypeBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 车型适配器
 * Created by Administrator on 2017/2/22.
 */

public class TypesViewAdapter extends BGAAdapterViewAdapter<TypeBean> {

    public TypesViewAdapter(Context context) {
        super(context, R.layout.item_conductormodels);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, TypeBean typeBean) {
        if (typeBean.getStatus() == 1) {
            helper.setTextColorRes(R.id.tv_type, R.color.announcementCloseColors);
            helper.setBackgroundRes(R.id.tv_type, R.drawable.shape_conductormodels1);
        } else {
            helper.setTextColorRes(R.id.tv_type, R.color.titletextcolors);
            helper.setBackgroundRes(R.id.tv_type, R.drawable.shape_conductormodels);
        }
        helper.setText(R.id.tv_type, typeBean.getName());
    }

}
