package com.lish.action;

import com.lish.domain.Message;
import com.lish.service.Imp.MessageServiceImp;
import com.lish.service.inter.MessageInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by lishuang on 2014/6/3.
 */
public class ListAction {

	@Getter @Setter
	private List<Message> messages;

	public String list(){

		MessageInterface messageInterface=new MessageServiceImp();
		messages=messageInterface.messages();
		return "list";
	}




}
