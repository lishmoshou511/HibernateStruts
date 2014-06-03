package com.lish.service.Imp;

import com.lish.domain.User;
import com.lish.service.inter.UserInterface;
import com.lish.util.MySessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by InnoXYZ on 14-6-3.
 */

//这是一个业务层的类，完成对users domain对象的各种操作。
public class UserServiceImp extends BaseImp implements UserInterface{
	//验证用户是否合法
	public User checkUser(User user){
		String hql="from User where id=? and password=?";
		String parameters[]={user.getId()+"",user.getPassword()};
		List list= MySessionFactory.executeQuery(hql,parameters);
		if(list.size()==0){
			return null;
		}else{
			return (User)list.get(0);
		}

	}


}
