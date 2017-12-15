package com.ruitukeji.zwbh.main;

import android.content.Intent;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.common.BaseActivity;
import com.ruitukeji.zwbh.common.BindView;
import com.ruitukeji.zwbh.common.ViewInject;
import com.ruitukeji.zwbh.constant.StringConstants;
import com.ruitukeji.zwbh.mine.setting.aboutus.AboutUsActivity;
import com.ruitukeji.zwbh.utils.ActivityTitleUtils;
import com.ruitukeji.zwbh.utils.MathUtil;

/**
 * 货物保险
 * Created by Administrator on 2017/2/23.
 */

public class CargoInsuranceActivity extends BaseActivity {

    @BindView(id = R.id.et_amountCover, click = true)
    private EditText et_amountCover;

    @BindView(id = R.id.tv_determine, click = true)
    private TextView tv_determine;

    @BindView(id = R.id.tv_cargoInsurance1, click = true)
    private TextView tv_cargoInsurance1;

    @BindView(id = R.id.tv_insuredAmount)
    private TextView tv_insuredAmount;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_cargoinsurance);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityTitleUtils.initToolbar(aty, getString(R.string.cargoInsurance), true, R.id.titlebar);
        String amountCover = getIntent().getStringExtra("amountCover");
        et_amountCover.setText(amountCover);
        if (et_amountCover.getText().toString().length() > 0) {
            String premium_rate = PreferenceHelper.readString(aty, StringConstants.FILENAME, "premium_rate");
            String insuredAmount = MathUtil.keepTwo(StringUtils.toDouble(et_amountCover.getText().toString()) * 100 / StringUtils.toDouble(premium_rate));
            tv_insuredAmount.setText(insuredAmount + getString(R.string.yuan));
        } else {
            tv_insuredAmount.setText("");
        }
        et_amountCover.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_amountCover.getText().toString().length() > 0) {
                    String premium_rate = PreferenceHelper.readString(aty, StringConstants.FILENAME, "premium_rate");
                    String insuredAmount = MathUtil.keepTwo(StringUtils.toDouble(et_amountCover.getText().toString()) * 100 / StringUtils.toDouble(premium_rate));
                    tv_insuredAmount.setText(insuredAmount + getString(R.string.yuan));
                } else {
                    tv_insuredAmount.setText("");
                }
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_cargoInsurance1:
                Intent intent1 = new Intent();
                intent1.setClass(aty, AboutUsActivity.class);
                intent1.putExtra("type", "shipper_insurance_instructions");
                showActivity(aty, intent1);
                break;
            case R.id.et_amountCover:
                et_amountCover.setEnabled(true);
                et_amountCover.setFocusable(true);
                et_amountCover.setFocusableInTouchMode(true);
                setSimulateClick(et_amountCover, 160, 40);
                break;
            case R.id.tv_determine:
                if (StringUtils.isEmpty(et_amountCover.getText().toString().trim())) {
                    ViewInject.toast("请输入投保金额");
                    return;
                }
                Intent intent = new Intent();
                // 获取内容
                intent.putExtra("amountCover", et_amountCover.getText().toString().trim());
                intent.putExtra("insuredAmount", tv_insuredAmount.getText().toString().trim().substring(0, tv_insuredAmount.getText().toString().trim().length() - 1));
                // 设置结果 结果码，一个数据
                setResult(RESULT_OK, intent);
                // 结束该activity 结束之后，前面的activity才可以处理结果
                aty.finish();
                break;
        }
    }

    /**
     * 模拟点击
     *
     * @param view
     * @param x
     * @param y
     */
    private void setSimulateClick(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }
}
