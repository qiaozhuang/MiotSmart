package com.miot.android.robot.host.db;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.miot.android.robot.host.entity.Cu;
import com.miot.android.robot.host.entity.SnCode;

/**
 * Created by Administrator on 2017/1/24 0024.
 */
public class SnCodeManager extends DataManager {

	public SnCodeManager(Context context) {
		super(context);
	}

	private static SnCodeManager instance=null;

	public static SnCodeManager getInstance(Context context) {
		if (instance==null){
			synchronized (SnCodeManager.class){
				if (instance==null){
					instance=new SnCodeManager(context);
				}
			}
		}
		return instance;
	}

	public void save(SnCode snCode){
		try {
			dbUtils.saveOrUpdate(snCode);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	public  SnCode getSnCode(String sn){
		try {
			return dbUtils.findFirst(Selector.from(SnCode.class).where("sn",
					"=", sn));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveCu(Cu cu){
		try {
			dbUtils.saveOrUpdate(cu);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public Cu getCu(String sn ){
		try {
			return dbUtils.findFirst(Selector.from(Cu.class).where("sn",
					"=", sn));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
}
