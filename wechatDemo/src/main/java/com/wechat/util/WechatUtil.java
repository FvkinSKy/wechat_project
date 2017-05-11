package com.wechat.util;

import com.thoughtworks.xstream.XStream;
import com.wechat.receiveEntity.RecNormalMsg;
import com.wechat.receiveEntity.RecPicture;
import com.wechat.revertEntity.RevImage;
import com.wechat.revertEntity.RevMessage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by a07 on 2017/5/7.
 * 工具类
 */
public class WechatUtil {

    private static String grant_type = "client_credential";//获取access_token

    private static String appid = "wx1d6af81819d42b41";//第三方用户唯一凭证

    private static String secret = "54b7f3bd903cfcb539ee0a672a98075f";// 第三方用户唯一凭证密钥，即appsecret

    private static Map<String, String> entityMap = new HashMap<String, String>();

    public static XStream xStream = new XStream();

    static {
        entityMap.put("RecNormalMsg", "com.wechat.receiveEntity.RecNormalMsg");
        entityMap.put("RecPicture", "com.wechat.receiveEntity.RecPicture");
    }

    /**
     * 解析XML为对象
     *
     * @param xml 微信服务器xml数据
     * @return RecNormalMsg对象
     */
    public static Object parseXMLtoEntity(String xml) {
        String className = "";
        //判断消息类型
        if (xml.indexOf("text") > 0) {//文本消息
            className = entityMap.get("RecNormalMsg");
        }
        if (xml.indexOf("image") > 0) {
            className = entityMap.get("RecPicture");
        }
        RecNormalMsg normalMsg = null;
        try {
            //反射创建对象
            Class<?> c = Class.forName(className);
            normalMsg = (RecNormalMsg) c.newInstance();
            //string 转xml 对象
            Document doc = DocumentHelper.parseText(xml);
            //获取根节点
            Element root = doc.getRootElement();
            //遍历
            for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
                Element element = (Element) it.next();
                //获取set方法
                Method method = c.getMethod("set" + element.getName(), String.class);
                //调用set方法
                method.invoke(normalMsg, element.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("xml转RecNormalMsg对象失败》》" + e.getMessage());
        }
        System.out.println("content=" + normalMsg.getContent() + "**ToUserName=" + normalMsg.getToUserName() + "**FromUserName=" + normalMsg.getFromUserName()
                + "**CreateTime=" + normalMsg.getCreateTime() + "**MsgType=" + normalMsg.getMsgType() + "**MsgId=" + normalMsg.getMsgId());
        return normalMsg;
    }

    /**
     * XML转Map
     *
     * @param xml
     * @return
     */
    public static Map<String, String> parseXMLtoMap(String xml) {
        Map<String, String> map = new HashMap<>();
        Document doc = DocumentHelper.createDocument();
        Element element = doc.getRootElement();
        for (Iterator<?> it = element.elementIterator(); it.hasNext(); ) {
            Element e = (Element) it.next();
            map.put(e.getName(), e.getText());
        }
        return map;
    }


    /**
     * 文本消息类转为xml,用于回复
     *
     * @return
     */
    public static String parseMsgEntityToXml(RevMessage revMessage) {
        xStream.alias("xml", revMessage.getClass());
        return xStream.toXML(revMessage);
    }

    /**
     * 图片消息转XML,用于回复
     *
     * @param revImage
     * @return
     */
    public static String parsePicEntityToXml(RevImage revImage) {
        xStream.alias("xml", revImage.getClass());
        return xStream.toXML(revImage);
    }

    /**
     * 图片消息类赋值
     *
     * @param map
     * @return
     */
    public static RecPicture parseMapToPicEntity(Map<String, String> map) {
        RecPicture recNormalMsg = null;
        try {
            Class<?> c = Class.forName(entityMap.get("RecPicture"));
            recNormalMsg = (RecPicture) c.newInstance();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println("key=" + entry.getKey() + " : " + "value=" + entry.getValue());
                Method method = c.getMethod("set" + entry.getKey(), String.class);
                method.invoke(recNormalMsg, entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recNormalMsg;
    }

    /**
     * 文字消息类赋值
     *
     * @param map
     * @return
     */
    public static RecNormalMsg parseMapToMsgEntity(Map<String, String> map) {
        RecNormalMsg recNormalMsg = null;
        try {
            Class<?> c = Class.forName(entityMap.get("RecNormalMsg"));
            recNormalMsg = (RecNormalMsg) c.newInstance();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println("key=" + entry.getKey() + " : " + "value=" + entry.getValue());
                Method method = c.getMethod("set" + entry.getKey(), String.class);
                method.invoke(recNormalMsg, entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recNormalMsg;
    }

    /**
     * 封装返回微信服务器的文字消息xml数据
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
        sb.append(
                WechatUtil.buildCommonXML(normalMsg.getFromUserName(), normalMsg.getToUserName(), normalMsg.getMsgType()));
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
        String[] code = new String[]{token, timestamp, nonce};
        //字典序排序
        Arrays.sort(code);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < code.length; i++) {
            sb.append(code[i]);
        }
        String result = WechatUtil.encipherBySHA(sb.toString());
        if (result.equals(signature.toUpperCase())) {
            return true;
        }
        return false;
    }

    /**
     * SHA1加密
     *
     * @param code 待加密数据
     * @return 加密后数据
     */
    public static String encipherBySHA(String code) {
        String bysha = "";
        try {
            byte[] b = code.getBytes();
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] digest = sha.digest(b);
            bysha = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bysha;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    /**
     * 发送GET请求获取accessToken
     *
     * @return access_token
     * {"access_token":"ACCESS_TOKEN","expires_in":7200}
     * {"errcode":40013,"errmsg":"invalid appid"}
     */
    public static String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?";
        String param = "grant_type=" + grant_type + "&appid=" + appid + "&secret=" + secret + "";
        CloseableHttpResponse response = null;
        String access_token = "";
        try {
            //发送HTTPGET请求
            HttpGet httpGet = new HttpGet(url + param);
            response = HttpClients.createDefault().execute(httpGet);
            //获取返回值
            access_token = EntityUtils.toString(response.getEntity());
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
        return access_token;
    }
}
