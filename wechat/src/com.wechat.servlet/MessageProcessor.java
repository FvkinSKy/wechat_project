package com.wechat.servlet;

import com.wechat.util.WechatUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by rui on 2017/5/4.
 * 处理微信服务器消息
 */
public class MessageProcessor extends HttpServlet {

    public static WechatUtil util = new WechatUtil();

    /**
     * 接收微信服务器的get请求
     * get请求参数说明：
     * signature  微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * timestamp 时间戳
     * nonce  	随机数
     * echostr 随机字符串
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //获取请求参数
        String signature = req.getParameter("signature");//signature=token+timestamp+nonce SHA1
        String echostr = req.getParameter("echostr");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        //校验请求是否来自微信服务器
        boolean b = WechatUtil.checkSignature("", timestamp, nonce, signature);
        if (b) {//返回echostr，表示接入成功
            PrintWriter pw = res.getWriter();
            pw.write(echostr);
        } else {

        }

    }
}
