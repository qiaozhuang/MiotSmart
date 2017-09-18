package com.miot.android.robot.host.db;


import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.miot.android.robot.host.entity.DeviceCommand;


/**
 * Created by Administrator on 2016/7/4 0004.
 */
public abstract class DataManager implements DbUtils.DbUpgradeListener {
	protected final Context context;

	/**
	 * 数据库管理器
	 */
	public final DbUtils dbUtils;

	public DataManager(Context context) {
		this.context = context;
		DbUtils.DaoConfig daoConfig = new DbUtils.DaoConfig(context);
		daoConfig.setDbVersion(11);
		daoConfig.setDbName("robot_device.db");
		daoConfig.setDbUpgradeListener(this);
		this.dbUtils = DbUtils.create(daoConfig);
		dbUtils.configDebug(true);
	}

	@Override
	public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
		try {
			if (oldVersion <11) {
					db.dropTable(DeviceCommand.class);
					db.createTableIfNotExist(DeviceCommand.class);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 删除所有的表数据
	 */
	public void deleteAllTable() {
		try {
			dbUtils.beginTransaction();
			dbUtils.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} finally {
			dbUtils.endTransaction();
		}
	}
}

