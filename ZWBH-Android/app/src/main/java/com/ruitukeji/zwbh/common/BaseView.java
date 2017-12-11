package com.ruitukeji.zwbh.common;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface BaseView<T> extends LoadingDialogView {

    void setPresenter(T presenter);
}