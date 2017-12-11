package com.ruitukeji.zwbh.entity;

/**
 * Created by Administrator on 2017/2/13.
 */

public class RefreshModel {
    public String title;
    public String detail;

    public RefreshModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public RefreshModel(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }
}
