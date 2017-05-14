package com.wechat.autotask;

import com.alibaba.fastjson.JSONObject;
import com.wechat.receiveEntity.AccessTokenEntity;
import com.wechat.util.MenuUtil;
import com.wechat.util.RedisConnUtil;
import com.wechat.util.WechatUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import redis.clients.jedis.Jedis;

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
                //保存到redis
                Jedis jedis = RedisConnUtil.getConn();
                jedis.set("access_token", object.getString("access_token"));
                jedis.close();
                //保存到对象中
                entity = JSONObject.parseObject(object.toJSONString(), AccessTokenEntity.class);
                System.out.println(entity);
                //删除菜单
                boolean result = MenuUtil.delMenu(entity);
                //菜单会随着access_token失效而消失，需要在失效前创建
                //创建菜单
                boolean buildresult = MenuUtil.buildMenu(MenuUtil.buildButtonJson(), entity);
                if (buildresult) {
                    System.out.println("菜单创建成功");
                }else {
                    System.out.println("菜单创建失败");
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
