package com.wechat.revertEntity;

/**
 * Created by rui on 2017/5/11.
 * 消息回复类基类
 */
public class BaseEntity {
    //接收方帐号（收到的OpenID）
    private String ToUserName;
    //开发者微信号
    private String FromUserName;
    //消息创建时间
    private String CreateTime;
    //消息类型
    private String MsgType;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
