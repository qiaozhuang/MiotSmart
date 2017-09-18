/*
 * 
 */
package com.miot.box.base.core.task.infc;
 // TODO: Auto-generated Javadoc

 /**
  * 任务激活所定义的行为准则.
  *
  * @author Administrator
  */
public interface ITaskWakeTimer 
extends
IDisposable{
	
	/**
	 * 设置报警时间.
	 *
	 * @param absoluteTime the absolute time
	 * @return the i task wake timer
	 */
	public ITaskWakeTimer setAlarmTime(long absoluteTime);	
	
	/**
	 * 取消回调.
	 *
	 * @return the i task wake timer
	 */
	public ITaskWakeTimer cancel();	
	
	/**
	 * Wake up task.
	 */
	public void wakeUpTask();	
	
	/**
	 * Sets the task.
	 *
	 * @param myTask the new task
	 */
	public void setTask(ITask myTask);
}
