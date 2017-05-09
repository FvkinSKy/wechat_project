package com.wechat.autotask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.entity.AccessTokenEntity;
import com.wechat.util.WechatUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by rui on 2017/5/9.
 * 自动任务类
 */
public class WechatAutoTask implements Job {

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
                String jsonString = JSON.toJSONString(accessToken);
                AccessTokenEntity entity = JSON.parseObject(jsonString, AccessTokenEntity.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            flag = false;
            System.out.println("自动任务结束！");
        }
    }
}
