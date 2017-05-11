package com.wechat.receiveEntity;

/**
 * Created by a07 on 2017/5/7.
 * 普通消息类父类
 */
public class RecEntity {
    //开发者微信号
    private String ToUserName;
    //发送方帐号（一个OpenID）
    private String FromUserName;
    //消息创建时间
    private String CreateTime;
    //text
    private String MsgType;
    //消息id
    private String MsgId;


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

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    @Override
    public String toString() {
        return "RecEntity{" +
                "ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", MsgType='" + MsgType + '\'' +
                ", MsgId='" + MsgId + '\'' +
                '}';
    }
}
