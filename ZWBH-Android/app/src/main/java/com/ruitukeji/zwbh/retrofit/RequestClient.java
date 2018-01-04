package com.ruitukeji.zwbh.retrofit;


import android.content.Context;

import com.kymjs.common.FileUtils;
import com.kymjs.common.Log;
import com.kymjs.common.NetworkUtils;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.constant.URLConstants;
import com.ruitukeji.zwbh.entity.BaseResult;
import com.ruitukeji.zwbh.entity.LoginBean;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.httputil.HttpRequest;
import com.ruitukeji.zwbh.utils.httputil.HttpUtilParams;
import com.ruitukeji.zwbh.utils.httputil.ResponseListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ruitukeji.zwbh.utils.httputil.HttpRequest.doFailure;
import static com.ruitukeji.zwbh.utils.httputil.HttpRequest.doSuccess;

/**
 * Created by ruitu on 2016/9/17.
 */

public class RequestClient {

    /**
     * @param httpParams 上传头像图片
     */
    public static void upLoadImg(HttpParams httpParams, int type, final ResponseListener<String> listener) {
//        for (int i = 0; i < files.size(); i++) {
//            File file = new File(files.get(i));
//            params.put("file" + i, file);
//        }
//        httpParams.putHeaders("Content-Type", "application/x-www-form-urlencoded");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //         PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                if (type == 0) {
                    HttpRequest.requestPostFORMHttp(URLConstants.UPLOADAVATAR, httpParams, listener);
                } else {
                    HttpRequest.requestPostFORMHttp(URLConstants.UPLOADQFCTIMG, httpParams, listener);
                }
            }
        }, listener);
    }


    /**
     * 上传图片
     */
    public static void upLoadImg(File file, String key, final ResponseListener<String> listener) {

        // String key = "<指定七牛服务上的文件名，或 null>";
        String token = " <从服务端SDK获取>";
        //new一个uploadManager类
//        UploadManager uploadManager = new UploadManager();
//        uploadManager.put(file, key, token,
//                new UpCompletionHandler() {
//                    @Override
//                    public void complete(String key, ResponseInfo info, JSONObject res) {
//                        //res包含hash、key等信息，具体字段取决于上传策略的设置
//                        if (info.isOK()) {
//                            Log.i("qiniu", "Upload Success");
//                        } else {
//                            Log.i("qiniu", "Upload Fail");
//                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
//                        }
//                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
//                    }
//                }, null);

    }


    /**
     * 刷新Token
     */
    public static void doRefreshToken(String refreshToken, TokenCallback callback, ResponseListener listener) {
        Log.d("tag", "doRefreshToken");
        Context context = KJActivityStack.create().topActivity();
        HttpParams params = HttpUtilParams.getInstance().getHttpParams();
        params.put("refreshToken", refreshToken);
        RxVolley.get(URLConstants.REFRESHTOKEN, params, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                BaseResult baseResult = (BaseResult) JsonUtil.getInstance().json2Obj(t, BaseResult.class);
                if (baseResult.getCode() != NumericConstants.SUCCESS) {
                    unDoList.clear();
                    isRefresh = false;
                    //    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    PreferenceHelper.write(context, StringConstants.FILENAME, "userId", 0);
                    PreferenceHelper.write(context, StringConstants.FILENAME, "accessToken", "");
                    PreferenceHelper.write(context, StringConstants.FILENAME, "refreshToken", "");
                    PreferenceHelper.write(context, StringConstants.FILENAME, "expireTime", "0");
                    PreferenceHelper.write(context, StringConstants.FILENAME, "timeBefore", "0");
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                LoginBean response1 = (LoginBean) JsonUtil.getInstance().json2Obj(t, LoginBean.class);
//                PreferenceHelper.write(context, StringConstants.FILENAME, "userId", response1.getResult().getUserId());
                PreferenceHelper.write(context, StringConstants.FILENAME, "accessToken", response1.getResult().getAccessToken());
                PreferenceHelper.write(context, StringConstants.FILENAME, "refreshToken", response1.getResult().getRefreshToken());
                PreferenceHelper.write(context, StringConstants.FILENAME, "expireTime", response1.getResult().getExpireTime() + "");
                PreferenceHelper.write(context, StringConstants.FILENAME, "timeBefore", System.currentTimeMillis() + "");
                for (int i = 0; i < unDoList.size(); i++) {
                    unDoList.get(i).execute();
                }
                unDoList.clear();
                isRefresh = false;
                callback.execute();
            }

            @Override
            public void onFailure(VolleyError error) {
                super.onFailure(error);
                Log.d("tag", "onSuccess");
                unDoList.clear();
                isRefresh = false;
                //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                PreferenceHelper.write(context, StringConstants.FILENAME, "userId", 0);
                PreferenceHelper.write(context, StringConstants.FILENAME, "accessToken", "");
                PreferenceHelper.write(context, StringConstants.FILENAME, "refreshToken", "");
                PreferenceHelper.write(context, StringConstants.FILENAME, "expireTime", "0");
                PreferenceHelper.write(context, StringConstants.FILENAME, "timeBefore", "0");
                listener.onFailure(NumericConstants.TOLINGIN + "");
            }
        });
    }

    /**
     * 应用配置参数
     */
    public static void getAppConfig(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestGetHttp(URLConstants.APPCONFIG, httpParams, listener);
    }

    /**
     * 获取文章内容
     */
    public static void getArticle(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestGetHttp(URLConstants.GETARTICLE, httpParams, listener);
    }

    /**
     * 登录
     */
    public static void postLogin(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestPostHttp(URLConstants.USERLOGIN, httpParams, listener);
    }

    /**
     * 第三方登录
     */
    public static void postThirdLogin(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestPostHttp(URLConstants.THIRDLOGIN, httpParams, listener);
    }

    /**
     * 第三方登录绑定手机号
     */
    public static void postThirdLoginAdd(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestPostHttp(URLConstants.THIRDLOGINADD, httpParams, listener);
    }

    /**
     * 发送验证码
     */
    public static void postCaptcha(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestPostHttp(URLConstants.SENDCAPTCHA, httpParams, listener);
    }

    /**
     * 注册
     */
    public static void postRegister(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestPostHttp(URLConstants.USERREG, httpParams, listener);
    }


    /**
     * 得到全部城市
     */
    public static void getAllCity(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestGetHttp(URLConstants.ALLCITY, httpParams, listener);
    }

    /**
     * 得到热门城市
     */
    public static void getHotCity(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestGetHttp(URLConstants.ALLHOTCITY, httpParams, listener);
    }


    /**
     * 个人认证----基本信息
     *
     * @param httpParams
     * @param listener
     */
    public static void postPersonalInformation(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "postPersonalInformation");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.PERSONAUTH, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 企业认证----企业信息
     *
     * @param httpParams
     * @param listener
     */
    public static void postEnterpriseInformation(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "postEnterpriseInformation");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //      PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.BUSINESSAUTH, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 重置密码
     */
    public static void postResetpwd(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestPostHttp(URLConstants.USERRESTPWD, httpParams, listener);
    }

    /**
     * 首页
     */
    public static void getHome(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getHome");
        String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
        if (!StringUtils.isEmpty(accessToken)) {
            httpParams.putHeaders("authorization-token", accessToken);
        }
        HttpRequest.requestGetHttp(URLConstants.HOME, httpParams, listener);
    }

    /**
     * 公告详情
     */
    public static void getAnnouncement(HttpParams httpParams, int id, final ResponseListener<String> listener) {
        Log.d("tag", "getAnnouncement");
        HttpRequest.requestGetHttp(URLConstants.ANNOUNCEMENT + "/" + id, httpParams, listener);
    }

    /**
     * 查询始发地目的地
     */
    public static void getAddress(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getAddress");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.INFOADDRESS, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 查询始发地目的地
     */
    public static void getDelAddress(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getDelAddress");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.DELADDRESS, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 是否默认地址
     */
    public static void getUpdateDefault(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getUpdateDefault");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.UPDATEDEFAULT, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 始发地目的地信息管理
     */
    public static void postAddress(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.ADDRESS, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取单条信息
     */
    public static void getOneInfoAddress(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.GETONEINFOADDRESS, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 修改始发地 目的地信息
     */
    public static void postUpdateAddress(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.UPDATEADDRESS, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 获取常用司机信息
     */
    public static void getDriverList(HttpParams httpParams, ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.GETDRIVERINFO, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 加入黑名单/移除黑名单
     */
    public static void postDriverBack(HttpParams httpParams, ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.DRIVERBACK, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 删除司机
     */
    public static void postDeleteDriver(HttpParams httpParams, ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.DELCOLLECTDRIVER, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取个人订单信息 发票
     */
    public static void getApplicationInvoiceList(HttpParams httpParams, ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.INVOICEGETGOODSINFO, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 申请个人和公司发票
     */
    public static void postApplicationInvoice(HttpParams httpParams, ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.INVOICEAPPLYINVOICE, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 开票记录
     */
    public static void getBillingRecordsList(HttpParams httpParams, ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.INVOICERECORD, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 开票详情
     */
    public static void getBillingDetails(HttpParams httpParams, ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.INVOICEREINFO, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 所含订单
     */
    public static void getContainsOrder(HttpParams httpParams, ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.CONTAINSORDER, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取周边司机
     * 经度
     * 纬度
     */
    public static void getNearbySearch(HttpParams httpParams, final ResponseListener<String> listener) {
        RxVolley.get(URLConstants.MAINNEARBYSEARCH, httpParams, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                listener.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                doFailure(errorNo, strMsg, listener);
            }
        });
    }


    /**
     * 获取驾车距离
     */
    public static void getDistance(HttpParams httpParams, final ResponseListener<String> listener) {
        RxVolley.get(URLConstants.DISTANCE, httpParams, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                listener.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                doFailure(errorNo, strMsg, listener);
            }
        });
    }

    /**
     * 获取消息列表
     */
    public static void getMessage(HttpParams httpParams, final ResponseListener<String> listener) {
        String accessToken = PreferenceHelper.readString(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "accessToken");
        if (!StringUtils.isEmpty(accessToken)) {
            httpParams.putHeaders("authorization-token", accessToken);
        }
        HttpRequest.requestGetHttp(URLConstants.MESSAGE, httpParams, listener);
    }

    /**
     * 删除消息
     */
    public static void postDeleteMessage(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.DELMESSAGE, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 标记已读消息
     */
    public static void postReadMessage(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    // PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.READMESSAGE, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取未读消息数量
     */
    public static void getUnRead(HttpParams httpParams, final ResponseListener<String> listener) {
        String accessToken = PreferenceHelper.readString(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "accessToken");
        if (!StringUtils.isEmpty(accessToken)) {
            httpParams.putHeaders("authorization-token", accessToken);
        }
        HttpRequest.requestGetHttp(URLConstants.GETUNREAD, httpParams, listener);
    }

    /**
     * 获取消息详情
     */
    public static void getMessageDetails(HttpParams httpParams, final ResponseListener<String> listener) {
        String accessToken = PreferenceHelper.readString(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "accessToken");
        if (!StringUtils.isEmpty(accessToken)) {
            httpParams.putHeaders("authorization-token", accessToken);
        }
        HttpRequest.requestGetHttp(URLConstants.MESSAGEDETAIL + "/706", httpParams, listener);
    }

    /**
     * 获取车长车型
     */
    public static void getConductorModels(HttpParams httpParams, final ResponseListener<String> listener) {
        HttpRequest.requestGetHttp(URLConstants.GETALLCARSTYLE, httpParams, listener);
    }

    /**
     * 同城 长途  提交订单
     */
    public static void postLogistics(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "postLogistics");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.ORDERADD, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 派单给司机
     */
    public static void postSendOrder(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "postSendOrder");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.SENDORDER, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 获取用户信息
     */
    public static void getInfo(HttpParams httpParams, ResponseListener<String> listener) {
        Log.d("tag", "getInfo");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.USERINFO, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 显示货源列表
     */
    public static void getGoodsList(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getGoodsList");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.GOODSLIST, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 货源详情功能
     */
    public static void getGoodDetail(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getGoodDetail");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostFORMHttp(URLConstants.GOODDETAIL, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 取消货源功能
     */
    public static void postCancelGoods(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "postCancelGoods");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.CANCELGOODS, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 更新用户信息
     */
    public static void putInfo(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getInfo");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //  PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.UPDATEINFO, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 改变广告状态
     */
    public static void postChangeAd(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "postChangeAd");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.CHANGEAD, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取广告状态
     */
    public static void getIsAd(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getIsAd");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.ISAD, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 获取个人认证
     */
    public static void getPersonalCertificate(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getPersonalCertificate");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //  PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.GETPERSONAUTHINFO, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取企业公司认证信息
     */
    public static void getCompanyAuthInfo(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getPersonalCertificate");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.GETCOMPANYAUTHINFO, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 显示订单列表
     */
    public static void getOrderList(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getOnlineService");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.SHOWORDERLIST, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 显示订单详情
     */
    public static void getOrderInfo(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getOrderInfo");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.SHOWORDERINFO, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 查看凭证
     */
    public static void getShowCerPic(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getShowCerPic");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.SHOWCERPIC, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取微信支付参数
     */
    public static void getWxPay(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getWxPay");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //  PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.WXPAY, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 通过余额支付
     */
    public static void postScorePay(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "postScorePay");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //  PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.SCOREPAY, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取支付宝支付参数
     */
    public static void getAlipay(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getAlipay");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.ALIPAY, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 上传支付凭证
     */
    public static void uploadCerPic(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "uploadCerPic");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //     PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.UPLOADCERPIC, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 显示司机报价列表
     */
    public static void getShowDriverQuoteList(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getShowDriverQuoteList");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.SHOWDRIVERQUOTELIST, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 提交司机报价
     */
    public static void sendDriverPrice(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "sendDriverPrice");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //  PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.SENDDRIVERPRICE, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 我的钱包
     */
    public static void getPay(HttpParams httpParams, final ResponseListener<String> listener) {
        Log.d("tag", "getPay");
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //   PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.PAY, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 提现记录
     */
    public static void showCashRecord(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.SHOWCASHRECORD, httpParams, listener);
            }
        }, listener);

    }

    /**
     * 显示我的推荐列表
     */
    public static void showMyRecommList(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //     PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.SHOWMYRECOMMLIST, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 余额提现
     */
    public static void postWithdrawal(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //     PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.APIURL, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 充值
     */
    public static void postRecharge(HttpParams httpParams, int type, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //       PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                if (type == 1) {
                    HttpRequest.requestPostHttp(URLConstants.RECHARGEBYALIPAY, httpParams, listener);
                } else if (type == 2) {
                    HttpRequest.requestPostHttp(URLConstants.RECHARGEBYWEXIN, httpParams, listener);
                }
            }
        }, listener);
    }

    /**
     * 查看账单
     */
    public static void getPayRecord(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //    PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.SHOWPAYRECORD, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 缴纳保证金
     */
    public static void getPayBond(HttpParams httpParams, int way, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //         PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                if (way == 1) {//支付宝
                    HttpRequest.requestGetHttp(URLConstants.ALIPAYCASH, httpParams, listener);
                } else if (way == 2) {//微信
                    HttpRequest.requestGetHttp(URLConstants.PAYBOND1, httpParams, listener);
                }
            }
        }, listener);
    }

    /**
     * 充值记录
     */
    public static void getPrepaidPhoneRecords(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //        PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.RECHARGERECORD, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取评价详情
     */
    public static void getEvaluationShare(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //       PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.COMMENTINFO, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 发送评价详情
     */
    public static void postEvaluationShare(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //        PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.SENDCOMMENTINFO, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 获取订单轨迹信息
     *
     * @param httpParams 提交参数
     * @param listener   回调
     */
    public static void getTrajectory(HttpParams httpParams, final ResponseListener<String> listener) {
        RxVolley.get(URLConstants.LOGISTICSPOSITIONING, httpParams, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                listener.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                doFailure(errorNo, strMsg, listener);
            }
        });
    }


    /**
     * 帮助中心
     */
    public static void getHelpCenter(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.SHOWMYRECOMMLIST, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 帮助中心详情
     */
    public static void getHelpCenterDetails(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestGetHttp(URLConstants.SHOWMYRECOMMLIST, httpParams, listener);
            }
        }, listener);
    }


    /**
     * 修改密码
     */
    public static void postChangePassword(HttpParams httpParams, final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //       PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                httpParams.putHeaders("authorization-token", accessToken);
                HttpRequest.requestPostHttp(URLConstants.UPDATEPWD, httpParams, listener);
            }
        }, listener);
    }

    /**
     * 下载App
     */
    @SuppressWarnings("unchecked")
    public static void downloadApp(String updateAppUrl, ProgressListener progressListener, final ResponseListener<String> listener) {
        RxVolley.download(FileUtils.getSaveFolder(StringConstants.DOWNLOADPATH).getAbsolutePath() + "/WZTXH.apk", updateAppUrl, progressListener, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                BaseResult result = new BaseResult();
                result.setResult(FileUtils.getSaveFolder(StringConstants.DOWNLOADPATH).getAbsolutePath() + "/WZTXH.apk");
                result.setMsg("SUCCESS");
                result.setCode(NumericConstants.SUCCESS);
                doSuccess(JsonUtil.getInstance().obj2JsonString(result), listener);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Log.d(errorNo + "====failure" + strMsg);
                doFailure(errorNo, strMsg, listener);
            }
        });
    }


    /**
     * 是否登录
     */
    public static void isLogin(final ResponseListener<String> listener) {
        doServer(new TokenCallback() {
            @Override
            public void execute() {
                String accessToken = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    //         PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
                    listener.onFailure(NumericConstants.TOLINGIN + "");
                    return;
                }
                listener.onSuccess("");
            }
        }, listener);
    }


    /**
     * 刷新token回调
     */
    public static boolean isRefresh = false;

    public static void doServer(final TokenCallback callback, ResponseListener listener) {
        final Context context = KJActivityStack.create().topActivity();
        if (!NetworkUtils.isNetWorkAvailable(context)) {
            doFailure(-1, "NetWork err", listener);
            return;
        }
        Log.d("tag", "isNetWorkAvailable" + true);
        String accessToken = PreferenceHelper.readString(context, StringConstants.FILENAME, "accessToken", "");
        if (StringUtils.isEmpty(accessToken)) {
            Log.d("tag", "onFailure");
            //     PreferenceHelper.write(KJActivityStack.create().topActivity(), StringConstants.FILENAME, "isGoneBanner", false);
            PreferenceHelper.write(context, StringConstants.FILENAME, "userId", 0);
            PreferenceHelper.write(context, StringConstants.FILENAME, "accessToken", "");
            PreferenceHelper.write(context, StringConstants.FILENAME, "refreshToken", "");
            PreferenceHelper.write(context, StringConstants.FILENAME, "expireTime", "0");
            PreferenceHelper.write(context, StringConstants.FILENAME, "timeBefore", "0");
            listener.onFailure(NumericConstants.TOLINGIN + "");
            return;
        }
        long nowTime = System.currentTimeMillis();
//        String timebefore = PreferenceHelper.readString(context, StringConstants.FILENAME, "timebefore", "0");
//        long timebefore1 = 0;
//        if (StringUtils.isEmpty(timebefore)) {
//            timebefore1 = 0;
//        } else {
//            timebefore1 = Long.decode(timebefore);
//        }
        String expireTime = PreferenceHelper.readString(context, StringConstants.FILENAME, "expireTime");
        long expireTime1 = 0;
        if (StringUtils.isEmpty(expireTime)) {
            expireTime1 = 0;
        } else {
            expireTime1 = Long.decode(expireTime);
        }
        long refreshTime = nowTime - expireTime1 * 1000 - 2000;
        if (refreshTime >= 0) {
            if (isRefresh) {
                unDoList.add(callback);
                return;
            }
            isRefresh = true;
            String refreshToken = PreferenceHelper.readString(context, StringConstants.FILENAME, "refreshToken");
            doRefreshToken(refreshToken, callback, listener);
        } else {
            Log.d("tag", "onSuccess");
            callback.execute();
        }
    }

    public interface TokenCallback {
        void execute();
    }

    private static List<TokenCallback> unDoList = new ArrayList<>();

}
