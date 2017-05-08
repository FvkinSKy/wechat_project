package com.wechat.servlet;

import com.wechat.util.MenuUtil;
import com.wechat.util.WechatUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by rui on 2017/5/8.
 * 接入验证
 */
@WebServlet(urlPatterns = {"/check.do"}, loadOnStartup = 1)
public class WechatCheckServlet extends HttpServlet {
    //令牌
    public static String myToken = "111";

    /**
     * 验证微信服务器发送的数据
     * get请求参数说明:
     * signature     	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
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

                //创建自定义菜单
                boolean menuResult = MenuUtil.buildMenu(MenuUtil.createMenuJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
