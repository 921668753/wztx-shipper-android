package com.ruitukeji.zwbh.adapter.mine.addressmanagement;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.kymjs.common.Log;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 地址搜索--------地址搜索适配器
 * Created by Administrator on 2017/2/16.
 */

public class PioAddressViewAdapter extends BGAAdapterViewAdapter<PoiItem> {

    public PioAddressViewAdapter(Context context) {
        super(context, R.layout.item_selectaddress);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, PoiItem model) {
        if (model.getLatLonPoint() != null && !StringUtils.isEmpty(String.valueOf(model.getLatLonPoint().getLongitude()))) {
            viewHolderHelper.setText(R.id.tv_placeName, model.getTitle());
            Log.d("tag", model.getProvinceName());
            Log.d("tag", model.getCityName());
            Log.d("tag", model.getAdName());
            Log.d("tag", model.getSnippet());
            Log.d("tag", model.getTitle());
            int orgprovince = model.getProvinceName().indexOf("省");
            if (orgprovince == -1) {
                viewHolderHelper.setText(R.id.tv_district, model.getCityName() + model.getAdName() + model.getSnippet());
            } else {
                viewHolderHelper.setText(R.id.tv_district, model.getProvinceName() + model.getCityName() + model.getAdName() + model.getSnippet());
            }
        } else {
            removeItem(position);
        }
    }
}
