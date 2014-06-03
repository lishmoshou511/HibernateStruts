package com.lish.util;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.Serializable;
import java.util.List;


/**
 * Created by lishuang on 2014/5/28.
 */

//在使用项目开发的时候一个数据库对应一个sessionFactory,一般而言，一个项目只有一个sessionFactory.
final public class MySessionFactory {
	private static final SessionFactory sessionFactory;
	private static final ServiceRegistry serviceRegistry;
	//禁止使用动态方法。
	private MySessionFactory(){}
	//线程局部变量模式
	private static final ThreadLocal<Session> threadLocal=new ThreadLocal<Session>();



	static {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();

			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}


	//获取一个全新的session.
	public static Session openSession() throws HibernateException {
		System.out.println("SessionFactory 类型："+sessionFactory);
		return sessionFactory.openSession();
	}

	//在session事务提交后会自动关闭。但是建议使用完毕后手动关闭。
	public static Session getCurrentSession() throws HibernateException {

		return sessionFactory.getCurrentSession();
	}

	//获取和线程关联的session
	public static Session getMyCurrentSession(){
		Session session=threadLocal.get();

		//判断是否得到
		if(session==null){
			session=sessionFactory.openSession();

			//把session对象设置到threadLocal,相当于该session对象已经和线程绑定。
			threadLocal.set(session);
		}

		return session;

	}
	public static List executeQuery(String hql,String[] params){
		//获取一个会话。
		Session session = MySessionFactory.getCurrentSession();
		Transaction transaction = null;
		List list=null;
		try {
			transaction = session.beginTransaction();

			Query query=session.createQuery(hql);
			//绑定参数
			if(params!=null && params.length>0){
				for(int i=0;i<params.length;i++){
					query.setString(i,params[i]);
				}
			}
			list=query.list();



			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException(e.getMessage());

		} finally {
			if (session != null && session.isOpen()) {

				session.close();
			}
		}
		return list;
	}
	public static List executeQueryByPage(String hql,String[] params,int pageSize,int pageNow){
		//获取一个会话。
		Session session = MySessionFactory.getCurrentSession();
		Transaction transaction = null;
		List list=null;
		try {
			transaction = session.beginTransaction();

			Query query=session.createQuery(hql);
			//绑定参数
			if(params!=null && params.length>0){
				for(int i=0;i<params.length;i++){
					query.setString(i,params[i]);
				}
			}
			list=query.setFirstResult((pageNow-1)*pageSize).setMaxResults(pageSize).list();



			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException(e.getMessage());

		} finally {
			if (session != null && session.isOpen()) {

				session.close();
			}
		}
		return list;
	}

	public static Object findById(Class clazz,Serializable id){
		//获取一个会话。
		Session session = MySessionFactory.getCurrentSession();
		Transaction transaction = null;
		Object obj=null;
		List list=null;
		try {
			transaction = session.beginTransaction();

			obj=session.load(clazz,id);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException(e.getMessage());

		} finally {
			if (session != null && session.isOpen()) {

				session.close();
			}
		}
		return obj;
	}







