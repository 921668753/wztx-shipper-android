package com.ruitukeji.zwbh.main.cargoinformation;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface AddCargoInformationContract {

    interface Presenter extends BasePresenter {
        /**
         * 未读消息数量
         */
        void getUnRead();
    }

    interface View extends BaseNewView<Presenter, String> {
    }

}
