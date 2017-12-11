package com.ruitukeji.zwbh.adapter;

import android.content.Context;

import com.amap.api.services.help.Tip;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.RefreshModel;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 地址搜索--------发货信息地址搜索适配器
 * Created by Administrator on 2017/2/16.
 */

public class PioAddressViewAdapter extends BGAAdapterViewAdapter<Tip> {


    public PioAddressViewAdapter(Context context) {
        super(context, R.layout.item_pioaddress);
    }

//    @Override
//    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
//      //  viewHolderHelper.setItemChildClickListener(R.id.tv_item_normal_delete);
//     //   viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
//    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, Tip model) {
        //    KjBitmapUtil.getInstance().getKjBitmap().display((ImageView) viewHolderHelper.getView(R.id.img_checkvoucher), model.detail, MyApplication.screenW, MyApplication.screenW / 2, R.mipmap.ic_launcher);
        // 这里不知道当前图片的尺寸，加载成功后会乱跳
//        Glide.with(mContext).load(model.icon).placeholder(R.mipmap.staggered_holder).error(R.mipmap.staggered_holder).dontAnimate().into((ImageView) viewHolderHelper.getView(R.id.iv_item_staggered_icon));
        viewHolderHelper.setText(R.id.tv_address, model.getDistrict() + model.getName());
    }
}
