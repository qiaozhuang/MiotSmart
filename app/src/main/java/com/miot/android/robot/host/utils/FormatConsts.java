package com.miot.android.robot.host.utils;

import com.jushang.smallzhi.BoxManager;

/**
 * Created by Administrator on 2016/11/9 0009.
 */
public class FormatConsts {

	public  final static int COMMAND_CONTROL_PORT=48883 ;//智能设备与小智通信端口号，可以自定义
	public  final static int TRAN_SERVER_PORT=48882 ;//智能设备与小智通信端口号，可以自定义
	public  final static int ROBOT_SERVER_PORT=37677 ;//向小智发送数据端口
	public  final static int VSP_SERVER_PORT=64536 ;//向小智发送数据端口
	public  final static int ROBOT_SEND_PORT=38889;//小智机器人监听端口
	public static final String FORMAL_SERVER_URL="dev.51miaomiao.cn";
	public static String FORMAL_SERVER_IP="www.51miaomiao.com";
	public final static String FORMAL_URL="www.51miaomiao.com";
	public static String ENDPOINT = "http://"
			+ FORMAL_SERVER_URL
			+ ":80/axis2/services/openBuzService";

	public static final String VSP_ACTION_SERVICE="com.miot.android.robot.vsp.SERVICE";
	public static final String HOST_ACTION_SERVICE="com.miot.android.robot.host.SERVICE";
	public static final String MIOT_DEVCEI_TYPE="WIFI";
	public static final String MIOT_DEVICE_TYPE_ZGB="FeiBiZigbee";




	public final static String NAMESPACE = "http://www.miotlink.org/openBuzService/";



	/**
	 * 小智机器人获取SN码
	 * @return
	 */
	public static String getSnCode(){
		String snCode= BoxManager.getInstance().getSnNumber();
		if (snCode.isEmpty()){
			return "";
		}
		return snCode;
	}
	/**
	 * 获取小智机器人的UDP广播编码
	 */
	public static final int REBOT_INIT=11000;

	public static String LOCALHOST_ADDRESS="";

	public static int LOCALPORT=0;

	public static String MAC="00:00:00:00:00:00";


}
