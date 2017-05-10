package com.wechat.autotask;

import com.alibaba.fastjson.JSONObject;
import com.wechat.entity.AccessTokenEntity;
import com.wechat.util.MenuUtil;
import com.wechat.util.WechatUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by rui on 2017/5/9.
 * 自动任务类
 */
public class WechatAutoTask implements Job {

    public static AccessTokenEntity entity = AccessTokenEntity.getInstance();

    private static Boolean flag = false;

    /**
     * 获取access_token
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (flag) {
            return;
        }
        flag = true;
        System.out.println("自动任务开始！");
        try {
            String accessToken = WechatUtil.getAccessToken();
            JSONObject object = JSONObject.parseObject(accessToken);
            if (object.containsKey("access_token")) {
                //保存到对象中
                entity = JSONObject.parseObject(object.toJSONString(), AccessTokenEntity.class);
                //调用接口创建菜单
                boolean result = MenuUtil.buildMenu(MenuUtil.buildButton(), entity);
                if (!result) {//失败则先删除菜单，等下一次自动任务再创建
                    MenuUtil.delMenu(entity);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            flag = false;
            System.out.println("自动任务结束！");
        }
    }
}
