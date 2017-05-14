package com.wechat.button;

/**
 * Created by rui on 2017/5/12.
 * 视图菜单类
 */
public class ViewButton extends BaseButton {
    //网页链接，用户点击菜单可打开链接
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
