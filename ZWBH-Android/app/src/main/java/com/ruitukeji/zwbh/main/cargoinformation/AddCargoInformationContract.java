package com.ruitukeji.zwbh.main.cargoinformation;

import com.ruitukeji.zwbh.common.BaseNewView;
import com.ruitukeji.zwbh.common.BasePresenter;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface AddCargoInformationContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取驾车距离
         * http://lbs.amap.com/api/webservice/guide/api/direction#driving
         */
        void getDistance(String origins, String destination);

        /**
         * 检验添加货物信息参数是否正确
         */
        void getAddCargoInformation();

    }

    interface View extends BaseNewView<Presenter, String> {
    }

}
