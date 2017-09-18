package com.miot.android.robot.host.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/1/24 0024.
 */
@Table(name = "cu")
public class Cu {

	@Id
	private String sn="";

	private int id=0;

	private int  version=0;

	private long sysTime=0;

	private String userdate="";

	private boolean isSys=false;


	public void setSys(boolean sys) {
		isSys = sys;
	}

	public boolean isSys() {
		return isSys;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getSysTime() {
		return sysTime;
	}

	public void setSysTime(long sysTime) {
		this.sysTime = sysTime;
	}

	public String getUserdate() {
		return userdate;
	}

	public void setUserdate(String userdate) {
		this.userdate = userdate;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSn() {
		return sn;
	}
}
