package com.ruitukeji.zwbh.mine.complaintcenter;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface ComplaintCenterContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取投诉中心信息
         */
        void getComplaintCenter(int page);

    }

    interface View extends BaseNewView<Presenter, String> {
//        /**
//         * http请求正确
//         * @param s
//         */
//        void getSuccess(String s);
//        /**
//         * http请求错误
//         */
//        void error(String msg);
    }

}
