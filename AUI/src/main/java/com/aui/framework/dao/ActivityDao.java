package com.aui.framework.dao;

import com.aui.dao.GenericDao;
import com.aui.framework.model.TBLActivity;

public interface ActivityDao extends GenericDao<TBLActivity>{

	void logActivity(TBLActivity tblActivity);
	
	TBLActivity retrieveActivityByUserName(String userName);
}
