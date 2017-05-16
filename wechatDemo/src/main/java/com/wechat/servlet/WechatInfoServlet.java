package com.wechat.servlet;

import com.wechat.util.RedisConnUtil;
import com.wechat.util.WechatUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by rui on 2017/5/16.
 */
@WebServlet("*.form")
public class WechatInfoServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        if ("/open.form".equals(path)) {
            openPage(req, res);
        } else if ("/openBatch.form".equals(path)) {
            openBatchPage(req, res);
        } else if ("/batch.form".equals(path)) {
            batchSend(req, res);
        }
    }

    /**
     * 批量发送消息
     *
     * @param req
     * @param res
     */
    protected void batchSend(HttpServletRequest req, HttpServletResponse res) {
        Jedis jedis = null;
        try {
            jedis = RedisConnUtil.getConn();
            String access_token = jedis.get("access_token");
            String msg = req.getParameter("msg");
            //获取用户列表
            List<String> openIdList = WechatUtil.getOpenId(access_token);
            boolean result = WechatUtil.batchSendMessageByOpenId(access_token, "text", "", openIdList, "this is batch test!!!");
            if (result) {
                res.sendRedirect("open.form");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    /**
     * 打开页面
     *
     * @param req
     * @param res
     */
    protected void openPage(HttpServletRequest req, HttpServletResponse res) {
        try {
            Jedis jedis = RedisConnUtil.getConn();
            String access_token = jedis.get("access_token");
            List<String> list = WechatUtil.getOpenId(access_token);
            req.setAttribute("openIdList", list);
            //转发
            req.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(req, res);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开群发消息界面
     *
     * @param req
     * @param res
     */
    protected void openBatchPage(HttpServletRequest req, HttpServletResponse res) {
        try {
            req.getRequestDispatcher("WEB-INF/jsp/batchSend.jsp").forward(req, res);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
