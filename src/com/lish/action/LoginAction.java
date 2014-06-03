package com.lish.action;

import com.lish.domain.User;
import com.lish.service.Imp.UserServiceImp;
import com.lish.service.inter.UserInterface;
import com.opensymphony.xwork2.ActionContext;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by lishuang on 2014/6/2.
 */
public class LoginAction {
	@Getter @Setter
	private String message;

	@Getter @Setter
	private String id;
	@Getter @Setter
	private String password;
	public String index(){
		//Test.createTable();




		return "login";
	}

	public String finish(){

		//使用Service到数据库的验证
		UserInterface userInterface =new UserServiceImp();


		//构建一个User对象
		User user=new User();
		user.setId(Integer.parseInt(this.id));
		user.setPassword(this.password);

		user= userInterface.checkUser(user);


		if(user!=null){
			this.message="Login successfully!";

			ActionContext context=ActionContext.getContext();
			Map session=context.getSession();
			session.put("user",user);

			return "loginok";
		}else{
			this.message="userid or password error!";
			return "loginerror";
		}
	}

	public String logout(){
		this.message="你已经成功退出";
		return "message";
	}
}
