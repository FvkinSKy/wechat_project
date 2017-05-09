package com.wechat.entity;

/**
 * Created by a07 on 2017/5/7.
 * 文本消息类
 */
public class RecNormalMsg extends RecEntity {
    //文本消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "RecNormalMsg{" +
                "Content='" + Content + '\'' +
                '}';
    }
}
