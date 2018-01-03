package com.ruitukeji.zwbh.main.selectaddress.selectioncity;

import android.content.Context;

import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.entity.main.selectaddress.InlandBean;
import com.ruitukeji.zwbh.entity.main.selectaddress.InlandHotCityBean;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.GetJsonDataUtil;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.entity.main.selectaddress.InlandHotCityBean.ResultBean;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by ruitu on 2016/9/24.
 */

public class InlandPresenter implements InlandContract.Presenter {
    private InlandContract.View mView;


    public InlandPresenter(InlandContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getAllCity(Context context) {
        String JsonData = null;
        JsonData = new GetJsonDataUtil().getJson(context, "chinacity.json");//获取assets目录下的json文件数据
        mView.getSuccess(JsonData, 0);
    }

    @Override
    public void getChildHotCity(Context context) {
        String JsonData = null;
        JsonData = new GetJsonDataUtil().getJson(context, "chinahotcity.json");//获取assets目录下的json文件数据
        mView.getSuccess(JsonData, 1);
    }

    @Override
    public ArrayList<InlandBean> parseData(String result) {//Gson 解析
        ArrayList<InlandBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            for (int i = 0; i < data.length(); i++) {
                InlandBean entity = (InlandBean) JsonUtil.getInstance().json2Obj(data.optJSONObject(i).toString(), InlandBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    public ArrayList<ResultBean> parseDataHot(String result) {

        ArrayList<ResultBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            for (int i = 0; i < data.length(); i++) {
                ResultBean entity = (ResultBean) JsonUtil.getInstance().json2Obj(data.optJSONObject(i).toString(), ResultBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    @Override
    public void getAllCity() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getAllCity(httpParams, new ResponseListener<String>() {
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
    public void getChildHotCity() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getHotCity(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }
}
