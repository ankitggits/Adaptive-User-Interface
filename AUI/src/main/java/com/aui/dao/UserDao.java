package com.aui.dao;

import com.aui.model.TBLUser;

public interface UserDao extends GenericDao<TBLUser>{

	void doRegister(TBLUser tblUser);
	
	TBLUser getUserByUserName(String userName);
}
