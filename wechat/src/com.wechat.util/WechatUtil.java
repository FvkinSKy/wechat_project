package com.wechat.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by rui on 2017/5/5.
 * 相关工具类
 */
public class WechatUtil {

    @Resource(name = "jdbcTool")
    private JDBCTool jdbcTool;

    public static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static Logger logger = LogManager.getLogger(WechatUtil.class);


    /**
     * get请求参数获取工具类
     *
     * @return
     */
    public static Map<String, String> StringToMap(String param) {
        if (param == null || param.indexOf("&") == 0) {
            return null;
        }
        String[] params = param.split("&");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < params.length; i++) {
            String[] result = params[i].split("=");
            for (int j = 0; j < result.length; j++) {
                if ("signature".equals(result[j])) {
                    map.put("signature", result[j + 1]);
                }
                if ("timestamp".equals(result[j])) {
                    map.put("timestamp", result[j + 1]);
                }
                if ("nonce".equals(result[j])) {
                    map.put("nonce", result[j + 1]);
                }
                if ("echostr".equals(result[j])) {
                    map.put("echostr", result[j + 1]);
                }
            }
        }
        return map;
    }

    /**
     * SHA-1加密
     *
     * @param code
     * @return
     */
    public static String encodeBySHA(String code) {
        try {
            byte[] b = code.getBytes();
            MessageDigest sha = MessageDigest.getInstance("SHA");
            byte[] digest = sha.digest(b);
            return DigestUtils.sha1Hex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 校验signature
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String token, String timestamp, String nonce, String signature) {
        List<String> list = new ArrayList<>();
        list.add(token);
        list.add(timestamp);
        list.add(nonce);
        //字典序排序
        Collections.sort(list);
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i);
        }
        //sha1加密
        result = WechatUtil.encodeBySHA(result);
        if (result.equals(signature)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取access_token
     *
     * @param grant_type 获取access_token填写client_credential
     * @param appid      第三方用户唯一凭证
     * @param secret     第三方用户唯一凭证密钥，即appsecret
     * @return
     */
    public String getAccessToken(String grant_type, String appid, String secret) {
        logger.info("开始获取access_token!!!");
        String result = "";
        InputStream is = null;
        BufferedReader br = null;
        CloseableHttpResponse response = null;
        try {
            //向微信服务器发送get请求
            List<Map<String, Object>> list = jdbcTool.findAll("select * from weburl where Ukey='getAccessToken'");
            //获取URL
            String url = String.valueOf(list.get(0).get("url")).trim();
            //封装请求参数
            List<NameValuePair> paramList = new ArrayList<>();
            paramList.add(new BasicNameValuePair("grant_type", grant_type));
            paramList.add(new BasicNameValuePair("appid", appid));
            paramList.add(new BasicNameValuePair("secret", secret));
            String param = EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
            //创建GET请求
            HttpGet httpGet = new HttpGet(url + "?" + param);
            //发送GET请求
            response = httpClient.execute(httpGet);
            //获取响应
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                is = entity.getContent();
                br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                result = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("获取access_token失败>>>" + e.getMessage());
            throw new RuntimeException("获取access_token失败");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        Map<String, String> map = WechatUtil.StringToMap("signature=123456&timestamp=0000&nonce=6666&echostr=99999");
//        System.out.println(map);
//        boolean r = WechatUtil.checkSignature("111", "222", "333");
//        System.out.println(r);
    }
}
