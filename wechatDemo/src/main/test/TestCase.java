import com.wechat.entity.RecNormalMsg;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * Created by a07 on 2017/5/7.
 */
public class TestCase {
    /**
     * 模拟微信服务器post请求
     */
    @Test
    public void test() throws UnsupportedEncodingException {
        String encode = URLEncoder.encode("今晚打老虎", "UTF-8");
        String send = "<xml>\n" +
                " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                " <CreateTime>1348831860</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content><![CDATA['" + encode + "']]></Content>\n" +
                " <MsgId>1234567890123456</MsgId>\n" +
                " </xml>";
        HttpPost post = new HttpPost("http://localhost:8080/wechatDemo/io.do");
        try {
            post.setEntity(new StringEntity(send));
            HttpResponse response = HttpClients.createDefault().execute(post);
            System.out.println(response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("success");
            }
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
            Class<?> c = Class.forName("com.wechat.entity.RecNormalMsg");
            normalMsg = (RecNormalMsg) c.newInstance();
            Field[] f = c.getDeclaredFields();
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
            Class<?> c = Class.forName("com.wechat.entity.RecNormalMsg");
            normalMsg = (RecNormalMsg) c.newInstance();
//            Method[] methods = c.getMethods();
//            for (int i = 0; i < methods.length; i++) {
//                Method m = methods[i];
//                System.out.println(m.getName());
//            }
            Field[] fields = c.getFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                System.out.println(f.getName());
                Method method = c.getMethod("set" + f.getName(), f.getType());
                System.out.println(method.getName());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}