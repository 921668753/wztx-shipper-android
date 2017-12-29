package com.ruitukeji.zwbh.main.announcement;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;
import com.ruitukeji.zwbh.common.BaseView;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface AnnouncementContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取公告通知详情
         */
        void getAnnouncement(int id);

    }

    interface View extends BaseNewView<Presenter, String> {

    }

}
