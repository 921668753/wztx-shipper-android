package com.ruitukeji.zwbh.adapter.mine.complaintcenter;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.RecommendedRecordBean.ResultBean.ListBean;

import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * 投诉中心适配器
 * Created by Administrator on 2017/2/13.
 */

public class ComplaintCenterViewAdapter extends BGAAdapterViewAdapter<ListBean> {

    public ComplaintCenterViewAdapter(Context context) {
        super(context, R.layout.item_complaintcenter);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListBean listBean) {

        /**
         * 投诉时间
         */
        viewHolderHelper.setText(R.id.tv_complaintsTime, listBean.getBonus() + "");

        /**
         * 姓名
         */
        viewHolderHelper.setText(R.id.tv_complaintsAboutDriver, listBean.getBonus() + "");

        /**
         * 车牌号
         */
        viewHolderHelper.setText(R.id.tv_licensePlateNumber, listBean.getBonus() + "");

        /**
         * 投诉原因
         */
        viewHolderHelper.setText(R.id.tv_complaintsReasons, listBean.getBonus() + "");

        /**
         * 处理结果
         */
        viewHolderHelper.setText(R.id.tv_processingResult, listBean.getBonus() + "");
    }

}