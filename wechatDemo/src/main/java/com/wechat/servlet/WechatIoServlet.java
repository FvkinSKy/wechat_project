package com.wechat.servlet;

import com.wechat.controller.WechatController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by a07 on 2017/5/7.
 * 处理微信服务器消息（POST）
 */
@WebServlet("/io.do")
public class WechatIoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //设置编码格式
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=utf-8");

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
        System.out.println("content" + content);

        //组装返回数据
        String result = WechatController.flowControl(content);
        //返回xml到微信服务器
        OutputStream os = res.getOutputStream();
        os.write(result.getBytes("UTF-8"));
        os.flush();
        os.close();
    }
}
