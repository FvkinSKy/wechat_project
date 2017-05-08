package com.wechat.autotask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by a07 on 2017/5/8.
 * 自动任务管理类
 */
public class AutoTaskManager implements Job{

    /**
     * 定时获取access_token
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
