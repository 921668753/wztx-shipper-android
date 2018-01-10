package com.ruitukeji.zwbh.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideApp;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.mine.PersonalCenterBean;
import com.ruitukeji.zwbh.loginregister.LoginActivity;
import com.ruitukeji.zwbh.mine.abnormalrecords.AbnormalRecordsActivity;
import com.ruitukeji.zwbh.mine.addressmanagement.AddressManagementActivity;
import com.ruitukeji.zwbh.mine.complaintcenter.ComplaintCenterActivity;
import com.ruitukeji.zwbh.mine.drivermanagement.DriverManagementActivity;
import com.ruitukeji.zwbh.mine.helpcenter.HelpCenterActivity;
import com.ruitukeji.zwbh.mine.invitefriends.SharePoliteActivity;
import com.ruitukeji.zwbh.mine.invoicemanagement.InvoiceManagementActivity;
import com.ruitukeji.zwbh.mine.myorder.MyOrderActivity;
import com.ruitukeji.zwbh.mine.mywallet.MyWalletActivity;
import com.ruitukeji.zwbh.mine.personaldata.PersonalDataActivity;
import com.ruitukeji.zwbh.mine.setting.SettingsActivity;
import com.ruitukeji.zwbh.mine.shippercertification.ShipperCertificationActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.picturerelated.GlideCircleTransform;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.RefreshLayoutUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 个人中心------我的
 * Created by Administrator on 2017/12/13.
 */

