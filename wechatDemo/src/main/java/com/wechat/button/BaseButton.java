package com.wechat.button;

/**
 * Created by rui on 2017/5/10.
 * 按钮基类
 */
public class BaseButton {
    //按钮名称
    private String name;
    //按钮类型
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
