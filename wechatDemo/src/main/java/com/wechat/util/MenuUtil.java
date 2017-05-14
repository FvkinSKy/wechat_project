package com.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechat.button.ClickButton;
import com.wechat.button.ViewButton;
import com.wechat.receiveEntity.AccessTokenEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
            httpPost.setEntity(new StringEntity(menu,"UTF-8"));
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
     * 删除自定义菜单
     *
     * @return
     */
    public static boolean delMenu(AccessTokenEntity entity) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + entity.getAccess_token();
        try {
            URL urlL = new URL(url);
            //url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null
            URI uri=new URI(urlL.getProtocol(),urlL.getHost(),urlL.getPath(),urlL.getQuery(),null);
            HttpGet httpGet = new HttpGet(url);
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
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建菜单JSON
     *
     * @return
     */
    public static String buildButtonJson() {
        ClickButton clickButton = new ClickButton();
        clickButton.setName("单击按钮");
        clickButton.setType("click");
        clickButton.setKey("image");

        ViewButton viewButton = new ViewButton();
        viewButton.setName("跳转按钮");
        viewButton.setType("view");
        viewButton.setUrl("https://www.baidu.com/");

        JSONArray array = new JSONArray();
        array.add(clickButton);
        array.add(viewButton);

        JSONObject object = new JSONObject();
        object.put("button", array);
        return object.toJSONString();
    }

    public static void main(String[] args) {
        String json = buildButtonJson();
        System.out.println(json);
    }
}
