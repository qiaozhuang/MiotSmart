package com.miot.android.robot.host.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class WriteLogUtils {

	static SimpleDateFormat mSimpleDateFormat = null;
	static StringBuffer stringBuffer=new StringBuffer();
	static String mTime="";
	public static void writeLog(String log){
		try {
		if (mSimpleDateFormat==null) {
			mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
			mTime = mSimpleDateFormat.format(new Date());
		}
		stringBuffer.append(log).append("\r\n");
			File miotlink = new File(Environment.getExternalStorageDirectory()
					+ "/"+"RobotLog") ;
			if( ! miotlink.exists())
				miotlink.mkdir() ;
		File mDirectory = new File(Environment.getExternalStorageDirectory()
				+ "/RobotLog");
		if (!mDirectory.exists())
			mDirectory.mkdir();
		FileOutputStream mFileOutputStream;
			mFileOutputStream = new FileOutputStream(mDirectory+ "/"+mTime+"_robot.txt");

		mFileOutputStream.write((stringBuffer.toString()).getBytes("UTF-8"));
		mFileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
