package com.ruitukeji.zwbh.adapter.mine.complaintcenter;

import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.entity.mine.complaintcenter.ComplaintCenterBean.ResultBean.ListBean;

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
        viewHolderHelper.setText(R.id.tv_complaintsTime, listBean.getTime());

        /**
         * 姓名
         */
        viewHolderHelper.setText(R.id.tv_complaintsAboutDriver, listBean.getDr_name() );

        /**
         * 车牌号
         */
        viewHolderHelper.setText(R.id.tv_licensePlateNumber, listBean.getCard_number() );

        /**
         * 投诉原因
         */
        viewHolderHelper.setText(R.id.tv_complaintsReasons, listBean.getReason() );

        /**
         * 处理结果
         */
        viewHolderHelper.setText(R.id.tv_processingResult, listBean.getProcessing_result());
    }

}