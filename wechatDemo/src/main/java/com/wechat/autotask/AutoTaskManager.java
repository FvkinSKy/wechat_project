package com.wechat.autotask;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by rui on 2017/5/9.
 * 自动任务管理类
 */
public class AutoTaskManager {
    private static SchedulerFactory sf = new StdSchedulerFactory();

    private static final String JOB_GROUP_NAME = "QUARTZ_JOBGROUP_NAME";

    private static final String TRIGER_GROUP_NAME = "QUARTZ_TRIGGERGROUP_NAME";

    /**
     * 添加自动任务
     */
    public static void addJob(String jobName, String triggerName, Class<? extends Job> jobClass, int hours) {
        try {
            //创建Scheduler对象
            Scheduler sch = sf.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGER_GROUP_NAME).startNow().withSchedule(
                    SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(hours).repeatForever()
            ).build();
            //添加job和trigger
            sch.scheduleJob(jobDetail, trigger);
            //启动
            sch.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    /**
     * 启动自动任务
     */
    public static void start() {
        try {
            //2小时执行一次，无限重复
            addJob("weChatTask", "trigger01", WechatAutoTask.class, 2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("自动任务启动失败！！");
        }
    }
}
