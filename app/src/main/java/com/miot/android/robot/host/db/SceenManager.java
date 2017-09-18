package com.miot.android.robot.host.db;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.miot.android.robot.host.entity.Scene;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class SceenManager extends DataManager {
	public SceenManager(Context context) {
		super(context);
	}

	private static SceenManager instance=null;

	public static SceenManager getInstance(Context context) {
		if (instance==null){
			synchronized (SceenManager.class){
				if (instance==null){
					instance=new SceenManager(context);
				}
			}
		}
		return instance;
	}

	public  void saveSceen(Scene sceen){
		try {
			dbUtils.saveOrUpdate(sceen);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public  void saveAll(ArrayList<Scene> sceens){
		try {
			dbUtils.saveAll(sceens);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Scene>  getSceenList(){
		try {
			return (ArrayList<Scene>)dbUtils.findAll(Scene.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Scene  getSceen(String id){
		try {
			return (Scene)dbUtils.findFirst(Selector.from(Scene.class).where("id",
					"=", id));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void delAllSceen(){
		try {
			dbUtils.deleteAll(Scene.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
