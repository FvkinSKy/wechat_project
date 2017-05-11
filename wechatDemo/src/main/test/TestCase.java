import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.receiveEntity.AccessTokenEntity;
import com.wechat.receiveEntity.RecNormalMsg;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;

/**
 * Created by a07 on 2017/5/7.
 */
public class TestCase {
    public static AccessTokenEntity entity = AccessTokenEntity.getInstance();
    private static String grant_type = "client_credential";//获取access_token

    private static String appid = "wx1d6af81819d42b41";//第三方用户唯一凭证

    private static String secret = "54b7f3bd903cfcb539ee0a672a98075f";// 第三方用户唯一凭证密钥，即appsecret

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


    @Test
    public void test2() {
        RecNormalMsg normalMsg = null;
        try {
            Class<?> c = Class.forName("com.wechat.receiveEntity.RecNormalMsg");
            normalMsg = (RecNormalMsg) c.newInstance();
//            Field[] f = c.getDeclaredFields();
            Field[] f = c.getFields();
            for (int i = 0; i < f.length; i++) {
                Field field = f[i];
                System.out.println(field.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        RecNormalMsg normalMsg = null;
        try {
            Class<?> c = Class.forName("com.wechat.receiveEntity.RecNormalMsg");
            normalMsg = (RecNormalMsg) c.newInstance();
            Field field[] = c.getDeclaredFields();
            Field.setAccessible(field, true);
            Field fields[] = c.getFields();
            Field.setAccessible(fields, true);
            for (int i = 0; i < fields.length; i++) {
                System.out.println(fields[i].getName());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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
        RecNormalMsg normalMsg = null;
        Class<?> c = Class.forName("com.wechat.receiveEntity.RecNormalMsg");

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
//            TestPOJO receiveEntity = JSON.parseObject(jsonString, TestPOJO.class);
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
}
