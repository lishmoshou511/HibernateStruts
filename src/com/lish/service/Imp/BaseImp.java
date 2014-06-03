package com.lish.service.Imp;

import com.lish.service.inter.BaseInterface;
import com.lish.util.MySessionFactory;

import java.io.Serializable;

/**
 * Created by InnoXYZ on 14-6-3.
 */
public abstract class BaseImp implements BaseInterface {
	@Override
	public Object findById(Class clazz, Serializable id) {
		return MySessionFactory.findById(clazz,id);
	}

	//根据id号删除。
}
