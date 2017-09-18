package com.miot.android.robot.host.entity;

/**
 * Created by Administrator on 2017/1/19 0019.
 */
public class Control {


	private String pu="";

	private String puTop="";

	private String replaceChar="";
	private String replaceNewCharField="";
	private String sendId="";
	private String replaceBytes="";

	public String getPu() {
		return pu;
	}

	public void setPu(String pu) {
		this.pu = pu;
	}

	public String getPuTop() {
		return puTop;
	}

	public void setPuTop(String puTop) {
		this.puTop = puTop;
	}

	public String getReplaceChar() {
		return replaceChar;
	}

	public void setReplaceChar(String replaceChar) {
		this.replaceChar = replaceChar;
	}

	public String getReplaceNewCharField() {
		return replaceNewCharField;
	}

	public void setReplaceNewCharField(String replaceNewCharField) {
		this.replaceNewCharField = replaceNewCharField;
	}

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	public String getReplaceBytes() {
		return replaceBytes;
	}

	public void setReplaceBytes(String replaceBytes) {
		this.replaceBytes = replaceBytes;
	}

	@Override
	public String toString() {
		return "Control{" +
				"pu='" + pu + '\'' +
				", puTop='" + puTop + '\'' +
				", replaceChar='" + replaceChar + '\'' +
				", replaceNewCharField='" + replaceNewCharField + '\'' +
				", sendId='" + sendId + '\'' +
				", replaceBytes='" + replaceBytes + '\'' +
				'}';
	}
}
