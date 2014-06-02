package com.lish.action;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lishuang on 2014/6/2.
 */
public class TestAction {
	@Getter @Setter
	private String message;
	public String index(){

		this.message="这里是我的测试页面，欢迎你！";
		System.out.println(this.getMessage());
		return "index";
	}
}
