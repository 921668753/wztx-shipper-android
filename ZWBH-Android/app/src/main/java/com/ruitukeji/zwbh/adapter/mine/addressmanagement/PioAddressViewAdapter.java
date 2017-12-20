package com.ruitukeji.zwbh.adapter.mine.addressmanagement;

import android.content.Context;

import com.amap.api.services.help.Tip;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 地址搜索--------地址搜索适配器
 * Created by Administrator on 2017/2/16.
 */

public class PioAddressViewAdapter extends BGAAdapterViewAdapter<Tip> {


    public PioAddressViewAdapter(Context context) {
        super(context, R.layout.item_selectaddress);
    }


    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, Tip model) {
        if (model.getPoint() != null && !StringUtils.isEmpty(String.valueOf(model.getPoint().getLatitude()))) {
            viewHolderHelper.setText(R.id.tv_placeName, model.getName());
            viewHolderHelper.setText(R.id.tv_district, model.getDistrict() + model.getAddress());
        } else {
            removeItem(position);
        }
    }
}
