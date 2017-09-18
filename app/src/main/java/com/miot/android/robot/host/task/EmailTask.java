package com.miot.android.robot.host.task;

import android.os.AsyncTask;

import com.android.robot.email.mail.SendEmail;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class EmailTask extends AsyncTask<String,Void,String>{

	@Override
	protected String doInBackground(String... params) {
		try {
			SendEmail.sendEmail(params[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
