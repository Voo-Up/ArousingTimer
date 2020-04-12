package com.vup.arousingtimer;

import android.graphics.drawable.Drawable;

public class AnotherListDO {
    private Drawable icon;
    private String appName;
    private String comment;
    private String url;

    public AnotherListDO(Drawable icon, String appName, String comment, String url) {
        this.icon = icon;
        this.appName = appName;
        this.comment = comment;
        this.url = url;
    }

    public AnotherListDO(String appName, String comment, String url) {
        this.appName = appName;
        this.comment = comment;
        this.url = url;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public AnotherListDO(String comment, String url) {
        this.comment = comment;
        this.url = url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
