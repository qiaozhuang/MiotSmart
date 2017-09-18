package com.android.robot.email.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator{
	String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
    
    public String getPassword() {
		return password;
	}
    public void setPassword(String password) {
		this.password = password;
	}
    
    public String getUserName() {
		return userName;
	}
    public void setUserName(String userName) {
		this.userName = userName;
	}
}
