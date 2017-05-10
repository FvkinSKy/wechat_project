package com.wechat.button;

/**
 * Created by rui on 2017/5/10.
 * 一级按钮
 */
public class MainButton extends BaseButton {
    //菜单key值,用于从素材库获取素材
    private String key;
    //view跳转的网页
    private String url;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
