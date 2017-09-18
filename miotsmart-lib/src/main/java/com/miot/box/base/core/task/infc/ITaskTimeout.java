/*
 * 
 */
package com.miot.box.base.core.task.infc;


 // TODO: Auto-generated Javadoc

import com.miot.box.base.task.Task;

/**
  * 某一类型的Task存在超时过程时所需要执行的行为规范.
  *
  * @param <T> the generic type
  * @author Administrator
  */
public interface ITaskTimeout<T extends Task> {
	
	/**
	 * 发生timeout后的行为.
	 *
	 * @param subTask  当前发生timeout的任务
	 */
	public void doTimeout(T subTask);
	
	/**
	 * 规范了当前超时过程已失效时的操作.
	 *
	 * @param subTask 失效过程所影响的任务
	 */
	public void onInvalid(T subTask);
	
	/**
	 * 注销当前的超时回调.
	 */
	public void cancel();
	
	/**
	 * 当前时间是否已符合超时条件.
	 *
	 * @param curTime 当前时间,由时间暂存提供以提高性能,并不需要绝对校准当前时间.
	 * @param task 包含了所关注的任务.内部提供了{@code startTime}和{@code timeOut}两项用于判定的时间戳
	 * @return true, if is timeout
	 * true 已出发超时
	 * false 尚未触发超时
	 * 回调处于disable时恒返回false
	 */
	public boolean isTimeout(long curTime, T task);
	
	/**
	 * 返回当前时间距离超时触发
	 * 尚需等待的时间.
	 *
	 * @param curTime 当前时间,由时间暂存提供以提高性能,并不需要绝对校准当前时间.
	 * @param task 包含了所关注的任务.内部提供了{@code startTime}和{@code timeOut}两项用于判定的时间戳
	 * @return the long
	 * 还需等待多久将会触发超时
	 */
	public long toWait(long curTime, T task);
	
	/**
	 * 回调是否可用.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled();
}