@SuppressLint("NewApi")
public class PersonalCenterActivity extends BaseActivity implements PersonalCenterContract.View, View.OnScrollChangeListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(id = R.id.mRefreshLayout)
    private BGARefreshLayout mRefreshLayout;

    @BindView(id = R.id.sv_mine)
    private ScrollView sv_mine;

    /**
     * 标题
     */
    @BindView(id = R.id.titlebar)
    private BGATitleBar titlebar;

    /**
     * 资料
     */
    @BindView(id = R.id.ll_personalData, click = true)
    private LinearLayout ll_personalData;
    @BindView(id = R.id.ll_personalData1, click = true)
    private LinearLayout ll_personalData1;

    /**
     * 头像
     */
    @BindView(id = R.id.img_headPortrait)
    private ImageView img_headPortrait;
    @BindView(id = R.id.img_headPortrait1)
    private ImageView img_headPortrait1;

    /**
     * 姓名
     */
    @BindView(id = R.id.tv_name)
    private TextView tv_name;
    @BindView(id = R.id.tv_name1)
    private TextView tv_name1;

    /**
     * 未完成认证
     */
    @BindView(id = R.id.ll_incompleteCertification, click = true)
    private LinearLayout ll_incompleteCertification;
    @BindView(id = R.id.tv_incompleteCertification)
    private TextView tv_incompleteCertification;
    @BindView(id = R.id.ll_incompleteCertification1, click = true)
    private LinearLayout ll_incompleteCertification1;
    @BindView(id = R.id.tv_incompleteCertification1)
    private TextView tv_incompleteCertification1;

    @BindView(id = R.id.ll_order1)
    private LinearLayout ll_order1;

    /**
     * 我的订单
     */
    @BindView(id = R.id.ll_myOrder, click = true)
    private LinearLayout ll_myOrder;
    @BindView(id = R.id.ll_myOrder1, click = true)
    private LinearLayout ll_myOrder1;

    /**
     * 地址管理
     */
    @BindView(id = R.id.ll_addressManagement, click = true)
    private LinearLayout ll_addressManagement;
    @BindView(id = R.id.ll_addressManagement1, click = true)
    private LinearLayout ll_addressManagement1;

    /**
     * 司机管理
     */
    @BindView(id = R.id.ll_driverManagement, click = true)
    private LinearLayout ll_driverManagement;

    /**
     * 身份认证
     */
    @BindView(id = R.id.ll_identityAuthentication, click = true)
    private LinearLayout ll_identityAuthentication;
    @BindView(id = R.id.tv_identityAuthentication)
    private TextView tv_identityAuthentication;


    /**
     * 发票管理
     */
    @BindView(id = R.id.ll_invoiceManagement, click = true)
    private LinearLayout ll_invoiceManagement;

    @BindView(id = R.id.tv_dividerWidth)
    private TextView tv_dividerWidth;

    /**
     * 异常记录
     */
    @BindView(id = R.id.ll_abnormalRecords, click = true)
    private LinearLayout ll_abnormalRecords;

    /**
     * 我的钱包
     */
    @BindView(id = R.id.ll_myWallet, click = true)
    private LinearLayout ll_myWallet;

    /**
     * 邀请好友
     */
    @BindView(id = R.id.ll_inviteFriends, click = true)
    private LinearLayout ll_inviteFriends;

    /**
     * 投诉中心
     */
    @BindView(id = R.id.ll_complaintCenter, click = true)
    private LinearLayout ll_complaintCenter;

    /**
     * 帮助中心
     */
    @BindView(id = R.id.ll_helpCenter, click = true)
    private LinearLayout ll_helpCenter;

    /**
     * 设置中心
     */
    @BindView(id = R.id.ll_systemSettings, click = true)
    private LinearLayout ll_systemSettings;
    private Handler handler = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_personalcenter);
    }

    @Override
    public void initData() {
        super.initData();
        handler = new Handler();
        mPresenter = new PersonalCenterPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        RefreshLayoutUtil.initRefreshLayout(mRefreshLayout, this, this, false);
        ActivityTitleUtils.initToolbar(aty, getString(R.string.app_name), true, R.id.titlebar);
        titlebar.getTitleCtv().setTextColor(getResources().getColor(R.color.white));
        titlebar.setBackgroundResource(R.color.announcementCloseColors);
        titlebar.setLeftDrawable(R.mipmap.mine_back);
        sv_mine.setOnScrollChangeListener(this);
        int personalCenterNum = PreferenceHelper.readInt(aty, StringConstants.FILENAME, "personalCenterNum", 0);
        if (personalCenterNum == 0) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PreferenceHelper.write(aty, StringConstants.FILENAME, "personalCenterNum", personalCenterNum + 1);
                    sv_mine.scrollTo(0, 2);
                    mRefreshLayout.beginRefreshing();
                }
            }, 500);
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sv_mine.scrollTo(0, 2);
                readLocal();
            }
        }, 50);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_personalData:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(1);
                break;
            case R.id.ll_personalData1:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(1);
                break;
            case R.id.ll_incompleteCertification:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(2);
                break;
            case R.id.ll_incompleteCertification1:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(2);
                break;
            case R.id.ll_myOrder:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(3);
                break;
            case R.id.ll_myOrder1:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(3);
                break;
            case R.id.ll_addressManagement:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(4);
                break;
            case R.id.ll_addressManagement1:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(4);
                break;
            case R.id.ll_driverManagement:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(5);
                break;
            case R.id.ll_driverManagement1:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(5);
                break;
            case R.id.ll_identityAuthentication:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(2);
                break;
            case R.id.ll_invoiceManagement:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(6);
                break;
            case R.id.ll_abnormalRecords:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(7);
                break;
            case R.id.ll_myWallet:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(8);
                break;
            case R.id.ll_inviteFriends:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(9);
                break;
            case R.id.ll_complaintCenter:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(10);
                break;
            case R.id.ll_helpCenter:
                getSuccess("", 11);
                break;
            case R.id.ll_systemSettings:
                getSuccess("", 12);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isAvatar = PreferenceHelper.readBoolean(this, StringConstants.FILENAME, "isAvatar", false);
        if (isAvatar) {
            String avatar = PreferenceHelper.readString(aty, StringConstants.FILENAME, "avatar", "");
            GlideImageLoader.glideLoader(this, avatar + "?imageView2/1/w/70/h/70", img_headPortrait, 0);
            PreferenceHelper.write(this, StringConstants.FILENAME, "isAvatar", false);
        }
    }

    @Override
    public void setPresenter(PersonalCenterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            ll_incompleteCertification.setVisibility(View.VISIBLE);
            ll_incompleteCertification1.setVisibility(View.VISIBLE);
            mRefreshLayout.setPullDownRefreshEnable(true);
            PersonalCenterBean userInfoBean = (PersonalCenterBean) JsonUtil.getInstance().json2Obj(success, PersonalCenterBean.class);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "id", userInfoBean.getResult().getId());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "phone", userInfoBean.getResult().getPhone());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "sex", userInfoBean.getResult().getSex());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "avatar", userInfoBean.getResult().getAvatar());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "real_name", userInfoBean.getResult().getReal_name());
            String auth_status = userInfoBean.getResult().getAuth_status();
            // PreferenceHelper.write(this, StringConstants.FILENAME, "isAvatar", false);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "auth_status", auth_status);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "type", userInfoBean.getResult().getType());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "recomm_code", userInfoBean.getResult().getRecomm_code());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "bond_status", userInfoBean.getResult().getBond_status());
            PreferenceHelper.write(aty, StringConstants.FILENAME, "bond", userInfoBean.getResult().getBond());
            readLocal();
        } else if (flag == 1) {
            showActivity(aty, PersonalDataActivity.class);
        } else if (flag == 2) {
            String auth_status = PreferenceHelper.readString(this, StringConstants.FILENAME, "auth_status");
            if (auth_status != null && auth_status.equals("check")) {
                ViewInject.toast(getString(R.string.inAuthentication) + "," + getString(R.string.pleaseWait));
                return;
            }
            String type = PreferenceHelper.readString(aty, StringConstants.FILENAME, "type", "all");
            Intent intent = new Intent(aty, ShipperCertificationActivity.class);
            intent.putExtra("type", type);
            showActivity(aty, intent);
        } else if (flag == 3) {
            showActivity(aty, MyOrderActivity.class);
        } else if (flag == 4) {
            showActivity(aty, AddressManagementActivity.class);
        } else if (flag == 5) {
            showActivity(aty, DriverManagementActivity.class);
        } else if (flag == 6) {
            showActivity(aty, InvoiceManagementActivity.class);
        } else if (flag == 7) {
            showActivity(aty, AbnormalRecordsActivity.class);
        } else if (flag == 8) {
            showActivity(aty, MyWalletActivity.class);
        } else if (flag == 9) {
            showActivity(aty, SharePoliteActivity.class);
        } else if (flag == 10) {
            showActivity(aty, ComplaintCenterActivity.class);
        } else if (flag == 11) {
            showActivity(aty, HelpCenterActivity.class);
        } else if (flag == 12) {
            showActivity(aty, SettingsActivity.class);
        }
        dismissLoadingDialog();
    }


    /**
     * 读取本地
     */
    private void readLocal() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
