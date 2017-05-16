import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.wechat.receiveEntity.AccessTokenEntity;
import com.wechat.revertEntity.Articles;
import com.wechat.revertEntity.IncludeImage;
import com.wechat.revertEntity.RevImage;
import com.wechat.util.MaterialUtil;
import com.wechat.util.MenuUtil;
import com.wechat.util.RedisConnUtil;
import com.wechat.util.WechatUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by a07 on 2017/5/7.
 */
public class TestCase {
    public static AccessTokenEntity entity = AccessTokenEntity.getInstance();
    private static String grant_type = "client_credential";//获取access_token

    private static String appid = "wx440013db0de931d1";//第三方用户唯一凭证

    private static String secret = "ce57f62f00f58c1d9ad0f6610d168f14";// 第三方用户唯一凭证密钥，即appsecret

    /**
     * 模拟微信服务器post请求
     */
    @Test
    public void test() throws UnsupportedEncodingException {
        String encode = URLEncoder.encode("今晚打老虎", "UTF-8");
        System.out.println("encode=" + encode);
        String send = "<xml>\n" +
                " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                " <CreateTime>1348831860</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content>" + encode + "</Content>\n" +
                " <MsgId>1234567890123456</MsgId>\n" +
                " </xml>";
        HttpPost post = new HttpPost("http://123.207.15.204/wechatDemo/io.do");
        try {
            post.setEntity(new StringEntity(send));
            HttpResponse response = HttpClients.createDefault().execute(post);
            System.out.println(response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("success");
            }
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    @Test
//    public void test2() {
//        RecNormalMsg normalMsg = null;
//        try {
//            Class<?> c = Class.forName("com.wechat.receiveEntity.RecNormalMsg");
//            normalMsg = (RecNormalMsg) c.newInstance();
////            Field[] f = c.getDeclaredFields();
//            Field[] f = c.getFields();
//            for (int i = 0; i < f.length; i++) {
//                Field field = f[i];
//                System.out.println(field.getName());
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void test3() {
//        RecNormalMsg normalMsg = null;
//        try {
//            Class<?> c = Class.forName("com.wechat.receiveEntity.RecNormalMsg");
//            normalMsg = (RecNormalMsg) c.newInstance();
//            Field field[] = c.getDeclaredFields();
//            Field.setAccessible(field, true);
//            Field fields[] = c.getFields();
//            Field.setAccessible(fields, true);
//            for (int i = 0; i < fields.length; i++) {
//                System.out.println(fields[i].getName());
//            }
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void test4() {
        String check = WechatUtil.encipherBySHA("zrwechat001222333");
        System.out.println(check);
        boolean result = WechatUtil.checkConnection(check, "zrwechat001", "222", "333");
        System.out.println(result);
    }

    /**
     * 模拟微信服务器发送GET请求
     */
    @Test
    public void test5() {
        HttpGet httpGet = new HttpGet("http://localhost:9090/wechatDemo/check.do?signature=05e4ff54c7e1491ad3f6cf4e1584c925f6c4b1f0&timestamp=222&nonce=333&echostr=666");
        try {
            CloseableHttpResponse response = HttpClients.createDefault().execute(httpGet);
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test6() {
        String json = "{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200}";
        AccessTokenEntity entity = JSON.parseObject(json, AccessTokenEntity.class);
        System.out.println(entity);
    }

    @Test
    public void test7() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
//        RecNormalMsg normalMsg = null;
//        Class<?> c = Class.forName("com.wechat.receiveEntity.RecNormalMsg");

//        Method method[] = c.getMethods();
//        for (int i=0;i<method.length;i++){
//            System.out.println(method[i].getName());
//        }
//        String send = "<xml>\n" +
//                " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
//                " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
//                " <CreateTime>1348831860</CreateTime>\n" +
//                " <MsgType><![CDATA[text]]></MsgType>\n" +
//                " <Content><![CDATA[content]]></Content>\n" +
//                " <MsgId>1234567890123456</MsgId>\n" +
//                " </xml>";
//        WechatUtil.parseXMLtoEntity(send);
    }

    /**
     * 获取access_token
     */
    @Test
    public void test8() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?";
        String param = "grant_type=" + grant_type + "&appid=" + appid + "&secret=" + secret + "";
        HttpGet httpGet = new HttpGet(url + param);
        try {
            CloseableHttpResponse response = HttpClients.createDefault().execute(httpGet);
            System.out.println(EntityUtils.toString(response.getEntity()));
            JSONObject object = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
//            if (object.containsKey("access_token")) {
//                //保存到对象中
//                String jsonString = JSON.toJSONString(EntityUtils.toString(response.getEntity()));
////                AccessTokenEntity receiveEntity = JSON.parseObject(jsonString, AccessTokenEntity.class);
////                System.out.println(receiveEntity);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test9() {
        String a = "{\"access_token\":\"NG2HlMIQIZIplhoNGCmv_yU7zu5g59ZnZyyH6_s3311W636oXsvT-WtGEJARvsc51AVeFhV89fj5M1DpqAlkD645IHwx7CSgzyK1g7bs2ltkp2IbMHX6UGDlzofIkUo5MFJcAAABUY\",\"expires_in\":7200}\n";
//        JSONObject object = JSONObject.parseObject(a);
        JSONObject object = new JSONObject();
        object.put("access_token", "NG2HlMIQIZIplhoNGCmv_yU7zu5g59ZnZyyH6_s3311W636oXsvT-WtGEJARvsc51AVeFhV89fj5M1DpqAlkD645IHwx7CSgzyK1g7bs2ltkp2IbMHX6UGDlzofIkUo5MFJcAAABUY");
        object.put("expires_in", "7200");
        if (object.containsKey("access_token")) {
            //保存到对象中
            String jsonString = JSON.toJSONString(a);
            System.out.println(jsonString);
            entity = JSONObject.parseObject(object.toJSONString(), AccessTokenEntity.class);
//          TestPOJO receiveEntity = JSON.parseObject(jsonString, TestPOJO.class);
            System.out.println(entity);
        }
    }

    @Test
    public void test10() {
        String url = "http://www.tuling123.com/openapi/api?key=a4500591896d4848a709cd5ab85dacf2&info=" + "周杰伦的图片";
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
                System.out.println(revert);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test11() {
//        String send = "<xml>\n" +
//                " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
//                " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
//                " <CreateTime>1348831860</CreateTime>\n" +
//                " <MsgType><![CDATA[location]]></MsgType>\n" +
//                " <Content>测试</Content>\n" +
//                " <MsgId>1234567890123456</MsgId>\n" +
//                " </xml>";
//        System.out.println(send.indexOf("text"));
        System.out.println("com.wechat.receiveEntity.RecNormalMsg".substring(0, 19));


    }

    @Test
    public void test12() {
//        GenericType<Object> g = new GenericType<>();
        IncludeImage includeImage = new IncludeImage();
        includeImage.setMediaId("this is mediaid");
        RevImage revImage = new RevImage();
        revImage.setImage(includeImage);
        revImage.setFromUserName("zr");
        revImage.setToUserName("wechat");
        revImage.setCreateTime(String.valueOf(new Date().getTime()));
        revImage.setMsgType("image");
        XStream xStream = new XStream();
        xStream.alias("xml", revImage.getClass());
        xStream.alias("MediaId", includeImage.getClass());
        System.out.println(xStream.toXML(revImage));

    }

    @Test
    public void test13() {
        String send = "<xml>\n" +
                " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                " <CreateTime>1348831860</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content>双击666</Content>\n" +
                " <MsgId>1234567890123456</MsgId>\n" +
                " </xml>";
        Map<String, String> map = WechatUtil.parseXMLtoMap(send);
        System.out.println(map);
    }

    /**
     * redis
     */
    @Test
    public void test14() {
        //设置连接
        Jedis jedis = RedisConnUtil.getConn();
        System.out.println(jedis);
        jedis.set("bingo", "双击666");
        System.out.println(jedis.get("bingo"));
    }

    @Test
    public void test15() {
        Jedis jedis = RedisConnUtil.getConn();
        System.out.println(jedis.get("bingo"));
//        jedis.set("bingo","12345");
        System.out.println(jedis.get("bingo"));
    }


    @Test
    public void test16() {
        AccessTokenEntity entity = new AccessTokenEntity();
        entity.setAccess_token("5KPRx8qUAx3e4nFyJrluN1w9HN5-kuXtB6Kja-dtCko3QROd03J20r2-vZYD-UvZclAHXd1AxGQR_r68W3T9mIzukhnxP_FDgMJ-x1DADUXEjvSv7HpV8cOjE2AgZ0BHEIDgABAAEZ");
        entity.setExpires_in("7200");
        boolean result = MenuUtil.buildMenu(MenuUtil.buildButtonJson(), entity);
        System.out.println(result);
    }


    /**
     * 永久素材上传测试
     */
    @Test
    public void test17() {
        Articles articles = new Articles();
        articles.setTitle("图文消息测试");//标题
        articles.setDigest("包含图片和文字");//摘要
        articles.setAuthor("ZR");//作者
        articles.setThumb_media_id("H5nCy9pZlaCSjLQ1sMg0LSDFXfUg3iNicKuSbDHBTuI");//封面图片素材id
        articles.setShow_cover_pic("1");//是否显示封面
        articles.setContent("这是图文消息测试！对于常用的素材，开发者可通过本接口上传到微信服务器，永久使用。新增的永久素材也可以在公众平台官网素材管理模块中查询管理。");//正文
        articles.setContent_source_url("https://mp.weixin.qq.com/wiki");//原文地址
        String jsonString = MaterialUtil.uploadArticles(articles);
        System.out.println(jsonString);

    }

    /**
     * 上传图片素材
     */
    @Test
    public void test18() {
        String accessToken = WechatUtil.getAccessToken();
        JSONObject object = JSONObject.parseObject(accessToken);
        String token = object.getString("access_token");
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + token + "&type=image";
        HttpPost httpPost = new HttpPost(url);

    }

    @Test
    public void test19() {
        String send = "<xml>\n" +
                "<ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                "<FromUserName><![CDATA[FromUser]]></FromUserName>\n" +
                "<CreateTime>123456789</CreateTime>\n" +
                "<MsgType><![CDATA[event]]></MsgType>\n" +
                "<Event><![CDATA[CLICK]]></Event>\n" +
                "<EventKey><![CDATA[Today_Weather]]></EventKey>\n" +
                "</xml>";
        HttpPost post = new HttpPost("http://123.207.15.204/wechatDemo/io.do");
        try {
            post.setEntity(new StringEntity(send));
            HttpResponse response = HttpClients.createDefault().execute(post);
            System.out.println(response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("success");
            }
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test20() {
        Jedis jedis = RedisConnUtil.getConn();
        System.out.println(jedis.get("access_token"));
    }

    @Test
    public void test21() {
        JSONArray aa = new JSONArray();
        aa.add("");
        aa.add("OPENID1");
        aa.add("OPENID2");

        JSONObject ooo = new JSONObject();
        ooo.put("openid", aa);

        JSONObject oo = new JSONObject();
        oo.put("total", 2);
        oo.put("count", 2);
        oo.put("data", ooo);
        oo.put("next_openid", "NEXT_OPENID");

        List<String> list = new ArrayList<>();
        JSONObject object = JSONObject.parseObject(oo.toJSONString());
        System.out.println("===" + object);
        if (object.containsKey("data")) {
            String data = object.getString("data");
            JSONObject dataobj = JSONObject.parseObject(data);
            JSONArray array = JSONArray.parseArray(dataobj.getString("openid"));
            if (array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    list.add(String.valueOf(array.get(i)));
                }
            }
        }
        System.out.println(list);
    }
}