	/*****************以下是网上下载的工具类******************/
	/**
	 * 添加
	 *
	 * @param obj
	 */
	public void add(Object obj) {
		Session session = null;
		Transaction tx = null;
		try {
			session = MySessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.save(obj);
			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 删除
	 *
	 * @param obj
	 */
	public void delete(Object obj) {
		Session session = null;
		Transaction tx = null;
		try {
			session = MySessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据主键删除
	 *
	 * @param clazz
	 * @param id
	 */
	public void deleteById(Class clazz, Serializable id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = MySessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.delete(session.get(clazz, id));
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 修改
	 *
	 * @param obj
	 */
	public void update(Object obj) {
		Session session = null;
		Transaction tx = null;
		try {
			session = MySessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	/**
	 * 根据主键查询
	 *
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object get(Class clazz, Serializable id) {
		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession();
			Object obj = session.get(clazz, id);
			return obj;
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	/**
	 * 根据多个属性查询
	 *
	 * @param clazz
	 * @param strs
	 * @return
	 */
	public Object getByNProperty(Class clazz, String... strs) {

		if (strs != null && strs.length != 0 && 0 != strs.length % 2) {
			StringBuffer hql = new StringBuffer("select model from "
					+ clazz.getName() + " as model where ");
			for (int i = 0; i < strs.length; i += 2) {
				hql.append(" " + strs[i] + " = " + strs[i + 1]);
			}

			Session session = null;
			try {
				session = MySessionFactory.getCurrentSession();
				List<Object> objs = session.createQuery(hql.toString()).list();
				if (objs != null && objs.size() != 0) {
					return objs.get(0);
				} else {
					return null;
				}
			} finally {
				if (session != null) {
					session.close();
				}
			}
		} else {
			return null;
		}

	}

	/**
	 * 根据属性查询
	 *
	 * @param clazz
	 * @param pName
	 * @param pValue
	 * @return
	 */
	public Object getUniqueByProperty(Class clazz, String pName, Object pValue) {
		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession();
			String hql = "select model from " + clazz.getName()
					+ " as model where model." + pName + " = '" + pValue + "'";
			List<Object> objs = session.createQuery(hql).list();
			if (objs != null && objs.size() != 0) {
				return objs.get(0);
			} else {
				return null;
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据HQL查询
	 *
	 * @param hql
	 * @return
	 */
	public Object getUniqueByHql(String hql) {

		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession();
			List<Object> objs = session.createQuery(hql).list();
			if (objs != null && objs.size() != 0) {
				return objs.get(0);
			} else {
				return null;
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据SQL查询
	 *
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public Object getUniqueBySql(String sql, Class clazz) {

		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(sql).addEntity(clazz);
			List<Object> objs = query.list();
			if (objs != null && objs.size() != 0) {
				return objs.get(0);
			} else {
				return null;
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	// ////////////////////查询单个完毕////////////////

	/**
	 * 查询所有
	 */
	public List<Object> getList(Class clazz) {
		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession();
			String hql = "select model from " + clazz.getName() + " as model ";
			List list = session.createQuery(hql).list();
			return list;
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	/**
	 * 根据属性查询 全部
	 *
	 * @param clazz
	 * @param pName
	 * @param pValue
	 * @return
	 */
	public List<Object> getListByProperty(Class clazz, String pName,
	                                      Object pValue) {
		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession(); // 获得安全session
			String hql = "select model from " + clazz.getName()
					+ " as model where model." + pName + " = '" + pValue + "'";
			return session.createQuery(hql).list();

		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	/**
	 * 根据属性和条件查询 全部
	 *
	 * @param clazz
	 * @param pName
	 * @param pValue
	 * @param condition
	 * @return
	 */
	public List<Object> getListByProperty(Class clazz, String pName,
	                                      Object pValue, String condition) {
		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession();
			String hql = "select model from " + clazz.getName()
					+ " as model where model." + pName + " " + condition
					+ " '%" + pValue + "%'";
			List<Object> list = session.createQuery(hql).list();
			return list;
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	/**
	 * 根据多个属性模糊查询
	 *
	 * @param clazz
	 * @param strs
	 * @return
	 */
	public List getListByNProperty(Class clazz, String... strs) {

		if (strs != null && strs.length != 0 && 0 != strs.length % 2) {
			StringBuffer hql = new StringBuffer("select model from "
					+ clazz.getName() + " as model where ");
			for (int i = 0; i < strs.length; i += 2) {
				hql.append(" " + strs[i] + " Like  %" + strs[i + 1] + "% ");
			}

			Session session = null;
			try {
				session = MySessionFactory.getCurrentSession();
				List<Object> objs = session.createQuery(hql.toString()).list();
				return objs;
			} finally {
				if (session != null) {
					session.close();
				}
			}
		} else {
			return null;
		}

	}

	/**
	 * 根据SQL查询 全部
	 *
	 * @param hql
	 * @return
	 */
	public List<Object> getListByHql(String hql) {

		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession();
			List list = session.createQuery(hql).list();
			return list;

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据SQL查询全部
	 *
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public List getListBySql(String sql, Class clazz) {

		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(sql).addEntity(clazz);
			return query.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 分页查询全部
	 *
	 * @param calzz
	 * @param pageUtil
	 */
	public void getListByPage(Class calzz, PageUtil pageUtil) {
		String hql = "SELECT model from " + calzz.getName() + " as model";
		getListByPage(hql, pageUtil);
	}

	/**
	 * 根据SQL分页查询
	 *
	 * @param hql
	 * @param pageUtil
	 */
	public void getListByPage(String hql, PageUtil pageUtil) {
		if (null == hql) {
			return;
		}
		Session session = null;

		try {
			session = MySessionFactory.getCurrentSession();
			Query query = session.createQuery(hql);
			query.setFirstResult(pageUtil.getFirstRec());
			query.setMaxResults(pageUtil.getPageSize());
			pageUtil.setList(query.list());

			String queryString = "";
			if (hql.toUpperCase().indexOf("SELECT") != -1) {
				int i = query.getQueryString().toUpperCase().indexOf("FROM");
				queryString = "Select count(*) "
						+ hql.substring(i, hql.length());
			} else {
				queryString = "Select count(*) " + hql;
			}
			// 去掉ORDER BY 的部分
			int j = queryString.toUpperCase().lastIndexOf("ORDER");
			if (j != -1) {
				queryString = queryString.substring(0, j);
			}
			Query cquery = session.createQuery(queryString);
			cquery.setCacheable(true);
			int recTotal = ((Integer) cquery.iterate().next()).intValue();
			pageUtil.setRecTotal(recTotal);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
