package com.lish.service.Imp;

import com.lish.domain.Message;
import com.lish.service.inter.MessageInterface;
import com.lish.util.MySessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by InnoXYZ on 14-6-3.
 */
public class MessageServiceImp extends BaseImp implements MessageInterface{

	@Override
	public List<Message> messages() {
		String hql="from Message";

		return MySessionFactory.executeQuery(hql,null);
	}
}
