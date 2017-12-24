package com.ruitukeji.zwbh.mine;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.GlideApp;
import com.ruitukeji.zwbh.common.GlideImageLoader;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.NumericConstants;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.entity.UploadImageBean;
import com.ruitukeji.zwbh.entity.UserInfoBean;
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
import com.ruitukeji.zwbh.utils.GlideCircleTransform;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.rx.MsgEvent;

import java.net.URL;

import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 个人中心------我的
 * Created by Administrator on 2017/12/13.
 */

public class PersonalCenterActivity extends BaseActivity implements PersonalCenterContract.View {

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

    /**
     * 头像
     */
    @BindView(id = R.id.img_headPortrait)
    private ImageView img_headPortrait;

    /**
     * 姓名
     */
    @BindView(id = R.id.tv_name)
    private TextView tv_name;

    /**
     * 未完成认证
     */
    @BindView(id = R.id.ll_incompleteCertification, click = true)
    private LinearLayout ll_incompleteCertification;
    @BindView(id = R.id.tv_incompleteCertification)
    private TextView tv_incompleteCertification;

    /**
     * 我的订单
     */
    @BindView(id = R.id.ll_myOrder, click = true)
    private LinearLayout ll_myOrder;

    /**
     * 地址管理
     */
    @BindView(id = R.id.ll_addressManagement, click = true)
    private LinearLayout ll_addressManagement;

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

    /**
     * 发票管理
     */
    @BindView(id = R.id.ll_invoiceManagement, click = true)
    private LinearLayout ll_invoiceManagement;

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

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_personalcenter);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new PersonalCenterPresenter(this);
        showLoadingDialog(getString(R.string.dataLoad));
        ((PersonalCenterContract.Presenter) mPresenter).getInfo();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.app_name), true, R.id.titlebar);
        titlebar.getTitleCtv().setTextColor(getResources().getColor(R.color.white));
        titlebar.setBackgroundResource(R.color.announcementCloseColors);
        titlebar.setLeftDrawable(R.mipmap.mine_back);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.ll_personalData:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(1);
                break;
            case R.id.ll_incompleteCertification:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(2);
                break;
            case R.id.ll_myOrder:
                ((PersonalCenterContract.Presenter) mPresenter).isLogin(3);
                break;
            case R.id.ll_addressManagement:
                //   ((PersonalCenterContract.Presenter) mPresenter).isLogin(4);
                getSuccess("", 4);
                break;
            case R.id.ll_driverManagement:
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
            UserInfoBean userInfoBean = (UserInfoBean) JsonUtil.getInstance().json2Obj(success, UserInfoBean.class);
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
            if (StringUtils.isEmpty(userInfoBean.getResult().getAvatar())) {
                img_headPortrait.setImageResource(R.mipmap.avatar);
            } else {
                GlideApp.with(this)
                        .load(userInfoBean.getResult().getAvatar() + "?imageView2/1/w/70/h/70")
                        .placeholder(R.mipmap.avatar)
                        .error(R.mipmap.arrow)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new GlideCircleTransform(this))
                        .dontAnimate()//没有任何淡入淡出效果
                        .into(img_headPortrait);
            }
            tv_name.setVisibility(View.VISIBLE);
            if (StringUtils.isEmpty(userInfoBean.getResult().getReal_name())) {
                tv_name.setText(userInfoBean.getResult().getPhone());
            } else {
                tv_name.setText(userInfoBean.getResult().getReal_name());
            }
            if (auth_status != null && auth_status.equals("pass")) {
                tv_incompleteCertification.setText(getString(R.string.certified));
            } else if (auth_status != null && auth_status.equals("check")) {
                tv_incompleteCertification.setText(getString(R.string.inAuthentication));
            } else if (auth_status != null && auth_status.equals("refuse")) {
                tv_incompleteCertification.setText(getString(R.string.refuse));
            } else {
                tv_incompleteCertification.setText(getString(R.string.incompleteCertification));
            }
        } else if (flag == 1) {
            showActivity(aty, PersonalDataActivity.class);
        } else if (flag == 2) {
            String auth_status = PreferenceHelper.readString(MyApplication.getContext(), StringConstants.FILENAME, "auth_status");
            if (auth_status != null && auth_status.equals("check")) {
                ViewInject.toast(getString(R.string.inAuthentication) + "," + getString(R.string.pleaseWait));
                return;
            }
            showActivity(aty, ShipperCertificationActivity.class);
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

    @Override
    public void errorMsg(String msg, int flag) {
        PreferenceHelper.write(this, StringConstants.FILENAME, "isAvatar", false);
        if (msg != null && msg.equals("" + NumericConstants.TOLINGIN)) {
            dismissLoadingDialog();
            tv_name.setText(getString(R.string.loginregister));
            img_headPortrait.setImageResource(R.mipmap.avatar);
            ll_incompleteCertification.setVisibility(View.GONE);
            if (flag != 0) {
                Intent intent = new Intent(aty, LoginActivity.class);
                intent.putExtra("type", "personalCenter");
                showActivity(aty, intent);
            }
            return;
        }
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
            showLoadingDialog(getString(R.string.dataLoad));
            ((PersonalCenterContract.Presenter) mPresenter).getInfo();
        }
//        else if (((String) msgEvent.getData()).equals("RxBusAvatarEvent")) {
////            img_headPortrait.setImageURI(Uri.parse(msgEvent.getMsg() + "?imageView2/1/w/70/h/70"));
//            GlideImageLoader.glideLoader(KJActivityStack.create().topActivity(), msgEvent.getMsg() + "?imageView2/1/w/70/h/70", img_headPortrait, 0);
//        }
    }
}
