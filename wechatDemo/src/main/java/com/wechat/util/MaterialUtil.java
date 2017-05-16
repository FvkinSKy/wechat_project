package com.wechat.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechat.revertEntity.Articles;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by a07 on 2017/5/14.
 * 素材工具类
 */
public class MaterialUtil {
    //新增永久图文素材请求地址
    private static String addArticlesUrl = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";
    //获取永久素材请求地址
    private static String getMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";

    //access_token
    private static String ACCESS_TOKEN = "";

    /**
     * 图文素材上传
     *
     * @return json字符串
     */
    public static String uploadArticles(Articles articles) {
        if (articles == null) {
            return "";
        }
        String result = "";
        try {
            ACCESS_TOKEN = RedisConnUtil.getConn().get("access_token");
            //组装json
            JSONArray array = new JSONArray();
            array.add(articles);
            JSONObject object = new JSONObject();
            object.put("articles", array);
            //调用接口
            HttpPost httpPost = new HttpPost(addArticlesUrl + ACCESS_TOKEN);
            httpPost.setEntity(new StringEntity(URLEncoder.encode(object.toJSONString(), "UTF-8")));
            CloseableHttpResponse response = HttpClients.createDefault().execute(httpPost);
            result = EntityUtils.toString(response.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取永久素材列表
     *
     * @param access_token
     * @param type         素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     * @param offset       从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count        返回素材的数量，取值在1到20之间
     * @return json
     */
    public static String getMaterialList(String access_token, String type, String offset, int count) {
        String result = "";
        CloseableHttpResponse response = null;
        try {
            //组装请求参数
            JSONObject object = new JSONObject();
            object.put("type", type);
            object.put("offset", offset);
            object.put("count", count);
            //发送post请求
            HttpPost httpPost = new HttpPost(getMaterialUrl + access_token);
            httpPost.setEntity(new StringEntity(object.toJSONString()));
            //获取响应
            response = HttpClients.createDefault().execute(httpPost);
            result = EntityUtils.toString(response.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
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
        return result;
    }

}
