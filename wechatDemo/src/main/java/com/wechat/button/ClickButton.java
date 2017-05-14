package com.wechat.button;

/**
 * Created by rui on 2017/5/12.
 * 单击按钮菜单类
 */
public class ClickButton extends BaseButton {
    //菜单KEY值，用于消息接口推送
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
