package com.wechat.servlet;

import com.wechat.autotask.AutoTaskManager;
import com.wechat.controller.WechatController;
import com.wechat.util.WechatUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by a07 on 2017/5/7.
 * 处理微信服务器消息（POST）
 */
@WebServlet(urlPatterns = {"/io.do"}, loadOnStartup = 1)
public class WechatIoServlet extends HttpServlet {
    //令牌
    private static String myToken = "zrwechat001";

    /**
     * 验证微信服务器发送的数据
     * get请求参数说明:
     * signature     微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * timestamp    时间戳
     * nonce     	随机数
     * echostr  随机字符串
     * 原样返回echostr完成验证
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //设置编码格式
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        try {
            //获取请求参数
            String signature = req.getParameter("signature");
            String timestamp = req.getParameter("timestamp");
            String nonce = req.getParameter("nonce");
            String echostr = req.getParameter("echostr");

            System.out.println(req.getParameterMap());
            //校验
            boolean result = WechatUtil.checkConnection(signature, myToken, timestamp, nonce);
            if (result) {
                System.out.println("返回数据" + echostr);
                //验证成功，返回echostr
                OutputStream os = res.getOutputStream();
                os.write(echostr.getBytes("UTF-8"));
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //设置编码格式
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=utf-8");
        try {
            //接收数据
            InputStream is = req.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String message = "";
            StringBuffer sb = new StringBuffer();
            while ((message = br.readLine()) != null) {
                sb.append(message);
            }
            //接收到的xml
            String content = sb.toString();
            //解析为map
            Map<String, String> map = WechatUtil.parseXMLtoMap(content);
            String type = String.valueOf(map.get("MsgType"));
            String result = "";
            //事件和消息分开处理
            if (type.equals("event")) {//事件处理
                result = WechatController.eventControl(map);
            } else {//消息处理
                //组装消息返回数据
                result = WechatController.flowControl(map);
            }
            //返回xml到微信服务器
            OutputStream os = res.getOutputStream();
            os.write(result.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {
        System.out.println("初始化自动任务");
        //初始化启用自动任务
        AutoTaskManager.start();
    }
}