//        float density = dm.density;
//        int width = dm.widthPixels;
        int height = dm.heightPixels;
        if (height >= sv_mine.getHeight() + 45) {
            tv_dividerWidth.setVisibility(View.GONE);
            ll_personalData1.setVisibility(View.GONE);
            ll_order1.setVisibility(View.GONE);
        }
        String accessToken = PreferenceHelper.readString(aty, StringConstants.FILENAME, "accessToken");
        if (StringUtils.isEmpty(accessToken)) {
            errorMsg(String.valueOf(NumericConstants.TOLINGIN), 0);
            return;
        }
        String avatar = PreferenceHelper.readString(aty, StringConstants.FILENAME, "avatar");
        if (StringUtils.isEmpty(avatar)) {
            img_headPortrait.setImageResource(R.mipmap.avatar);
            img_headPortrait1.setImageResource(R.mipmap.avatar);
        } else {
            GlideApp.with(this)
                    .load(avatar + "?imageView2/1/w/70/h/70")
                    .placeholder(R.mipmap.avatar)
                    .error(R.mipmap.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransform(this))
                    .dontAnimate()//没有任何淡入淡出效果
                    .into(img_headPortrait);
            GlideApp.with(this)
                    .load(avatar + "?imageView2/1/w/70/h/70")
                    .placeholder(R.mipmap.avatar)
                    .error(R.mipmap.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransform(this))
                    .dontAnimate()//没有任何淡入淡出效果
                    .into(img_headPortrait1);
        }
        tv_name.setVisibility(View.VISIBLE);
        tv_name1.setVisibility(View.VISIBLE);
        String real_name = PreferenceHelper.readString(aty, StringConstants.FILENAME, "real_name");
        String phone = PreferenceHelper.readString(aty, StringConstants.FILENAME, "phone");
        if (StringUtils.isEmpty(real_name)) {
            tv_name.setText(phone);
            tv_name1.setText(phone);
        } else {
            tv_name.setText(real_name);
            tv_name1.setText(real_name);
        }
        String auth_status = PreferenceHelper.readString(aty, StringConstants.FILENAME, "auth_status");
        if (auth_status != null && auth_status.equals("pass")) {
            tv_incompleteCertification.setText(getString(R.string.certified));
            tv_incompleteCertification1.setText(getString(R.string.certified));
            tv_identityAuthentication.setText(getString(R.string.certified));
        } else if (auth_status != null && auth_status.equals("check")) {
            tv_incompleteCertification.setText(getString(R.string.inAuthentication));
            tv_incompleteCertification1.setText(getString(R.string.inAuthentication));
            tv_identityAuthentication.setText(getString(R.string.inAuthentication));
        } else if (auth_status != null && auth_status.equals("refuse")) {
            tv_incompleteCertification.setText(getString(R.string.refuse));
            tv_incompleteCertification1.setText(getString(R.string.refuse));
            tv_identityAuthentication.setText(getString(R.string.refuse));
        } else {
            tv_incompleteCertification.setText(getString(R.string.incompleteCertification));
            tv_incompleteCertification1.setText(getString(R.string.incompleteCertification));
            tv_identityAuthentication.setText(getString(R.string.goCertification));
        }
    }


    @Override
    public void errorMsg(String msg, int flag) {
        PreferenceHelper.write(this, StringConstants.FILENAME, "isAvatar", false);
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            mRefreshLayout.setPullDownRefreshEnable(false);
            tv_name.setText(getString(R.string.loginregister));
            img_headPortrait.setImageResource(R.mipmap.avatar);
            ll_incompleteCertification.setVisibility(View.GONE);
            tv_name1.setText(getString(R.string.loginregister));
            img_headPortrait1.setImageResource(R.mipmap.avatar);
            ll_incompleteCertification1.setVisibility(View.GONE);
            if (flag != 0) {
                Intent intent = new Intent(aty, LoginActivity.class);
                intent.putExtra("type", "personalCenter");
                showActivity(aty, intent);
            }
            return;
        }
        mRefreshLayout.setPullDownRefreshEnable(true);
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }

    /**
     * 在接收消息的时候，选择性接收消息：
     */
    @Override
    public void callMsgEvent(MsgEvent msgEvent) {
        super.callMsgEvent(msgEvent);
        if (((String) msgEvent.getData()).equals("RxBusLoginEvent")) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sv_mine.scrollTo(0, 1);
                    mRefreshLayout.beginRefreshing();
                }
            }, 600);
        } else if (((String) msgEvent.getData()).equals("RxBusAvatarEvent")) {
            //  img_headPortrait.setImageURI(Uri.parse(msgEvent.getMsg() + "?imageView2/1/w/70/h/70"));
            //   GlideImageLoader.glideLoader(KJActivityStack.create().topActivity(), msgEvent.getMsg() + "?imageView2/1/w/70/h/70", img_headPortrait, 0);
        } else if (((String) msgEvent.getData()).equals("RxBusShipperCertificationEvent")) {
            tv_incompleteCertification.setText(getString(R.string.inAuthentication));
            tv_incompleteCertification1.setText(getString(R.string.inAuthentication));
            tv_identityAuthentication.setText(getString(R.string.inAuthentication));
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endRefreshing();
        showLoadingDialog(getString(R.string.dataLoad));
        ((PersonalCenterContract.Presenter) mPresenter).getInfo();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endLoadingMore();
        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Log.d("tag", "scrollY" + scrollY);
        Log.d("tag", "oldScrollY" + oldScrollY);
        if (scrollY == 0) {
            tv_dividerWidth.setVisibility(View.GONE);
            ll_personalData1.setVisibility(View.GONE);
            ll_order1.setVisibility(View.GONE);
        } else {
            tv_dividerWidth.setVisibility(View.VISIBLE);
            ll_personalData1.setVisibility(View.VISIBLE);
            ll_order1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
