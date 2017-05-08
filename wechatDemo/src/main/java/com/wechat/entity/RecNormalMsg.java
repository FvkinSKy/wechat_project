package com.wechat.entity;

import javax.persistence.Entity;

/**
 * Created by a07 on 2017/5/7.
 * 文本消息类
 */
@Entity
public class RecNormalMsg extends RecEntity {
    //文本消息内容
    public String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "RecNormalMsg{" +
                "ToUserName='" + ToUserName + '\'' +
                ", Content='" + Content + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", MsgType='" + MsgType + '\'' +
                ", MsgId='" + MsgId + '\'' +
                '}';
    }
}
