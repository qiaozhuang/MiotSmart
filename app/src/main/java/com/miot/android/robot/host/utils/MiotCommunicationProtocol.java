package com.miot.android.robot.host.utils;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class MiotCommunicationProtocol {

	/**
	 * 协议的开头
	 */
	private static  final String START_QUERTY_UART="F2F2";


	/**
	 * 查询
	 */
	private static final String DEVICE_FUNCTION_CODE="F0";

	/**
	 * 更新数据状态
	 */
	private static final String DEVICE_UPDATE_DATA="02";

	/**
	 * 更新设备功能码
	 */
	private static final String DEVICE_UPDATE_REBOT="03";
	/**
	 * 更新数据成功
	 */
	public static final String DEVICE_RESULT_UPDATE_SUCCESS="01";
	/**
	 * 更新数据失败
	 */
	public static final String DEVICE_RESULT_UPDATE_FAIL="00";

	/**
	 * 下载成功
	 */
	public static final String DEVICE_UPDATE_REBOT_DOWNLOAD_SUCCESS="03";
	/**
	 * 下载失败
	 */
	public static final String DEVICE_UPDATE_REBOT_DOWNLOAD_FAIL="02";
	/**
	 * 下载中
	 */
	public static final String DEVICE_UPDATE_REBOT_DOWNLOAD_START="01";

	/**
	 * 结束位
	 */
	private static final String DEVICE_LASET_UART="xx7E";

	/**
	 * 回复查询数据命令
	 * @param version
	 * @param cuId
	 * @param systemTime
	 * @return
	 */
	public static String  getQueryHostInfo(int version,int cuId,long systemTime){
		String result="";
		result+=START_QUERTY_UART+DEVICE_FUNCTION_CODE+"10";
		result+=bytesToHexString(intToByteArray(version));
		result+=bytesToHexString(intToByteArray(cuId));
		result+=bytesToHexString(getBytes(systemTime));
		result+=DEVICE_LASET_UART;
		return rfc(result);
	}

	/**
	 * 更新数据协议
	 * @param state
	 * @return
	 */
	public static String getUPDATE_RebotInfo(String state){
		String result="";
		result+=START_QUERTY_UART+DEVICE_UPDATE_DATA+"01";
		result+=state;
		result+=DEVICE_LASET_UART;
		return rfc(result);
	}

	/**
	 * 更新设备协议
	 * @param state
	 * @return
	 */
	public static String getUPDATE_RebotDevice(String state){
		String result="";
		result+=START_QUERTY_UART+DEVICE_UPDATE_REBOT+"01";
		result+=state;
		result+=DEVICE_LASET_UART;
		return rfc(result);
	}

	/**
	 * long 转byte
	 * @param data
	 * @return
	 */
	public static byte[] getBytes(long data) {
		byte[] bytes = new byte[8];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data >> 8) & 0xff);
		bytes[2] = (byte) ((data >> 16) & 0xff);
		bytes[3] = (byte) ((data >> 24) & 0xff);
		bytes[4] = (byte) ((data >> 32) & 0xff);
		bytes[5] = (byte) ((data >> 40) & 0xff);
		bytes[6] = (byte) ((data >> 48) & 0xff);
		bytes[7] = (byte) ((data >> 56) & 0xff);
		return bytes;
	}
	/**
	 * byte 转16 进制
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder sb = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}

		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				sb.append(0);
			}
			sb.append(hv);
			sb.append(" ");
		}
		return sb.toString();
	}
	public static String bytesFeiBiToHexString(byte[] src) {

		StringBuilder sb = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		src=reverse(src);
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				sb.append(0);
			}
			sb.append(hv);
			sb.append(" ");
		}
		return sb.toString();
	}

	public static String decToHex(int dec) {
		String hex = "";
		while(dec != 0) {
			String h = Integer.toString(dec & 0xff, 16);
			if((h.length() & 0x01) == 1)
				h = '0' + h;
			hex = hex + h;
			dec = dec >> 8;
		}
		return hex;
	}

	public static byte[] reverse(byte[] myByte)
	{
		byte[] newByte = new byte[myByte.length];

		for (int i = 0; i < myByte.length; i++)
		{
			newByte[i] = myByte[myByte.length - 1 - i];
		}
		return newByte;
	}

	public static String bytesToBigHexString(byte[] src) {
		StringBuilder sb = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}

		for (int i = 0; i < src.length; i--) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				sb.append(0);
			}
			sb.append(hv);
			sb.append(" ");
		}
		return sb.toString();
	}
	/**
	 * int 转 byte
	 * @param a
	 * @return
	 */
	public static byte[] intToByteArray(int a) {
		return new byte[] {
				(byte) ((a >> 24) & 0xFF),
				(byte) ((a >> 16) & 0xFF),
				(byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF)
		};
	}

	public static byte[] zgbIntToByteArray(int value) {
		return new byte[] {
				(byte) ((value >> 24) & 0xFF),
				(byte) ((value >> 16) & 0xFF),
				(byte) ((value >> 8) & 0xFF),
				(byte) (value & 0xFF)
		};
	}

	//十六进制相加
	public static  String hexAdd(String a,String b){
		String re="";
		int shi=Integer.parseInt(a,16)+Integer.parseInt(b,16);
		String shiliu=Integer.toHexString(shi).toUpperCase();
		if(shiliu.length()<2)
		{
			re="0"+shiliu;
		}
		else if(shiliu.length()>2){
			re=shiliu.substring(shiliu.length()-2, shiliu.length());
		}
		else{
			re=shiliu;
		}
		return re;
	}


	//按2位分割字符串返回数组
	public static String[] splitStrs(String str){
		int m = str.length()/2;
		if (m * 2 < str.length()) {
			m++;
		}
		String[] strs = new String[m];
		int j = 0;
		for (int i = 0; i < str.length(); i++) {if (i % 2 == 0) {
			strs[j] = "" + str.charAt(i);
		} else {
			strs[j] = strs[j] + "" + str.charAt(i);
			j++;
		}
		}
		return strs;
	}

	// 重算校检码返回最终命令[只适合下发指令，进入指令校检以xx替代]
	public static String rfc(String oldCom) {
		String newCom = "00";
		oldCom = oldCom.replace(" ", "");
		String[] oldComArr = splitStrs(oldCom.replace("F2F2", "")
				.replace("xx7E", ""));
		for (int i = 0; i < oldComArr.length; i++) {
			newCom = hexAdd(newCom, oldComArr[i]);
		}
		String reStr = oldCom.replace("xx7E", "") + newCom + "7E";
		return reStr.toUpperCase();
	}


	public static String resultCallBack(String message){
		String result="";
		String [] strings=splitStrs(message);
		if (strings.length>3){
			result=strings[2];
		}
		return  result;
	}
	public static String updateCallBack(String message){
		String result="";
		String [] strings=splitStrs(message);
		if (strings.length>4){
			result=strings[3];
		}
		return  result;
	}
}
