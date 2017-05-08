package com.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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
    public static boolean buildMenu(String menu) {
        //获取access_token
        JSONObject accessToken = WechatUtil.getAccessToken();
        if (accessToken.containsKey("access_token")) {
            //发送POST请求
            try {
                //创建HTTPPOST
                String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken.get("access_token");
                HttpPost httpPost = new HttpPost(url);
                //参数
                httpPost.setEntity(new StringEntity(menu));
                //获取响应
                CloseableHttpResponse response = HttpClients.createDefault().execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
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
        return false;
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
}
