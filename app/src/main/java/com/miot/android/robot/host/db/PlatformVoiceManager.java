package com.miot.android.robot.host.db;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.miot.android.robot.host.entity.PlatformVoice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11 0011.
 */
public class PlatformVoiceManager extends DataManager {
	public PlatformVoiceManager(Context context) {
		super(context);
	}
	private static PlatformVoiceManager instance=null;

	public static PlatformVoiceManager getInstance(Context context) {
		if (instance==null){
			synchronized (PlatformVoiceManager.class){
				if (instance==null){
					instance=new PlatformVoiceManager(context);
				}
			}
		}
		return instance;
	}

	public void saveAll(ArrayList<PlatformVoice> platformVoices){
		try {
			dbUtils.saveAll(platformVoices);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	public void saveOrUpdate(PlatformVoice platformVoice){
		try {
			dbUtils.saveOrUpdate(platformVoice);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public PlatformVoice getPlatformVoice(String  platformVoice){
		try {
			return (PlatformVoice)dbUtils.findFirst(Selector.from(PlatformVoice.class).where("code",
					"=", platformVoice));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
}
