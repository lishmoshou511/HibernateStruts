package com.lish.service.inter;

import java.io.Serializable;

/**
 * Created by InnoXYZ on 14-6-3.
 */
public interface BaseInterface {
	//把一些通用的方法声明到该基础接口中
	public Object findById(Class clazz,Serializable id);
}
