package com.wechat.revertEntity;

/**
 * Created by rui on 2017/5/11.
 * 回复文字消息类
 */
public class RevMessage extends BaseEntity {
    //回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
