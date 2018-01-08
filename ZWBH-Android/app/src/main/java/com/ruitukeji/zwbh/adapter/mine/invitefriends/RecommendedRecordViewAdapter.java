package com.ruitukeji.zwbh.adapter.mine.invitefriends;

import android.content.Context;

import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.entity.mine.invitefriends.RecommendedRecordBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.imageview.BGAImageView;


/**
 * 推荐记录适配器
 * Created by Administrator on 2017/2/13.
 */

public class RecommendedRecordViewAdapter extends BGAAdapterViewAdapter<ListBean> {
    public RecommendedRecordViewAdapter(Context context) {
        super(context, R.layout.item_recommendedrecord);
    }

//    @Override
//    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
//      //  viewHolderHelper.setItemChildClickListener(R.id.tv_item_normal_delete);
//     //   viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
//    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {
        /**
         * 姓名
         */
        if (StringUtils.isEmpty(listBean.getName())) {
            viewHolderHelper.setText(R.id.tv_name, listBean.getPhone());
        } else {
            viewHolderHelper.setText(R.id.tv_name, listBean.getName());
        }

        /**
         * 奖励
         */
        viewHolderHelper.setText(R.id.tv_reward, listBean.getBonus());

        /**
         * 头像
         */
        GlideImageLoader.glideLoader(mContext, listBean.getAvatar(), (BGAImageView) viewHolderHelper.getView(R.id.img_user), 0);

        // 这里不知道当前图片的尺寸，加载成功后会乱跳
//        Glide.with(mContext).load(model.icon).placeholder(R.mipmap.staggered_holder).error(R.mipmap.staggered_holder).dontAnimate().into((ImageView) viewHolderHelper.getView(R.id.iv_item_staggered_icon));

    }

}