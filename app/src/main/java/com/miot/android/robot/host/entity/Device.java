package com.miot.android.robot.host.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class Device {

	private ArrayList<Pu> pus;

	private String puTocken="";

	private ArrayList<Scene> sceens=null;

	private String sceenTocken="";

	public ArrayList<Pu> getPus() {
		return pus;
	}

	public void setPus(ArrayList<Pu> pus) {
		this.pus = pus;
	}

	public String getSceenTocken() {
		return sceenTocken;
	}

	public void setSceenTocken(String sceenTocken) {
		this.sceenTocken = sceenTocken;
	}

	public ArrayList<Scene> getSceens() {
		return sceens;
	}

	public void setSceens(ArrayList<Scene> sceens) {
		this.sceens = sceens;
	}

	public String getPuTocken() {
		return puTocken;
	}

	public void setPuTocken(String puTocken) {
		this.puTocken = puTocken;
	}
}
