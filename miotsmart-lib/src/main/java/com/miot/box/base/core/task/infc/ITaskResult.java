/*
 * 
 */
package com.miot.box.base.core.task.infc;

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITaskResult.
 *
 * @ClassName: ITaskResult
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:32
 * I task result.
 */
public interface ITaskResult
extends
IDisposable{
	
	/**
	 * 任务编号 0x80000001-0xFFFFFFFF <br>
	 * 结果类型 0x00 - 0x7FFFFFFF <br>.
	 *
	 * @return the serial num
	 */
	public int getSerialNum();
	
	/**
	 * reset设为进入队列
	 * 此处需要 volatile变量来作为标记.
	 *
	 * @param reset   true 重置这一变量 false 为默认值将设定对象已进入notify队列
	 */
	public void setResponse(boolean reset);
	
	/**
	 * 当前存在队列中的状态.
	 *
	 * @return true 已进入响应队列
	 */
	public boolean isResponsed();
	
	/**
	 * Sets the listen serial.
	 *
	 * @param bindSerial 当前任务结果绑定的Listener SerialNum 0 将顺序提交到所有监听者
	 */
	public void setListenSerial(int bindSerial);
	
	/**
	 * 订阅此任务的Listener SerialNum  0 将顺序提交到所有监听者.
	 *
	 * @return the listen serial
	 */
	public int    getListenSerial();	
	
	/**
	 * 是否出错.
	 *
	 * @return true, if successful
	 */
	public boolean hasError();	
	
	/**
	 * 设置异常.
	 *
	 * @param ex the new error
	 */
	public void setError(Exception ex);	
	
	/**
	 * 获得异常信息.
	 *
	 * @return the error
	 */
	public Exception getError();	
	
	/**
	 * 设置属性列表.
	 *
	 * @param map the map
	 */
	public void setAttributes(Map<String, Object> map);	
	
	/**
	 * 设置单个属性.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void setAttribute(String key, Object value);	
	
	/**
	 * 获得属性.
	 *
	 * @param key the key
	 * @return the attribute
	 */
	public Object getAttribute(String key);
}
