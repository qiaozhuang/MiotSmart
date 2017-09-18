package com.miot.android.robot.host.db;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.miot.android.robot.host.entity.Model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class ModelManager extends DataManager {

	public ModelManager(Context context) {
		super(context);
	}
	private static ModelManager instance=null;

	/**
	 * 获取moldeId 实例
	 * @param context
	 * @return
	 */
	public static ModelManager getInstance(Context context) {
		if (instance==null){
			synchronized (ModelManager.class){
				if (instance==null){
					instance=new ModelManager(context);
				}
			}
		}
		return instance;
	}

	public  void saveAll(ArrayList<Model> models){
		try {
			dbUtils.saveAll(models);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	public  void saveOrUpdate(Model model){
		try {
			dbUtils.saveOrUpdate(model);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	public  ArrayList<Model> getModels(){
		try {
			return (ArrayList<Model>)dbUtils.findAll(Model.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	public  void saveOrUpdateAll(ArrayList<Model> models){
		try {
			dbUtils.saveOrUpdateAll(models);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public Model getModel(String modelId){
			try {
				return (Model)dbUtils.findFirst(Selector.from(Model.class).where("modelId",
						"=", modelId));
			} catch (DbException e) {
				e.printStackTrace();
			}
			return null;

	}
}
