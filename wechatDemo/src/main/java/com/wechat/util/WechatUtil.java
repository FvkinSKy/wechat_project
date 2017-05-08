package com.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.entity.RecNormalMsg;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by a07 on 2017/5/7.
 * 工具类
 */
public class WechatUtil {

    public static String grant_type = "";//获取access_token填写client_credential

    public static String appid = "";//第三方用户唯一凭证

    public static String secret = "";// 第三方用户唯一凭证密钥，即appsecret

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
                Field field = c.getField(element.getName());
                field.setAccessible(true);
                field.set(normalMsg, element.getData());
////                //获取set方法
//                Method method = c.getMethod("set" + field.getName(), field.getType());
////                //调用set方法
//                method.invoke(normalMsg, element.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("xml转RecNormalMsg对象失败》》" + e.getMessage());
        }
        System.out.println(normalMsg);
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

    /**
     * 校验微信服务器连接
     *
     * @param signature 加密签名
     * @param token     令牌
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     */
    public static boolean checkConnection(String signature, String token, String timestamp, String nonce) {
        //字典序排序
        List<String> list = new ArrayList<String>();
        list.add(token);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i);
        }
        //加密
        result = WechatUtil.encipherBySHA(result);
        if (result.equals(signature)) {
            //标识该请求来源于微信
            return true;
        } else {
            return false;
        }
    }

    /**
     * SHA1加密
     *
     * @param code 待加密数据
     * @return 加密后数据
     */
    public static String encipherBySHA(String code) {
        try {
            byte[] b = code.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA");
            byte[] digest = sha.digest(b);
            return DigestUtils.sha1Hex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 发送GET请求获取accessToken
     *
     * @return access_token
     * {"access_token":"ACCESS_TOKEN","expires_in":7200}
     * {"errcode":40013,"errmsg":"invalid appid"}
     */
    public static JSONObject getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?";
        String param = "grant_type='" + grant_type + "'&appid='" + appid + "'&secret='" + secret + "'";
        CloseableHttpResponse response = null;
        String access_token = "";
        JSONObject object = null;
        try {
            //发送HTTPGET请求
            HttpGet httpGet = new HttpGet(url + param);
            response = HttpClients.createDefault().execute(httpGet);
            //获取返回值
            access_token = EntityUtils.toString(response.getEntity());
            object = JSON.parseObject(access_token);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}
