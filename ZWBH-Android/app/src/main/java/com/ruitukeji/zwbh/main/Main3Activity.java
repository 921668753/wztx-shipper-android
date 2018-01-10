package com.ruitukeji.zwbh.main;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BaseFragment;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.dialog.LocationBouncedDialog;
import com.ruitukeji.zwbh.entity.BaseResult;
import com.ruitukeji.zwbh.entity.main.HomeBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.main.announcement.AnnouncementActivity;
import com.ruitukeji.zwbh.main.fragment.IntercityFragment;
import com.ruitukeji.zwbh.main.fragment.SameCityFragment;
import com.ruitukeji.zwbh.main.message.SystemMessageActivity;
import com.ruitukeji.zwbh.mine.PersonalCenterActivity;
import com.ruitukeji.zwbh.utils.FileNewUtil;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.sunfusheng.marqueeview.MarqueeView;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class Main3Activity extends BaseActivity implements MainContract.View {

    /**
     * 个人中心
     */
    @BindView(id = R.id.img_user, click = true)
    private ImageView img_user;

    /**
     * 消息中心
     */
    @BindView(id = R.id.img_message, click = true)
    private ImageView img_message;
    @BindView(id = R.id.tv_message)
    public TextView tv_message;


    /**
     * 同城物流
     */
    @BindView(id = R.id.ll_cityDistribution, click = true)
    private LinearLayout ll_cityDistribution;
    @BindView(id = R.id.tv_cityDistribution)
    private TextView tv_cityDistribution;
    @BindView(id = R.id.tv_cityDistribution1)
    private TextView tv_cityDistribution1;
    /**
     * 全国物流
     */
    @BindView(id = R.id.ll_longTrunk, click = true)
    private LinearLayout ll_longTrunk;
    @BindView(id = R.id.tv_longTrunk)
    private TextView tv_longTrunk;
    @BindView(id = R.id.tv_longTrunk1)
    private TextView tv_longTrunk1;
    /**
     * 通知
     */
    @BindView(id = R.id.ll_ad)
    private LinearLayout ll_ad;
    @BindView(id = R.id.marqueeView)
    private MarqueeView marqueeView;


    private boolean isFirst = false;
    private LocationBouncedDialog locationBouncedDialog;
    private long firstTime = 0;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private BaseFragment contentFragment;
    private BaseFragment contentFragment1;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main2);
    }

    @Override
    public void initData() {
        super.initData();
        initDialog();
        contentFragment = new SameCityFragment();
        contentFragment1 = new IntercityFragment();
        mPresenter = new MainPresenter(this);
        ((MainContract.Presenter) mPresenter).getHome();
        registerMessageReceiver();
    }

    private void initDialog() {
        isFirst = getIntent().getBooleanExtra("isFirst", false);
        locationBouncedDialog = new LocationBouncedDialog(isFirst, this);
        locationBouncedDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
        locationBouncedDialog.show();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ((MainContract.Presenter) mPresenter).chooseLogisticsType(this, 0, tv_cityDistribution, tv_cityDistribution1, tv_longTrunk, tv_longTrunk1);
        changeFragment(contentFragment);
    }

    public void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(R.id.main_content, targetFragment);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.img_user:
                showActivity(aty, PersonalCenterActivity.class);
                break;
            case R.id.img_message:
                showActivity(aty, SystemMessageActivity.class);
                tv_message.setVisibility(View.GONE);
                break;
            case R.id.ll_cityDistribution:
                ((MainContract.Presenter) mPresenter).chooseLogisticsType(this, 0, tv_cityDistribution, tv_cityDistribution1, tv_longTrunk, tv_longTrunk1);
                changeFragment(contentFragment);
                break;
            case R.id.ll_longTrunk:
                ((MainContract.Presenter) mPresenter).chooseLogisticsType(this, 1, tv_cityDistribution, tv_cityDistribution1, tv_longTrunk, tv_longTrunk1);
                changeFragment(contentFragment1);
                break;
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    /**
     * 退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    //如果两次按键时间间隔大于2秒，则不退出
                    ViewInject.toast(getString(R.string.exitProcedureAgain));
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    //  int i = 1 / 0;
                    //   KjBitmapUtil.getInstance().getKjBitmap().cleanCache();
                    MobclickAgent.onProfileSignOff();//关闭账号统计     退出登录也加
                    JPushInterface.stopCrashHandler(getApplication());//JPush关闭CrashLog上报
                    MobclickAgent.onKillProcess(aty);
                    KJActivityStack.create().appExit(aty);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 1) {
            if (!(success.equals("true"))) {
                return;
            }
            String updateAppUrl = PreferenceHelper.readString(this, StringConstants.FILENAME, "updateAppUrl", null);
            if (StringUtils.isEmpty(updateAppUrl)) {
                return;
            }
        } else if (flag == 2) {
            BaseResult baseResult = (BaseResult) JsonUtil.getInstance().json2Obj(success, BaseResult.class);
            if ((String) baseResult.getResult() == null) {
                return;
            }
            File path = new File((String) baseResult.getResult());
            FileNewUtil.installApkFile(this, path.getAbsolutePath());
            dismissLoadingDialog();
        } else if (flag == 3) {
            HomeBean homeBean = (HomeBean) JsonUtil.getInstance().json2Obj(success, HomeBean.class);
            if (homeBean.getResult().getUnreadMsg() == null || homeBean.getResult().getUnreadMsg().getMsgX() == 0) {
                tv_message.setVisibility(View.GONE);
            } else {
                tv_message.setVisibility(View.VISIBLE);
                String accessToken = PreferenceHelper.readString(this, StringConstants.FILENAME, "accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    tv_message.setVisibility(View.GONE);
                }
                tv_message.setText(String.valueOf(homeBean.getResult().getUnreadMsg().getMsgX()));
            }
            HomeBean.ResultBean.StartAddressBean start_address = homeBean.getResult().getStart_address();
            if (start_address != null && start_address.getCity() != null) {
                PreferenceHelper.write(this, StringConstants.FILENAME, "isDefaultAddress", true);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceLat", start_address.getAddress_maps().split(",")[1]);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceLongi", start_address.getAddress_maps().split(",")[0]);
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceDistrict", start_address.getCity());
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenancePlaceName", start_address.getAddress_name());
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceDetailedAddress", start_address.getAddress_detail());
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceDeliveryCustomer", start_address.getClient());
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceShipper", start_address.getClient_name());
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenancePhone", start_address.getPhone());
                PreferenceHelper.write(this, StringConstants.FILENAME, "provenanceEixedTelephone", start_address.getTelphone());
            }
            HomeBean.ResultBean.EndAddressBean end_address = homeBean.getResult().getEnd_address();
            if (end_address != null && end_address.getCity() != null) {
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationLat", end_address.getAddress_maps().split(",")[1]);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationLongi", end_address.getAddress_maps().split(",")[0]);
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationDistrict", end_address.getCity());
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationPlaceName", end_address.getAddress_name());
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationDetailedAddress", end_address.getAddress_detail());
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationDeliveryCustomer", end_address.getClient());
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationShipper", end_address.getClient_name());
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationPhone", end_address.getPhone());
                PreferenceHelper.write(this, StringConstants.FILENAME, "destinationEixedTelephone", end_address.getTelphone());
            }
            processLogic(homeBean.getResult().getList());
            dismissLoadingDialog();
        }
    }

    /**
     * 广告轮播图
     */
    List<String> tips = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private void processLogic(List<HomeBean.ResultBean.ListBean> list) {
        if (list == null || list.size() == 0) {
            ll_ad.setVisibility(View.GONE);
            return;
        }
        tips.clear();
        for (int i = 0; i < list.size(); i++) {
            tips.add(list.get(i).getAd_content());
        }
        marqueeView.startWithList(tips);
// 在代码里设置自己的动画
        //   marqueeView.startWithList(tips, R.anim.anim_bottom_in, R.anim.anim_top_out);
        ll_ad.setVisibility(View.VISIBLE);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Intent intent = new Intent(aty, AnnouncementActivity.class);
                intent.putExtra("id", list.get(position).getId());
                showActivity(aty, intent);
            }
        });
    }

    @Override
    public void errorMsg(String msg, int flag) {
        if (flag == 0) {
            ViewInject.toast(msg);
        } else if (flag == 1) {
        } else if (flag == 2) {
        } else if (flag == 3) {
            ((MainContract.Presenter) mPresenter).getHome();
        } else if (flag == 5 || flag == 6 || flag == 7 || flag == 8 || flag == 9 || flag == 10 || flag == 11 || flag == 12) {
            if (msg.equals("" + NumericConstants.TOLINGIN)) {
                showActivity(aty, LoginActivity.class);
            }
        }
        dismissLoadingDialog();
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        Intent intent = registerReceiver(mMessageReceiver, filter);
        //极光推送 定制声音、震动、闪灯等 Notification 样式。
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
//        builder.statusBarDrawable = R.mipmap.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);
    }


    /**
     * 关闭弹框
     */
    public void dismissDialog() {
        //实现页面跳转
        if (isFirst) {
            return;
        }
        if (locationBouncedDialog != null && locationBouncedDialog.isShowing()) {
            locationBouncedDialog.cancel();
        }
    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!StringUtils.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                //  setCostomMsg(showMsg.toString());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK) {
//            /**
//             * 选择始发地页面返回
//             */
//            isProvenance = 1;
//            provenanceLat = data.getStringExtra("lat");
//            provenanceLongi = data.getStringExtra("longi");
//            provenanceDistrict = data.getStringExtra("district");
//            provenancePlaceName = data.getStringExtra("placeName");
//            provenanceDetailedAddress = data.getStringExtra("detailedAddress");
//            provenanceDeliveryCustomer = data.getStringExtra("deliveryCustomer");
//            provenanceShipper = data.getStringExtra("shipper");
//            provenancePhone = data.getStringExtra("phone");
//            isOff = data.getIntExtra("isOff1", 0);
//            provenanceEixedTelephone = data.getStringExtra("eixedTelephone");
//            tv_pleaseEnterDeparturePoint.setText(provenancePlaceName);
//            if (tran_type == 0) {
//                int orgprovince = provenancePlaceName.indexOf("省");
//                int orgcity = provenancePlaceName.indexOf("市");
//                if (orgprovince != -1 && orgcity != -1) {
//                    province = provenancePlaceName.substring(0, orgprovince + 1);
//                    city = provenancePlaceName.substring(orgprovince + 1, orgcity + 1);
//                }
//            }
//        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW && resultCode == RESULT_OK) {
//            /**
//             * 选择目的地页面返回
//             */
//            isDestination = 1;
//            destinationLat = data.getStringExtra("lat");
//            destinationLongi = data.getStringExtra("longi");
//            destinationDistrict = data.getStringExtra("district");
//            destinationPlaceName = data.getStringExtra("placeName");
//            destinationDetailedAddress = data.getStringExtra("detailedAddress");
//            destinationDeliveryCustomer = data.getStringExtra("deliveryCustomer");
//            destinationShipper = data.getStringExtra("shipper");
//            destinationPhone = data.getStringExtra("phone");
//            isOff1 = data.getIntExtra("isOff1", 0);
//            destinationEixedTelephone = data.getStringExtra("eixedTelephone");
//            tv_enterDestination.setText(destinationPlaceName);
//        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW1 && resultCode == RESULT_OK) {
//            /**
//             * 目的地信息
//             */
//            cleanDestination();
//        }
    }

}