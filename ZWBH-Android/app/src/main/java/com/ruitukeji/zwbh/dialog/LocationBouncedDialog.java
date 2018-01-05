package com.ruitukeji.zwbh.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruitukeji.zwbh.R;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 定位搜索
 * Created by Administrator on 2017/2/21.
 */

public class LocationBouncedDialog extends Dialog implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private boolean src;
    private Context context;
    private BGABanner mForegroundBanner;
    private TextView tv_guide_skip;
    private TextView btn_guide_enter;

    public LocationBouncedDialog(boolean src, Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.src = src;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (src) {
            setContentView(R.layout.activity_cuideview);
            mForegroundBanner = (BGABanner) findViewById(R.id.banner_guide_foreground);
            tv_guide_skip = (TextView) findViewById(R.id.tv_guide_skip);
            tv_guide_skip.setOnClickListener(this);
            btn_guide_enter = (TextView) findViewById(R.id.btn_guide_enter);
            btn_guide_enter.setOnClickListener(this);
            btn_guide_enter.setVisibility(View.GONE);
            setListener();
            // 设置数据源
            mForegroundBanner.setData(R.mipmap.guidepage, R.mipmap.guidepage1, R.mipmap.guidepage2);
        } else {
            setContentView(R.layout.dialog_locationbounced);
        }
        getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        getWindow().setWindowAnimations(0);
    }

    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_guide_skip:
                cancel();
                break;
            case R.id.btn_guide_enter:
                cancel();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position != 2) {
            btn_guide_enter.setVisibility(View.GONE);
            return;
        }
        btn_guide_enter.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface DialogOnKeyDownListener {

        void onKeyDownListener(int keyCode, KeyEvent event);
    }

    private DialogOnKeyDownListener dialogOnKeyDownListener;

    public void setDialogOnKeyDownListener(DialogOnKeyDownListener dialogOnKeyDownListener) {
        this.dialogOnKeyDownListener = dialogOnKeyDownListener;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (dialogOnKeyDownListener != null) {
            dialogOnKeyDownListener.onKeyDownListener(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}
