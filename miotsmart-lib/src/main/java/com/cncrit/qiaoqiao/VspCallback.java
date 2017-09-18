package com.cncrit.qiaoqiao;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public interface VspCallback {

	/**
	 *
	 * 设备登录返回数据包
	 * @param id
	 */
	public void loginRes(int type,int id,int sessionId,int domId);

	/**
	 * 设备登录失败
	 * @param code
	 */
	public void loginFail(int code);

	/**
	 * 设备登录平台心跳超时
	 */
	public void timeOut();

	/**
	 * 设备发送数据失败
	 */
	public void sendDataFail();

	/**
	 *监听vsp 返回数据包
	 * @param id
	 * @param mlcc
	 */
	public void receiverTTContent(int id,String mlcc);


	/**
	 * 设备登出
	 * @param id
	 */
	public void logout(int id);

}
