package com.miot.android.robot.host.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.SqlInfoBuilder;
import com.lidroid.xutils.exception.DbException;
import com.miot.android.robot.host.entity.DeviceCommand;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class RobotDeviceCommandManager extends DataManager {
	public RobotDeviceCommandManager(Context context) {
		super(context);
	}
	public static RobotDeviceCommandManager instance=null;

	public static RobotDeviceCommandManager getInstance(Context context) {
		if (instance==null){
			synchronized (RobotDeviceCommandManager.class){
				if (instance==null){
					instance=new RobotDeviceCommandManager(context);
				}
			}
		}
		return instance;
	}


	public boolean insertAllPuModelAndDeleteBefore(ArrayList<DeviceCommand> entities) {
		if (entities == null || entities.size() == 0)
			return false;
		SQLiteDatabase database = dbUtils.getDatabase();
		try {
			database.beginTransaction();
			dbUtils.createTableIfNotExist(DeviceCommand.class);
			dbUtils.execNonQuery(SqlInfoBuilder.buildDeleteSqlInfo(dbUtils,
					DeviceCommand.class));
			Observable.from(entities).subscribe(new Action1<DeviceCommand>() {
				@Override
				public void call(DeviceCommand dbModel) {
					try {
						dbUtils.execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(dbUtils,
								dbModel, DeviceCommand.class));
					} catch (DbException e) {
						e.printStackTrace();
					}
				}
			});
			database.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
		} finally {
			database.endTransaction();
		}
		return false;
	}

	public DeviceCommand getDeviceCommad(String code){
		try {
			 return dbUtils.findFirst(Selector.from(DeviceCommand.class).where("code",
					"=", code));
		} catch (DbException e) {
			e.printStackTrace();
		}

		return null;
	}
}
