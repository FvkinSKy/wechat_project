package com.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechat.button.MainButton;
import com.wechat.receiveEntity.AccessTokenEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by rui on 2017/5/8.
 * 自定义菜单工具
 * 发送POST请求到微信服务器，生成自定义菜单
 */
public class MenuUtil {

    /**
     * 创建自定义菜单
     *
     * @return
     */
    public static boolean buildMenu(String menu, AccessTokenEntity entity) {
        //获取access_token
        String access_token = entity.getAccess_token();
        //发送POST请求
        try {
            //创建HTTPPOST
            String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
            HttpPost httpPost = new HttpPost(url);
            //参数
            httpPost.setEntity(new StringEntity(menu));
            //获取响应
            CloseableHttpResponse response = HttpClients.createDefault().execute(httpPost);
            HttpEntity httpEntityentity = response.getEntity();
            String result = EntityUtils.toString(httpEntityentity);
            JSONObject jsonObject = JSON.parseObject(result);
            if (String.valueOf(jsonObject.get("errcode")).equals("0")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建自定义菜单失败!!!");
        }
    }

    /**
     * 创建菜单JSON
     *
     * @return
     */
    public static String createMenuJson() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"今日比赛\",\n" +
                "          \"key\":\"V1001_TODAY_MATCH\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"搜索\",\n" +
                "               \"url\":\"http://www.baidu.com/\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"click\",\n" +
                "               \"name\":\"赞一下我们\",\n" +
                "               \"key\":\"V1001_GOOD\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }");
        return sb.toString();
    }

    /**
     * 删除自定义菜单
     *
     * @return
     */
    public static boolean delMenu(AccessTokenEntity entity) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + entity.getAccess_token();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = HttpClients.createDefault().execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            JSONObject object = JSONObject.parseObject(result);
            if (String.valueOf(object.get("errcode")).equals("0")) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("删除自定义菜单失败!!!" + e.getMessage());
        }
        return false;
    }

    /**
     * 使用类创建菜单
     *
     * @return
     */
    public static String buildButton() {
        MainButton mainButton = new MainButton();
        mainButton.setName("今日比赛");
        mainButton.setType("click");
        mainButton.setKey("M1_TODAY_MATCH");

        MainButton mainButton2 = new MainButton();
        mainButton2.setName("MMR查询");
        mainButton2.setType("view");
        mainButton2.setUrl("https://www.baidu.com/");

        String main01 = JSONObject.toJSONString(mainButton);
        String main02 = JSONObject.toJSONString(mainButton2);
        JSONArray array = new JSONArray();
        array.add(main01);
        array.add(main02);
        JSONObject object = new JSONObject();
        object.put("button", array);
        return object.toJSONString();
    }

    public static void main(String[] args) {
        String a = buildButton();
        System.out.println(a);
    }
}
