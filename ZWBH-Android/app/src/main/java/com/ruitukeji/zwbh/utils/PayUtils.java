package com.ruitukeji.zwbh.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.mywallet.recharge.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * Created by Administrator on 2017/2/17.
 */

public class PayUtils {

    private final Activity context;
    private final Class<?> cls;


    public PayUtils(Activity context, Class<?> cls) {
        this.context = context;
        this.cls = cls;

    }

    private static final int SDK_PAY_FLAG = 1;

    /**
     * 支付宝
     *
     * @param orderParam
     */

    public void doPay(String orderParam) {
        final String payInfo = orderParam;
        Log.d("payInfo", payInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(context);
                // 调用支付接口
                Map<String, String> result = alipay.payV2(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            String result = msg.obj.toString();
            Log.d("payInfo", result);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (!StringUtils.isEmpty(result)) {
                        if (TextUtils.equals(resultStatus, "9000")) {// 操作成功
                            PreferenceHelper.write(context, StringConstants.FILENAME, "payClass", context.getClass().getName());
                            context.startActivity(new Intent(context, cls));
                        } else if (TextUtils.equals(resultStatus, "4000")) {// 系统异常
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_system_exception));
                        } else if (TextUtils.equals(resultStatus, "4001")) {// 数据格式不正确
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_system_exception));
                        } else if (TextUtils.equals(resultStatus, "4003")) {// 该用户绑定的支付宝账户被冻结或不允许支付
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_account_exception));
                        } else if (TextUtils.equals(resultStatus, "4004")) {// 该用户已解除绑定
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_has_unbound));
                        } else if (TextUtils.equals(resultStatus, "4005")) {// 绑定失败或没有绑定
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_system_exception));
                        } else if (TextUtils.equals(resultStatus, "4006")) {// 订单支付失败
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_order_error));
                        } else if (TextUtils.equals(resultStatus, "4010")) {// 重新绑定账户
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_system_exception));
                        } else if (TextUtils.equals(resultStatus, "6000")) {// 支付服务正在进行升级操作
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_system_exception));
                        } else if (TextUtils.equals(resultStatus, "6001")) {// 用户中途取消支付操作
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_order_cancel));
                        } else if (TextUtils.equals(resultStatus, "6002")) {// 网络连接异常
                            ViewInject.toast(MyApplication.getContext().getString(R.string.pay_network));
                        } else if (TextUtils.equals(resultStatus, "7001")) {// 网页支付失败
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_order_error));
                        } else if (TextUtils.equals(resultStatus, "8000")) {// 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_system_exception));
                        } else {
                            ViewInject.toast(MyApplication.getContext().getString(R.string.pay_error));
                        }
                    } else {
                        ViewInject.toast(MyApplication.getContext().getString(R.string.alipay_system_exception));
                    }
                    break;
            }
        }
    };

    /**
     * 微信支付
     *
     * @param appId
     * @param partnerId
     * @param prepayId
     * @param packages
     * @param nonceStr
     * @param timeStamp
     * @param paySign
     */
    public void doPayment(String appId, String partnerId, String prepayId, String packages, String nonceStr, String timeStamp, String paySign) {
        PreferenceHelper.write(context, StringConstants.FILENAME, "payClass", context.getClass().getName());
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, appId);
        if (msgApi.isWXAppInstalled() && msgApi.isWXAppSupportAPI()) {
            msgApi.registerApp(appId);
            PayReq request = new PayReq();
            request.appId = appId;
            request.partnerId = partnerId;
            request.prepayId = prepayId;
            request.packageValue = packages;
            request.nonceStr = nonceStr;
            request.timeStamp = timeStamp;
            request.sign = paySign;
            msgApi.sendReq(request);
        } else {
            ViewInject.toast(MyApplication.getContext().getString(R.string.wxappinstalled));
        }
    }
}
