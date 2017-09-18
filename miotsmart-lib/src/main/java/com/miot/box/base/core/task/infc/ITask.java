/*
 * 
 */
package com.miot.box.base.core.task.infc;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITask.
 *
 * @ClassName: ITask
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:38
 * I task.
 */
public interface ITask {
	
	/**
	 * 初始化任务.
	 */
	public void initTask();	
	
	/**
	 * 执行任务.
	 *
	 * @throws Exception the exception
	 */
	public void runTask() throws Exception;	
	
	/**
	 * 完成任务.
	 */
	public void finishTask();	
	
	/**
	 * 终端任务.
	 */
	public void interruptTask();	
	
	/**
	 * 激活任务.
	 */
	public void wakeUpTask();	
	
	/**
	 * 是否需要警示.
	 *
	 * @return true, if successful
	 */
	public boolean needAlarm();
}
