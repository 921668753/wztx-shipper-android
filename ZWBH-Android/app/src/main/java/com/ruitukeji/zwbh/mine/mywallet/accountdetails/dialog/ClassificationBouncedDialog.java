package com.ruitukeji.zwbh.mine.mywallet.accountdetails.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.adapter.mine.mywallet.accountdetails.ClassificationViewAdapter;
import com.ruitukeji.zwbh.common.BaseDialog;

/**
 * 分类弹框
 * Created by Administrator on 2017/12/12.
 */

public class ClassificationBouncedDialog extends BaseDialog implements AdapterView.OnItemClickListener {

    private Context context;
    private ListView lv_classification;

    private ClassificationDialogCallBack callBack;//回调

    public ClassificationBouncedDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_classificationbounced);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        initView();
    }

    private void initView() {
        lv_classification = (ListView) findViewById(R.id.lv_classification);
        lv_classification.setOnItemClickListener(this);
        ClassificationViewAdapter classificationViewAdapter = new ClassificationViewAdapter(context);
        lv_classification.setAdapter(classificationViewAdapter);
    }

    public void setClassificationDialogCallBack(ClassificationDialogCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (callBack != null) {
            callBack.confirm();
        }
    }


    public interface ClassificationDialogCallBack {
        void confirm();
    }

}
