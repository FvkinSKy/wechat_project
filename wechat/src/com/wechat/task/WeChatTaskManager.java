package com.wechat.task;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

/**
 * Created by rui on 2017/5/5.
 */
public class WeChatTaskManager {
    private static SchedulerFactory factory = new StdSchedulerFactory();

    /**
     * 添加一个间隔自动任务
     *
     * @param
     */
    public static void addJob(String taskName, String jobClass, String taskId,
                              String jobDelayTime, String jobRepeatCount, String jobInterval) {
        try {
            Scheduler sch = factory.getScheduler();
            //设置启动时间
            long startTime = System.currentTimeMillis() + Long.parseLong(jobDelayTime);
            //间隔任务触发器
            //SimpleTrigger(String name, String group, Date startTime, Date endTime, int repeatCount, long repeatInterval)
            SimpleTrigger st = new SimpleTriggerImpl();

        } catch (Exception e) {
            throw new RuntimeException("间隔任务创建失败  " + e.getMessage());
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler sched = factory.getScheduler();
            sched.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
