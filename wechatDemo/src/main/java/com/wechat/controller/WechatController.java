package com.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.entity.RecNormalMsg;
import com.wechat.util.WechatUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by a07 on 2017/5/7.
 * 流程控制类
 */
public class WechatController {
    /**
     * 获取微信端XML后返回处理后的XML
     *
     * @param recxml
     * @return
     */
    public static String flowControl(String recxml) {
        RecNormalMsg normalMsg = WechatUtil.parseXMLtoEntity(recxml);
        return WechatUtil.buildReturnXML(normalMsg, getRobot(normalMsg.getContent()));
    }

    /**
     * 调用图灵机器人接口实现自动回复
     *
     * @param msg
     * @return
     */
    private static String getRobot(String msg) {
        if (msg.indexOf(" ") > 0) {
            msg = msg.replace(" ", ",");
        }
        String url = "http://www.tuling123.com/openapi/api?key=a4500591896d4848a709cd5ab85dacf2&info=" + msg;
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
}
