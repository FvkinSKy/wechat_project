package com.wechat.button;

/**
 * Created by a07 on 2017/5/14.
 * 下发消息按钮
 */
public class MediaButton extends BaseButton {
    //调用新增永久素材接口返回的合法media_id
    private String media_id;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
