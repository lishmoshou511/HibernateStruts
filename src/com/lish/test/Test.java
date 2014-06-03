package com.lish.test;

import com.lish.util.MySessionFactory;

/**
 * Created by InnoXYZ on 14-6-3.
 */
public class Test {
	public static void createTable(){
		MySessionFactory.openSession();
	}
}
