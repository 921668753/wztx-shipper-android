package com.ruitukeji.zwbh.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.lzy.imagepicker.bean.ImageItem;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.GlideImageLoader;


import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;


/**
 * 凭证适配器 图片列表适配器
 * Created by Administrator on 2017/2/16.
 */

public class ImageViewAdapter extends BGAAdapterViewAdapter<ImageItem> {

    public ImageViewAdapter(Context context) {
        super(context, R.layout.item_imageview);
    }

//    @Override
//    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
//      //  viewHolderHelper.setItemChildClickListener(R.id.tv_item_normal_delete);
//     //   viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
//    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ImageItem model) {
        // 这里不知道当前图片的尺寸，加载成功后会乱跳
        //   Glide.with(mContext).load(model.icon).placeholder(R.mipmap.staggered_holder).error(R.mipmap.staggered_holder).dontAnimate().into((ImageView) viewHolderHelper.getView(R.id.iv_item_staggered_icon));
        GlideImageLoader.glideLoader(mContext, model.path, (ImageView) viewHolderHelper.getView(R.id.img_imgView),1);
    }

}