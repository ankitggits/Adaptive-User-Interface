package com.aui.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public abstract class GenericDaoImpl<T extends Serializable> implements GenericDao<T> {
	
	private Class<T> type;
	private SessionFactory sessionFactory;

	/**
	 * @return the type
	 */
	public Class<T> getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Class<T> type) {
		this.type = type;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}

