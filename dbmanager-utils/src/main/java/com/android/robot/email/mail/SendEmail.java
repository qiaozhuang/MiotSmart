package com.android.robot.email.mail;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class SendEmail {

	static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");

	public static void sendEmail(String txt)throws Exception{
		MultiMailsender.MultiMailSenderInfo mailInfo = new MultiMailsender.MultiMailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("15715760196@163.com");
		mailInfo.setPassword("!@qzbx1017");//您的邮箱密码
		mailInfo.setFromAddress("15715760196@163.com");
		mailInfo.setToAddress("qiaozhuang1989@qq.com");
		mailInfo.setSubject("小智机器人"+mSimpleDateFormat.format(new Date()));
		mailInfo.setContent(txt);
		MultiMailsender sms = new MultiMailsender();
		sms.sendTextMail(mailInfo);
	}
}
