package com.ruitukeji.zwbh.main.message;

import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.entity.main.message.SystemMessageBean.ResultBean.ListBean;
import com.ruitukeji.zwbh.retrofit.RequestClient;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/15.
 */

public class SystemMessagePresenter implements SystemMessageContract.Presenter {


    private SystemMessageContract.View mView;

    public SystemMessagePresenter(SystemMessageContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    /**
     * 获取消息列表
     *
     * @param page 页码
     */
    @Override
    public void getMessage(String type, int page) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
//        httpParams.put("push_type", type);
//        httpParams.put("page", page);
//        httpParams.put("pageSize", 20);
        RequestClient.getMessage(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg, 0);
            }
        });
    }

    /**
     * 删除消息
     */
    @Override
    public void postDeleteMessage(List<ListBean> masageList) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
        String msgStr = getMsgIdList(masageList);
        if (StringUtils.isEmpty(msgStr)) {
            mView.error(KJActivityStack.create().topActivity().getString(R.string.delete2), 1);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map map = new HashMap();
        map.put("msg_id", msgStr);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postDeleteMessage(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg, 1);
            }
        });
    }

    @Override
    public void postReadMessage(List<ListBean> masageList) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.dataLoad));
        String msgStr = getMsgIdList(masageList);
        if (StringUtils.isEmpty(msgStr)) {
            mView.error(KJActivityStack.create().topActivity().getString(R.string.markedRead1), 2);
            return;
        }
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        Map map = new HashMap();
        map.put("msg_id", msgStr);
        httpParams.putJsonParams(JsonUtil.getInstance().obj2JsonString(map).toString());
        RequestClient.postReadMessage(httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 2);
            }

            @Override
            public void onFailure(String msg) {
                mView.error(msg, 2);
            }
        });
    }

    /**
     * 获取标记的id
     */
    private String getMsgIdList(List<ListBean> masageList) {
        String msgIdStr = "";
        for (int i = 0; i < masageList.size(); i++) {
            if (masageList.get(i).getIsSelected() == 1) {
                msgIdStr = msgIdStr + "," + masageList.get(i).getId();
            }
        }
        if (StringUtils.isEmpty(msgIdStr)) {
            return "";
        }
        msgIdStr = msgIdStr.substring(1);
        return msgIdStr;
    }
}
