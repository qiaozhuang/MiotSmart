package com.miot.android.robot.host.db;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.miot.android.robot.host.entity.Pu;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class PuManager extends DataManager {

	private static PuManager instance=null;

	public static PuManager getInstance(Context context) {
		if (instance==null){
			synchronized (PuManager.class){
				if (instance==null){
					instance=new PuManager(context);
				}
			}
		}
		return instance;
	}

	public PuManager(Context context) {
		super(context);
	}
	public void savePu(Pu pu){
		try {
			dbUtils.saveOrUpdate(pu);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public Pu getPu(String id){
		try {
			return (Pu)dbUtils.findFirst(Selector.from(Pu.class).where("puId",
					"=", id));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	public  void saveAll(ArrayList<Pu> Pu){
		try {
			dbUtils.saveAll(Pu);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Pu> getPuList(){
		try {
			return (ArrayList<Pu>)dbUtils.findAll(Pu.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteAllPu(){
		try {
			dbUtils.deleteAll(Pu.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
