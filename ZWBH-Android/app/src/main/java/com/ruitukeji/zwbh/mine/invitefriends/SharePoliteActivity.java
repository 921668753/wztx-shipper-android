package com.ruitukeji.zwbh.mine.invitefriends;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.mine.setting.aboutus.AboutUsActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.bingoogolapple.titlebar.BGATitleBar.SimpleDelegate;

/**
 * 分享有礼
 * Created by Administrator on 2017/2/10.
 */

public class SharePoliteActivity extends BaseActivity {

    @BindView(id = R.id.tv_recommendInviteCode)
    private TextView tv_recommendInviteCode;

    @BindView(id = R.id.tv_ruleDescription, click = true)
    private TextView tv_ruleDescription;

    @BindView(id = R.id.ll_weChatFriends, click = true)
    private LinearLayout ll_weChatFriends;

    @BindView(id = R.id.ll_circleFriends, click = true)
    private LinearLayout ll_circleFriends;

    @BindView(id = R.id.ll_QQFriends, click = true)
    private LinearLayout ll_QQFriends;

    @BindView(id = R.id.ll_Qzone, click = true)
    private LinearLayout ll_Qzone;

    @BindView(id = R.id.img_qrCodeLogo)
    private ImageView iv_qrCodeLogo;

    private AsyncTask<Void, Void, Bitmap> asyncTaskBitmap;
    private String recomm_code;
    private String share_shipper = null;
    private String share_shipper_description;
    private String share_shipper_title;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_sharepolite);
    }


    @Override
    public void initData() {
        super.initData();
        recomm_code = PreferenceHelper.readString(this, StringConstants.FILENAME, "recomm_code");
        share_shipper = PreferenceHelper.readString(this, StringConstants.FILENAME, "share_shipper");
        share_shipper_description = PreferenceHelper.readString(this, StringConstants.FILENAME, "share_shipper_description");
        share_shipper_title = PreferenceHelper.readString(this, StringConstants.FILENAME, "share_shipper_title");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        SimpleDelegate simpleDelegate = new SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                aty.finish();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();
                showActivity(aty, RecommendedRecordActivity.class);
            }
        };
        ActivityTitleUtils.initToolbar(aty, getString(R.string.sharePolite), getString(R.string.recommendedRecord), R.id.titlebar, simpleDelegate);
        tv_recommendInviteCode.setText(recomm_code);
        createQRCodeWithLogo();
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_ruleDescription:
                Intent intent = new Intent();
                intent.setClass(aty, AboutUsActivity.class);
                intent.putExtra("type", "shipper_recommend_reward");
                showActivity(aty, intent);
                break;
            case R.id.ll_weChatFriends:
                umShare(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.ll_circleFriends:
                umShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.ll_QQFriends:
                umShare(SHARE_MEDIA.QQ);
                break;
            case R.id.ll_Qzone:
                umShare(SHARE_MEDIA.QZONE);
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void createQRCodeWithLogo() {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
        asyncTaskBitmap = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                share_shipper = PreferenceHelper.readString(aty, StringConstants.FILENAME, "share_shipper");
                Bitmap logoBitmap = BitmapFactory.decodeResource(SharePoliteActivity.this.getResources(), R.mipmap.android_template);
                return QRCodeEncoder.syncEncodeQRCode(share_shipper + "?code=" + recomm_code, BGAQRCodeUtil.dp2px(SharePoliteActivity.this, 150), Color.BLACK, logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    iv_qrCodeLogo.setImageBitmap(bitmap);
                } else {
                    ViewInject.toast("生成带logo的二维码失败");
                }
            }
        }.execute();
    }

    /**
     * 直接分享
     * SHARE_MEDIA.QQ
     */
    public void umShare(SHARE_MEDIA platform) {
        UMImage thumb = new UMImage(this, R.mipmap.android_template);
        UMWeb web = new UMWeb(share_shipper + "?code=" + recomm_code);
        web.setTitle(share_shipper_title);//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(share_shipper_description);//描述
        new ShareAction(aty).setPlatform(platform)
//                .withText("hello")
//                .withMedia(thumb)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
            showLoadingDialog(getString(R.string.shareJumpLoad));
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            dismissLoadingDialog();
            Log.d("throw", "platform" + platform);
            ViewInject.toast(getString(R.string.shareSuccess));
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            dismissLoadingDialog();
//            if (t.getMessage().contains("没有安装应用")) {
//                ViewInject.toast(getString(R.string.shareError1));
//            } else {
            ViewInject.toast(getString(R.string.shareError));
            //   ViewInject.toast(t.getMessage());
            Log.d("throw", "throw:" + t.getMessage());
            //   }
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.d("throw", "throw:" + "onCancel");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        asyncTaskBitmap.cancel(true);
        asyncTaskBitmap = null;
        super.onDestroy();
    }
}
