package com.ruitukeji.zwbh.main.cargoinformation;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.BuildConfig;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

/**
 * Created by Administrator on 2017/2/15.
 */

public class AddCargoInformationPresenter implements AddCargoInformationContract.Presenter {

    private AddCargoInformationContract.View mView;

    public AddCargoInformationPresenter(AddCargoInformationContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    /**
     * * 获取驾车距离
     *
     * @param origins     出发点
     * @param destination 目的地
     */
    @Override
    public void getDistance(String origins, String destination) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("key", BuildConfig.GAODE_WEBKEY);
        httpParams.put("origins", origins);
        httpParams.put("destination", destination);
        RequestClient.getDistance(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

    @Override
    public void getAddCargoInformation() {

    }


//    @Override
//    public void getAddCargoInformation() {
//        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//        RequestClient.getUnRead(httpParams, new ResponseListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                mView.getSuccess(response, 0);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mView.errorMsg(msg, 0);
//            }
//        });
//    }


}
