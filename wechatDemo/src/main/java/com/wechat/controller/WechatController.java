package com.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.revertEntity.IncludeImage;
import com.wechat.revertEntity.RevImage;
import com.wechat.revertEntity.RevMessage;
import com.wechat.util.WechatUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by a07 on 2017/5/7.
 * 流程控制类
 */
public class WechatController {
    //文本消息
    private static String text_type = "text";
    //图片消息
    private static String image_type = "image";

    //回复文字消息类
    private static RevMessage revMessage = new RevMessage();
    //回复图片消息类
    private static RevImage revImage = new RevImage();
    //回复图片消息分类
    private static IncludeImage includeImage = new IncludeImage();


    /**
     * 获取微信端XML后返回处理后的XML
     *
     * @param recxml 微信端发送的XML
     * @return
     */
    public static String flowControl(String recxml) {
        Map<String, String> map = WechatUtil.parseXMLtoMap(recxml);
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String MsgType = map.get("MsgType");
        String Content = map.get("Content");

        String createTime = String.valueOf(new Date().getTime());//创建时间
        if (MsgType.equals(text_type)) {//回复文字消息
            revMessage.setToUserName(fromUserName);
            revMessage.setFromUserName(toUserName);
            revMessage.setCreateTime(createTime);
            revMessage.setMsgType(MsgType);
            revMessage.setContent(getRobot(Content));//调用图灵机器人接口
            return WechatUtil.parseMsgEntityToXml(revMessage);
        }
        if (MsgType.equals(image_type)) {//回复图片消息
            revImage.setToUserName(fromUserName);
            revImage.setFromUserName(toUserName);
            revImage.setCreateTime(createTime);
            revImage.setMsgType(MsgType);
            //TODO
            includeImage.setMediaId("");
            revImage.setImage(includeImage);
            return WechatUtil.parsePicEntityToXml(revImage, includeImage);
        }
        return "";
    }

    /**
     * 调用图灵机器人接口实现自动回复
     *
     * @param msg
     * @return
     */
    private static String getRobot(String msg) {
        String url = "http://www.tuling123.com/openapi/api?key=a4500591896d4848a709cd5ab85dacf2&info=" + msg.trim();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String revert = "";
        try {
            response = HttpClients.createDefault().execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject object = JSON.parseObject(result);
                revert = String.valueOf(object.get("text"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return revert;
    }

    public static void main(String[] args) {
        String send = "<xml>\n" +
                " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                " <CreateTime>1348831860</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content>双击666</Content>\n" +
                " <MsgId>1234567890123456</MsgId>\n" +
                " </xml>";
        flowControl(send);
    }
}
