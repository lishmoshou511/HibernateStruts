package com.lish.action;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lishuang on 2014/6/2.
 */
public class LoginAction {
	@Getter @Setter
	private String message;

	@Getter @Setter
	private String userid;
	@Getter @Setter
	private String userpwd;
	public String index(){
		System.out.println("Message:"+this.message);

		return "login";
	}

	public String finish(){

		if("123".equals(userid)){
			this.message="登录成功!";
			return "loginok";
		}else{
			this.message="用户名或者密码错误";
			return "loginerror";
		}
	}

	public String logout(){
		this.message="你已经成功退出";
		return "message";
	}
}
