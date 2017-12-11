package com.ruitukeji.zwbh.mine.myorder.orderfragment;

import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.application.MyApplication;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.KJActivityStack;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.dialog.ShareBouncedDialog;
import com.ruitukeji.zwbh.entity.EvaluationShareBean;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.JsonUtil;
import com.ruitukeji.zwbh.utils.StringNewUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.Log;

/**
 * 评价分享
 * Created by Administrator on 2017/2/16.
 */

public class EvaluationShareActivity extends BaseActivity implements EvaluationShareContract.View {

    public ShareBouncedDialog shareBouncedDialog;

    @BindView(id = R.id.rb_serviceAttitude)
    private RatingBar rb_serviceAttitude;

    @BindView(id = R.id.rb_satisfaction)
    private RatingBar rb_satisfaction;


    @BindView(id = R.id.rb_deliveryTime)
    private RatingBar rb_deliveryTime;

    @BindView(id = R.id.et_note)
    private EditText et_note;

    @BindView(id = R.id.tv_customerEvaluation)
    private TextView tv_customerEvaluation;


    @BindView(id = R.id.tv_submit, click = true)
    private TextView tv_submit;

    private int order_id;
    private String status;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_evaluationshare);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new EvaluationSharePresenter(this);
        order_id = getIntent().getIntExtra("order_id", 0);
        status = getIntent().getStringExtra("status");
        if (status.equals("comment")) {
            tv_submit.setVisibility(View.GONE);
            showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
            ((EvaluationShareContract.Presenter) mPresenter).getEvaluationShare(order_id);
        }
    }


    @Override
    public void initWidget() {
        super.initWidget();
//        SimpleDelegate simpleDelegate = new SimpleDelegate() {
//            @Override
//            public void onClickLeftCtv() {
//                super.onClickLeftCtv();
//                aty.finish();
//            }
//
//            @Override
//            public void onClickRightCtv() {
//                super.onClickRightCtv();
//                if (statusOrder == 6) {
//                    ViewInject.toast(getString(R.string.comment1));
//                    return;
//                }
//                // showActivity(aty, RecommendedRecordActivity.class);
//                if (shareBouncedDialog == null) {
//                    shareBouncedDialog = new ShareBouncedDialog(getApplicationContext()) {
//                        @Override
//                        public void share(SHARE_MEDIA platform) {
//                            umShare(platform);
//                            shareBouncedDialog = null;
//                        }
//                    };
//                }
//                shareBouncedDialog.show();
//                shareBouncedDialog.setCanceledOnTouchOutside(false);
//            }
//        };
        // ActivityTitleUtils.initToolbar(aty, getString(R.string.evaluation), getString(R.string.share), R.id.titlebar, simpleDelegate);
        ActivityTitleUtils.initToolbar(aty, getString(R.string.evaluation), true, R.id.titlebar);
        rb_serviceAttitude.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rb_serviceAttitude.setRating(rating);
            }
        });
        rb_satisfaction.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rb_satisfaction.setRating(rating);
            }
        });
        rb_deliveryTime.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rb_deliveryTime.setRating(rating);
            }
        });
        //输入过滤器可以实现很多效果
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!StringNewUtils.isChinese(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        et_note.setFilters(new InputFilter[]{filter});
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_submit:
                showLoadingDialog(MyApplication.getContext().getString(R.string.submissionLoad));
                ((EvaluationShareContract.Presenter) mPresenter).postEvaluationShare(order_id, rb_deliveryTime.getRating(), rb_serviceAttitude.getRating(), rb_satisfaction.getRating(), et_note.getText().toString().trim());
                break;
        }
    }

    /**
     * 直接分享
     * SHARE_MEDIA.QQ
     */
    public void umShare(SHARE_MEDIA platform) {
        UMImage thumb = new UMImage(this, R.drawable.ic_launcher);
        UMWeb web = new UMWeb("");
        web.setTitle("This is music title");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("my description");//描述
        new ShareAction(aty).setPlatform(platform)
//                .withText("hello")
//                .withMedia(thumb)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();

//        new ShareAction(aty).withText("hello")
//                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
//                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
            showLoadingDialog(getString(R.string.dataLoad));
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            dismissLoadingDialog();
            Log.d("plat", "platform" + platform);
            ViewInject.toast(getString(R.string.shareSuccess));
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            dismissLoadingDialog();
            ViewInject.toast(getString(R.string.shareError));
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            dismissLoadingDialog();
            ViewInject.toast(getString(R.string.shareError));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shareBouncedDialog = null;
    }

    @Override
    public void setPresenter(EvaluationShareContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String s, int flag) {
        if (flag == 0) {
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrderDetailsActivity", true);
            String fragment = PreferenceHelper.readString(aty, StringConstants.FILENAME, "refreshOrderFragment", "AllOrderFragment");
            if (fragment.equals("EvaluationShareFragment")) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshEvaluationShare", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder1", true);
            } else if (fragment.equals("AllOrderFragment")) {
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshEvaluationShare1", true);
                PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshOrder", true);
            }
            ViewInject.toast(getString(R.string.commentSecc));
            KJActivityStack.create().finishActivity(OrderDetailsActivity.class);
            finish();
//            showLoadingDialog(MyApplication.getContext().getString(R.string.dataLoad));
//            ((EvaluationShareContract.Presenter) mPresenter).getEvaluationShare(order_id);
//            if (StringUtils.isEmpty(et_note.getText().toString().trim())) {
//                et_note.setVisibility(View.GONE);
//            }
//            et_note.setEnabled(false);
//            et_note.setFocusable(false);
//            et_note.setFocusableInTouchMode(false);
//            rb_deliveryTime.setIsIndicator(true);
//            rb_serviceAttitude.setIsIndicator(true);
//            rb_satisfaction.setIsIndicator(true);
//            tv_submit.setVisibility(View.GONE);
        } else if (flag == 1) {
            et_note.setEnabled(false);
            et_note.setFocusable(false);
            et_note.setFocusableInTouchMode(false);

            rb_deliveryTime.setIsIndicator(true);
            rb_serviceAttitude.setIsIndicator(true);
            rb_satisfaction.setIsIndicator(true);

            EvaluationShareBean evaluationShareBean = (EvaluationShareBean) JsonUtil.getInstance().json2Obj(s, EvaluationShareBean.class);
            rb_deliveryTime.setRating(evaluationShareBean.getResult().getLimit_ship());
            rb_serviceAttitude.setRating(evaluationShareBean.getResult().getAttitude());
            rb_satisfaction.setRating(evaluationShareBean.getResult().getSatisfaction());
            if (StringUtils.isEmpty(evaluationShareBean.getResult().getContent())) {
                tv_customerEvaluation.setVisibility(View.GONE);
                et_note.setVisibility(View.GONE);
            } else {
                tv_customerEvaluation.setVisibility(View.VISIBLE);
                et_note.setText(evaluationShareBean.getResult().getContent());
            }
            tv_submit.setVisibility(View.GONE);
            dismissLoadingDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void error(String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }
}
