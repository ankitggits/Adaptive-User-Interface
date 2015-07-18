package com.aui.dao;

import java.io.Serializable;

import org.hibernate.Session;

public interface GenericDao<T extends Serializable> {
	
	Session getSession();

}
