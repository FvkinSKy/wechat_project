package com.wechat.util;

import com.wechat.entity.RecNormalMsg;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by a07 on 2017/5/7.
 * 工具类
 */
public class WechatUtil {

    public static Map<String, String> entityMap = new HashMap<String, String>();

    static {
        entityMap.put("RecNormalMsg", "com.wechat.entity.RecNormalMsg");
    }

    /**
     * 解析XML为对象
     *
     * @param xml 微信服务器xml数据
     * @return RecNormalMsg对象
     */
    public static RecNormalMsg parseXMLtoEntity(String xml) {
        RecNormalMsg normalMsg = null;
        try {
            //反射创建对象
            Class<?> c = Class.forName(entityMap.get("RecNormalMsg"));
            normalMsg = (RecNormalMsg) c.newInstance();
            //string 转xml 对象
            Document doc = DocumentHelper.parseText(xml);
            //获取根节点
            Element root = doc.getRootElement();
            //遍历
            for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                Element element = (Element) it.next();
                //获取实体类属性
                Field field = c.getDeclaredField(element.getName());
                //获取set方法
                Method method = c.getDeclaredMethod("set" + field.getName(), field.getType());
                //调用set方法
                method.invoke(normalMsg, element.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("xml转RecNormalMsg对象失败》》" + e.getMessage());
        }
        return normalMsg;
    }

    /**
     * 封装返回微信服务器的xml数据
     * <xml>
     * <ToUserName><![CDATA[toUser]]></ToUserName>
     * <FromUserName><![CDATA[fromUser]]></FromUserName>
     * <CreateTime>12345678</CreateTime>
     * <MsgType><![CDATA[text]]></MsgType>
     * <Content><![CDATA[你好]]></Content>
     * </xml>
     *
     * @param normalMsg
     * @param message
     * @return
     */
    public static String buildReturnXML(RecNormalMsg normalMsg, String message) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        WechatUtil.buildCommonXML(normalMsg.getFromUserName(), normalMsg.getToUserName(), normalMsg.getMsgType());
        sb.append("<Content><![CDATA[").append(message).append("]]></Content>");
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * xml中的共有元素
     *
     * @return
     */
    public static String buildCommonXML(String toUserName, String fromUserName, String msgType) {
        StringBuffer sb = new StringBuffer();
        sb.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>")
                .append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>")
                .append("<CreateTime>").append(new Date().getTime()).append("</CreateTime>")
                .append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>");
        return sb.toString();
    }
}
