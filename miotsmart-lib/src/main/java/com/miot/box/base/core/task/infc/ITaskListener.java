/*
 * 
 */
package com.miot.box.base.core.task.infc;

import android.app.Activity;

import com.miot.box.base.task.TaskService;


// TODO: Auto-generated Javadoc

/**
 * 任务回调器.
 *
 * @author Administrator
 */
public interface ITaskListener {

    /**
     * 分发接口.
     *
     * @param message the message
     * @param activity the activity
     * @return true, if successful
     */
    public boolean ioHandle(ITaskResult message, Activity activity);

    /**
     * 根据服务分发.
     *
     * @param message the message
     * @param service the service
     * @return true, if successful
     */
    public boolean ioHandle(ITaskResult message, TaskService service);

    /**
     * 异常捕获.
     *
     * @param taskResult the task result
     * @param service the service
     * @return true, if successful
     */
    public boolean exceptionCaught(ITaskResult taskResult, TaskService service);

    /**
     * 是否可用.
     *
     * @return true, if is enable
     */
    public boolean isEnable();

    /**
     * 设置串号.
     *
     * @param bindSerial 绑定串号
     */
    public void setBindSerial(int bindSerial);

    /**
     * 获取绑定串号.
     *
     * @return the bind serial
     */
    public int getBindSerial();
}
